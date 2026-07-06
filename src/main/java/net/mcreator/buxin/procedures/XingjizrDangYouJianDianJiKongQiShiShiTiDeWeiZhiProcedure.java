
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.entity.SummonIronGolemEntity;
import net.mcreator.buxin.gameasset.BuxinAnimations;
import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.init.BuxinModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Random;

public class XingjizrDangYouJianDianJiKongQiShiShiTiDeWeiZhiProcedure {
	public static void execute(Entity entity, LevelAccessor world, double x, double y, double z, Random random, double fuck) {
		if (entity == null || world.isClientSide())
			return;
		BuxinMod.queueServerWork(20, () -> {
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = new SummonIronGolemEntity(BuxinModEntities.SUMMON_IRON_GOLEM.get(), _level);
				entityToSpawn.moveTo((x + 2), (y), z, 0, 0);
				entityToSpawn.setYBodyRot(0);
				entityToSpawn.setYHeadRot(0);
				entityToSpawn.setDeltaMovement(0, 0, 0);
				if (entityToSpawn instanceof Mob _mobToSpawn)
					_mobToSpawn.finalizeSpawn(_level, _level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
				world.addFreshEntity(entityToSpawn);
			}
			BuxinMod.queueServerWork(20, () -> {
				if (world instanceof ServerLevel _level) {
					Entity entityToSpawn = new SummonIronGolemEntity(BuxinModEntities.SUMMON_IRON_GOLEM.get(), _level);
					entityToSpawn.moveTo(x, y, (z - (float)fuck), 0, 0);
					entityToSpawn.setYBodyRot(0);
					entityToSpawn.setYHeadRot(0);
					entityToSpawn.setDeltaMovement(0, 0, 0);
					if (entityToSpawn instanceof Mob _mobToSpawn)
						_mobToSpawn.finalizeSpawn(_level, _level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
					world.addFreshEntity(entityToSpawn);
				}
				BuxinMod.queueServerWork(20, () -> {
					if (world instanceof ServerLevel _level) {
						Entity entityToSpawn = new SummonIronGolemEntity(BuxinModEntities.SUMMON_IRON_GOLEM.get(), _level);
						entityToSpawn.moveTo((x - 2), (y), z, 0, 0);
						entityToSpawn.setYBodyRot(0);
						entityToSpawn.setYHeadRot(0);
						entityToSpawn.setDeltaMovement(0, 0, 0);
						if (entityToSpawn instanceof Mob _mobToSpawn)
							_mobToSpawn.finalizeSpawn(_level, _level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
						world.addFreshEntity(entityToSpawn);
					}
				});
			});
		});

		if (entity instanceof Player _player)
			_player.getCooldowns().addCooldown(_player.getMainHandItem().getItem(), 195);
		if (entity instanceof LivingEntity) {
			entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(entityPatch -> {
				if (entityPatch instanceof LivingEntityPatch livingEntityPatch) {
					livingEntityPatch.playAnimationSynchronized(BuxinAnimations.SUMMONING, 0.2F);
				}
			});
		}
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:summoning")), SoundSource.NEUTRAL, 1, 1);
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:summoning")), SoundSource.NEUTRAL, 1, 1, false);
			}
		}
	}
}
