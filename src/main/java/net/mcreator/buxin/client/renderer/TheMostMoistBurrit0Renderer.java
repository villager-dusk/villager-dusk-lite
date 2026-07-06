
package net.mcreator.buxin.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.mcreator.buxin.client.renderer.father.RangedAttackMobRenderer;
import net.mcreator.buxin.entity.TheMostMoistBurrit0Entity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;

public class TheMostMoistBurrit0Renderer extends RangedAttackMobRenderer<TheMostMoistBurrit0Entity, HumanoidModel<TheMostMoistBurrit0Entity>> {
    public TheMostMoistBurrit0Renderer(EntityRendererProvider.Context context) {
        super(context, new PlayerModel(context.bakeLayer(ModelLayers.PLAYER), false), 0.5F);
        this.addLayer(new HumanoidArmorLayer(this, 
            new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), 
            new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
            context.getModelManager()));
    }

    @Override
    public ResourceLocation getTextureLocation(TheMostMoistBurrit0Entity entity) {
        return entity.getHealth() > entity.getMaxHealth() * 0.5 ? 
            new ResourceLocation("buxin:textures/entities/the_most_moist_burrit0.png") : 
            new ResourceLocation("buxin:textures/entities/the_most_moist_burrit0_half_health.png");
    }
}
