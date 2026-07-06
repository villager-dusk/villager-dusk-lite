package net.mcreator.buxin.client.renderer.layer.iron_golem;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.mcreator.buxin.client.model.ModelSnakeBlade;
import net.mcreator.buxin.client.model.Model村骑头盔;
import net.minecraft.client.model.IronGolemModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.IronGolem;
import org.jetbrains.annotations.NotNull;

public class GlowingEyesLayer extends RenderLayer<IronGolem, IronGolemModel<IronGolem>> {
    public GlowingEyesLayer(RenderLayerParent<IronGolem, IronGolemModel<IronGolem>> p_117346_) {
        super(p_117346_);
    }
    private static final ResourceLocation EYES_LOCATION = new ResourceLocation("buxin:textures/entities/iron_golem_glowing_eyes.png");

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int i, @NotNull IronGolem golem, float v, float v1, float v2, float v3, float v4, float v5) {
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityTranslucentEmissive(EYES_LOCATION));
        this.getParentModel().renderToBuffer(poseStack,vertexConsumer,15728880, LivingEntityRenderer.getOverlayCoords(golem, 0.0f),1,1,1,1);
    }
}
