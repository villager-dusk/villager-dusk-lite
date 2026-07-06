
package net.mcreator.buxin.client.renderer;

import net.mcreator.buxin.client.renderer.layer.cape.NotchCapeLayer;
import net.mcreator.buxin.entity.NotchEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class NotchRenderer extends HumanoidMobRenderer<NotchEntity, PlayerModel<NotchEntity>> {
	public NotchRenderer(EntityRendererProvider.Context context) {
		super(context, new PlayerModel(context.bakeLayer(ModelLayers.PLAYER), false), 0.5F);
		this.addLayer(new HumanoidArmorLayer(this, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),context.getModelManager()));
		this.addLayer(new NotchCapeLayer(this));
	}
	
	@Override
	public ResourceLocation getTextureLocation(NotchEntity entity) {
		return new ResourceLocation("buxin:textures/entities/notch.png");
	}
}
