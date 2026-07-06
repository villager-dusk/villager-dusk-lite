
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.init.BuxinModMobEffects;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.BlockPos;

import net.mcreator.buxin.init.BuxinModParticleTypes;
import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.entity.SnakeBydEntity;
import net.mcreator.buxin.BuxinMod;

import java.util.stream.Collectors;
import java.util.List;
import java.util.Comparator;

public class SherenDangYouJianDianJiKongQiShiShiTiDeWeiZhiProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity == null || world.isClientSide())
            return;
        {
            final Vec3 _center = new Vec3(x, y, z);
            List<LivingEntity> _entfound = world.getEntitiesOfClass(LivingEntity.class, new AABB(_center, _center).inflate(14 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                    .toList();
            for (LivingEntity entityiterator : _entfound) {
                if (entity != entityiterator && !(entityiterator instanceof Player player)) {
                    if (world instanceof Level _level) {
                        if (!_level.isClientSide()) {
                            _level.playSound(null, new BlockPos((int) x, (int) y, (int) z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:she_ren")), SoundSource.NEUTRAL, 1, 1);
                        } else {
                            _level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:she_ren")), SoundSource.NEUTRAL, 1, 1, false);
                        }
                    }
                    if (world instanceof Level _level) {
                        if (!_level.isClientSide()) {
                            _level.playSound(null, new BlockPos((int) x, (int) y, (int) z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:dian_hu")), SoundSource.NEUTRAL, (float) 0.5, (float) 0.85);
                        } else {
                            _level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:dian_hu")), SoundSource.NEUTRAL, (float) 0.75, (float) 0.85, false);
                        }
                    }
                    entityiterator.addEffect(new MobEffectInstance(BuxinModMobEffects.ENTITY_CANT_BLOCK.get(), 100, 1, false, false));
                    entityiterator.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 100, false, false));

                    if (world instanceof ServerLevel _level)
                        _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_22.get()), x, y, z, 100, 4, 4, 4, 1);

                    if (!(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString()).equals("minecraft:item")) {
                        if (world instanceof ServerLevel _level) {
                            Mob entityToSpawn = new SnakeBydEntity(BuxinModEntities.SNAKE_BYD.get(), _level);
                            entityToSpawn.moveTo((entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ()), world.getRandom().nextFloat() * 360F, 0);
                            entityToSpawn.finalizeSpawn(_level, _level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                            world.addFreshEntity(entityToSpawn);
                        }
                    }
                    BuxinMod.queueServerWork((int) 23.2, () -> {
                        entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.GENERIC)), 18);
                        BuxinMod.queueServerWork((int) 23.2, () -> {
                            entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.GENERIC)), 17);
                            BuxinMod.queueServerWork((int) 23.2, () -> {
                                entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.GENERIC)), 16);
                            });
                        });
                        if (entity instanceof Player _player)
                            _player.getCooldowns().addCooldown(itemstack.getItem(), 250);
                    });
                }
            }
        }
    }
}
