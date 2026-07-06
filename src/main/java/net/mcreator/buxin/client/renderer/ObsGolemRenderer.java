
package net.mcreator.buxin.client.renderer;

import net.mcreator.buxin.client.renderer.layer.iron_golem.GlowingEyesLayer;
import net.mcreator.buxin.client.renderer.layer.iron_golem.EnchantGolemLayer;
import net.mcreator.buxin.client.renderer.layer.iron_golem.HeadLayer;
import net.mcreator.buxin.entity.ObsGolemEntity;
import net.minecraft.client.model.IronGolemModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IronGolemRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.IronGolem;

public class ObsGolemRenderer extends IronGolemRenderer {
    public ObsGolemRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.addLayer(new EnchantGolemLayer(this));
        this.addLayer(new GlowingEyesLayer(this));
        this.addLayer(new HeadLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(IronGolem entity) {
        if (entity instanceof ObsGolemEntity) {
            return new ResourceLocation("buxin", "textures/entities/obsgolem.png");
        }
        return null;
    }
}
