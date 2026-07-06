
package net.mcreator.buxin.procedures;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;

import net.mcreator.buxin.BuxinMod;

public class HuoQbjDangYouJianDianJiKongQiShiShiTiDeWeiZhiProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity == null || world.isClientSide())
            return;
        {
            Entity _shootFrom = entity;
            Level projectileLevel = _shootFrom.level();
            if (!projectileLevel.isClientSide()) {
                Projectile _entityToSpawn = new LargeFireball(EntityType.FIREBALL, projectileLevel);
                _entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
                _entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 2.3f, 5);
                projectileLevel.addFreshEntity(_entityToSpawn);
            }
        }
        BuxinMod.queueServerWork(3, () -> {
            {
                Entity _shootFrom = entity;
                Level projectileLevel = _shootFrom.level();
                if (!projectileLevel.isClientSide()) {
                    Projectile _entityToSpawn = new LargeFireball(EntityType.FIREBALL, projectileLevel);
                    _entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
                    _entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 2.0f, 5);
                    projectileLevel.addFreshEntity(_entityToSpawn);
                }
            }
        });
        if (world instanceof ServerLevel _level)
            _level.sendParticles(ParticleTypes.FLAME, x, y, z, 20, 0, 0, 0, 0.3);
        if (entity instanceof Player _player)
            _player.getCooldowns().addCooldown(itemstack.getItem(), 35);
        world.setBlock(BlockPos.containing(x + 1, y, z + 0), Blocks.FIRE.defaultBlockState(), 3);
        world.setBlock(BlockPos.containing(x + 2, y, z + 0), Blocks.FIRE.defaultBlockState(), 3);
        world.setBlock(BlockPos.containing(x + 3, y, z + 0), Blocks.FIRE.defaultBlockState(), 3);
        world.setBlock(BlockPos.containing(x + 4, y, z + 0), Blocks.FIRE.defaultBlockState(), 3);
        world.setBlock(BlockPos.containing(x + 5, y, z + 0), Blocks.FIRE.defaultBlockState(), 3);
        world.setBlock(BlockPos.containing(x + 6, y, z + 0), Blocks.FIRE.defaultBlockState(), 3);
        world.setBlock(BlockPos.containing(x + 0, y, z + 1), Blocks.FIRE.defaultBlockState(), 3);
        world.setBlock(BlockPos.containing(x + 0, y, z + 2), Blocks.FIRE.defaultBlockState(), 3);
        world.setBlock(BlockPos.containing(x + 0, y, z + 4), Blocks.FIRE.defaultBlockState(), 3);
        world.setBlock(BlockPos.containing(x + 0, y, z + 5), Blocks.FIRE.defaultBlockState(), 3);
        world.setBlock(BlockPos.containing(x + 0, y, z + 6), Blocks.FIRE.defaultBlockState(), 3);
    }
}
