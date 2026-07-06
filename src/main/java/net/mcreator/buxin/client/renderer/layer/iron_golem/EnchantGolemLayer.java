package net.mcreator.buxin.client.renderer.layer.iron_golem;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.minecraft.client.model.IronGolemModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.IronGolem;
import yesman.epicfight.api.client.model.AnimatedMesh;
import yesman.epicfight.api.client.model.Meshes;

public class EnchantGolemLayer extends RenderLayer<IronGolem, IronGolemModel<IronGolem>> {
    public EnchantGolemLayer(RenderLayerParent<IronGolem, IronGolemModel<IronGolem>> p_117346_) {
        super(p_117346_);
    }
    private AnimatedMesh mesh = Meshes.IRON_GOLEM;
    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, IronGolem entity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entity.isInvisible() && entity.getItemBySlot(EquipmentSlot.MAINHAND).isEnchanted()) {
            var patch = AnimationPlayer.getlivingEntityPatch(entity);
            if(patch == null) {
                VertexConsumer glintBuffer = bufferSource.getBuffer(RenderType.entityGlint());
                this.getParentModel().renderToBuffer(poseStack, glintBuffer, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
            }
        }
    }
}
