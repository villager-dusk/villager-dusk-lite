
package net.mcreator.buxin.item.model;

import net.mcreator.buxin.item.Blood303WeaponItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class Blood303WeaponItemModel extends DefaultedItemGeoModel<Blood303WeaponItem> {

    public Blood303WeaponItemModel() {
        super(new ResourceLocation("buxin", "blood_303_weapon"));
    }

    @Override
    public ResourceLocation getAnimationResource(Blood303WeaponItem animatable) {
        return new ResourceLocation("buxin", "animations/blood_303_weapon.animation.json");
    }

    @Override
    public ResourceLocation getTextureResource(Blood303WeaponItem animatable) {
        return new ResourceLocation("buxin", "textures/items/1718418150794.png");
    }
}
