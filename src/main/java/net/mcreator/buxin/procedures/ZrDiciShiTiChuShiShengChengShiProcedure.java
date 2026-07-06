package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class ZrDiciShiTiChuShiShengChengShiProcedure {
    public static void execute(LevelAccessor world, Entity entity) {
        if (entity == null || world.isClientSide())
            return;
        BuxinMod.queueServerWork(25, () -> {
            if (!entity.level().isClientSide())
                entity.discard();
        });
    }
}