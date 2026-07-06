
package net.mcreator.buxin.item;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.mcreator.buxin.item.father.CustomEnchantableItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.InteractionResult;
import net.minecraft.network.chat.Component;

import net.mcreator.buxin.procedures.SummonXddDangYouJianDianJiFangKuaiShiFangKuaiDeWeiZhiProcedure;

import java.util.List;

public class SummonXddItem extends CustomEnchantableItem {
	public SummonXddItem() {
		super(new CustomEnchantableItem.Properties().stacksTo(64).rarity(Rarity.COMMON));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isFoil(ItemStack itemstack) {
		return true;
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		//list.add(Component.literal("\u53EC\u5524\u201C\u738B\u67D0\u4EBAxdd\""));
	}
}
