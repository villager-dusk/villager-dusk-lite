
package net.mcreator.buxin.client.renderer;

import net.mcreator.buxin.client.model.ModelPrisoner;
import net.mcreator.buxin.entity.CunMinWeiBingEntity;
import net.mcreator.buxin.entity.VillagerPrisonerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class VillagerPrisonerEntityRenderer extends MobRenderer<VillagerPrisonerEntity, ModelPrisoner<VillagerPrisonerEntity>> {
    public VillagerPrisonerEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new ModelPrisoner<>(context.bakeLayer(ModelPrisoner.LAYER_LOCATION)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(VillagerPrisonerEntity entity) {
        return new ResourceLocation("buxin:textures/entities/villager_.png");
    }
}
