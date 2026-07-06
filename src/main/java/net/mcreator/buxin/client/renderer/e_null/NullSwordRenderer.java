
package net.mcreator.buxin.client.renderer.e_null;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.entity.e_null.NullSwordEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class NullSwordRenderer extends HumanoidMobRenderer<NullSwordEntity, HumanoidModel<NullSwordEntity>> {

    public NullSwordRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5F);
        this.addLayer(new HumanoidArmorLayer<>(this,
            new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
            new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
    }

    public ResourceLocation getTextureLocation(NullSwordEntity herobrineentity) {
        return new ResourceLocation(BuxinMod.MODID, "textures/entities/empty.png");
    }
}
