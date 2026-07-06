
package net.mcreator.buxin.client.renderer;

import net.mcreator.buxin.client.renderer.father.RangedAttackMobRenderer;
import net.mcreator.buxin.client.renderer.layer.cape.AngrySteveCapeLayer;
import net.mcreator.buxin.entity.Niubi14Entity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class Niubi14Renderer extends RangedAttackMobRenderer<Niubi14Entity, PlayerModel<Niubi14Entity>> {
    public Niubi14Renderer(EntityRendererProvider.Context context) {
        super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false), 0.5F);
        this.addLayer(new HumanoidArmorLayer<>(this, 
            new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), 
            new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
        this.addLayer(new AngrySteveCapeLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(@NotNull Niubi14Entity entity) {
        if (entity.getHealth() > 60) {
            return new ResourceLocation("buxin", "textures/entities/steve.png");
        } else {
            return new ResourceLocation("buxin", "textures/entities/angry_steve.png");
        }
    }
}
