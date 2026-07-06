
package net.mcreator.buxin.item.model;

import net.mcreator.buxin.item.SuperSnakeSwordItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import net.minecraft.world.item.Item;

public class SuperSnakeSwordItemModel extends DefaultedItemGeoModel<SuperSnakeSwordItem> {
	public SuperSnakeSwordItemModel() {
		super(new ResourceLocation("buxin", "super_snake_sword"));
	}

	@Override
	public ResourceLocation getAnimationResource(SuperSnakeSwordItem animatable) {
		return new ResourceLocation("buxin", "animations/super_snake_sword.animation.json");
	}

	@Override
	public ResourceLocation getTextureResource(SuperSnakeSwordItem animatable) {
		return new ResourceLocation("buxin", "textures/items/super_snake_sword_2.png");
	}
}
