
package net.mcreator.buxin.item.model;

import net.mcreator.buxin.item.WhatItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class WhatItemModel extends GeoModel<WhatItem> {
	@Override
	public ResourceLocation getAnimationResource(WhatItem animatable) {
		return new ResourceLocation("buxin", "animations/dark_herobrine_armor.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(WhatItem animatable) {
		return new ResourceLocation("buxin", "geo/dark_herobrine_armor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(WhatItem animatable) {
		return new ResourceLocation("buxin", "textures/items/dark_herobrine_armor.png");
	}
}
