
package net.mcreator.buxin.item.model;

import net.mcreator.buxin.item.GeSiLaSherenItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GeSiLaSherenItemModel extends GeoModel<GeSiLaSherenItem> {
	@Override
	public ResourceLocation getAnimationResource(GeSiLaSherenItem animatable) {
		return new ResourceLocation("buxin", "animations/ge_si_la_sr.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(GeSiLaSherenItem animatable) {
		return new ResourceLocation("buxin", "geo/ge_si_la_sr.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(GeSiLaSherenItem animatable) {
		return new ResourceLocation("buxin", "textures/items/gsl_new_2.png");
	}
}
