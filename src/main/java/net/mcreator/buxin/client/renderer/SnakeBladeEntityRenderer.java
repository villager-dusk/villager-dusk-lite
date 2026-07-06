
package net.mcreator.buxin.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.client.model.ModelSnakeBlade;
import net.mcreator.buxin.client.model.ModelSnakeBladeFragment;
import net.mcreator.buxin.config.common.SnakeBladeConfig;
import net.mcreator.buxin.entity.snakeblade_test.entity.SnakeBladeEntity;
import net.mcreator.buxin.my_method.Method_114514;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;

public class SnakeBladeEntityRenderer extends EntityRenderer<SnakeBladeEntity> {
    private static final float CURVE_AMPLITUDE = 0.75F;
    private static final float CURVE_FREQUENCY = 0.65F;
    private static final float CURVE_PHASE = 0.1F;
    private static final ResourceLocation SNAKE_BLADE_TEXTURE = new ResourceLocation(BuxinMod.MODID,"textures/entities/fragment_chain.png");
    private static final ResourceLocation FRAGMENT_CHAIN_TEXTURE = new ResourceLocation(BuxinMod.MODID,"textures/entities/fragment_chain.png");
    private static ModelSnakeBlade snakeBladeModel;
    private static ModelSnakeBlade tongueModel;
    public static final int MAX_NECK_SEGMENTS = 128;
    private static final float FRAG_LEN = 0.6F;
    private static final float HEAD_CLEAR = 0.35F;


