
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.init.BuxinModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DiedTotemDangWuPinZaiShouShangMeiKeFaShengProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null || world.isClientSide())
            return;

        if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == BuxinModItems.DIED_TOTEM.get()) {
            if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) <= 1) {
                if (world.isClientSide())
                    Minecraft.getInstance().gameRenderer.displayItemActivation((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY));
                ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY)).shrink(1);
                if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
                    _entity.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 750, 5));
                if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
                    _entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 740, 5));
                if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
                    _entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 740, 5));
                if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
                    _entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 50, 5));
                if (world instanceof Level _level) {
                    if (!_level.isClientSide()) {
                        _level.playSound(null, new BlockPos((int) x, (int) y, (int) z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:ghost_shout")), SoundSource.NEUTRAL, 1, 1);
                    } else {
                        _level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:ghost_shout")), SoundSource.NEUTRAL, 1, 1, false);
                    }
                }
                {
                    final Vec3 _center = new Vec3(x, y, z);
                    List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(4 / 2d), e -> true).stream()
                            .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                            .collect(Collectors.toList());
                    for (Entity entityiterator : _entfound) {
                        if ((entityiterator == entity) == false) {
                            entityiterator.hurt(entityiterator.damageSources().generic(), (float) (entityiterator instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1));
                            if (entity instanceof LivingEntity _entity) {
                                _entity.hurt(_entity.damageSources().generic(), (float) (entityiterator instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1));
                            }
                            if (entity instanceof LivingEntity _entity)
                                _entity.setHealth(0);
                        }
                    }
                }
            }
        }
        if ((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == BuxinModItems.DIED_TOTEM.get()) {
            if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) <= 1) {
                if (world.isClientSide())
                    Minecraft.getInstance().gameRenderer.displayItemActivation((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY));
                ((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY)).shrink(1);
                if (world instanceof Level _level) {
                    if (!_level.isClientSide()) {
                        _level.playSound(null, new BlockPos((int) x, (int) y, (int) z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:ghost_shout")), SoundSource.NEUTRAL, 1, 1);
                    } else {
                        _level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:ghost_shout")), SoundSource.NEUTRAL, 1, 1, false);
                    }
                }
                if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
                    _entity.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 750, 5));
                if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
                    _entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 740, 5));
                if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
                    _entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 740, 5));
                if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
                    _entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 50, 5));
                {
                    final Vec3 _center = new Vec3(x, y, z);
                    List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(4 / 2d), e -> true).stream()
                            .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                            .collect(Collectors.toList());
                    for (Entity entityiterator : _entfound) {
                        if ((entityiterator == entity) == false) {
                            entityiterator.hurt(entityiterator.damageSources().generic(), (float) (entityiterator instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1));
                            if (entity instanceof LivingEntity _entity) {
                                _entity.hurt(_entity.damageSources().generic(), (float) (entityiterator instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1));
                            }
                            if (entity instanceof LivingEntity _entity)
                                _entity.setHealth(0);
                        }
                    }
                }
            }
        }
    }
}
