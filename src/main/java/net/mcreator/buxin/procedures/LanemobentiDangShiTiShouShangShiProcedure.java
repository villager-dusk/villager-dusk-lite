
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.init.BuxinModParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class LanemobentiDangShiTiShouShangShiProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null || world.isClientSide())
            return;
        Random random = new Random();
        int value = random.nextInt(3);
        if (Math.random() < 0.6) {
            if (Math.random() > 0.935) {
                if (world instanceof Level _level) {
                    if (!_level.isClientSide()) {
                        _level.playSound(null, new BlockPos((int) x, (int) y, (int) z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:zjdl")), SoundSource.NEUTRAL, 2, 1);
                    } else {
                        _level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:zjdl")), SoundSource.NEUTRAL, 2, 1, false);
                    }
                }
                {
                    final Vec3 _center = new Vec3(x, y, z);
                    List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(6 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                            .collect(Collectors.toList());
                    for (Entity entityiterator : _entfound) {
                        if (world instanceof ServerLevel _level)
                            _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.NIUBI_24.get()), x, y, z, 40, 6, 0, 6, 1);
                        if ((entity == entityiterator) == false) {
                            try {
                                entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(EntityPatch -> {
                                    if (EntityPatch instanceof LivingEntityPatch<?> LivingEntityPatch) {
                                        LivingEntityPatch.playAnimationSynchronized(Animations.WRATHFUL_LIGHTING, 0F);
                                    }
                                });
                            } catch (Exception e) {
                            }
                            if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
                                _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, 3, false, false));
                            if (world instanceof ServerLevel _level) {
                                LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
                                entityToSpawn.setCustomName(Component.literal("b"));
                                entityToSpawn.moveTo(Vec3.atBottomCenterOf(new BlockPos((int) entityiterator.getX(), (int) entityiterator.getY(), (int) entityiterator.getZ())));
                                entityToSpawn.setVisualOnly(true);
                                _level.addFreshEntity(entityToSpawn);
                            }
                            entityiterator.hurt(entityiterator.damageSources().generic(), 20);
                        }
                    }
                }
            }
        }
    }
}
