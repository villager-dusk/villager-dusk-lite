
package net.mcreator.buxin.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.mcreator.buxin.BuxinMod;

public class IceAndFireSwordDangShiTiBeiGaiWuPinJiZhongProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null || world.isClientSide())
            return;
        if (Math.random() > 0.5) {
            entity.setSecondsOnFire(3);
        }
        if (Math.random() < 0.5) {
            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
                _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 999));
            if (world instanceof Level _level) {
                if (!_level.isClientSide()) {
                    _level.playSound(null, new BlockPos((int) x, (int) y, (int) z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.glass.hit")), SoundSource.NEUTRAL, 1, 1);
                } else {
                    _level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.glass.hit")), SoundSource.NEUTRAL, 1, 1, false);
                }
            }
            entity.hurt(new DamageSources(world.registryAccess()).generic(), 3);
            world.setBlock(new BlockPos((int) x, (int) y, (int) z), Blocks.PACKED_ICE.defaultBlockState(), 3);
            world.setBlock(new BlockPos((int) x, (int) (y + 1), (int) z), Blocks.PACKED_ICE.defaultBlockState(), 3);
            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
                _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 999));
            BuxinMod.queueServerWork(40, () -> {
                world.destroyBlock(new BlockPos((int) x, (int) y, (int) z), false);
                world.destroyBlock(new BlockPos((int) x, (int) (y + 1), (int) z), false);
            });
            if (world instanceof Level _level) {
                if (!_level.isClientSide()) {
                    _level.playSound(null, new BlockPos((int) x, (int) y, (int) z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.glass.hit")), SoundSource.NEUTRAL, 1, 1);
                } else {
                    _level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.glass.hit")), SoundSource.NEUTRAL, 1, 1, false);
                }
            }
        }
    }
}
