
package net.mcreator.buxin.client.renderer.ender_dragon;

import com.mojang.blaze3d.vertex.PoseStack;
import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.entity.dragon.normal.DragonBeamEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DragonBeamRenderer extends EntityRenderer<DragonBeamEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(BuxinMod.MODID, "textures/entities/dragon_beam.png");

    public DragonBeamRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public void render(DragonBeamEntity dragonBeam, float entityYaw, float delta, PoseStack poseStack, MultiBufferSource multiBufferSource, int light) {
        // 渲染逻辑由实际需求补充
    }

    @Override
    public ResourceLocation getTextureLocation(DragonBeamEntity dragonBeam) {
        return TEXTURE;
    }
}
