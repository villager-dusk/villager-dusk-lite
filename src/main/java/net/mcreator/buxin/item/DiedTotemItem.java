package net.mcreator.buxin.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.mcreator.buxin.item.father.CustomEnchantableItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import net.mcreator.buxin.procedures.DiedTotemDangWuPinZaiShouShangMeiKeFaShengProcedure;

import java.util.List;

public class DiedTotemItem extends CustomEnchantableItem {
    public DiedTotemItem() {
        super(new CustomEnchantableItem.Properties().stacksTo(6).rarity(Rarity.RARE));
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        //list.add(Component.literal("\u66FF\u6B7B\u56FE\u817E"));
    }

    @Override
    public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
        if (selected)
            DiedTotemDangWuPinZaiShouShangMeiKeFaShengProcedure.execute(world, entity.getX(), entity.getY(), entity.getZ(), entity);
    }
}