
package net.mcreator.buxin.item.model;

import net.mcreator.buxin.item.FireSnakeSwordItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class FireSnakeSwordItemModel extends GeoModel<FireSnakeSwordItem> {
    @Override
    public ResourceLocation getAnimationResource(FireSnakeSwordItem animatable) {
        return new ResourceLocation("buxin", "animations/sarpa_released.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(FireSnakeSwordItem animatable) {
        return new ResourceLocation("buxin", "geo/sarpa_released.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FireSnakeSwordItem animatable) {
        return new ResourceLocation("buxin", "textures/items/wdad.png");
    }
}
