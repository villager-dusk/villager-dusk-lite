
package net.mcreator.buxin.client.eyes.renderer.biped.patched_layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.mcreator.buxin.client.eyes.renderer.biped.normal.GlowingEyesLayer;
import net.mcreator.buxin.entity.e_null.NullEntity;
import net.mcreator.buxin.my_method.Method_114514;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.client.model.AnimatedMesh;
import yesman.epicfight.api.client.model.MeshProvider;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.layer.ModelRenderLayer;
import yesman.epicfight.client.renderer.patched.layer.PatchedEyesLayer;
import yesman.epicfight.client.renderer.patched.layer.PatchedLayer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@OnlyIn(Dist.CLIENT)
public class PatchedGlowingEyesLayer<E extends LivingEntity, T extends LivingEntityPatch<E>, M extends EntityModel<E>, AM extends AnimatedMesh> extends ModelRenderLayer<E, T, M, EyesLayer<E, M>, AM> {
	public PatchedGlowingEyesLayer(MeshProvider<AM> mesh) {
		super(mesh);
	}
	protected void renderLayer(T entitypatch, E entityliving, EyesLayer<E, M> vanillaLayer, PoseStack postStack, MultiBufferSource buffer, int packedLightIn, OpenMatrix4f[] poses, float bob, float yRot, float xRot, float partialTicks) {
		if(entityliving instanceof NullEntity) {
			RenderType renderType = RenderType.eyes(GlowingEyesLayer.getEYES_TEXTURE());
			if(this.mesh instanceof AnimatedMesh animatedMesh) {
				animatedMesh.draw(postStack, buffer, renderType,15728640, 1.0F, 1.0F, 1.0F, 1.0F, OverlayTexture.NO_OVERLAY,entitypatch.getArmature(), poses);
			}
		}
	}
}
