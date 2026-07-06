package net.mcreator.buxin.client.logo;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class SimpleOverlay {
    private static final ResourceLocation LOGO = new ResourceLocation("buxin", "textures/gui/logo.png");
    private static final String VERSION = "Villager Dusk Lite";
    private static final String WEBSITE = "villager-dusk.github.io";
    private static final int X = 10, Y = 10;
    private static final int LOGO_W = 16, LOGO_H = 16;
    private static final float ALPHA = 0.6f;

    private static final String SCORE_TAG = "player_score";
    private static final int MAX_SCORE = 114514;

    private static int clientScore = 0;

    private static String lastPlayerKey = "";

    private static String getCurrentWorldName() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.hasSingleplayerServer() && mc.getSingleplayerServer() != null) {
            return mc.getSingleplayerServer().getWorldData().getLevelName();
        }
        return "default";
    }

    private static String getStorageKey(String uuid) {
        String worldName = getCurrentWorldName();
        worldName = worldName.replaceAll("[^a-zA-Z0-9._-]", "_");
        return worldName + ":" + uuid;
    }

    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.screen != null) return;
        if (mc.player == null) return;

        String currentKey = getStorageKey(mc.player.getUUID().toString());
        if (!lastPlayerKey.equals(currentKey)) {
            lastPlayerKey = currentKey;
            clientScore = loadScore(currentKey);
            if (clientScore > MAX_SCORE) {
                clientScore = MAX_SCORE;
                saveScore(currentKey, clientScore);
            }
        }

        GuiGraphics guiGraphics = event.getGuiGraphics();

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, ALPHA);

        guiGraphics.blit(LOGO, X, Y, 0, 0, LOGO_W, LOGO_H, LOGO_W, LOGO_H);

        int textX = X + LOGO_W + 5;
        int textY = Y + (LOGO_H / 3) - (mc.font.lineHeight / 3);
        int color = (int)(ALPHA * 255) << 24 | 0xFFFFFF;

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(textX, textY, 0);
        guiGraphics.pose().scale(0.9f, 0.9f, 1.0f);
        guiGraphics.drawString(mc.font, Component.literal(VERSION), 0, 0, color);
        guiGraphics.pose().popPose();

        float websiteY = textY + mc.font.lineHeight - 1.1f;
        float websiteScale = 0.6f;
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(textX, websiteY, 0);
        guiGraphics.pose().scale(websiteScale, websiteScale, 1.0f);
        guiGraphics.drawString(mc.font, Component.literal(WEBSITE), 0, 0, color);
        guiGraphics.pose().popPose();

        renderScore(mc, guiGraphics, clientScore);

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.disableBlend();
    }

    private static void renderScore(Minecraft mc, GuiGraphics guiGraphics, int score) {
        int screenHeight = mc.getWindow().getGuiScaledHeight();

        String label = Component.translatable("text.buxin.rank").getString();
        String scoreStr = String.valueOf(score);
        int labelWidth = mc.font.width(label);

        int baseX = 10;
        int baseY = screenHeight - 30;

        float labelScale = 0.7f;
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(baseX, baseY, 0);
        guiGraphics.pose().scale(labelScale, labelScale, 1.0f);
        guiGraphics.drawString(mc.font, Component.literal(label), 0, 0, 0xFFFFFF);
        guiGraphics.pose().popPose();

        float scoreScale = 1.2f;
        int length = scoreStr.length();
        if (length >= 7) scoreScale = 0.7f;
        else if (length >= 6) scoreScale = 0.85f;
        else if (length >= 5) scoreScale = 1.0f;

        int labelScaledWidth = (int)(labelWidth * labelScale);
        int scoreX = baseX + labelScaledWidth;
        int scoreY = baseY - (int)(mc.font.lineHeight * (scoreScale - labelScale) / 2);

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(scoreX, scoreY, 0);
        guiGraphics.pose().scale(scoreScale, scoreScale, 1.0f);
        guiGraphics.drawString(mc.font, Component.literal(scoreStr), 0, 0, 0xFFAA00);
        guiGraphics.pose().popPose();
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        if (event.getSource().getEntity() instanceof Player player) {
            if (player.getUUID().equals(mc.player.getUUID())) {
                int addScore;
                var entity = event.getEntity();
                if (entity.getMaxHealth() > 20) {
                    addScore = 2 + (int)(Math.random() * 2);
                } else {
                    addScore = 1;
                }

                int newScore = clientScore + addScore;

                if (newScore > MAX_SCORE) {
                    newScore = MAX_SCORE;
                }

                clientScore = newScore;

                String key = getStorageKey(mc.player.getUUID().toString());
                saveScore(key, clientScore);
            }
        }
    }

    private static void saveScore(String key, int score) {
        try {
            java.nio.file.Path path = java.nio.file.Paths.get("config", "buxin_scores.dat");
            java.nio.file.Files.createDirectories(path.getParent());

            java.util.Properties props = new java.util.Properties();
            if (java.nio.file.Files.exists(path)) {
                try (java.io.InputStream in = java.nio.file.Files.newInputStream(path)) {
                    props.load(in);
                }
            }
            props.setProperty(key, String.valueOf(score));
            try (java.io.OutputStream out = java.nio.file.Files.newOutputStream(path)) {
                props.store(out, "Player Scores");
            }
        } catch (Exception e) {
            System.err.println("Failed: " + e.getMessage());
        }
    }

    private static int loadScore(String key) {
        try {
            java.nio.file.Path path = java.nio.file.Paths.get("config", "buxin_scores.dat");
            if (!java.nio.file.Files.exists(path)) return 0;

            java.util.Properties props = new java.util.Properties();
            try (java.io.InputStream in = java.nio.file.Files.newInputStream(path)) {
                props.load(in);
            }
            String val = props.getProperty(key);
            return val != null ? Integer.parseInt(val) : 0;
        } catch (Exception e) {
            return 0;
        }
    }
}