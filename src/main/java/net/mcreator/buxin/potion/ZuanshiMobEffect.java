
package net.mcreator.buxin.potion;

import net.mcreator.buxin.procedures.ChunzuanProcedure;
import net.minecraft.ChatFormatting;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class ZuanshiMobEffect extends MobEffect {
	public ZuanshiMobEffect() {
		super(MobEffectCategory.BENEFICIAL, ChatFormatting.AQUA.getColor());
	}

	@Override
	public String getDescriptionId() {
		return "effect.buxin.zuanshi";
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}
