
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.init.BuxinModMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class ZanZhu5ShiTiChuShiShengChengShiProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null || world.isClientSide())
            return;

        if (world instanceof Level _level) {
            if (!_level.isClientSide()) {
                _level.playSound(null, new BlockPos((int) x, (int) y, (int) z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:npc1")), SoundSource.NEUTRAL, 1, 1);
            } else {
                _level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:npc1")), SoundSource.NEUTRAL, 1, 1, false);
            }
        }

        BuxinMod.queueServerWork(18, () -> {
            if (world instanceof Level _level && !_level.isClientSide())
                _level.explode(null, x, y, z, 3, Level.ExplosionInteraction.NONE);
        });

        if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
            _entity.addEffect(new MobEffectInstance(BuxinModMobEffects.ZUANSHI.get(), 900, 3));

        entity.getPersistentData().putDouble("battle", 0);
        entity.getPersistentData().putBoolean("isbuxinentity", true);
    }
}
