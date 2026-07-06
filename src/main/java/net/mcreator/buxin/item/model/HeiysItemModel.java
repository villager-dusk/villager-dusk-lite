
package net.mcreator.buxin.item.model;

import net.mcreator.buxin.item.HeiysItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HeiysItemModel extends GeoModel<HeiysItem> {
	@Override
	public ResourceLocation getAnimationResource(HeiysItem animatable) {
		return new ResourceLocation("buxin", "animations/hei_yao_shi.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(HeiysItem animatable) {
		return new ResourceLocation("buxin", "geo/hei_yao_shi.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(HeiysItem animatable) {
		return new ResourceLocation("buxin", "textures/items/an_ying_hei_yao_shi_.png");
	}
}
