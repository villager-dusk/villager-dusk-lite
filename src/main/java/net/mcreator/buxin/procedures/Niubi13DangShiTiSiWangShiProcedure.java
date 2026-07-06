
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.entity.Niubi14Entity;
import net.mcreator.buxin.init.BuxinModEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;

import java.util.Random;

public class Niubi13DangShiTiSiWangShiProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		BuxinMod.queueServerWork(40, () -> {
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = new Niubi14Entity(BuxinModEntities.NIUBI_14.get(), _level);
				entityToSpawn.moveTo(x + new Random().nextInt(3) + 2, y, z + new Random().nextInt(5) + 2, 0, 0);
				entityToSpawn.setYBodyRot(0);
				entityToSpawn.setYHeadRot(0);
				if (entityToSpawn instanceof Mob _mobToSpawn) {
					_mobToSpawn.finalizeSpawn(_level, _level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
				}
				world.addFreshEntity(entityToSpawn);
			}
		});
	}
}
