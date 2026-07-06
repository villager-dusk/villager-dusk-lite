
package net.mcreator.buxin.item.model;

import net.mcreator.buxin.item.Fuzi3Item;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Fuzi3ItemModel extends GeoModel<Fuzi3Item> {
	@Override
	public ResourceLocation getAnimationResource(Fuzi3Item animatable) {
		return new ResourceLocation("buxin", "animations/ruby_axe.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(Fuzi3Item animatable) {
		return new ResourceLocation("buxin", "geo/ruby_axe.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(Fuzi3Item animatable) {
		return new ResourceLocation("buxin", "textures/items/ruby_axe.png");
	}
}
