
package net.mcreator.buxin.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;

import net.mcreator.buxin.entity.WhatShitPhase2Entity;

public class WhatShitPhase2Renderer extends HumanoidMobRenderer<WhatShitPhase2Entity, PlayerModel<WhatShitPhase2Entity>> {
	public WhatShitPhase2Renderer(EntityRendererProvider.Context context) {
		super(context, new PlayerModel(context.bakeLayer(ModelLayers.PLAYER), false), 0.5F);
		this.addLayer(new HumanoidArmorLayer(this, 
				new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), 
				new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),context.getModelManager()));
	}

	@Override
	public ResourceLocation getTextureLocation(WhatShitPhase2Entity whatShitPhase2Entity) {
		return null;
	}
}
