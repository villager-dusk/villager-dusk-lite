package net.mcreator.buxin.item.model;

import net.mcreator.buxin.item.Iron2Item;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Iron2ItemModel extends GeoModel<Iron2Item> {
	@Override
	public ResourceLocation getAnimationResource(Iron2Item animatable) {
		return new ResourceLocation("buxin", "animations/iron.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(Iron2Item animatable) {
		return new ResourceLocation("buxin", "geo/iron.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(Iron2Item animatable) {
		return new ResourceLocation("buxin", "textures/items/90942423291812559.png");
	}
}