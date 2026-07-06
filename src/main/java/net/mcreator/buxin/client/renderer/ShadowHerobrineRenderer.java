package net.mcreator.buxin.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.mcreator.buxin.entity.DarkHerobrineEntity;
import net.mcreator.buxin.entity.shadow_herorbrine.ShadowHerobrineEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;

public class ShadowHerobrineRenderer extends HumanoidMobRenderer<ShadowHerobrineEntity, PlayerModel<ShadowHerobrineEntity>> {
    public ShadowHerobrineRenderer(EntityRendererProvider.Context context) {
        super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false), 0.5F);
        this.addLayer(new HumanoidArmorLayer<>(this,
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                context.getModelManager()));
    }

    @Override
    public ResourceLocation getTextureLocation(ShadowHerobrineEntity entity) {
        return new ResourceLocation("buxin:textures/entities/kui_ying_.png");
    }

    @Override
    public void render(ShadowHerobrineEntity entity, float pEntityYaw, float pPartialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
        int packedLight2 = entity.isNoAi() ? 15728880 : packedLight;
        super.render(entity, pEntityYaw, pPartialTicks, matrixStack, buffer, packedLight2);
    }
}