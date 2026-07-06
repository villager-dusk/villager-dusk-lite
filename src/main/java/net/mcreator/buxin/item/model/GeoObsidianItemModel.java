
package net.mcreator.buxin.item.model;

import net.mcreator.buxin.item.GeoObsidianItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GeoObsidianItemModel extends GeoModel<GeoObsidianItem> {
    @Override
    public ResourceLocation getAnimationResource(GeoObsidianItem animatable) {
        return new ResourceLocation("buxin", "animations/geo_obsidian.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(GeoObsidianItem animatable) {
        return new ResourceLocation("buxin", "geo/geo_obsidian.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GeoObsidianItem animatable) {
        return new ResourceLocation("buxin", "textures/items/2231.png");
    }
}
