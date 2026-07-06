
package net.mcreator.buxin.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;

import net.mcreator.buxin.init.BuxinModMobEffects;
import net.mcreator.buxin.BuxinMod;

public class VeryVerybigSwordDangShiTiBeiGaiWuPinJiZhongProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null || world.isClientSide())
			return;
		// 1.20.1: Use DamageSources instead of DamageSource
		entity.hurt(new DamageSources(world.registryAccess()).generic(),
				(float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) + (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1)));
		if (!world.isClientSide()) {
			// 1.20.1: Use Entity.RemovalReason.KILLED correctly
			entity.remove(Entity.RemovalReason.KILLED);
			if (entity instanceof LivingEntity) {
				WhatShitPhase2OnEntityUpdateTick.setMaxHealth((LivingEntity) entity, 0);
				((LivingEntity) entity).setHealth(0);
			}
		}
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
			_entity.addEffect(new MobEffectInstance(BuxinModMobEffects.KILL.get(), 1000, 5));
		}
		if (!world.isClientSide() && world.getServer() != null) {
			// Empty block, no changes needed
		}
	}
}
