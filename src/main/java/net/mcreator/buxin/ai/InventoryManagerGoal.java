
package net.mcreator.buxin.ai;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.ChestBlockEntity;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class InventoryManagerGoal extends Goal {
    private final PathfinderMob entity;
    private BlockPos chestPos;
    private int cooldown;
    private int operationTime;
    public InventoryManagerGoal(PathfinderMob entity) {
        this.entity = entity;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (cooldown > 0) {
            cooldown--;
            return false;
        }

        if (entity.level().isNight()) {
            return false;
        }

        chestPos = findNearestChestWithItems();
        return chestPos != null;
    }

    @Override
    public void start() {
        operationTime = 0;
        Level level = this.entity.level();

        if (!level.isClientSide() && entity.getServer() != null) {
            entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(entity, entity.position(), entity.getRotationVector(), level instanceof ServerLevel ? (ServerLevel) level : null, 6,
                    entity.getName().getString(), entity.getDisplayName(), entity.getServer(), entity), "/indestructible @s play \"buxin:biped/combat/dig\" 0.5 2");
        }
        if (!level.isClientSide() && entity.getServer() != null) {
            entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(entity, entity.position(), entity.getRotationVector(), level instanceof ServerLevel ? (ServerLevel) level : null, 6,
                    entity.getName().getString(), entity.getDisplayName(), entity.getServer(), entity), "/playsound minecraft:block.chest.open ambient @a");
        }
        entity.getNavigation().moveTo(chestPos.getX(), chestPos.getY(), chestPos.getZ(), 1.4);
    }

    @Override
    public void tick() {
        if (chestPos == null) return;

        double distance = entity.distanceToSqr(chestPos.getX(), chestPos.getY(), chestPos.getZ());

        if (distance < 9.0) {
            entity.getNavigation().stop();
            entity.getLookControl().setLookAt(chestPos.getX(), chestPos.getY(), chestPos.getZ());

            operationTime++;
            if (operationTime >= 25) {
                lootChestAndEquip();
                cooldown = 100;
                stop();
            }
        } else {
            entity.getNavigation().moveTo(chestPos.getX(), chestPos.getY(), chestPos.getZ(), 1.0);
        }
    }

    @Override
    public void stop() {
        chestPos = null;
        operationTime = 0;
        Level level = this.entity.level();
        if (!level.isClientSide() && entity.getServer() != null) {
            entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(entity, entity.position(), entity.getRotationVector(), level instanceof ServerLevel ? (ServerLevel) level : null, 6,
                    entity.getName().getString(), entity.getDisplayName(), entity.getServer(), entity), "/indestructible @s play \"buxin:biped/combat/dig\" 0.5 2");
        }
        if (!level.isClientSide() && entity.getServer() != null) {
            entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(entity, entity.position(), entity.getRotationVector(), level instanceof ServerLevel ? (ServerLevel) level : null, 6,
                    entity.getName().getString(), entity.getDisplayName(), entity.getServer(), entity), "/playsound minecraft:block.chest.close ambient @a");
        }
        entity.getNavigation().stop();
    }

    private void lootChestAndEquip() {
        Level level = this.entity.level();
        if (level.getBlockState(chestPos).is(Blocks.CHEST) &&
                level.getBlockEntity(chestPos) instanceof ChestBlockEntity chest) {

            List<ItemStack> lootedItems = takeAllItemsFromChest(chest);

            equipBetterItems(lootedItems);

            returnUnwantedItemsToChest(chest, lootedItems);
        }
    }

    private List<ItemStack> takeAllItemsFromChest(ChestBlockEntity chest) {
        List<ItemStack> items = new ArrayList<>();

        for (int i = 0; i < chest.getContainerSize(); i++) {
            ItemStack stack = chest.getItem(i);
            if (!stack.isEmpty()) {
                items.add(stack.copy());
                chest.setItem(i, ItemStack.EMPTY);
            }
        }

        return items;
    }

    private void equipBetterItems(List<ItemStack> items) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() == EquipmentSlot.Type.ARMOR) {
                equipBestArmorForSlot(items, slot);
            }
        }

        equipBestMainHandItem(items);

        equipBestOffHandItem(items);
    }

    private void equipBestArmorForSlot(List<ItemStack> items, EquipmentSlot slot) {
        ItemStack currentItem = entity.getItemBySlot(slot);
        float currentDefense = getArmorDefense(currentItem);

        ItemStack bestArmor = null;
        float bestDefense = currentDefense;
        int bestIndex = -1;

        for (int i = 0; i < items.size(); i++) {
            ItemStack item = items.get(i);
            if (isArmorForSlot(item, slot)) {
                float defense = getArmorDefense(item);
                if (defense > bestDefense) {
                    bestDefense = defense;
                    bestArmor = item;
                    bestIndex = i;
                }
            }
        }

        if (bestArmor != null) {
            if (!currentItem.isEmpty()) {
                items.add(currentItem.copy());
            }
            entity.setItemSlot(slot, bestArmor.copy());
            if (bestIndex != -1) {
                items.set(bestIndex, ItemStack.EMPTY);
            }

            entity.playSound(net.minecraft.sounds.SoundEvents.ARMOR_EQUIP_GENERIC, 1.0F, 1.0F);
        }
    }

    private void equipBestMainHandItem(List<ItemStack> items) {
        ItemStack currentItem = entity.getItemBySlot(EquipmentSlot.MAINHAND);
        float currentDamage = getItemDamage(currentItem);

        ItemStack bestWeapon = null;
        float bestDamage = currentDamage;
        int bestIndex = -1;

        for (int i = 0; i < items.size(); i++) {
            ItemStack item = items.get(i);
            if (isWeapon(item)) {
                float damage = getItemDamage(item);
                if (damage > bestDamage) {
                    bestDamage = damage;
                    bestWeapon = item;
                    bestIndex = i;
                }
            }
        }

        if (bestWeapon != null) {
            if (!currentItem.isEmpty()) {
                items.add(currentItem.copy());
            }

            entity.setItemSlot(EquipmentSlot.MAINHAND, bestWeapon.copy());

            if (bestIndex != -1) {
                items.set(bestIndex, ItemStack.EMPTY);
            }
        }
    }
    private void equipBestOffHandItem(List<ItemStack> items) {
        ItemStack currentItem = entity.getItemBySlot(EquipmentSlot.OFFHAND);
        float currentDamage = getItemDamage(currentItem);

        ItemStack bestWeapon = null;
        float bestDamage = currentDamage;
        int bestIndex = -1;

        for (int i = 0; i < items.size(); i++) {
            ItemStack item = items.get(i);
            if (isWeapon(item)) {
                float damage = getItemDamage(item);
                if (damage > bestDamage) {
                    bestDamage = damage;
                    bestWeapon = item;
                    bestIndex = i;
                }
            }
        }
        if (bestWeapon != null) {
            if (!currentItem.isEmpty()) {
                items.add(currentItem.copy());
            }

            entity.setItemSlot(EquipmentSlot.OFFHAND, bestWeapon.copy());

            if (bestIndex != -1) {
                items.set(bestIndex, ItemStack.EMPTY);
            }
        }
    }

    private void returnUnwantedItemsToChest(ChestBlockEntity chest, List<ItemStack> items) {
        for (ItemStack item : items) {
            if (!item.isEmpty()) {
                for (int i = 0; i < chest.getContainerSize(); i++) {
                    if (chest.getItem(i).isEmpty()) {
                        chest.setItem(i, item.copy());
                        break;
                    }
                }
            }
        }
    }

    private boolean isArmorForSlot(ItemStack item, EquipmentSlot slot) {
        return !item.isEmpty() &&
                item.getItem() instanceof ArmorItem &&
                ((ArmorItem) item.getItem()).getEquipmentSlot() == slot;
    }

    private float getArmorDefense(ItemStack armor) {
        if (armor.isEmpty() || !(armor.getItem() instanceof ArmorItem)) {
            return 0.0f;
        }
        return ((ArmorItem) armor.getItem()).getDefense();
    }

    private boolean isWeapon(ItemStack item) {
        if (item.isEmpty()) return false;
        return item.getItem() instanceof net.minecraft.world.item.SwordItem ||
                item.getItem() instanceof net.minecraft.world.item.AxeItem ||
                item.getItem() instanceof net.minecraft.world.item.TridentItem;
    }

    private float getItemDamage(ItemStack item) {
        if (item.isEmpty()) return 0.0f;
        if (item.getItem() instanceof net.minecraft.world.item.TieredItem) {
            net.minecraft.world.item.Tier tier = ((net.minecraft.world.item.TieredItem) item.getItem()).getTier();
            if (tier == net.minecraft.world.item.Tiers.NETHERITE) return 10.0f;
            if (tier == net.minecraft.world.item.Tiers.DIAMOND) return 8.0f;
            if (tier == net.minecraft.world.item.Tiers.IRON) return 6.0f;
            if (tier == net.minecraft.world.item.Tiers.STONE) return 5.0f;
            if (tier == net.minecraft.world.item.Tiers.GOLD) return 4.0f;
            if (tier == net.minecraft.world.item.Tiers.WOOD) return 3.0f;
        }
        return 1.0f;
    }

    private BlockPos findNearestChestWithItems() {
        BlockPos entityPos = entity.blockPosition();
        Level level = this.entity.level();
        for (int x = -30; x <= 30; x++) {
            for (int y = -15; y <= 15; y++) {
                for (int z = -30; z <= 30; z++) {
                    BlockPos checkPos = entityPos.offset(x, y, z);
                    if (level.getBlockState(checkPos).is(Blocks.CHEST) &&
                        level.getBlockEntity(checkPos) instanceof ChestBlockEntity chest) {
                        if (chestHasItems(chest)) {
                            return checkPos;
                        }
                    }
                }
            }
        }
        return null;
    }

    private boolean chestHasItems(ChestBlockEntity chest) {
        for (int i = 0; i < chest.getContainerSize(); i++) {
            if (!chest.getItem(i).isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
