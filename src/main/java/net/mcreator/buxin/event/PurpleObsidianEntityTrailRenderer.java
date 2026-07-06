
package net.mcreator.buxin.event;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import org.joml.Matrix4f;
import net.mcreator.buxin.entity.projectileblock.BuxinBlockProjectileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent.Stage;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class PurpleObsidianEntityTrailRenderer {
    protected static final Map<Integer, TrailSession> activeTrails = new ConcurrentHashMap();
    public PurpleObsidianEntityTrailRenderer() {
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == Phase.END && Minecraft.getInstance().level != null) {
            Minecraft mc = Minecraft.getInstance();
            Iterator<Map.Entry<Integer, TrailSession>> iterator = activeTrails.entrySet().iterator();

            while(iterator.hasNext()) {
                Map.Entry<Integer, TrailSession> entry = iterator.next();
                int entityId = entry.getKey();
                TrailSession session = entry.getValue();
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

            for (Entity entity : mc.level.entitiesForRendering()) {
                if (entity instanceof BuxinBlockProjectileEntity && entity.getCustomName() != null && entity.getCustomName().getString().equals("p")) {
                    if (!activeTrails.containsKey(entity.getId()) && entity.getDeltaMovement().lengthSqr() > 1.0E-4) {
                        TrailSession newSession = new TrailSession();
                        Vec3 currentPos = entity.position();
                        Vec3 previousPos = currentPos.subtract(entity.getDeltaMovement());
                        newSession.positionHistory.add(currentPos);
                        newSession.positionHistory.add(previousPos);
                        activeTrails.put(entity.getId(), newSession);
                    }
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
            Matrix4f matrix = poseStack.last().pose();
            Iterator<Map.Entry<Integer, TrailSession>> var8 = activeTrails.entrySet().iterator();

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

                    entry = var8.next();
                    session = (TrailSession)entry.getValue();
                    points = new ArrayList(session.positionHistory);
                } while(points.size() < 2);

                Entity entity = mc.level.getEntity((Integer)entry.getKey());
                Vec3 interpolatedPos = entity != null && !session.isFading ? entity.position() : (Vec3)points.get(0);
                Vec3 secondPoint = (Vec3)points.get(1);
                Vec3 flightDirection = interpolatedPos.subtract(secondPoint).normalize();
                Vec3 startPoint = interpolatedPos.add(flightDirection.scale(0.5));
                float currentWidth = 0.22F * session.decayFactor;
                Vec3 worldUp = new Vec3(0.0, 1.0, 0.0);
                Vec3 horizontalWidth = flightDirection.cross(worldUp).normalize().scale((double)currentWidth);
                Vec3 verticalWidth = flightDirection.cross(horizontalWidth).normalize().scale((double)currentWidth);
                if (horizontalWidth.lengthSqr() < 1.0E-4) {
                    horizontalWidth = (new Vec3(1.0, 0.0, 0.0)).scale((double)currentWidth);
                    verticalWidth = (new Vec3(0.0, 0.0, 1.0)).scale((double)currentWidth);
                }

                buffer.begin(Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);
                this.renderStrip(buffer, matrix, points, startPoint, cameraPos, horizontalWidth, session.decayFactor);
                tesselator.end();
                buffer.begin(Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);
                this.renderStrip(buffer, matrix, points, startPoint, cameraPos, verticalWidth, session.decayFactor);
                tesselator.end();
            }
        }
    }

    protected void renderStrip(BufferBuilder buffer, Matrix4f matrix, List<Vec3> points, Vec3 startPoint, Vec3 cameraPos, Vec3 widthVector, float decayFactor) {
        int totalPoints = points.size();

        for(int i = 0; i < totalPoints; ++i) {
            Vec3 currentPoint = i == 0 ? startPoint : (Vec3)points.get(i);
            float relX = (float)(currentPoint.x - cameraPos.x);
            float relY = (float)(currentPoint.y - cameraPos.y);
            float relZ = (float)(currentPoint.z - cameraPos.z);
            float progress = (float)i / (float)totalPoints;
            float widthMultiplier = 1.0F - progress * 0.1F;
            float offsetX = (float)widthVector.x * widthMultiplier;
            float offsetY = (float)widthVector.y * widthMultiplier;
            float offsetZ = (float)widthVector.z * widthMultiplier;
            buffer.vertex(matrix, relX + offsetX, relY + offsetY, relZ + offsetZ).color(normalRGBToMcRGB(27f), normalRGBToMcRGB(12f), normalRGBToMcRGB(37f), 0.5f).endVertex();
            buffer.vertex(matrix, relX - offsetX, relY - offsetY, relZ - offsetZ).color(normalRGBToMcRGB(27f), normalRGBToMcRGB(12f), normalRGBToMcRGB(37f), 0.5f).endVertex();
        }
    }

    protected static class TrailSession {
        ConcurrentLinkedDeque<Vec3> positionHistory = new ConcurrentLinkedDeque<>();
        float decayFactor = 1.0F;
        boolean isFading = false;

        protected TrailSession() {
        }
    }

    private float normalRGBToMcRGB(float normalRGB){
        return (float) 1 / 255 * normalRGB;
    }
}
