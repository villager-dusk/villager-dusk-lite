
package net.mcreator.buxin.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;

public class Jshaman2ShiTiChuShiShengChengShiProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null || world.isClientSide())
            return;

        if (world instanceof ServerLevel _level) {
            LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
            if (entityToSpawn != null) {
                entityToSpawn.moveTo(Vec3.atBottomCenterOf(new BlockPos((int) x, (int) y, (int) z)));
                _level.addFreshEntity(entityToSpawn);
            }
        }
        entity.getPersistentData().putDouble("battle", 0);
        entity.getPersistentData().putBoolean("isbuxinentity", true);
    }
}
