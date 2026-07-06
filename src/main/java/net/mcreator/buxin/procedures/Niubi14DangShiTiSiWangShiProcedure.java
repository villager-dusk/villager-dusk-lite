
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.entity.Niubi14Entity;
import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.my_method.Method_114514;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class Niubi14DangShiTiSiWangShiProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null || world.isClientSide())
            return;
        if(Math.random() > 0.25) {

            Method_114514.play_sound(world,x,y,z,"buxin:steve_no");
            BuxinMod.queueServerWork(5, () -> {
                if (world instanceof Level _level && !_level.isClientSide()) {
                    ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY));
                    entityToSpawn.setPickUpDelay(10);
                    entityToSpawn.setUnlimitedLifetime();
                    _level.addFreshEntity(entityToSpawn);
                }
                if (world instanceof Level _level && !_level.isClientSide()) {
                    ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY));
                    entityToSpawn.setPickUpDelay(10);
                    entityToSpawn.setUnlimitedLifetime();
                    _level.addFreshEntity(entityToSpawn);
                }
                if (world instanceof Level _level && !_level.isClientSide()) {
                    ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.DIAMOND));
                    entityToSpawn.setPickUpDelay(10);
                    entityToSpawn.setUnlimitedLifetime();
                    _level.addFreshEntity(entityToSpawn);
                }
                if (world instanceof Level _level && !_level.isClientSide()) {
                    ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(BuxinModItems.LEGENDARY_SWORD.get()));
                    entityToSpawn.setPickUpDelay(10);
                    entityToSpawn.setUnlimitedLifetime();
                    _level.addFreshEntity(entityToSpawn);
                }
                BuxinMod.queueServerWork(5, () -> {
                    if (world instanceof Level _level && !_level.isClientSide()) {
                        ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(BuxinModItems.DIAMOND_2.get()));
                        entityToSpawn.setPickUpDelay(10);
                        entityToSpawn.setUnlimitedLifetime();
                        _level.addFreshEntity(entityToSpawn);
                    }
                    if (world instanceof Level _level && !_level.isClientSide()) {
                        ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(BuxinModItems.YUMIJUAN.get()));
                        entityToSpawn.setPickUpDelay(10);
                        entityToSpawn.setUnlimitedLifetime();
                        _level.addFreshEntity(entityToSpawn);
                    }
                    if (world instanceof Level _level && !_level.isClientSide()) {
                        ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(BuxinModItems.DIAMOND_2.get()));
                        entityToSpawn.setPickUpDelay(10);
                        entityToSpawn.setUnlimitedLifetime();
                        _level.addFreshEntity(entityToSpawn);
                    }
                    if (world instanceof Level _level && !_level.isClientSide()) {
                        ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.DIAMOND));
                        entityToSpawn.setPickUpDelay(10);
                        entityToSpawn.setUnlimitedLifetime();
                        _level.addFreshEntity(entityToSpawn);
                    }
                    BuxinMod.queueServerWork(27, () -> {
                        if (world instanceof Level _level && !_level.isClientSide()) {
                            ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(BuxinModItems.DIAMOND_2.get()));
                            entityToSpawn.setPickUpDelay(10);
                            entityToSpawn.setUnlimitedLifetime();
                            _level.addFreshEntity(entityToSpawn);
                        }
                        if (world instanceof Level _level && !_level.isClientSide()) {
                            ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(BuxinModItems.DIAMOND_2.get()));
                            entityToSpawn.setPickUpDelay(10);
                            entityToSpawn.setUnlimitedLifetime();
                            _level.addFreshEntity(entityToSpawn);
                        }
                        if (world instanceof Level _level && !_level.isClientSide()) {
                            ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));
                            entityToSpawn.setPickUpDelay(10);
                            entityToSpawn.setUnlimitedLifetime();
                            _level.addFreshEntity(entityToSpawn);
                        }
                        if (world instanceof Level _level && !_level.isClientSide()) {
                            ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.DIAMOND));
                            entityToSpawn.setPickUpDelay(10);
                            entityToSpawn.setUnlimitedLifetime();
                            _level.addFreshEntity(entityToSpawn);
                        }
                        if (world instanceof Level _level && !_level.isClientSide()) {
                            ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));
                            entityToSpawn.setPickUpDelay(10);
                            entityToSpawn.setUnlimitedLifetime();
                            _level.addFreshEntity(entityToSpawn);
                        }
                    });
                });
            });
        } else {
            BuxinMod.queueServerWork(100, () -> {
                if (world instanceof ServerLevel _level) {
                    Entity entityToSpawn = new Niubi14Entity(BuxinModEntities.NIUBI_14.get(), _level);
                    entityToSpawn.moveTo(entity.getX() + 0, entity.getY(), entity.getZ() + 0, world.getRandom().nextFloat() * 360F, 0);
                    if (entityToSpawn instanceof Mob _mobToSpawn)
                        _mobToSpawn.finalizeSpawn(_level, _level.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                    _level.addFreshEntity(entityToSpawn);
                }
            });
        }
    }
}
