package net.mcreator.buxin.item.model;

import net.mcreator.buxin.item.Wp2Item;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Wp2ItemModel extends GeoModel<Wp2Item> {
    @Override
    public ResourceLocation getAnimationResource(Wp2Item animatable) {
        return new ResourceLocation("buxin", "animations/obs_stick_2wp.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(Wp2Item animatable) {
        return new ResourceLocation("buxin", "geo/obs_stick_2wp.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Wp2Item animatable) {
        return new ResourceLocation("buxin", "textures/items/1.png");
    }
}