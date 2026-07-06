package net.mcreator.buxin.item.model;

import net.mcreator.buxin.item.EnderGlock17Item;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class EnderGlock17ItemModel extends GeoModel<EnderGlock17Item> {
    @Override
    public ResourceLocation getAnimationResource(EnderGlock17Item animatable) {
        return new ResourceLocation("buxin", "animations/ender_glock17.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(EnderGlock17Item animatable) {
        return new ResourceLocation("buxin", "geo/ender_glock17.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EnderGlock17Item animatable) {
        return new ResourceLocation("buxin", "textures/items/ender_glock17.png");
    }
}