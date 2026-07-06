
package net.mcreator.buxin.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;

public class ZhanshenzhirenemoShiTiChuShiSwhengChengShiProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null || world.isClientSide())
            return;
        
        if (world instanceof Level _level) {
            if (!_level.isClientSide()) {
                _level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:spawn")), SoundSource.NEUTRAL, 1, 1);
            }
        }

        ItemStack feet = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY);
        if (!feet.isEmpty()) {
            feet.enchant(ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation("epicfight:all_damage_protection")), 5);
        }
        ItemStack legs = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY);
        if (!legs.isEmpty()) {
            legs.enchant(ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation("epicfight:all_damage_protection")), 5);
        }
        ItemStack chest = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY);
        if (!chest.isEmpty()) {
            chest.enchant(ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation("epicfight:all_damage_protection")), 5);
        }
        ItemStack head = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY);
        if (!head.isEmpty()) {
            head.enchant(ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation("epicfight:all_damage_protection")), 5);
        }

        entity.getPersistentData().putDouble("battle", 0);
        entity.getPersistentData().putBoolean("isbuxinentity", true);
    }
}
