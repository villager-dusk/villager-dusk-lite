
package net.mcreator.buxin.client.arrow_trail;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent.Stage;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class TrailRenderer {
    private static final Map<Integer, TrailSession> activeTrails = new ConcurrentHashMap();
    private static final int MAX_HISTORY_SIZE = 20;
    private static final float TRAIL_BASE_WIDTH = 0.15F;
    private static final float START_OFFSET = 0.5F;
    private static final float DECAY_RATE = 0.2F;
    private static final float MAX_OPACITY = 0.7F;
    private static final float TAIL_SHRINK_RATIO = 0.6F;

    public TrailRenderer() {
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == Phase.END && Minecraft.getInstance().level != null) {
            Minecraft mc = Minecraft.getInstance();
            Iterator<Map.Entry<Integer, TrailSession>> iterator = activeTrails.entrySet().iterator();

            while(iterator.hasNext()) {
                Map.Entry<Integer, TrailSession> entry = (Map.Entry)iterator.next();
                int entityId = (Integer)entry.getKey();
                TrailSession session = (TrailSession)entry.getValue();
                Entity entity = mc.level.getEntity(entityId);
                boolean isMoving = entity != null && entity.isAlive() && entity.getDeltaMovement().lengthSqr() > 1.0E-4;
                if (isMoving) {
                    session.isFading = false;
                    session.decayFactor = 1.0F;
                    session.positionHistory.addFirst(entity.position());
                    if (session.positionHistory.size() > 20) {
                        session.positionHistory.pollLast();
                    }
                } else {
                    session.isFading = true;
                    session.decayFactor -= 0.2F;
                    if (session.decayFactor <= 0.0F) {
                        iterator.remove();
                    }
                }
            }

            AABB worldBounds = new AABB(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
            for (Entity entity : mc.level.getEntitiesOfClass(AbstractArrow.class, worldBounds)) {
                AbstractArrow arrow = (AbstractArrow)entity;
                if (!activeTrails.containsKey(arrow.getId()) && arrow.getDeltaMovement().lengthSqr() > 1.0E-4) {
                    TrailSession newSession = new TrailSession();
                    Vec3 currentPos = arrow.position();
                    Vec3 previousPos = currentPos.subtract(arrow.getDeltaMovement());
                    newSession.positionHistory.add(currentPos);
                    newSession.positionHistory.add(previousPos);
                    activeTrails.put(arrow.getId(), newSession);
                }
            }
        }
    }

    @SubscribeEvent
    public void onRenderWorld(RenderLevelStageEvent event) {
        if (event.getStage() == Stage.AFTER_TRANSLUCENT_BLOCKS) {
            Minecraft mc = Minecraft.getInstance();
            PoseStack poseStack = event.getPoseStack();
            Vec3 cameraPos = event.getCamera().getPosition();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableCull();
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder buffer = tesselator.getBuilder();
            org.joml.Matrix4f matrix = poseStack.last().pose();
            Iterator var8 = activeTrails.entrySet().iterator();

            while(true) {
                Map.Entry entry;
                TrailSession session;
                ArrayList points;
                do {
                    if (!var8.hasNext()) {
                        RenderSystem.enableCull();
                        RenderSystem.disableBlend();
                        return;
                    }

                    entry = (Map.Entry)var8.next();
                    session = (TrailSession)entry.getValue();
                    points = new ArrayList(session.positionHistory);
                } while(points.size() < 2);

                Entity entity = mc.level.getEntity((Integer)entry.getKey());
                Vec3 interpolatedPos = entity != null && !session.isFading ? entity.position() : (Vec3)points.get(0);
                Vec3 secondPoint = (Vec3)points.get(1);
                Vec3 flightDirection = interpolatedPos.subtract(secondPoint).normalize();
                Vec3 startPoint = interpolatedPos.add(flightDirection.scale(0.5));
                float currentWidth = 0.15F * session.decayFactor;
                Vec3 worldUp = new Vec3(0.0, 1.0, 0.0);
                Vec3 horizontalWidth = flightDirection.cross(worldUp).normalize().scale((double)currentWidth);
                Vec3 verticalWidth = flightDirection.cross(horizontalWidth).normalize().scale((double)currentWidth);
                if (horizontalWidth.lengthSqr() < 1.0E-4) {
                    horizontalWidth = (new Vec3(1.0, 0.0, 0.0)).scale((double)currentWidth);
                    verticalWidth = (new Vec3(0.0, 0.0, 1.0)).scale((double)currentWidth);
                }

                buffer.begin(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);
                this.renderStrip(buffer, matrix, points, startPoint, cameraPos, horizontalWidth, session.decayFactor);
                tesselator.end();
                buffer.begin(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);
                this.renderStrip(buffer, matrix, points, startPoint, cameraPos, verticalWidth, session.decayFactor);
                tesselator.end();
            }
        }
    }

    private void renderStrip(BufferBuilder buffer, org.joml.Matrix4f matrix, List<Vec3> points, Vec3 startPoint, Vec3 cameraPos, Vec3 widthVector, float decayFactor) {
        int totalPoints = points.size();

        for(int i = 0; i < totalPoints; ++i) {
            Vec3 currentPoint = i == 0 ? startPoint : (Vec3)points.get(i);
            float relX = (float)(currentPoint.x - cameraPos.x);
            float relY = (float)(currentPoint.y - cameraPos.y);
            float relZ = (float)(currentPoint.z - cameraPos.z);
            float progress = (float)i / (float)totalPoints;
            float widthMultiplier = 1.0F - progress * 0.6F;
            float offsetX = (float)widthVector.x * widthMultiplier;
            float offsetY = (float)widthVector.y * widthMultiplier;
            float offsetZ = (float)widthVector.z * widthMultiplier;
            float alpha = progress > 0.8F ? (1.0F - progress) * 5.0F : 1.0F;
            if (decayFactor < 1.0F) {
                alpha *= 0.8F;
            }

            alpha *= 0.7F;
            buffer.vertex(matrix, relX + offsetX, relY + offsetY, relZ + offsetZ).color(1.0F, 1.0F, 1.0F, alpha).endVertex();
            buffer.vertex(matrix, relX - offsetX, relY - offsetY, relZ - offsetZ).color(1.0F, 1.0F, 1.0F, alpha).endVertex();
        }
    }

    private static class TrailSession {
        ConcurrentLinkedDeque<Vec3> positionHistory = new ConcurrentLinkedDeque();
        float decayFactor = 1.0F;
        boolean isFading = false;

        private TrailSession() {
        }
    }
}
