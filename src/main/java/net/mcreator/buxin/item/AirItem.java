
package net.mcreator.buxin.item;

import net.mcreator.buxin.item.father.CustomEnchantableItem;
import net.minecraft.world.item.Rarity;

public class AirItem extends CustomEnchantableItem {
	public AirItem() {
		super(new CustomEnchantableItem.Properties().stacksTo(1).rarity(Rarity.COMMON));
	}
}
