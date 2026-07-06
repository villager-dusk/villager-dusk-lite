
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.init.BuxinModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;

public class ZhanshenzhirenemoDangShiTiSiWangShiProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null || world.isClientSide())
            return;
        if (world instanceof ServerLevel _level) {
            LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
            entityToSpawn.moveTo(Vec3.atBottomCenterOf(new BlockPos((int)x, (int)y, (int)z)));
            entityToSpawn.setVisualOnly(true);
            _level.addFreshEntity(entityToSpawn);
        }

        BuxinMod.queueServerWork(11, () -> {
            if (world instanceof Level _level && !_level.isClientSide()) {
                ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY));
                entityToSpawn.setPickUpDelay(10);
                entityToSpawn.setUnlimitedLifetime();
                _level.addFreshEntity(entityToSpawn);
            }
            if (world instanceof Level _level && !_level.isClientSide()) {
                ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.TRIDENT));
                entityToSpawn.setPickUpDelay(10);
                entityToSpawn.setUnlimitedLifetime();
                _level.addFreshEntity(entityToSpawn);
            }
            if (world instanceof Level _level && !_level.isClientSide()) {
                ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(Items.TRIDENT));
                entityToSpawn.setPickUpDelay(10);
                entityToSpawn.setUnlimitedLifetime();
                _level.addFreshEntity(entityToSpawn);
            }
            BuxinMod.queueServerWork(21, () -> {
                if (world instanceof Level _level && !_level.isClientSide()) {
                    ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, new ItemStack(BuxinModItems.YUMIJUAN.get()));
                    entityToSpawn.setPickUpDelay(10);
                    entityToSpawn.setUnlimitedLifetime();
                    _level.addFreshEntity(entityToSpawn);
                }
                if (world instanceof Level _level && !_level.isClientSide()) {
                    ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 2), z, (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY));
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
                });
            });
        });
    }
}
