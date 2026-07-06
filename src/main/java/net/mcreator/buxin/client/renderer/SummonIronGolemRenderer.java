
package net.mcreator.buxin.client.renderer;

import software.bernie.geckolib.renderer.GeoEntityRenderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.mcreator.buxin.entity.model.SummonIronGolemModel;
import net.mcreator.buxin.entity.SummonIronGolemEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class SummonIronGolemRenderer extends GeoEntityRenderer<SummonIronGolemEntity> {
    public SummonIronGolemRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SummonIronGolemModel());
        this.shadowRadius = 0.5f;
    }

}
