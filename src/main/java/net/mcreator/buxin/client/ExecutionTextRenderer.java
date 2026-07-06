package net.mcreator.buxin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
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
public class ExecutionTextRenderer {
    private static final Set<UUID> executionEntities = new HashSet<>();
    private static final String EXECUTION_TEXT = "[可处决]";

    public static void addExecutionEntity(Entity entity) {
        if (entity != null) {
            executionEntities.add(entity.getUUID());
        }
    }

    public static void removeExecutionEntity(Entity entity) {
        if (entity != null) {
            executionEntities.remove(entity.getUUID());
        }
    }

    public static void clearAll() {
        executionEntities.clear();
    }

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        try {
            if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) {
                return;
            }

            Minecraft mc = Minecraft.getInstance();
            if (mc.level == null || mc.player == null || executionEntities.isEmpty()) {
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

            for (UUID entityId : executionEntities) {
                // 正确的方法：通过UUID获取实体
                Entity entity = getEntityByUUID(mc, entityId);
                if (entity == null || !entity.isAlive()) {
                    continue;
                }

                // 计算文字位置（实体头上1格处）
                double x = entity.getX();
                double y = entity.getY() + entity.getBbHeight() + 0.85; // 头上1格
                double z = entity.getZ();

                // 相对于相机的位置
                Vec3 entityPos = new Vec3(x, y, z);
                Vec3 transformedPos = entityPos.subtract(cameraPos);

                // 开始渲染
                poseStack.pushPose();

                // 移动到实体位置
                poseStack.translate(transformedPos.x, transformedPos.y, transformedPos.z);

                // 让文字始终面向相机
                poseStack.mulPose(event.getCamera().rotation());

                // 设置文字大小
                float scale = 0.03f;
                poseStack.scale(-scale, -scale, scale);

                // 计算文字宽度以居中显示
                int textWidth = font.width(EXECUTION_TEXT);
                float textOffsetX = -textWidth / 2.0f;

                // 渲染文字
                /*
                font.drawInBatch(
                        EXECUTION_TEXT,
                        textOffsetX,
                        0,
                        0xFF0000, // 红色文字
                        false,
                        poseStack.last().pose(),
                        bufferSource,
                        true,
                        0,
                        15728880,
                        false
                );
*/
                poseStack.popPose();
            }

            bufferSource.endBatch();
            poseStack.popPose();
        } catch (Exception e){
            System.err.println(e);
        }
    }

    // 通过UUID获取实体的辅助方法
    private static Entity getEntityByUUID(Minecraft mc, UUID uuid) {
        // 遍历所有实体查找匹配的UUID
        if (mc.level != null) {
            for (Entity entity : mc.level.entitiesForRendering()) {
                if (entity.getUUID().equals(uuid)) {
                    return entity;
                }
            }
        }
        return null;
    }
}