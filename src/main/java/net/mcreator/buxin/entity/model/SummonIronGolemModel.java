
package net.mcreator.buxin.entity.model;

import net.mcreator.buxin.entity.SummonIronGolemEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SummonIronGolemModel extends GeoModel<SummonIronGolemEntity> {
	@Override
	public ResourceLocation getAnimationResource(SummonIronGolemEntity entity) {
		return new ResourceLocation("buxin", "animations/summon_iron_golem.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(SummonIronGolemEntity entity) {
		return new ResourceLocation("buxin", "geo/summon_iron_golem.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SummonIronGolemEntity entity) {
		return new ResourceLocation("buxin", "textures/entities/" + entity.getTexture() + ".png");
	}
}
