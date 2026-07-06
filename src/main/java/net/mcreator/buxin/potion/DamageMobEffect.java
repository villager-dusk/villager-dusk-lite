
package net.mcreator.buxin.potion;

import net.mcreator.buxin.procedures.DamageZaiXiaoGuoChiXuShiMeiKeFaShengProcedure;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class DamageMobEffect extends MobEffect {
	public DamageMobEffect() {
		super(MobEffectCategory.HARMFUL, -6422528);
	}

	@Override
	public String getDescriptionId() {
		return "effect.buxin.damage";
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		DamageZaiXiaoGuoChiXuShiMeiKeFaShengProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}
