
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.init.BuxinModItems;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Niubi14DangShiTiShouShangShiProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourcentity) {
        if (entity == null || sourcentity == null)
            return;
        try {
            double attack = 0;
            if (entity.getPersistentData().getBoolean("abcd") == true) {
                if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) >= 200) {
                    {
                        final Vec3 _center = new Vec3(x, y, z);
                        List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                                .collect(Collectors.toList());
                    }
                } else {
                    {
                        final Vec3 _center = new Vec3(x, y, z);
                        List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(6 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                                .collect(Collectors.toList());
                        for (Entity entityiterator : _entfound) {
                            if ((entityiterator == entity) == false) {
                                Entity target = entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null;
                                if (entityiterator == target) {
                                    if (entity instanceof Mob _entity && target != null) {
                                        _entity.getNavigation().moveTo(target.getX(), target.getY(), target.getZ(), 1.2);
                                    }
                                }
                            }
                        }
                    }
                }
                if (world.getLevelData().getGameRules().getBoolean(GameRules.RULE_ANNOUNCE_ADVANCEMENTS) == true) {
                    entity.getPersistentData().putDouble("timer", (entity.getPersistentData().getDouble("timer") + 1));
                    if (entity.getPersistentData().getDouble("timer") == 80) {
                        entity.getPersistentData().putDouble("timer", 0);
                        attack = Mth.nextInt(RandomSource.create(), 1, 4);
                        {
                            final Vec3 _center = new Vec3(x, y, z);
                            List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(64 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                                    .collect(Collectors.toList());
                        }
                    }
                    {
                        final Vec3 _center = new Vec3(x, y, z);
                        List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(5 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                                .collect(Collectors.toList());
                        for (Entity entityiterator : _entfound) {
                            Entity target = entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null;
                            if (target == entityiterator) {
                                if (attack == 4) {
                                    ItemStack mainHand = entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY;
                                    if (mainHand.is(BuxinModItems.LEGENDARY_SWORD.get())) {
                                        if (entity instanceof LivingEntity _entity) {
                                            ItemStack _setstack = new ItemStack(Items.BOW);
                                            _setstack.setCount(1);
                                            _entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
                                            if (_entity instanceof Player _player)
                                                _player.getInventory().setChanged();
                                        }
                                        BuxinMod.queueServerWork(95, () -> {
                                            if (entity instanceof LivingEntity _entity) {
                                                ItemStack _setstack = new ItemStack(BuxinModItems.LEGENDARY_SWORD.get());
                                                _setstack.setCount(1);
                                                _entity.setItemInHand(InteractionHand.OFF_HAND, _setstack);
                                                if (_entity instanceof Player _player)
                                                    _player.getInventory().setChanged();
                                            }
                                        });
                                    }
                                    if (mainHand.is(BuxinModItems.NIUBI_15.get())) {
                                        if (entity instanceof LivingEntity _entity) {
                                            ItemStack _setstack = new ItemStack(BuxinModItems.WOOPIE.get());
                                            _setstack.setCount(1);
                                            _entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
                                            if (_entity instanceof Player _player)
                                                _player.getInventory().setChanged();
                                        }
                                        BuxinMod.queueServerWork(95, () -> {
                                            if (entity instanceof LivingEntity _entity) {
                                                ItemStack _setstack = new ItemStack(BuxinModItems.WOOPIE.get());
                                                _setstack.setCount(1);
                                                _entity.setItemInHand(InteractionHand.OFF_HAND, _setstack);
                                                if (_entity instanceof Player _player)
                                                    _player.getInventory().setChanged();
                                            }
                                        });
                                    }
                                    if (mainHand.is(BuxinModItems.WOOR_DOOR.get())) {
                                        if (entity instanceof LivingEntity _entity) {
                                            ItemStack _setstack = new ItemStack(Items.BOW);
                                            _setstack.setCount(1);
                                            _entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
                                            if (_entity instanceof Player _player)
                                                _player.getInventory().setChanged();
                                        }
                                        BuxinMod.queueServerWork(95, () -> {
                                            if (entity instanceof LivingEntity _entity) {
                                                ItemStack _setstack = new ItemStack(BuxinModItems.WOOPIE.get());
                                                _setstack.setCount(1);
                                                _entity.setItemInHand(InteractionHand.OFF_HAND, _setstack);
                                                if (_entity instanceof Player _player)
                                                    _player.getInventory().setChanged();
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
    }
}
