
package net.mcreator.buxin.item.model;

import net.mcreator.buxin.item.Darkobs2Item;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Darkobs2ItemModel extends GeoModel<Darkobs2Item> {
	@Override
	public ResourceLocation getAnimationResource(Darkobs2Item animatable) {
		return new ResourceLocation("buxin", "animations/hei_yao_shi_2.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(Darkobs2Item animatable) {
		return new ResourceLocation("buxin", "geo/hei_yao_shi_2.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(Darkobs2Item animatable) {
		return new ResourceLocation("buxin", "textures/items/an_ying_hei_yao_shi_.png");
	}
}
