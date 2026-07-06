
package net.mcreator.buxin.client.renderer;

import net.mcreator.buxin.client.renderer.father.RangedAttackMobRenderer;
import net.mcreator.buxin.client.renderer.layer.cape.GreenVillagerCavalryCapeLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;

import net.mcreator.buxin.entity.GreenVillagerCavalryEntity;

public class GreenVillagerCavalryRenderer extends RangedAttackMobRenderer<GreenVillagerCavalryEntity, PlayerModel<GreenVillagerCavalryEntity>> {
	public GreenVillagerCavalryRenderer(EntityRendererProvider.Context context) {
		super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false), 0.5F);
		this.addLayer(new HumanoidArmorLayer<>(this,
				new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
				new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
				context.getModelManager()
		));
		this.addLayer(new GreenVillagerCavalryCapeLayer(this));
	}

	@Override
	public ResourceLocation getTextureLocation(GreenVillagerCavalryEntity entity) {
		return new ResourceLocation("buxin:textures/entities/lan_cun_min_qi_shi_.png");
	}
}
