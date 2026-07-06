
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.gameasset.BuxinAnimations;
import net.mcreator.buxin.init.BuxinModMobEffects;
import net.mcreator.buxin.my_method.Method_114514;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.items.ItemHandlerHelper;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.RandomSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.BuxinMod;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Niubi2DangYouJianDianJiKongQiShiShiTiDeWeiZhiProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity == null || world.isClientSide())
            return;
        entity.getPersistentData().putBoolean("player_wake_up", false);
        if (true) {
            if ((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == BuxinModItems.WOOPIE.get()) {
                entity.getPersistentData().putBoolean("player_wake_up", true);
                Random random1 = new Random();
                if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
                    _entity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, (int) 2.6, 100));
                Method_114514.play_sound(entity.level(), entity.getX(), entity.getY(), entity.getZ(), "buxin:attack");
                Method_114514.play_sound(entity.level(), entity.getX(), entity.getY(), entity.getZ(), "buxin:test");
                Method_114514.entity_use_command(entity,"/effect give @s minecraft:strength 10 20");
                BuxinMod.queueServerWork(8, () -> {
                    entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(EntityPatch -> {
                        if (EntityPatch instanceof LivingEntityPatch<?> LivingEntityPatch) {
                            LivingEntityPatch.playAnimationSynchronized(BuxinAnimations.Legendary_Sword_Heavy_Attack, 0F);
                        }
                    });
                });
                BuxinMod.queueServerWork(6,() -> {
                    Method_114514.play_sound(entity,"epicfight:sfx.entity_move",6f,0.7f + new Random().nextFloat(0.25f));
                });
                {
                    final Vec3 _center = new Vec3(x, y, z);
                    List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(5 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).collect(Collectors.toList());
                    for (Entity entityiterator : _entfound) {
                        if ((entity == entityiterator) == false) {
                            if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
                                _entity.addEffect(new MobEffectInstance(BuxinModMobEffects.ENTITY_CANT_BLOCK.get(), 100, 1, false, false));
                        }
                    }
                }

                BuxinMod.queueServerWork(8,() -> {
                    Method_114514.entity_use_command(entity,"/particle minecraft:totem_of_undying ~ ~ ~ 0 0 0 0.5 100");
                });
                if (world instanceof Level _level) {
                    if (!_level.isClientSide()) {
                        _level.playSound(null, new BlockPos((int)x, (int)y, (int)z),
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.totem.use")),
                                SoundSource.NEUTRAL, 1, 1);
                    } else {
                        _level.playLocalSound(x, y, z,
                                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.totem.use")),
                                SoundSource.NEUTRAL, 1, 1, false);
                    }
                }
                if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
                    _entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, 10));
                BuxinMod.queueServerWork(24, () -> {
                    Method_114514.entity_use_command(entity,"/impactful @s shake 34 4 4");
                });
                if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
                    _entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 90, 3));
                if (entity instanceof LivingEntity _entity) {
                    ItemStack _setstack = new ItemStack(BuxinModItems.JUE_XING_ZHAN_SHEN_ZHI_REN.get());
                    _setstack.setCount(1);
                    _entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
                    if (_entity instanceof Player _player)
                        _player.getInventory().setChanged();
                }

                {
                }
                BuxinMod.queueServerWork(80, () -> {
                    if (entity instanceof Player _playerHasItem ? _playerHasItem.getInventory().contains(new ItemStack(BuxinModItems.JUE_XING_ZHAN_SHEN_ZHI_REN.get())) : false) {
                        entity.getPersistentData().putBoolean("player_wake_up", false);
                        if (entity instanceof Player _player) {
                            ItemStack _setstack = new ItemStack(BuxinModItems.NIUBI_2DANSHOU.get());
                            _setstack.setCount(1);
                            ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
                        }
                        if (entity instanceof Player _player)
                            _player.getCooldowns().addCooldown(itemstack.getItem(), 195);
                    }
                });
            }
        }
    }
}
