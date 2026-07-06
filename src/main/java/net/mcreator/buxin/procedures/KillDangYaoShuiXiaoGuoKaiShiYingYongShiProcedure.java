
package net.mcreator.buxin.procedures;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;

public class KillDangYaoShuiXiaoGuoKaiShiYingYongShiProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 999, false, false));
		for (int index0 = 0; index0 < 999; index0++) {
			if (!entity.level().isClientSide())
				entity.discard();
		}
		if (entity.level() instanceof ServerLevel serverLevel) {
			DamageSource damageSource = new DamageSource(
				serverLevel.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE)
					.getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("generic")))
			);
			entity.hurt(damageSource, 999);
			entity.hurt(damageSource, entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1);
		}
		if (entity instanceof LivingEntity _entity)
			_entity.setHealth(0);
	}
}
