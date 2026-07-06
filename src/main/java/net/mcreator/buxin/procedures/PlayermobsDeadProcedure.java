
package net.mcreator.buxin.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class PlayermobsDeadProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null || world.isClientSide())
			return;
        if (Math.random() < 0.95) {
            if (world instanceof Level _level && !_level.isClientSide()) {
                ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 1), z, (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY));
                entityToSpawn.setPickUpDelay(10);
                _level.addFreshEntity(entityToSpawn);
            }
            if (world instanceof Level _level && !_level.isClientSide()) {
                ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 1), z, (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY));
                entityToSpawn.setPickUpDelay(10);
                entityToSpawn.setUnlimitedLifetime();
                _level.addFreshEntity(entityToSpawn);
            }
            if (world instanceof Level _level && !_level.isClientSide()) {
                ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 1), z, (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY));
                entityToSpawn.setPickUpDelay(10);
                entityToSpawn.setUnlimitedLifetime();
                _level.addFreshEntity(entityToSpawn);
            }
            if (world instanceof Level _level && !_level.isClientSide()) {
                ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 1), z, (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY));
                entityToSpawn.setPickUpDelay(10);
                entityToSpawn.setUnlimitedLifetime();
                _level.addFreshEntity(entityToSpawn);
            }
            if (world instanceof Level _level && !_level.isClientSide()) {
                ItemEntity entityToSpawn = new ItemEntity(_level, x, (y + 1), z, (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY));
                entityToSpawn.setPickUpDelay(10);
                entityToSpawn.setUnlimitedLifetime();
                _level.addFreshEntity(entityToSpawn);
            }
        }
    }
}
