package net.mcreator.buxin.item.model;

import net.mcreator.buxin.item.YZszrItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class YZszrItemModel extends GeoModel<YZszrItem> {
	@Override
	public ResourceLocation getAnimationResource(YZszrItem animatable) {
		return new ResourceLocation("buxin", "animations/shadow_legendary_sword.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(YZszrItem animatable) {
		return new ResourceLocation("buxin", "geo/shadow_legendary_sword.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(YZszrItem animatable) {
		return new ResourceLocation("buxin", "textures/items/shadow_legendary_sword_2.png");
	}
}