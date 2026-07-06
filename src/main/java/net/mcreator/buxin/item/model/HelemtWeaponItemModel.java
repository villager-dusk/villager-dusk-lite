
package net.mcreator.buxin.item.model;

import net.mcreator.buxin.item.HelemtWeaponItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HelemtWeaponItemModel extends GeoModel<HelemtWeaponItem> {
    @Override
    public ResourceLocation getAnimationResource(HelemtWeaponItem animatable) {
        return new ResourceLocation("buxin", "animations/helemt_herobrine_weapon.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(HelemtWeaponItem animatable) {
        return new ResourceLocation("buxin", "geo/helemt_herobrine_weapon.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(HelemtWeaponItem animatable) {
        return new ResourceLocation("buxin", "textures/items/1.png");
    }
}
