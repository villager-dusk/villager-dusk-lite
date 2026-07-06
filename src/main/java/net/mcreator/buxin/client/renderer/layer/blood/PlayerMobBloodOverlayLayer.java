package net.mcreator.buxin.client.renderer.layer.blood;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
//import se.gory_moon.player_mobs.entity.PlayerMobEntity;

/*
public class PlayerMobBloodOverlayLayer extends RenderLayer<PlayerMobEntity, PlayerModel<PlayerMobEntity>> {
    private static final ResourceLocation TEX = new ResourceLocation("buxin", "textures/entities/player_mob_blood.png");

    public PlayerMobBloodOverlayLayer(RenderLayerParent<PlayerMobEntity, PlayerModel<PlayerMobEntity>> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack pose, MultiBufferSource buf, int light, PlayerMobEntity e, float limbSwing, float limbSwingAmount, float partialTick, float age, float headYaw, float headPitch) {
        var model = this.getParentModel();
        var vc = buf.getBuffer(RenderType.entityCutoutNoCull(TEX));
        model.renderToBuffer(pose, vc, light, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
    }
}

 */