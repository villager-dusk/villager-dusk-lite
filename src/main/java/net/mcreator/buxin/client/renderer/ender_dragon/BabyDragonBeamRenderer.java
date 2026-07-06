
package net.mcreator.buxin.client.renderer.ender_dragon;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.entity.dragon.baby.BabyDragonBeamEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BabyDragonBeamRenderer extends EntityRenderer<BabyDragonBeamEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(BuxinMod.MODID, "textures/entities/dragon_beam.png");

    public BabyDragonBeamRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public void render(BabyDragonBeamEntity beam, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
        VertexConsumer vertexconsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        poseStack.pushPose();
        PoseStack.Pose posestack$pose = poseStack.last();
        com.mojang.math.Axis axis = com.mojang.math.Axis.YN;
        float size = 0.2F;
        float yOffset = (float)(beam.getBoundingBox().getYsize() / 2.0D);
        for (int i = 0; i < 4; ++i) {
            poseStack.pushPose();
            float f = (float)(i * 90) * ((float)Math.PI / 180F);
            poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(f));
            poseStack.translate(0.0D, yOffset, 0.0D);
            poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(90.0F));
            poseStack.scale(size, size, 1.0F);
            vertexconsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
            vertexconsumer.vertex(posestack$pose.pose(), -0.5F, -0.5F, 0.0F).color(255, 255, 255, 255).uv(0.0F, 0.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(posestack$pose.normal(), 0.0F, 0.0F, 1.0F).endVertex();
            vertexconsumer.vertex(posestack$pose.pose(), 0.5F, -0.5F, 0.0F).color(255, 255, 255, 255).uv(1.0F, 0.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(posestack$pose.normal(), 0.0F, 0.0F, 1.0F).endVertex();
            vertexconsumer.vertex(posestack$pose.pose(), 0.5F, 0.5F, 0.0F).color(255, 255, 255, 255).uv(1.0F, 1.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(posestack$pose.normal(), 0.0F, 0.0F, 1.0F).endVertex();
            vertexconsumer.vertex(posestack$pose.pose(), -0.5F, 0.5F, 0.0F).color(255, 255, 255, 255).uv(0.0F, 1.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(posestack$pose.normal(), 0.0F, 0.0F, 1.0F).endVertex();
            poseStack.popPose();
        }
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(BabyDragonBeamEntity dragonBeam) {
        return TEXTURE;
    }
}
