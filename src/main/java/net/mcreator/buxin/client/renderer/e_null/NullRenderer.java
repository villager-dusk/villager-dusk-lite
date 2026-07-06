package net.mcreator.buxin.client.renderer.e_null;

import net.mcreator.buxin.client.eyes.renderer.biped.normal.GlowingEyesLayer;
import net.mcreator.buxin.entity.e_null.NullEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class NullRenderer extends HumanoidMobRenderer<NullEntity, HumanoidModel<NullEntity>> {
    public NullRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
        this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
        this.addLayer(new GlowingEyesLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(NullEntity entity) {
        return new ResourceLocation("buxin:textures/entities/null.png");
    }
}