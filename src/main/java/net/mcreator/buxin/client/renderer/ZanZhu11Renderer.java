
package net.mcreator.buxin.client.renderer;

import net.mcreator.buxin.entity.ZanZhu11Entity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class ZanZhu11Renderer extends HumanoidMobRenderer<ZanZhu11Entity, PlayerModel<ZanZhu11Entity>> {
    public ZanZhu11Renderer(EntityRendererProvider.Context context) {
        super(context, new PlayerModel(context.bakeLayer(ModelLayers.PLAYER), false), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(ZanZhu11Entity entity) {
        return new ResourceLocation("buxin:textures/entities/1714796199122.png");
    }
}