    public SnakeBladeEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        ModelPart fragRoot = pContext.bakeLayer(ModelSnakeBladeFragment.LAYER_LOCATION);
        tongueModel = new ModelSnakeBlade<>(fragRoot);
        ModelPart bladeRoot = pContext.bakeLayer(ModelSnakeBlade.LAYER_LOCATION);
        snakeBladeModel = new ModelSnakeBlade<>(bladeRoot);
    }

    @Override
    public boolean shouldRender(SnakeBladeEntity entity, Frustum frustum, double x, double y, double z) {
        Entity next = entity.getFromEntity();
        return next != null && frustum.isVisible(entity.getBoundingBox().minmax(next.getBoundingBox())) || super.shouldRender(entity, frustum, x, y, z);
    }

    @Override
    public ResourceLocation getTextureLocation(SnakeBladeEntity entity) {
        return FRAGMENT_CHAIN_TEXTURE;
    }
    @Override
    public void render(SnakeBladeEntity entity, float pEntityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light) {
        super.render(entity, pEntityYaw, partialTicks, poseStack, buffer, light);
        poseStack.pushPose();
        Entity fromEntity = entity.getFromEntity();
        float x = (float) Mth.lerp(partialTicks, entity.xo, entity.getX());
        float y = (float)Mth.lerp(partialTicks, entity.yo, entity.getY());
        float z = (float)Mth.lerp(partialTicks, entity.zo, entity.getZ());

        if (fromEntity != null) {
            float progress = (entity.prevProgress + (entity.getProgress() - entity.prevProgress) * partialTicks) / SnakeBladeEntity.MAX_EXTEND_TIME;
            Vec3 distVec;
            Vec3 swordPos = Method_114514.getArmPosition(entity, new Vec3f(0,0,0), Armatures.BIPED.toolR, 1F, 0.0F);

            if (swordPos != null) {
                distVec = swordPos.subtract(x, y + 1.2F, z);
            } else {
                distVec = getPositionOfPriorMob(entity, fromEntity, partialTicks).subtract(x, y, z);
            }

            Vec3 to = distVec.scale(1F - progress);
            Vec3 from = distVec;
            Vec3 direction = to.subtract(from).normalize();
            Vec3 upVector = new Vec3(0, 1, 0);
            Vec3 rightVector = direction.cross(upVector).normalize();
            Vec3 midPoint = from.add(to).scale(0.5);
            float curveStrength = entity.getCurveStrength();
            if (fromEntity instanceof LivingEntity living && living.swinging) {
                curveStrength *= 3.0f;
            }


            float time = entity.tickCount + partialTicks;
            float waveOffset = Mth.sin(time * 0.1f) * 0.1f;


            Vec3 control1 = midPoint.add(rightVector.scale(curveStrength * 0.5 + waveOffset));
            Vec3 control2 = midPoint.add(rightVector.scale(-curveStrength * 0.3 + waveOffset));

            int segmentCount = 0;
            Vec3 currentNeckButt = from;
            VertexConsumer snakebladeFragmentConsumer;
            snakebladeFragmentConsumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.entityCutoutNoCull(FRAGMENT_CHAIN_TEXTURE), true, true);

            double distanceLeft = from.distanceTo(to);
            double buildUpTo = Math.max(0.0, distanceLeft - HEAD_CLEAR);


            Vec3 prevSegmentPos = from;
            float segmentProgress = 0;
            int totalSegments = (int)Math.ceil(buildUpTo / FRAG_LEN);

            while (segmentCount < MAX_NECK_SEGMENTS && buildUpTo > 1.0e-3) {
                double step = Math.min(buildUpTo, FRAG_LEN);


                segmentProgress = segmentCount / (float)totalSegments;


                Vec3 curveOffset;
                if (entity.hasCurveEffect()) {

                    Vec3 straightPos = from.add(to.subtract(from).scale(segmentProgress));
                    Vec3 bezierPos = calculateBezierPoint(from, to, control1, control2, segmentProgress);
                    curveOffset = bezierPos.subtract(straightPos);


                    float sineWave = Mth.sin(segmentProgress * CURVE_FREQUENCY * 2 * Mth.PI + CURVE_PHASE + time * 0.05f);
                    curveOffset = curveOffset.add(rightVector.scale(sineWave * CURVE_AMPLITUDE * 0.2f));


                    Vec3 sag = calculateSagCurve(from, to, segmentProgress, 0.1f);
                    curveOffset = curveOffset.add(sag);
                } else {

                    curveOffset = Vec3.ZERO;
                }


                Vec3 targetPos;
                if (segmentCount == totalSegments - 1) {
                    targetPos = to;
                } else {
                    Vec3 straightTarget = from.add(to.subtract(from).scale((segmentCount + 1) / (float)totalSegments));
                    targetPos = straightTarget.add(curveOffset);
                }

                Vec3 currentPosWithCurve = currentNeckButt.add(curveOffset);
                Vec3 dir = targetPos.subtract(currentPosWithCurve);
                Vec3 next = dir.normalize().scale(step).add(currentPosWithCurve);


                float twistAngle = 0;
                if (entity.hasTwistEffect()) {
                    twistAngle = segmentProgress * 360 * 1f + time * 5;
                }

                int neckLight = 15728880;


                renderCurvedNeckCube(currentPosWithCurve, next, poseStack, snakebladeFragmentConsumer, neckLight, OverlayTexture.NO_OVERLAY, 0, twistAngle, segmentProgress);

                currentNeckButt = next.subtract(curveOffset);
                prevSegmentPos = currentPosWithCurve;
                buildUpTo -= step;
                segmentCount++;
            }

            VertexConsumer snakeBladeComsumer;
            snakeBladeComsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(SNAKE_BLADE_TEXTURE));
            if (entity.hasBlade() || entity.isRetracting()) {
                poseStack.pushPose();


                Vec3 bladePos = to;
                if (entity.hasCurveEffect() && segmentCount > 0) {

                    bladePos = prevSegmentPos;
                }

                poseStack.translate(bladePos.x, bladePos.y, bladePos.z);


                Vec3 headDir;
                if (segmentCount > 0) {
                    headDir = bladePos.subtract(prevSegmentPos);
                } else {
                    headDir = to.subtract(currentNeckButt);
                }


                float bladeSway = 0;
                if (entity.hasCurveEffect()) {
                    bladeSway = Mth.sin(time * 0.2f) * 10f;
                }

                float rotY = (float)(Mth.atan2(headDir.x, headDir.z) * 180F / 3.1415926) + bladeSway;
                float rotX = (float)(-Mth.atan2(headDir.y, headDir.horizontalDistance()) * 180F / 3.1415926);

                poseStack.mulPose(Axis.YP.rotationDegrees(rotY));
                poseStack.mulPose(Axis.XP.rotationDegrees(rotX));

                snakeBladeModel.renderToBuffer(poseStack, snakeBladeComsumer, 15728880, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
                poseStack.popPose();
            }
        }
        poseStack.popPose();
    }


    private Vec3 getPositionOfPriorMob(SnakeBladeEntity segment, Entity mob, float partialTicks){

        double d4 = Mth.lerp(partialTicks, mob.xo, mob.getX());
        double d5 = Mth.lerp(partialTicks, mob.yo, mob.getY());
        double d6 = Mth.lerp(partialTicks, mob.zo, mob.getZ());

        float f3 = 0;

        if(mob instanceof Player player && segment.isCreator(mob)){

            float f = player.getAttackAnim(partialTicks);

            float f1 = Mth.sin(Mth.sqrt(f) * (float) 3.1415926);

            float f2 = Mth.lerp(partialTicks, player.yBodyRotO, player.yBodyRot) * ((float) 3.1415926 / 180F);

            int i = player.getMainArm() == HumanoidArm.RIGHT ? 1 : -1;

            i = -i;

            double x = Mth.sin(f2);
            double y = Mth.cos(f2);

            double z = (double) i * 0.35D;

            if (this.entityRenderDispatcher.options.getCameraType().isFirstPerson() && player == Minecraft.getInstance().player) {


                double d7 = 960.0D / (double) this.entityRenderDispatcher.options.fov().get();

                Vec3 vec3 = this.entityRenderDispatcher.camera.getNearPlane().getPointOnPlane((float) i * 0.6F, -1);

                vec3 = vec3.scale(d7);

                vec3 = vec3.yRot(f1 * 0.25F);
                vec3 = vec3.xRot(-f1 * 0.35F);

                d4 = Mth.lerp(partialTicks, player.xo, player.getX()) + vec3.x;
                d5 = Mth.lerp(partialTicks, player.yo, player.getY()) + vec3.y;
                d6 = Mth.lerp(partialTicks, player.zo, player.getZ()) + vec3.z;

                f3 = player.getEyeHeight() * 0.5F;
            } else {



                d4 = Mth.lerp(partialTicks, player.xo, player.getX()) - y * z - x * 0.2D;

                d5 = player.yo + (double) player.getEyeHeight() + (player.getY() - player.yo) * (double) partialTicks - 1D;

                d6 = Mth.lerp(partialTicks, player.zo, player.getZ()) - x * z + y * 0.2D;


                f3 = (player.isCrouching() ? -0.1875F : 0.0F) - player.getEyeHeight() * 0.4F;
            }
        }

        return new Vec3(d4, d5 + f3, d6);
    }
    private Vec3 calculateBezierPoint(Vec3 start, Vec3 end, Vec3 control1, Vec3 control2, float t) {
        float u = 1 - t;
        float tt = t * t;
        float uu = u * u;
        float uuu = uu * u;
        float ttt = tt * t;


        Vec3 point = start.scale(uuu).add(control1.scale(3 * uu * t)).add(control2.scale(3 * u * tt)).add(end.scale(ttt));
        return point;
    }


    private Vec3 calculateSagCurve(Vec3 start, Vec3 end, float progress, float sagAmount) {

        float midProgress = 0.5f;
        float sagValue = sagAmount * 4 * (progress - progress * progress);

        return new Vec3(0, -sagValue, 0);
    }

    public void renderCurvedNeckCube(Vec3 from, Vec3 to, PoseStack poseStack, VertexConsumer buffer, int packedLightIn, int overlayCoords, float additionalYaw, float twistAngle, float segmentProgress) {
        Vec3 dir = to.subtract(from);

        float yaw = (float)(Mth.atan2(dir.x, dir.z) * (180F / 3.1415926));
        float pitch = (float)(-Mth.atan2(dir.y, dir.horizontalDistance()) * (180F / 3.1415926));
        float curveBend = Mth.sin(segmentProgress * Mth.PI) * 10f;
        poseStack.pushPose();
        poseStack.translate(from.x, from.y, from.z);
        poseStack.mulPose(Axis.YP.rotationDegrees(yaw + additionalYaw + twistAngle));
        poseStack.mulPose(Axis.XP.rotationDegrees(pitch + curveBend));


        float scale = 1.0f - Mth.abs(Mth.sin(segmentProgress * Mth.PI)) * 0.1f;
        poseStack.scale(scale, scale, 1.0f);

        tongueModel.renderToBuffer(poseStack, buffer, packedLightIn, overlayCoords, 1, 1, 1, 1);
        poseStack.popPose();
    }
}
