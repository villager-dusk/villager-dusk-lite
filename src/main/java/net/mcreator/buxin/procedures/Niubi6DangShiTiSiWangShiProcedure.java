package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.entity.Grave2Entity;
import net.mcreator.buxin.init.BuxinModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;

public class Niubi6DangShiTiSiWangShiProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        BuxinMod.queueServerWork(65, () -> {
            if (world instanceof ServerLevel _level) {
                Entity entityToSpawn = new Grave2Entity(BuxinModEntities.GRAVE_2.get(), _level);
                entityToSpawn.moveTo(x, y, z, 0, 0);
                entityToSpawn.setYBodyRot(0);
                entityToSpawn.setYHeadRot(0);
                entityToSpawn.setDeltaMovement(0, 0, 0);
                if (entityToSpawn instanceof Mob _mobToSpawn)
                    _mobToSpawn.finalizeSpawn(_level, _level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                world.addFreshEntity(entityToSpawn);
            }
            if (world instanceof ServerLevel _level)
                _level.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, x, y, z, 900, 0, 0, 0, 1);
        });
    }
}