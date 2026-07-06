
package net.mcreator.buxin.item.model;

import net.mcreator.buxin.item.FuziItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class FuziItemModel extends GeoModel<FuziItem> {
	@Override
	public ResourceLocation getAnimationResource(FuziItem animatable) {
		return new ResourceLocation("buxin", "animations/red_steel_axe.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(FuziItem animatable) {
		return new ResourceLocation("buxin", "geo/red_steel_axe.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(FuziItem animatable) {
		return new ResourceLocation("buxin", "textures/items/ruby_axe.png");
	}
}
