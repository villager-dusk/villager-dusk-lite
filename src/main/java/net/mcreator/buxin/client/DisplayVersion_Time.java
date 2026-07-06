package net.mcreator.buxin.client;

import net.mcreator.buxin.config.client.ClientGuiConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.api.distmarker.Dist;

import java.util.Calendar;

@Mod.EventBusSubscriber(modid = "buxin", value = Dist.CLIENT)
public class DisplayVersion_Time {
    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGuiEvent.Post event) {
        if(ClientGuiConfig.EnableTimeGui.get()) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null || !mc.player.getActiveEffects().isEmpty()) return;

            GuiGraphics guiGraphics = event.getGuiGraphics();
            int width = mc.getWindow().getGuiScaledWidth();

            int y = 5;

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            int second = calendar.get(Calendar.SECOND);
            String text2 = "§e" + year + "/" + month + "/" + day + "/" + hour + ":" + minute + ":" + second;
          //  String text3 = "§c" + Component.translatable("chat.buxin.killed_entities").getString() + " : " + mc.player.getStats().getValue(Stats.MOB_KILLED);
            int y2 = 15;
            int y3 = 25;
        }
    }
}