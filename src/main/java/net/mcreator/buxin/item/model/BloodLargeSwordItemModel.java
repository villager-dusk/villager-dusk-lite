
package net.mcreator.buxin.item.model;

import net.mcreator.buxin.item.BloodLargeSwordItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BloodLargeSwordItemModel extends GeoModel<BloodLargeSwordItem> {
	@Override
	public ResourceLocation getAnimationResource(BloodLargeSwordItem animatable) {
		return new ResourceLocation("buxin", "animations/blood_great_sword2.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(BloodLargeSwordItem animatable) {
		return new ResourceLocation("buxin", "geo/blood_great_sword2.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(BloodLargeSwordItem animatable) {
		return new ResourceLocation("buxin", "textures/items/blood_sword.png");
	}
}
