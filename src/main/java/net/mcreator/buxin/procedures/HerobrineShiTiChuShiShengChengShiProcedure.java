package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.gameasset.BuxinAnimations;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.mcreator.buxin.my_method.Method_114514;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class HerobrineShiTiChuShiShengChengShiProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null || world.isClientSide())
            return;
        entity.getPersistentData().putDouble("battle", 0);
        entity.getPersistentData().putBoolean("isbuxinentity", true);
        if (world instanceof Level _level) {
            if (!_level.isClientSide()) {
                _level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:bu_shi")), SoundSource.NEUTRAL, 1, 1);
            } else {
                _level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:bu_shi")), SoundSource.NEUTRAL, 1, 1, false);
            }
        }
        Method_114514.send_message_to_all_over_the_world(entity.level, "<Herobrine3> You are so weak.You are nothing ! ");
        Method_114514.herobrine_born(entity);
        BuxinMod.queueServerWork(45, () -> {
            AnimationPlayer.playAnimation(entity, BuxinAnimations.DIG);
        });
    }
}