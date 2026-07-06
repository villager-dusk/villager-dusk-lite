
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.entity.BaozhajianEntity;
import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.init.BuxinModParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class NotchDangShiTiShouShangShiProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null || world.isClientSide())
			return;
		if (world instanceof ServerLevel _level)
			_level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.HUOHUA_2.get()), x, y, z, (int) Mth.nextDouble(RandomSource.create(), 78, 150), 0.1, 0.6, 1, 0.4);
		if (Math.random() < 0.5) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:ge_dang")), SoundSource.NEUTRAL, (float) (1 + Mth.nextDouble(RandomSource.create(), 0, 0.2)),
							(float) (1 + Mth.nextDouble(RandomSource.create(), 0, 0.1)));
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:ge_dang")), SoundSource.NEUTRAL, (float) (1 + Mth.nextDouble(RandomSource.create(), 0, 0.2)),
							(float) (1 + Mth.nextDouble(RandomSource.create(), 0, 0.1)), false);
				}
			}
		}
		if (Math.random() > 0.5) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:clash2")), SoundSource.NEUTRAL, (float) (1 + Mth.nextDouble(RandomSource.create(), 0, 0.2)),
							(float) (1 + Mth.nextDouble(RandomSource.create(), 0, 0.1)));
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:clash2")), SoundSource.NEUTRAL, (float) (1 + Mth.nextDouble(RandomSource.create(), 0, 0.2)),
							(float) (1 + Mth.nextDouble(RandomSource.create(), 0, 0.1)), false);
				}
			}
		}
		if (Math.random() < 0.6) {
			if (Math.random() < 0.6) {
				if (world instanceof ServerLevel _level)
					_level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.HUOHUA.get()), x, y, z, 100, 0.1, 0.6, 1, 0.4);
				if (world instanceof ServerLevel _level)
					_level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.HUOHUA.get()), x, y, z, 100, 0.1, 0.6, 1, 0.4);
				if (world instanceof ServerLevel _level)
					_level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.HUOHUA.get()), x, y, z, 140, 0.1, 0.6, 1, 0.4);
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:w")), SoundSource.NEUTRAL, (float) (1 + Mth.nextDouble(RandomSource.create(), 0, 0.2)),
								(float) (1 + Mth.nextDouble(RandomSource.create(), 0, 0.1)));
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:w")), SoundSource.NEUTRAL, (float) (1 + Mth.nextDouble(RandomSource.create(), 0, 0.2)),
								(float) (1 + Mth.nextDouble(RandomSource.create(), 0, 0.1)), false);
					}
				}
				if (Math.random() < 0.4) {
					if (world instanceof ServerLevel _level)
						_level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.HUOHUA.get()), x, y, z, 1000, 0, 1, 0, 0.2);
				}
			}
		}
		if (Math.random() > 0.5) {
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) > 160) {
				BuxinMod.queueServerWork(20, () -> {
					{
						Entity _shootFrom = entity;
						Level projectileLevel = _shootFrom.level();
						if (!projectileLevel.isClientSide()) {
							Projectile _entityToSpawn = new Object() {
								public Projectile getArrow(Level level, float damage, int knockback) {
									AbstractArrow entityToSpawn = new BaozhajianEntity(BuxinModEntities.BAOZHAJIAN.get(), level);
									entityToSpawn.setBaseDamage(damage);
									entityToSpawn.setKnockback(knockback);
									entityToSpawn.setSilent(true);
									return entityToSpawn;
								}
							}.getArrow(projectileLevel, 1, 1);
							_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
							_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, (float) 0.5, 2);
							projectileLevel.addFreshEntity(_entityToSpawn);
						}
					}
				});
			}
		}
	}
}
