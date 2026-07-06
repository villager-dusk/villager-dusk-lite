
package net.mcreator.buxin.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.mcreator.buxin.entity.JianGuoEntity;
import net.mcreator.buxin.entity.model.JianGuoModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class JianGuoRenderer extends GeoEntityRenderer<JianGuoEntity> {
    public JianGuoRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new JianGuoModel());

        this.shadowRadius = 0.5f;
    }


}
