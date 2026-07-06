
package net.mcreator.buxin.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.mcreator.buxin.item.father.CustomEnchantableItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import net.mcreator.buxin.procedures.YumijuanWanJiaWanChengShiYongWuPinShiProcedure;
import net.mcreator.buxin.procedures.YumijuanDangWuPinZaiBeiBaoZhongMeiKeFaShengProcedure;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class HaiNanYeZhiItem extends CustomEnchantableItem {
    public HaiNanYeZhiItem() {
        super(new CustomEnchantableItem.Properties()
                .stacksTo(64)
                .rarity(Rarity.COMMON)
                .food((new FoodProperties.Builder()).nutrition(114).saturationMod(20f).alwaysEat().meat().build()));
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemstack, Level world, LivingEntity entity) {
        ItemStack retval = super.finishUsingItem(itemstack, world, entity);
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();

        YumijuanWanJiaWanChengShiYongWuPinShiProcedure.execute(entity);
        return retval;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isFoil(ItemStack itemstack) {
        return true;
    }
}
