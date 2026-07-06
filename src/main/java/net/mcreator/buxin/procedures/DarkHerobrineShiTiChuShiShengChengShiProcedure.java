
package net.mcreator.buxin.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;

public class DarkHerobrineShiTiChuShiShengChengShiProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null || world.isClientSide())
            return;
        if (world instanceof ServerLevel _level)
            _level.sendParticles(ParticleTypes.SMOKE, x, y, z, 500, 1, 1, 1, 1);
        if (world instanceof ServerLevel _level) {
            LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
            entityToSpawn.moveTo(Vec3.atBottomCenterOf(new BlockPos((int)x, (int)y, (int)z)));
            entityToSpawn.setVisualOnly(true);
            _level.addFreshEntity(entityToSpawn);
        }
        entity.getPersistentData().putDouble("battle", 0);
    }
}
