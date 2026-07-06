
package net.mcreator.buxin.item.model;

import net.mcreator.buxin.item.HeiYaoShi2Item;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HeiYaoShi2ItemModel extends GeoModel<HeiYaoShi2Item> {
    @Override
    public ResourceLocation getAnimationResource(HeiYaoShi2Item animatable) {
        return new ResourceLocation("buxin", "animations/hei_yao_shi_2.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(HeiYaoShi2Item animatable) {
        return new ResourceLocation("buxin", "geo/hei_yao_shi_2.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(HeiYaoShi2Item animatable) {
        return new ResourceLocation("buxin", "textures/items/hei_yao_shi_2.png");
    }
}
