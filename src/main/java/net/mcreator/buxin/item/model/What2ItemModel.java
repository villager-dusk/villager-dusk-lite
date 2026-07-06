package net.mcreator.buxin.item.model;

import net.mcreator.buxin.item.What2Item;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class What2ItemModel extends GeoModel<What2Item> {
	@Override
	public ResourceLocation getAnimationResource(What2Item animatable) {
		return new ResourceLocation("buxin", "animations/dark_herobrine_armor.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(What2Item animatable) {
		return new ResourceLocation("buxin", "geo/dark_herobrine_armor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(What2Item animatable) {
		return new ResourceLocation("buxin", "textures/items/dark_herobrine_armor.png");
	}
}