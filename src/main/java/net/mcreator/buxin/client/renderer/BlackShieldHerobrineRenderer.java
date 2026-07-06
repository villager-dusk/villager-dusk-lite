
package net.mcreator.buxin.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.mcreator.buxin.entity.BlackShieldHerobrineEntity;
import net.mcreator.buxin.entity.PurpleDemonEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class BlackShieldHerobrineRenderer extends HumanoidMobRenderer<BlackShieldHerobrineEntity, PlayerModel<BlackShieldHerobrineEntity>> {
    public BlackShieldHerobrineRenderer(EntityRendererProvider.Context context) {
        super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false), 0.5F);
        this.addLayer(new HumanoidArmorLayer<>(this,
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                context.getModelManager()));
    }

    @Override
    public ResourceLocation getTextureLocation(BlackShieldHerobrineEntity entity) {
        return new ResourceLocation("buxin:textures/entities/purple_demon.png");
    }

    protected void setupRotations(BlackShieldHerobrineEntity p_114109_, PoseStack p_114110_, float p_114111_, float p_114112_, float p_114113_) {
        super.setupRotations(p_114109_, p_114110_, p_114111_, p_114112_, p_114113_);
        float $$5 = p_114109_.getSwimAmount(p_114113_);
        if ($$5 > 0.0F) {
            p_114110_.mulPose(Axis.XP.rotationDegrees(Mth.lerp($$5, p_114109_.getXRot(), -10.0F - p_114109_.getXRot())));
        }
    }
}
