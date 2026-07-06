
package net.mcreator.buxin.client.renderer;

import net.mcreator.buxin.entity.Entity303Entity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class Entity303Renderer extends HumanoidMobRenderer<Entity303Entity, PlayerModel<Entity303Entity>> {
	public Entity303Renderer(EntityRendererProvider.Context context) {
		super(context, new PlayerModel(context.bakeLayer(ModelLayers.PLAYER), false), 0.5F);
		this.addLayer(new HumanoidArmorLayer(this, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),context.getModelManager()));
	}

	@Override
	public ResourceLocation getTextureLocation(Entity303Entity entity) {
		return new ResourceLocation("buxin:textures/entities/09f5855f298cf918.png");
	}
}
