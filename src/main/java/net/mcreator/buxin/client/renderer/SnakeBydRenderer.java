
package net.mcreator.buxin.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.mcreator.buxin.entity.SnakeBydEntity;
import net.mcreator.buxin.entity.model.SnakeBydModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SnakeBydRenderer extends GeoEntityRenderer<SnakeBydEntity> {
    public SnakeBydRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SnakeBydModel());
        this.shadowRadius = 0.5f;
    }
}
