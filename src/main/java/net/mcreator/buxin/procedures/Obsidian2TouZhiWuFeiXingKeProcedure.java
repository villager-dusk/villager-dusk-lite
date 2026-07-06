
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class Obsidian2TouZhiWuFeiXingKeProcedure {
    public static void execute(Entity entity, LevelAccessor world, double x, double y, double z) {
        BuxinMod.queueServerWork(3, () -> {
            if(world != null) {
                world.setBlock(new BlockPos((int)x, (int)y, (int)z), Blocks.OBSIDIAN.defaultBlockState(), 3);
            }

            LevelAccessor _level = world;
            if(Math.random() > 0.5) {
                Entity _ent = entity;
                if (!_ent.level().isClientSide() && _ent.getServer() != null) {
                    _ent.getServer().getCommands().performPrefixedCommand(
                        new CommandSourceStack(
                            null,
                            _ent.position(),
                            _ent.getRotationVector(),
                            _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null,
                            6,
                            _ent.getName().getString(),
                            _ent.getDisplayName(),
                            _ent.getServer(),
                            _ent
                        ),
                        "playsound buxin:metal1 voice @a ~ ~ ~"
                    );
                }
            } else {
                Entity _ent = entity;
            }
        });
        BuxinMod.queueServerWork(40, () -> {
            Entity _ent = entity;
            if (!_ent.level().isClientSide() && _ent.getServer() != null) {
                _ent.getServer().getCommands().performPrefixedCommand(
                    new CommandSourceStack(
                        null,
                        _ent.position(),
                        _ent.getRotationVector(),
                        _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null,
                        6,
                        _ent.getName().getString(),
                        _ent.getDisplayName(),
                        _ent.getServer(),
                        _ent
                    ),
                    "playsound buxin:shi_qu voice @a ~ ~ ~"
                );
            }
            try {
                world.setBlock(new BlockPos((int)x, (int)y, (int)z), Blocks.AIR.defaultBlockState(), 3);
            } catch (Exception e) {
                // No change needed here — exception handling remains valid
            }
        });
    }
}
