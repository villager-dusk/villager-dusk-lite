
package net.mcreator.buxin.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.mcreator.buxin.item.father.CustomEnchantableItem;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;

import net.mcreator.buxin.procedures.FishingRod2DangYouJianDianJiKongQiShiShiTiDeWeiZhiProcedure;

import java.util.List;

public class FishingRod2Item extends FishingRodItem {
	public FishingRod2Item() {
		super(new CustomEnchantableItem.Properties().durability(100));
	}

	@Override
	public int getEnchantmentValue() {
		return 2;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
		InteractionResultHolder<ItemStack> result = super.use(world, entity, hand);
		FishingRod2DangYouJianDianJiKongQiShiShiTiDeWeiZhiProcedure.execute();
		return result;
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		InteractionResult result = super.useOn(context);
		FishingRod2DangYouJianDianJiKongQiShiShiTiDeWeiZhiProcedure.execute();
		return result;
	}
}
