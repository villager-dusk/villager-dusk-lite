
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.init.BuxinModParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class YingchuihimDangShiTiGengXinKeShiProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
        if (entity == null || sourceentity == null)
            return;
        entity.getPersistentData().putBoolean("ABAB", true);
        if (world instanceof ServerLevel _level)
            _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_22.get()), x, y, z, 1, 1, 1, 1, 1);
        if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < 50) {
            if (entity.getPersistentData().getBoolean("ABAB") == true) {

                if (world instanceof Level _level) {
                    if (!_level.isClientSide()) {
                        _level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:zhao_huan")), SoundSource.NEUTRAL, 1, 1);
                    } else {
                        _level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:zhao_huan")), SoundSource.NEUTRAL, 1, 1, false);
                    }
                }
                sourceentity.startRiding(entity);
                entity.getPersistentData().putBoolean("ABAB", false);
            }
        }
    }
}
