
package net.mcreator.buxin.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.Matrix4f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LightningBoltRenderer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LightningBolt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(LightningBoltRenderer.class)
public abstract class MixinColorBoltEntity {
    private static final float PURPLE_RED = 0.7F;
    private static final float PURPLE_GREEN = 0.3F;
    private static final float PURPLE_BLUE = 1.0F;

    private static final float CYAN_RED = 0.3F;
    private static final float CYAN_GREEN = 1.0F;
    private static final float CYAN_BLUE = 1.0F;

    private static final float BLACK_RED = 0.0F;
    private static final float BLACK_GREEN = 1.0F;
    private static final float BLACK_BLUE = 0.2F;

    @Inject(
            method = "render(Lnet/minecraft/world/entity/LightningBolt;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onRender(LightningBolt entity, float entityYaw, float partialTicks,
                          PoseStack matrixStack, MultiBufferSource buffer,
                          int packedLight, CallbackInfo ci) {

        if (entity.getCustomName() != null && entity.getCustomName().getString().equals("b")) {
            renderCustomLightning(entity, matrixStack, buffer, CYAN_RED, CYAN_GREEN, CYAN_BLUE);
            ci.cancel();
        }
        else if (entity.getCustomName() != null && entity.getCustomName().getString().equals("p")) {
            renderCustomLightning(entity, matrixStack, buffer, PURPLE_RED, PURPLE_GREEN, PURPLE_BLUE);
            ci.cancel();
        }
        else if (entity.getCustomName() != null && entity.getCustomName().getString().equals("h")) {
            renderCustomLightning(entity, matrixStack, buffer, BLACK_RED, BLACK_GREEN, BLACK_BLUE);
            ci.cancel();
        }
    }

    private void renderCustomLightning(LightningBolt entity, PoseStack matrixStack, MultiBufferSource buffer,
                                       float red, float green, float blue) {
        float[] xOffsets = new float[8];
        float[] zOffsets = new float[8];
        float currentX = 0.0F;
        float currentZ = 0.0F;
        RandomSource random = RandomSource.create(entity.seed);

        for(int i = 7; i >= 0; --i) {
            xOffsets[i] = currentX;
            zOffsets[i] = currentZ;
            currentX += (random.nextInt(11) - 5);
            currentZ += (random.nextInt(11) - 5);
        }

        VertexConsumer vertexBuilder = buffer.getBuffer(RenderType.lightning());
        Matrix4f matrix = matrixStack.last().pose();

        for(int layer = 0; layer < 4; ++layer) {
            RandomSource layerRandom = RandomSource.create(entity.seed);

            for(int segment = 0; segment < 3; ++segment) {
                int startIdx = (segment == 0) ? 7 : 7 - segment;
                int endIdx = (segment == 0) ? 0 : startIdx - 2;

                float segmentX = xOffsets[startIdx] - currentX;
                float segmentZ = zOffsets[startIdx] - currentZ;

                for(int i = startIdx; i >= endIdx; --i) {
                    float prevX = segmentX;
                    float prevZ = segmentZ;

                    if (segment == 0) {
                        segmentX += (layerRandom.nextInt(11) - 5);
                        segmentZ += (layerRandom.nextInt(11) - 5);
                    } else {
                        segmentX += (layerRandom.nextInt(31) - 15);
                        segmentZ += (layerRandom.nextInt(31) - 15);
                    }

                    float width1 = 0.1F + layer * 0.2F;
                    float width2 = 0.1F + layer * 0.2F;

                    if (segment == 0) {
                        width1 *= i * 0.1F + 1.0F;
                        width2 *= (i - 1.0F) * 0.1F + 1.0F;
                    }

                    renderQuad(matrix, vertexBuilder, segmentX, segmentZ, i,
                            prevX, prevZ, red, green, blue,
                            width1, width2, false, false, true, false);
                    renderQuad(matrix, vertexBuilder, segmentX, segmentZ, i,
                            prevX, prevZ, red, green, blue,
                            width1, width2, true, false, true, true);
                    renderQuad(matrix, vertexBuilder, segmentX, segmentZ, i,
                            prevX, prevZ, red, green, blue,
                            width1, width2, true, true, false, true);
                    renderQuad(matrix, vertexBuilder, segmentX, segmentZ, i,
                            prevX, prevZ, red, green, blue,
                            width1, width2, false, true, false, false);
                }
            }
        }
    }

    private void renderQuad(Matrix4f matrix, VertexConsumer builder,
                            float x1, float z1, int yIdx,
                            float x2, float z2,
                            float red, float green, float blue,
                            float width1, float width2,
                            boolean flipX1, boolean flipZ1,
                            boolean flipX2, boolean flipZ2) {

        builder.vertex(matrix,
                        x1 + (flipX1 ? width1 : -width1),
                        yIdx * 16,
                        z1 + (flipZ1 ? width1 : -width1))
                .color(red, green, blue, 0.3F)
                .endVertex();

        builder.vertex(matrix,
                        x2 + (flipX1 ? width2 : -width2),
                        (yIdx + 1) * 16,
                        z2 + (flipZ1 ? width2 : -width2))
                .color(red, green, blue, 0.3F)
                .endVertex();

        builder.vertex(matrix,
                        x2 + (flipX2 ? width2 : -width2),
                        (yIdx + 1) * 16,
                        z2 + (flipZ2 ? width2 : -width2))
                .color(red, green, blue, 0.3F)
                .endVertex();

        builder.vertex(matrix,
                        x1 + (flipX2 ? width1 : -width1),
                        yIdx * 16,
                        z1 + (flipZ2 ? width1 : -width1))
                .color(red, green, blue, 0.3F)
                .endVertex();
    }
}
