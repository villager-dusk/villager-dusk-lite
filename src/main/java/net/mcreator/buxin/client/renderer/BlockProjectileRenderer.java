
package net.mcreator.buxin.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.mcreator.buxin.entity.projectileblock.BuxinBlockProjectileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

public class BlockProjectileRenderer extends EntityRenderer<BuxinBlockProjectileEntity> {
    private final BlockRenderDispatcher blockRenderDispatcher;

    public BlockProjectileRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        this.blockRenderDispatcher = ctx.getBlockRenderDispatcher();
    }

    @Override
    public void render(BuxinBlockProjectileEntity entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        BlockState block = entity.getCarriedBlock();

        poseStack.pushPose();
        poseStack.scale(1.0F, 1.0F, 1.0F);
        poseStack.translate(-0.5, -0.5, -0.5);

        float age = entity.tickCount + partialTicks;
        poseStack.mulPose(Axis.XP.rotationDegrees(entity.getRotX() * age));
        poseStack.mulPose(Axis.YP.rotationDegrees(entity.getRotY() * age));
        poseStack.mulPose(Axis.ZP.rotationDegrees(entity.getRotZ() * age));

        this.blockRenderDispatcher.renderSingleBlock(block, poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);

        poseStack.popPose();
        super.render(entity, yaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(BuxinBlockProjectileEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
