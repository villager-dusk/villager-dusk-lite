
package net.mcreator.buxin.procedures;

import com.mojang.authlib.GameProfile;
import net.mcreator.buxin.entity.AlexEntity;
import net.mcreator.buxin.entity.Niubi6Entity;
import net.mcreator.buxin.init.BuxinModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.server.ServerLifecycleHooks;

public class IsBuxinEntityProcedure {
    public static void execute(Entity entity) {
        if (entity == null)
            return;
        entity.getPersistentData().putDouble("battle", 0);
        entity.getPersistentData().putBoolean("isbuxinentity", true);
        if (entity instanceof IronGolem) {
            if (Math.random() > 0.5) {
                ItemStack mainHand = Items.IRON_INGOT.getDefaultInstance();
                if (Math.random() > 0.5) {
                    mainHand.enchant(Enchantments.SHARPNESS, 1);
                }
                entity.setItemSlot(EquipmentSlot.MAINHAND, mainHand);
            }
            if (Math.random() > 0.5) {
                ItemStack head = BuxinModItems.CUN_MIN_TOU_KUI_HELMET.get().getDefaultInstance();
                if (Math.random() > 0.5) {
                    head.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 4);
                }
                entity.setItemSlot(EquipmentSlot.HEAD, head);
            }
        }
        if (entity instanceof Niubi6Entity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            ItemStack legs = livingEntity.getItemBySlot(EquipmentSlot.LEGS).copy();
            if (!legs.isEmpty()) {
                legs.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 1);
                livingEntity.setItemSlot(EquipmentSlot.LEGS, legs);
            }
            ItemStack chest = livingEntity.getItemBySlot(EquipmentSlot.CHEST).copy();
            if (!chest.isEmpty()) {
                chest.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 1);
                livingEntity.setItemSlot(EquipmentSlot.CHEST, chest);
            }
            ItemStack mainHand = livingEntity.getMainHandItem().copy();
            if (!mainHand.isEmpty()) {
                mainHand.enchant(Enchantments.SHARPNESS, 1);
                livingEntity.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, mainHand);
            }
            ItemStack offhand = livingEntity.getOffhandItem().copy();
            if (!offhand.isEmpty()) {
                offhand.enchant(Enchantments.SHARPNESS, 1);
                livingEntity.setItemInHand(net.minecraft.world.InteractionHand.OFF_HAND, offhand);
            }
        }
    }
}
