
package net.mcreator.buxin.entity.model;

import net.mcreator.buxin.entity.SnakeBydEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SnakeBydModel extends GeoModel<SnakeBydEntity> {
	@Override
	public ResourceLocation getAnimationResource(SnakeBydEntity entity) {
		return new ResourceLocation("buxin", "animations/snake_sword_skill.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(SnakeBydEntity entity) {
		return new ResourceLocation("buxin", "geo/snake_sword_skill.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SnakeBydEntity entity) {
		return new ResourceLocation("buxin", "textures/entities/" + entity.getTexture() + ".png");
	}
}
