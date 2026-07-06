
package net.mcreator.buxin.item.model;

import net.mcreator.buxin.item.DiamondWuShiSwordItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class DiamondWuShiSwordItemModel extends GeoModel<DiamondWuShiSwordItem> {
	@Override
	public ResourceLocation getAnimationResource(DiamondWuShiSwordItem animatable) {
		return new ResourceLocation("buxin", "animations/blue_sword.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(DiamondWuShiSwordItem animatable) {
		return new ResourceLocation("buxin", "geo/blue_sword.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(DiamondWuShiSwordItem animatable) {
		return new ResourceLocation("buxin", "textures/items/oooppp.png");
	}
}
