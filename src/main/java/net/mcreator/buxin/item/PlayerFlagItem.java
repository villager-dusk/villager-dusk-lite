
package net.mcreator.buxin.item;

import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.item.father.CustomEnchantableItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

import java.lang.reflect.Method;

public class PlayerFlagItem extends CustomEnchantableItem {
    public PlayerFlagItem() {
        super(new Properties().stacksTo(1).rarity(Rarity.COMMON));
    }
/*
	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
		InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
		ItemStack itemstack = ar.getObject();
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		if(!world.isClientSide){
			if(true && entity.getItemBySlot(EquipmentSlot.HEAD).getItem() == BuxinModItems.JOINPLAYERTEAMITEM.get()){
				Method_114514.entity_use_command(entity,"/tp @e[team = player] " + entity.getDisplayName().getString());
				Method_114514.entity_use_command(entity,"/title @s title \"集结成功！\"");
				if(entity.getMainHandItem().getItem() == BuxinModItems.PLAYERFLAG.get()){
					entity.getCooldowns().addCooldown(entity.getMainHandItem().getItem(), 2000);
				}
				if(entity.getOffhandItem().getItem() == BuxinModItems.PLAYERFLAG.get()){
					entity.getCooldowns().addCooldown(entity.getOffhandItem().getItem(), 2000);
				}
			}
		}
		return ar;
	}

 */
}
