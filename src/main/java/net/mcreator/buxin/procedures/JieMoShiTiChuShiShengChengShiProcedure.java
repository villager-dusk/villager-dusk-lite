
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.entity.SummonIronGolemEntity;
import net.mcreator.buxin.gameasset.BuxinAnimations;
import net.mcreator.buxin.init.BuxinModEntities;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class JieMoShiTiChuShiShengChengShiProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		Level world = entity.level();
		double x = entity.getX(), y = entity.getY(), z = entity.getZ();
		entity.getPersistentData().putDouble("battle", 0);
		entity.getPersistentData().putBoolean("isbuxinentity", true);
		entity.getPersistentData().putBoolean("niu", true);
		BuxinMod.queueServerWork(40, () -> {
			try {
				entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(EntityPatch -> {
					if (EntityPatch instanceof LivingEntityPatch livingEntityPatch) {
						livingEntityPatch.playAnimationSynchronized(BuxinAnimations.SUMMONING, 0F);
					}
				});
			} catch (Exception e) {
			}
			BuxinMod.queueServerWork(20, () -> {
				if (world instanceof ServerLevel _level) {
					Entity entityToSpawn = new SummonIronGolemEntity(BuxinModEntities.SUMMON_IRON_GOLEM.get(), _level);
					entityToSpawn.moveTo((x + 2), (y + 2), z, 0, 0);
					entityToSpawn.setYBodyRot(0);
					entityToSpawn.setYHeadRot(0);
					entityToSpawn.setDeltaMovement(0, 0, 0);
					if (entityToSpawn instanceof Mob _mobToSpawn) {
						_mobToSpawn.finalizeSpawn(_level, _level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
					}
					world.addFreshEntity(entityToSpawn);
				}
				BuxinMod.queueServerWork(20, () -> {
					if (world instanceof ServerLevel _level) {
						Entity entityToSpawn = new SummonIronGolemEntity(BuxinModEntities.SUMMON_IRON_GOLEM.get(), _level);
						entityToSpawn.moveTo(x, y, (z - 2), 0, 0);
						entityToSpawn.setYBodyRot(0);
						entityToSpawn.setYHeadRot(0);
						entityToSpawn.setDeltaMovement(0, 0, 0);
						if (entityToSpawn instanceof Mob _mobToSpawn) {
							_mobToSpawn.finalizeSpawn(_level, _level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
						}
						world.addFreshEntity(entityToSpawn);
						BuxinMod.queueServerWork(40, () -> {
							entity.startRiding(entityToSpawn);
						});
					}
					BuxinMod.queueServerWork(20, () -> {
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = new SummonIronGolemEntity(BuxinModEntities.SUMMON_IRON_GOLEM.get(), _level);
							entityToSpawn.moveTo((x - 2), (y + 2), z, 0, 0);
							entityToSpawn.setYBodyRot(0);
							entityToSpawn.setYHeadRot(0);
							entityToSpawn.setDeltaMovement(0, 0, 0);
							if (entityToSpawn instanceof Mob _mobToSpawn) {
								_mobToSpawn.finalizeSpawn(_level, _level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
							}
							world.addFreshEntity(entityToSpawn);
						}
					});
				});
			});
		});
	}
}
