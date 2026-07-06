
package net.mcreator.buxin.client.renderer;

import net.mcreator.buxin.client.renderer.layer.iron_golem.GlowingEyesLayer;
import net.mcreator.buxin.client.renderer.layer.iron_golem.EnchantGolemLayer;
import net.mcreator.buxin.client.renderer.layer.iron_golem.HeadLayer;
import net.mcreator.buxin.entity.IceGolemEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IronGolemRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.IronGolem;

public class IceGolemRenderer extends IronGolemRenderer {
	public IceGolemRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.addLayer(new EnchantGolemLayer(this));
		this.addLayer(new GlowingEyesLayer(this));
		this.addLayer(new HeadLayer(this));
		/*
		// 注意：HumanoidArmorLayer 在 1.20.1 中构造函数变为：
		// HumanoidArmorLayer(EntityRendererProvider.Context, HumanoidModel, HumanoidModel)
		// 但此处被注释，且原代码中 context.bakeLayer 已废弃（应使用 context.getEquipmentRenderer().getArmorModel(...) 等），
		// 因项目未启用该层，故保持注释状态，不修改。
		 */
	}

	@Override
	public ResourceLocation getTextureLocation(IronGolem entity) {
		if (entity instanceof IceGolemEntity) {
			return new ResourceLocation("buxin:textures/entities/iron_golem_-_fu_ben_.png");
		}
        return null;
    }
}
