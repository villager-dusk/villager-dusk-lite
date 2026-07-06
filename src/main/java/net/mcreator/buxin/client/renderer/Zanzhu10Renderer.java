package net.mcreator.buxin.client.renderer;

import net.mcreator.buxin.entity.Zanzhu10Entity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class Zanzhu10Renderer extends HumanoidMobRenderer<Zanzhu10Entity, PlayerModel<Zanzhu10Entity>> {
    public Zanzhu10Renderer(EntityRendererProvider.Context context) {
        super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false), 0.5F);

        this.addLayer(new HumanoidArmorLayer(this,
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                context.getModelManager()));
    }

    @Override
    public ResourceLocation getTextureLocation(Zanzhu10Entity entity) {
        return new ResourceLocation("buxin", "textures/entities/image_1714908720634.png");
    }
}