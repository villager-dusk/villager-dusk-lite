
package net.mcreator.buxin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.mcreator.buxin.BuxinMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class FLTextRenderer {
    private static final Set<UUID> FLEntities = new HashSet<>();
    private static final String FL_TEXT = "(可俘虏)";
    
    public static void addFLEntity(Entity entity) {
        if (entity != null) {
            FLEntities.add(entity.getUUID());
        }
    }
    
    public static void removeFLEntity(Entity entity) {
        if (entity != null) {
            FLEntities.remove(entity.getUUID());
        }
    }
    
    public static void clearAll() {
        FLEntities.clear();
    }
    
    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        try {
            if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) {
                return;
            }

            Minecraft mc = Minecraft.getInstance();
            if (mc.level == null || mc.player == null || FLEntities.isEmpty()) {
                return;
            }

            if(mc.options.hideGui){
                return;
            }

            PoseStack poseStack = event.getPoseStack();
            Font font = mc.font;
            Vec3 cameraPos = event.getCamera().getPosition();
            MultiBufferSource.BufferSource bufferSource = mc.renderBuffers().bufferSource();

            poseStack.pushPose();

            for (UUID entityId : FLEntities) {
                Entity entity = getEntityByUUID(mc, entityId);
                if (entity == null || !entity.isAlive()) {
                    continue;
                }

                double x = entity.getX();
                double y = entity.getY() + entity.getBbHeight() + 1;
                double z = entity.getZ();

                Vec3 entityPos = new Vec3(x, y, z);
                Vec3 transformedPos = entityPos.subtract(cameraPos);

                poseStack.pushPose();

                poseStack.translate(transformedPos.x, transformedPos.y, transformedPos.z);

                poseStack.mulPose(event.getCamera().rotation());

                float scale = 0.03f;
                poseStack.scale(-scale, -scale, scale);

                int textWidth = font.width(FL_TEXT);
                float textOffsetX = -textWidth / 2.0f;

                // The actual rendering of the text can be done using font.drawInBatch
                // but the original code has it commented out, so we keep it that way.

                poseStack.popPose();
            }

            bufferSource.endBatch();
            poseStack.popPose();
        } catch (Exception e){
            BuxinMod.LOGGER.error(e);
        }
    }
    
    private static Entity getEntityByUUID(Minecraft mc, UUID uuid) {
        if(mc.level != null) {
            for (Entity entity : mc.level.entitiesForRendering()) {
                if (entity.getUUID().equals(uuid)) {
                    return entity;
                }
            }
        }
        return null;
    }
}
