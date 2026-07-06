
package net.mcreator.buxin.entity.model;

import net.mcreator.buxin.entity.JianGuoEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class JianGuoModel extends GeoModel<JianGuoEntity> {
	@Override
	public ResourceLocation getAnimationResource(JianGuoEntity entity) {
		return new ResourceLocation("buxin", "animations/jian_guo.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(JianGuoEntity entity) {
		return new ResourceLocation("buxin", "geo/jian_guo.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(JianGuoEntity entity) {
		return new ResourceLocation("buxin", "textures/entities/" + entity.getTexture() + ".png");
	}
}
