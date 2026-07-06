
package net.mcreator.buxin.entity.model;

import net.mcreator.buxin.entity.SteveNbEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SteveNbModel extends GeoModel<SteveNbEntity> {
    @Override
    public ResourceLocation getAnimationResource(SteveNbEntity entity) {
        return new ResourceLocation("buxin", "animations/l2.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(SteveNbEntity entity) {
        return new ResourceLocation("buxin", "geo/l2.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SteveNbEntity entity) {
        return new ResourceLocation("buxin", "textures/entities/" + entity.getTexture() + ".png");
    }
}
