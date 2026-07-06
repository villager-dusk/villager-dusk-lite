
package net.mcreator.buxin.item.model;

import net.mcreator.buxin.item.VeryVerybigSwordItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import software.bernie.geckolib.model.GeoModel;

public class VeryVerybigSwordItemModel extends GeoModel<VeryVerybigSwordItem> {
	@Override
	public ResourceLocation getAnimationResource(VeryVerybigSwordItem animatable) {
		return new ResourceLocation("buxin", "animations/dark_legendary_sword_geck.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(VeryVerybigSwordItem animatable) {
		return new ResourceLocation("buxin", "geo/dark_legendary_sword_geck.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(VeryVerybigSwordItem animatable) {
		return new ResourceLocation("buxin", "textures/items/dark_legendary_sword.png");
	}
}
