
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.entity.SnakeBydEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class SnakeBydShiTiChuShiShengChengShiProcedure {
    public static void execute(LevelAccessor world, Entity entity) {
        if (entity == null || world.isClientSide())
            return;
        entity.getPersistentData().putBoolean("YH", true);
        if (entity instanceof SnakeBydEntity) {
            ((SnakeBydEntity) entity).setAnimation("animation.snake_sword_skill.idle");
        }
        BuxinMod.queueServerWork((int) 81.6, () -> {
            if (!entity.level().isClientSide())
                entity.discard();
        });
    }
}
