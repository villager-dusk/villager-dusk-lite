
package net.mcreator.buxin.client.renderer;

import net.mcreator.buxin.entity.Herobrine1Entity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class Herobrine1Renderer extends HumanoidMobRenderer<Herobrine1Entity, PlayerModel<Herobrine1Entity>> {
	public Herobrine1Renderer(EntityRendererProvider.Context context) {
		super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false), 0.5F);
		this.addLayer(new HumanoidArmorLayer<>(this,
				new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
				new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
				context.getModelManager()));
	}
	@Override
	public ResourceLocation getTextureLocation(Herobrine1Entity entity) {
		return new ResourceLocation("buxin:textures/entities/kui_ying_.png");
	}
}
