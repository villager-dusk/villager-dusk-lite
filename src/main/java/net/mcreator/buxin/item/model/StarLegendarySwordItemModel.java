package net.mcreator.buxin.item.model;

import net.mcreator.buxin.item.StarLegendarySwordItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class StarLegendarySwordItemModel extends GeoModel<StarLegendarySwordItem> {
	@Override
	public ResourceLocation getAnimationResource(StarLegendarySwordItem animatable) {
		return new ResourceLocation("buxin", "animations/star_legend_sword.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(StarLegendarySwordItem animatable) {
		return new ResourceLocation("buxin", "geo/star_legend_sword.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(StarLegendarySwordItem animatable) {
		return new ResourceLocation("buxin", "textures/items/114514.png");
	}
}