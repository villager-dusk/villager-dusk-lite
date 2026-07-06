
package net.mcreator.buxin.item.model;

import net.mcreator.buxin.item.SherenItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SherenItemModel extends GeoModel<SherenItem> {
    @Override
    public ResourceLocation getAnimationResource(SherenItem animatable) {
        return new ResourceLocation("buxin", "animations/sarpa_released.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(SherenItem animatable) {
        return new ResourceLocation("buxin", "geo/sarpa_released.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SherenItem animatable) {
        return new ResourceLocation("buxin", "textures/items/sarpa_released_2.png");
    }
}
