
package net.mcreator.buxin.utils;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.Entity;

public class NothingLayer<T extends Entity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private final RenderLayer<?, ?> replacedLayer;

    public NothingLayer(RenderLayerParent<T, M> renderer, RenderLayer<?, ?> replacedLayer) {
        super(renderer);
        this.replacedLayer = replacedLayer;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    public RenderLayer<?, ?> getReplacedLayer() {
        return this.replacedLayer;
    }
}
