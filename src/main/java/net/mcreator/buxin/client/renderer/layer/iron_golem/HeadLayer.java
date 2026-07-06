package net.mcreator.buxin.client.renderer.layer.iron_golem;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import com.mojang.math.Axis;
import net.mcreator.buxin.client.model.Model村骑头盔;
import net.mcreator.buxin.init.BuxinModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.IronGolemModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.IronGolem;
import org.jetbrains.annotations.NotNull;

public class HeadLayer extends RenderLayer<IronGolem, IronGolemModel<IronGolem>> {
    private static Model村骑头盔 model;
    public HeadLayer(RenderLayerParent<IronGolem, IronGolemModel<IronGolem>> parent) {
        super(parent);
        model = new Model村骑头盔(Minecraft.getInstance().getEntityModels().bakeLayer(Model村骑头盔.LAYER_LOCATION));
    }
    /*
    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int i, @NotNull IronGolem golem, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if(golem.getItemBySlot(EquipmentSlot.HEAD).getItem() == BuxinModItems.CUN_MIN_TOU_KUI_HELMET.get()) {
            poseStack.pushPose();
            poseStack.translate(0, 0, -0.15);
            poseStack.mulPose(Vector3f.YN.rotationDegrees(-netHeadYaw));
            poseStack.mulPose(Vector3f.XP.rotationDegrees(headPitch));
            poseStack.translate(0, -0.51, 0);
            poseStack.scale(1.25f, 1.25f, 1.25f);
            VertexConsumer vertexConsumer = golem.getItemBySlot(EquipmentSlot.HEAD).isEnchanted() ? multiBufferSource.getBuffer(RenderType.entityTranslucentEmissive(new ResourceLocation("buxin:textures/models/armor/__layer_1.png"))) : multiBufferSource.getBuffer(RenderType.armorCutoutNoCull(new ResourceLocation("buxin:textures/models/armor/__layer_1.png")));
            model.renderToBuffer(poseStack, vertexConsumer,i, LivingEntityRenderer.getOverlayCoords(golem, 0.0f), 1, 1, 1, 1);
            poseStack.popPose();
        }
    }

     */
    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int i, @NotNull IronGolem golem, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if(golem.getItemBySlot(EquipmentSlot.HEAD).getItem() == BuxinModItems.CUN_MIN_TOU_KUI_HELMET.get()) {
            poseStack.pushPose();
            poseStack.translate(0, 0, -0.15);
            poseStack.mulPose(Axis.YN.rotationDegrees(-netHeadYaw));
            poseStack.mulPose(Axis.XP.rotationDegrees(-headPitch));
            poseStack.translate(0, -0.45, 0);
            poseStack.scale(1.15f, 1.15f, 1.15f);
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(new ResourceLocation("buxin:textures/models/armor/__layer_1.png")));
            model.renderToBuffer(poseStack, vertexConsumer, i, LivingEntityRenderer.getOverlayCoords(golem, 0.0f), 1, 1, 1, 1);
            if(golem.getItemBySlot(EquipmentSlot.HEAD).isEnchanted()) {
                renderEnchantmentGlint(poseStack, multiBufferSource);
            }
            poseStack.popPose();
        }
    }

    private void renderEnchantmentGlint(PoseStack poseStack, MultiBufferSource multiBufferSource) {
        VertexConsumer glintConsumer = ItemRenderer.getFoilBufferDirect(multiBufferSource, RenderType.entityCutoutNoCull(new ResourceLocation("buxin:textures/models/armor/__layer_1.png")), false, true);
        float alpha = 1.0f; // 闪烁效果
        model.renderToBuffer(poseStack, glintConsumer, 15728880, OverlayTexture.NO_OVERLAY, 1, 1, 1, alpha);
    }
}
