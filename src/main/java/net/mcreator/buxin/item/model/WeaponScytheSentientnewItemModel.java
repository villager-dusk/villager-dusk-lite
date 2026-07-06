
package net.mcreator.buxin.item.model;

import net.mcreator.buxin.item.WeaponScytheSentientnewItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class WeaponScytheSentientnewItemModel extends GeoModel<WeaponScytheSentientnewItem> {
	@Override
	public ResourceLocation getAnimationResource(WeaponScytheSentientnewItem animatable) {
		return new ResourceLocation("buxin", "animations/weapon_scythe_sentientnew.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(WeaponScytheSentientnewItem animatable) {
		return new ResourceLocation("buxin", "geo/weapon_scythe_sentientnew.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(WeaponScytheSentientnewItem animatable) {
		return new ResourceLocation("buxin", "textures/items/wddadwd.png");
	}
}
