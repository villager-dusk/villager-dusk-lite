
package net.mcreator.buxin.client.renderer;

import net.mcreator.buxin.client.renderer.layer.iron_golem.GlowingEyesLayer;
import net.mcreator.buxin.client.renderer.layer.iron_golem.EnchantGolemLayer;
import net.mcreator.buxin.client.renderer.layer.iron_golem.HeadLayer;
import net.mcreator.buxin.entity.RedGolem2Entity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IronGolemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.IronGolem;

public class RedGolem2Renderer extends IronGolemRenderer {
	public RedGolem2Renderer(EntityRendererProvider.Context context) {
		super(context);
		this.addLayer(new EnchantGolemLayer(this));
		this.addLayer(new GlowingEyesLayer(this));
		this.addLayer(new HeadLayer(this));
	}

	@Override
	public ResourceLocation getTextureLocation(IronGolem entity) {
		if (entity instanceof RedGolem2Entity) {
			return new ResourceLocation("buxin", "textures/entities/iron_golem.png");
		}
		return null;
	}
}
