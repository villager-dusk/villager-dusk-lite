
package net.mcreator.buxin.item.model;

import net.mcreator.buxin.item.BloodGreatswordItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BloodGreatswordItemModel extends GeoModel<BloodGreatswordItem> {
	@Override
	public ResourceLocation getAnimationResource(BloodGreatswordItem animatable) {
		return new ResourceLocation("buxin", "animations/blood_great_sword2.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(BloodGreatswordItem animatable) {
		return new ResourceLocation("buxin", "geo/blood_great_sword2.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(BloodGreatswordItem animatable) {
		return new ResourceLocation("buxin", "textures/items/blood_sword.png");
	}
}
