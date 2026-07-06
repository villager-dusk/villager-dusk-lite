
package net.mcreator.buxin.item.model;

import net.mcreator.buxin.item.ZszrJueXingItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ZszrJueXingItemModel extends GeoModel<ZszrJueXingItem> {
	@Override
	public ResourceLocation getAnimationResource(ZszrJueXingItem animatable) {
		return new ResourceLocation("buxin", "animations/zszr.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(ZszrJueXingItem animatable) {
		return new ResourceLocation("buxin", "geo/zszr.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ZszrJueXingItem animatable) {
		return new ResourceLocation("buxin", "textures/items/zszr.png");
	}
}
