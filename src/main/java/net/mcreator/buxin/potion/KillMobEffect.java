
package net.mcreator.buxin.potion;

import net.mcreator.buxin.procedures.KillDangYaoShuiXiaoGuoKaiShiYingYongShiProcedure;
import net.minecraft.ChatFormatting;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class KillMobEffect extends MobEffect {
	public KillMobEffect() {
		super(MobEffectCategory.BENEFICIAL, ChatFormatting.AQUA.getColor());
	}

	@Override
	public String getDescriptionId() {
		return "effect.buxin.kill";
	}

	@Override
	public void addAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
		KillDangYaoShuiXiaoGuoKaiShiYingYongShiProcedure.execute(entity);
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}
