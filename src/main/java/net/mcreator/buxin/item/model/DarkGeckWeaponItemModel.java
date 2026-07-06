
package net.mcreator.buxin.item.model;

import net.mcreator.buxin.item.DarkGeckWeaponItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class DarkGeckWeaponItemModel extends GeoModel<DarkGeckWeaponItem> {
	@Override
	public ResourceLocation getAnimationResource(DarkGeckWeaponItem animatable) {
		return new ResourceLocation("buxin", "animations/dark_herobrine_weapon_geck.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(DarkGeckWeaponItem animatable) {
		return new ResourceLocation("buxin", "geo/dark_herobrine_weapon_geck.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(DarkGeckWeaponItem animatable) {
		return new ResourceLocation("buxin", "textures/items/dark_herobrine_weapon.png");
	}
}
