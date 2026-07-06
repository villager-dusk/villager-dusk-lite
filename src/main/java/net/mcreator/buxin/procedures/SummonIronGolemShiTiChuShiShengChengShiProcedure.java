
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.entity.IceGolemEntity;
import net.mcreator.buxin.entity.ObsGolemEntity;
import net.mcreator.buxin.entity.RedGolem2Entity;
import net.mcreator.buxin.entity.SummonIronGolemEntity;
import net.mcreator.buxin.init.BuxinModEntities;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;

public class SummonIronGolemShiTiChuShiShengChengShiProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null || world.isClientSide())
			return;
		Random random = new Random();
		double value = random.nextInt(3);
		entity.getPersistentData().putBoolean("YH", true);
		if (entity instanceof SummonIronGolemEntity) {
			((SummonIronGolemEntity) entity).setAnimation("animation.summon_iron_golem.new2");
		}
		BuxinMod.queueServerWork(53, () -> {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					entity.fallDistance = 0;
					_level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("epicfight:sfx.ground_slam")), SoundSource.NEUTRAL, 1, 1);
				} else {
					entity.fallDistance = 0;
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("epicfight:sfx.ground_slam")), SoundSource.NEUTRAL, 1, 1, false);
				}
			}
		});
		BuxinMod.queueServerWork(63, () -> {
			if (!entity.level().isClientSide())
				entity.discard();
			if (value == 0) {
				if (world instanceof ServerLevel _level) {
					Entity entityToSpawn = new ObsGolemEntity(BuxinModEntities.OBS_GOLEM.get(), _level);
					entityToSpawn.moveTo(x, y, z, 0, 0);
					entityToSpawn.setYBodyRot(0);
					entityToSpawn.setYHeadRot(0);
					entityToSpawn.setDeltaMovement(0, 0, 0);
					if (entityToSpawn instanceof Mob _mobToSpawn)
						_mobToSpawn.finalizeSpawn(_level, _level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
					_level.addFreshEntity(entityToSpawn);
				}
			}
			if(value == 1){
				if (world instanceof ServerLevel _level) {
					Entity entityToSpawn = new RedGolem2Entity(BuxinModEntities.RED_GOLEM.get(), _level);
					entityToSpawn.moveTo(x, y, z, 0, 0);
					entityToSpawn.setYBodyRot(0);
					entityToSpawn.setYHeadRot(0);
					entityToSpawn.setDeltaMovement(0, 0, 0);
					if (entityToSpawn instanceof Mob _mobToSpawn)
						_mobToSpawn.finalizeSpawn(_level, _level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
					_level.addFreshEntity(entityToSpawn);
				}
			}
			if(value == 2){
				if (world instanceof ServerLevel _level) {
					Entity entityToSpawn = new IceGolemEntity(BuxinModEntities.ICE_GOLEM.get(), _level);
					entityToSpawn.moveTo(x, y, z, 0, 0);
					entityToSpawn.setYBodyRot(0);
					entityToSpawn.setYHeadRot(0);
					entityToSpawn.setDeltaMovement(0, 0, 0);
					if (entityToSpawn instanceof Mob _mobToSpawn)
						_mobToSpawn.finalizeSpawn(_level, _level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
					_level.addFreshEntity(entityToSpawn);
				}
			}
		});
	}
}
