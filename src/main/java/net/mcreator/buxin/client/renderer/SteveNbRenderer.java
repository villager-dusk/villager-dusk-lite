
package net.mcreator.buxin.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.mcreator.buxin.entity.SteveNbEntity;
import net.mcreator.buxin.entity.model.SteveNbModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SteveNbRenderer extends GeoEntityRenderer<SteveNbEntity> {
    public SteveNbRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SteveNbModel());
        this.shadowRadius = 0.5f;
    }


}
