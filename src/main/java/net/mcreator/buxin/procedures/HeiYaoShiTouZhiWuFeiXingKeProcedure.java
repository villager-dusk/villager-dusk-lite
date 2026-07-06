
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.init.BuxinModBlocks;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class HeiYaoShiTouZhiWuFeiXingKeProcedure {
    public static void execute(Entity entity, LevelAccessor world, double x, double y, double z) {
        BuxinMod.queueServerWork(3, () -> {
            LevelAccessor _level = world;
            if (Math.random() > 0.5) {
                if (!_level.isClientSide()) {
                    Entity _ent = entity;
                    if (!_ent.level().isClientSide() && _ent.getServer() != null) {
                        _ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(_ent, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 6,
                                _ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "/playsound buxin:metal1 voice @a ~ ~ ~");
                    }
                }
            } else {
                Entity _ent = entity;
                if (!_ent.level().isClientSide() && _ent.getServer() != null) {
                    _ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(_ent, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 6,
                            _ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "/playsound buxin:metal2 voice @a ~ ~ ~");
                }
            }
            try {
                if (entity.isAlive())
                    world.setBlock(new BlockPos((int) x, (int) y, (int) z), BuxinModBlocks.AMY.get().defaultBlockState(), 3);
            } catch (Exception e) {
            }
        });
        BuxinMod.queueServerWork(60, () -> {
            Entity _ent = entity;
            if (!_ent.level().isClientSide() && _ent.getServer() != null) {
                _ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(_ent, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 6,
                        _ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), "/playsound buxin:block_place voice @a ~ ~ ~");
            }
            try {
                if (entity.isAlive())
                    world.setBlock(new BlockPos((int) x, (int) y, (int) z), BuxinModBlocks.AMY.get().defaultBlockState(), 3);
            } catch (Exception e) {
            }
        });
    }
}
