
package net.mcreator.buxin.item.model;

import net.mcreator.buxin.item.SnakelegendaryswordItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SnakelegendaryswordItemModel extends GeoModel<SnakelegendaryswordItem> {
    @Override
    public ResourceLocation getAnimationResource(SnakelegendaryswordItem animatable) {
        return new ResourceLocation("buxin", "animations/snake_legendary_sword.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(SnakelegendaryswordItem animatable) {
        return new ResourceLocation("buxin", "geo/snake_legendary_sword.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SnakelegendaryswordItem animatable) {
        return new ResourceLocation("buxin", "textures/items/snake_legendary_sword.png");
    }
}
