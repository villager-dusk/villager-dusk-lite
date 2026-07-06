
package net.mcreator.buxin.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.mcreator.buxin.entity.greg_hb.GregHbEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class GregHbRenderer extends HumanoidMobRenderer<GregHbEntity, PlayerModel<GregHbEntity>> {
    public GregHbRenderer(EntityRendererProvider.Context context) {
        super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false), 0.5F);
        this.addLayer(new HumanoidArmorLayer<>(
                this,
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                context.getModelManager()
        ));
    }

    @Override
    public ResourceLocation getTextureLocation(GregHbEntity entity) {
        return new ResourceLocation("buxin:textures/entities/greg_hb.png");
    }

    @Override
    public void render(GregHbEntity entity, float pEntityYaw, float pPartialTick, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
        int packedLight2 = entity.getPersistentData().getBoolean("isUseHerobrineTexture") ? 15728880 : packedLight;
        super.render(entity, pEntityYaw, pPartialTick, matrixStack, buffer, packedLight2);
    }
}
