package net.mcreator.buxin.item.model;

import net.mcreator.buxin.item.Fuzi2Item;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class Fuzi2ItemModel extends GeoModel<Fuzi2Item> {
    @Override
    public ResourceLocation getAnimationResource(Fuzi2Item animatable) {
        return new ResourceLocation("buxin", "animations/red_steel_axe.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(Fuzi2Item animatable) {
        return new ResourceLocation("buxin", "geo/red_steel_axe.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Fuzi2Item animatable) {
        return new ResourceLocation("buxin", "textures/items/ruby_axe.png");
    }
}