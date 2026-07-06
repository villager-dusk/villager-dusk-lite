
package net.mcreator.buxin.item;

import net.mcreator.buxin.item.father.CustomEnchantableItem;
import net.mcreator.buxin.procedures.Niubi26DangWuPinZaiShouShangMeiKeFaShengProcedure;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = "buxin", value = Dist.CLIENT)
public class Niubi26Item extends CustomEnchantableItem {
    public Niubi26Item() {
        // Forge 47.4.10 要求使用 CreativeModeTabs 而非废弃的 CreativeModeTab 静态字段
        super(new CustomEnchantableItem.Properties()
                
                .durability(15)
                .rarity(Rarity.COMMON));
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        //list.add(Component.literal("\u5DE5\u4F5C\u53F0"));
    }

    @Override
    public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
        if (selected)
            Niubi26DangWuPinZaiShouShangMeiKeFaShengProcedure.execute(entity); // 1.20.1 中需传入 world 参数（如原 procedure 需适配）
    }
}
