
package net.mcreator.buxin.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;

import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.entity.ZanZhu5Entity;

public class SummonXddDangYouJianDianJiFangKuaiShiFangKuaiDeWeiZhiProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        if (world instanceof ServerLevel _level) {
            Entity entityToSpawn = new ZanZhu5Entity(BuxinModEntities.ZAN_ZHU_5.get(), _level);
            entityToSpawn.moveTo(x, (y + 2), z, 0, 0);
            entityToSpawn.setYBodyRot(0);
            entityToSpawn.setYHeadRot(0);
            entityToSpawn.setDeltaMovement(0, 0, 0);
            _level.addFreshEntity(entityToSpawn);
        }
    }
}
