
package net.mcreator.buxin.item;

import net.mcreator.buxin.item.father.CustomEnchantableItem;
import net.mcreator.buxin.procedures.YumijuanWanJiaWanChengShiYongWuPinShiProcedure;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;

public class YumijuanItem extends CustomEnchantableItem {
    public YumijuanItem() {
        super(new CustomEnchantableItem.Properties().stacksTo(64).rarity(Rarity.COMMON).food((new FoodProperties.Builder()).nutrition(114).saturationMod(20f).alwaysEat().meat().build()));
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        //list.add(Component.literal("\u542C\u8BF4\u5403\u4E86\u4EE5\u540E\u5C31\u80FD\u5355\u6311Herobrine!"));
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
}
