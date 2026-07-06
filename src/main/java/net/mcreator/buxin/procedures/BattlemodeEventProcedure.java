
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.entity.CunMinWeiBingEntity;
import net.mcreator.buxin.init.BuxinModEntities;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;

import java.util.Random;

public class BattlemodeEventProcedure {
	public static void execute(Entity entity, LevelAccessor world, double x, double y, double z) {
		double posX = entity.getX() + Math.random() * 10;
		double posZ = entity.getZ() + Math.random() * 15;
		Random random = new Random();
		double fuck = random.nextInt(4);
		
		if (!entity.level().isClientSide() && entity.getServer() != null) {
			entity.getServer().getCommands().performPrefixedCommand(
				new CommandSourceStack(
					CommandSource.NULL, 
					entity.position(), 
					entity.getRotationVector(), 
					entity.level() instanceof ServerLevel ? (ServerLevel) entity.level() : null, 
					6,
					entity.getName().getString(), 
					entity.getDisplayName(), 
					entity.level().getServer(), 
					entity
				), 
				"/tp @s 8.5 -60.00 -42.50"
			);
		}
		if (!entity.level().isClientSide() && entity.getServer() != null) {
			entity.getServer().getCommands().performPrefixedCommand(
				new CommandSourceStack(
					CommandSource.NULL, 
					entity.position(), 
					entity.getRotationVector(), 
					entity.level() instanceof ServerLevel ? (ServerLevel) entity.level() : null, 
					6,
					entity.getName().getString(), 
					entity.getDisplayName(), 
					entity.level().getServer(), 
					entity
				), 
				"/kill @e[type=!minecraft:player]"
			);
		}
		if (!entity.level().isClientSide() && entity.getServer() != null) {
			entity.getServer().getCommands().performPrefixedCommand(
				new CommandSourceStack(
					CommandSource.NULL, 
					entity.position(), 
					entity.getRotationVector(), 
					entity.level() instanceof ServerLevel ? (ServerLevel) entity.level() : null, 
					6,
					entity.getName().getString(), 
					entity.getDisplayName(), 
					entity.level().getServer(), 
					entity
				), 
				"/kill @e[type=!minecraft:player]"
			);
		}
		
		BuxinMod.queueServerWork(40, () -> {
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = new CunMinWeiBingEntity(BuxinModEntities.CUN_MIN_WEI_BING.get(), _level);
				entityToSpawn.moveTo(entity.getX() + fuck, entity.getY(), entity.getZ() + fuck, world.getRandom().nextFloat() * 360F, 0);
				if (entityToSpawn instanceof Mob _mobToSpawn) {
					_mobToSpawn.finalizeSpawn(_level, _level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
				}
				_level.addFreshEntity(entityToSpawn);
			}
		});
	}
}
