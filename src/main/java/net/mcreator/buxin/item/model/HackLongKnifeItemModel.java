
package net.mcreator.buxin.item.model;

import net.mcreator.buxin.item.HackLongKnifeItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HackLongKnifeItemModel extends GeoModel<HackLongKnifeItem> {
	@Override
	public ResourceLocation getAnimationResource(HackLongKnifeItem animatable) {
		return new ResourceLocation("buxin", "animations/srcd.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(HackLongKnifeItem animatable) {
		return new ResourceLocation("buxin", "geo/srcd.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(HackLongKnifeItem animatable) {
		return new ResourceLocation("buxin", "textures/items/ppp.png");
	}
}
