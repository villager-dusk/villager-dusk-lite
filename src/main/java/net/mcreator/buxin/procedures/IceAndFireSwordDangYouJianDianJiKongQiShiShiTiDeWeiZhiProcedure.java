package net.mcreator.buxin.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import net.mcreator.buxin.BuxinMod;

import java.util.stream.Collectors;
import java.util.List;
import java.util.Comparator;

public class IceAndFireSwordDangYouJianDianJiKongQiShiShiTiDeWeiZhiProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) throws InstantiationException, IllegalAccessException {
		if (entity == null || world.isClientSide())
			return;
		if (true) {
			if (Math.random() > 0.5) {
				entity.clearFire();
				{
					final Vec3 _center = new Vec3(x, y, z);
					List<Entity> _entfound = world.getEntities(Entity.class.newInstance(), new AABB(_center, _center).inflate(10), e -> e != entity && e.distanceToSqr(_center) <= 100.0)
							.stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
							.collect(Collectors.toList());
					for (Entity entityiterator : _entfound) {
						if ((entity == entityiterator) == false) {
							if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 120, 999));
							world.setBlock(new BlockPos((int)entityiterator.getX(), (int)entityiterator.getY(), (int)entityiterator.getZ()), Blocks.ICE.defaultBlockState(), 3);
							world.setBlock(new BlockPos((int)entityiterator.getX(), (int)(entityiterator.getY() + 1), (int)entityiterator.getZ()), Blocks.ICE.defaultBlockState(), 3);
							world.setBlock(new BlockPos((int)(entityiterator.getX() + 1), (int)entityiterator.getY(), (int)entityiterator.getZ()), Blocks.ICE.defaultBlockState(), 3);
							world.setBlock(new BlockPos((int)(entityiterator.getX() - 1), (int)entityiterator.getY(), (int)entityiterator.getZ()), Blocks.ICE.defaultBlockState(), 3);
							world.setBlock(new BlockPos((int)entityiterator.getX(), (int)entityiterator.getY(), (int)(entityiterator.getZ() + 1)), Blocks.ICE.defaultBlockState(), 3);
							world.setBlock(new BlockPos((int)entityiterator.getX(), (int)entityiterator.getY(), (int)(entityiterator.getZ() - 1)), Blocks.ICE.defaultBlockState(), 3);
							entityiterator.clearFire();
							BuxinMod.queueServerWork(50, () -> {
								world.destroyBlock(new BlockPos((int)entityiterator.getX(), (int)entityiterator.getY(), (int)entityiterator.getZ()), false);
								world.destroyBlock(new BlockPos((int)entityiterator.getX(), (int)(entityiterator.getY() + 1), (int)entityiterator.getZ()), false);
								world.destroyBlock(new BlockPos((int)(entityiterator.getX() + 1), (int)entityiterator.getY(), (int)entityiterator.getZ()), false);
								world.destroyBlock(new BlockPos((int)(entityiterator.getX() - 1), (int)entityiterator.getY(), (int)entityiterator.getZ()), false);
								world.destroyBlock(new BlockPos((int)entityiterator.getX(), (int)entityiterator.getY(), (int)(entityiterator.getZ() + 1)), false);
								world.destroyBlock(new BlockPos((int)entityiterator.getX(), (int)entityiterator.getY(), (int)(entityiterator.getZ() - 1)), false);
								entityiterator.hurt(entityiterator.damageSources().generic(), 30);
							});
						}
					}
				}
			}
		}
		if (Math.random() < 0.5) {
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntities(Entity.class.newInstance(), new AABB(_center, _center).inflate(12), e -> e != entity && e.distanceToSqr(_center) <= 144.0)
						.stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
						.collect(Collectors.toList());
				for (Entity entityiterator : _entfound) {
					if ((entity == entityiterator) == false) {
						if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 120, 1));
						if (world instanceof ServerLevel _level) {
							LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
							entityToSpawn.moveTo(Vec3.atBottomCenterOf(new BlockPos((int)entityiterator.getX(), (int)entityiterator.getY(), (int)entityiterator.getZ())));
							entityToSpawn.setVisualOnly(false);
							_level.addFreshEntity(entityToSpawn);
						}
						entityiterator.setSecondsOnFire(10);
						entityiterator.hurt(entityiterator.damageSources().generic(), 45);
					}
				}
			}
		}
		if (Math.random() == 0.5) {
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntities(Entity.class.newInstance(), new AABB(_center, _center).inflate(12), e -> e != entity && e.distanceToSqr(_center) <= 144.0)
						.stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
						.collect(Collectors.toList());
				for (Entity entityiterator : _entfound) {
					if ((entity == entityiterator) == false) {
						if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 120, 1));
						if (world instanceof ServerLevel _level) {
							LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
							entityToSpawn.moveTo(Vec3.atBottomCenterOf(new BlockPos((int)entityiterator.getX(), (int)entityiterator.getY(), (int)entityiterator.getZ())));
							entityToSpawn.setVisualOnly(false);
							_level.addFreshEntity(entityToSpawn);
						}
						entityiterator.setSecondsOnFire(10);
						entityiterator.hurt(entityiterator.damageSources().generic(), 45);
						if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 120, 999));
						world.setBlock(new BlockPos((int)entityiterator.getX(), (int)entityiterator.getY(), (int)entityiterator.getZ()), Blocks.ICE.defaultBlockState(), 3);
						world.setBlock(new BlockPos((int)entityiterator.getX(), (int)(entityiterator.getY() + 1), (int)entityiterator.getZ()), Blocks.ICE.defaultBlockState(), 3);
						world.setBlock(new BlockPos((int)(entityiterator.getX() + 1), (int)entityiterator.getY(), (int)entityiterator.getZ()), Blocks.ICE.defaultBlockState(), 3);
						world.setBlock(new BlockPos((int)(entityiterator.getX() - 1), (int)entityiterator.getY(), (int)entityiterator.getZ()), Blocks.ICE.defaultBlockState(), 3);
						world.setBlock(new BlockPos((int)entityiterator.getX(), (int)entityiterator.getY(), (int)(entityiterator.getZ() + 1)), Blocks.ICE.defaultBlockState(), 3);
						world.setBlock(new BlockPos((int)entityiterator.getX(), (int)entityiterator.getY(), (int)(entityiterator.getZ() - 1)), Blocks.ICE.defaultBlockState(), 3);
						BuxinMod.queueServerWork(50, () -> {
							if (world instanceof Level _level && !_level.isClientSide()) {
								_level.explode(null, entityiterator.getX(), entityiterator.getY(), entityiterator.getZ(), 4f, Level.ExplosionInteraction.NONE);
							}
							world.destroyBlock(new BlockPos((int)entityiterator.getX(), (int)entityiterator.getY(), (int)entityiterator.getZ()), false);
							world.destroyBlock(new BlockPos((int)entityiterator.getX(), (int)(entityiterator.getY() + 1), (int)entityiterator.getZ()), false);
							world.destroyBlock(new BlockPos((int)(entityiterator.getX() + 1), (int)entityiterator.getY(), (int)entityiterator.getZ()), false);
							world.destroyBlock(new BlockPos((int)(entityiterator.getX() - 1), (int)entityiterator.getY(), (int)entityiterator.getZ()), false);
							world.destroyBlock(new BlockPos((int)entityiterator.getX(), (int)entityiterator.getY(), (int)(entityiterator.getZ() + 1)), false);
							world.destroyBlock(new BlockPos((int)entityiterator.getX(), (int)entityiterator.getY(), (int)(entityiterator.getZ() - 1)), false);
							entityiterator.hurt(entityiterator.damageSources().generic(), 30);
						});
					}
				}
			}
		}
	}
}