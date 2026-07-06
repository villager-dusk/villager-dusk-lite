
package net.mcreator.buxin.client.eyes.renderer.biped.normal;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.mcreator.buxin.entity.e_null.NullEntity;
import net.mcreator.buxin.my_method.Method_114514;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class GlowingEyesLayer<T extends LivingEntity, M extends EntityModel<T>> extends EyesLayer<T, M> {
	public GlowingEyesLayer(RenderLayerParent<T, M> renderer) {
		super(renderer);
	}

	private static final ResourceLocation EYES_TEXTURE = new ResourceLocation("buxin:textures/eyes/herobrine_eye.png");

	public static ResourceLocation getEYES_TEXTURE() {
		return EYES_TEXTURE;
	}

	@Override
	public void render(PoseStack p_116983_, MultiBufferSource p_116984_, int p_116985_, T entity, float p_116987_, float p_116988_, float p_116989_, float p_116990_, float p_116991_, float p_116992_) {
		if(entity instanceof NullEntity) {
			VertexConsumer $$10 = p_116984_.getBuffer(RenderType.eyes(EYES_TEXTURE));
			this.getParentModel().renderToBuffer(p_116983_, $$10, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	@Override
	public RenderType renderType() {
		return RenderType.eyes(new ResourceLocation("buxin:textures/eyes/air.png"));
	}
}
