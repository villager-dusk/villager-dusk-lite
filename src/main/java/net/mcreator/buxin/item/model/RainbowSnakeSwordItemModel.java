package net.mcreator.buxin.item.model;

import net.mcreator.buxin.item.RainbowSnakeSwordItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class RainbowSnakeSwordItemModel extends GeoModel<RainbowSnakeSwordItem> {
	@Override
	public ResourceLocation getAnimationResource(RainbowSnakeSwordItem animatable) {
		return new ResourceLocation("buxin", "animations/sarpa_released.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(RainbowSnakeSwordItem animatable) {
		return new ResourceLocation("buxin", "geo/sarpa_released.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(RainbowSnakeSwordItem animatable) {
		return new ResourceLocation("buxin", "textures/items/super_snake_sword_512x.png");
	}
}