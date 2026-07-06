
package net.mcreator.buxin.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.config.common.VFXParticleConfig;
import net.mcreator.buxin.entity.woopie.WoopieEntity;
import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.item.father.CustomEnchantableItem;
import net.mcreator.buxin.my_method.VFXTool;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class WoopieItem extends CustomEnchantableItem {
	public WoopieItem() {
		super(new CustomEnchantableItem.Properties().stacksTo(1).rarity(Rarity.COMMON));
	}

	@Override
	public int getUseDuration(ItemStack itemstack) {
		return 9999;
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
		if (equipmentSlot == EquipmentSlot.MAINHAND) {
			ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.putAll(super.getDefaultAttributeModifiers(equipmentSlot));
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Item modifier", 14d, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Item modifier", -2.4, AttributeModifier.Operation.ADDITION));
			return builder.build();
		}
		return super.getDefaultAttributeModifiers(equipmentSlot);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isFoil(ItemStack itemstack) {
		return true;
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		list.add(Component.literal("Woopie"));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		InteractionResultHolder<ItemStack> ar = super.use(world, player, hand);
		if (!(player.getMainHandItem().getItem() == BuxinModItems.JUE_XING_ZHAN_SHEN_ZHI_REN.get()) && !(player.getMainHandItem().getItem() == BuxinModItems.NIUBI_2DANSHOU.get()) && !(player.getMainHandItem().getItem() == BuxinModItems.LEGENDARY_SWORD.get())) {
			ItemStack itemstack = ar.getObject();
			double x = player.getX();
			double y = player.getY();
			double z = player.getZ();

			if (!(world instanceof ServerLevel server)) {
				return InteractionResultHolder.pass(itemstack);
			}

			if (player.getPersistentData().contains("WoopieUUID")) {
				return InteractionResultHolder.pass(itemstack);
			}

			var look = player.getLookAngle();
			double sx = player.getX() + look.x;
			double sy = player.getEyeY() - 0.2;
			double sz = player.getZ() + look.z;
			if (VFXParticleConfig.VFXParticleConfig.get() && BuxinMod.isWindows()) {
				VFXTool.addVFXParticle(player.position(), BuxinMod.MODID, "woopie", player.level());
			}
			WoopieEntity sword = BuxinModEntities.WOOPIE.get().create(server);
			if (sword == null) {
				return InteractionResultHolder.fail(itemstack);
			}

			sword.moveTo(sx, sy, sz, player.getYRot(), player.getXRot());
			sword.setPlayer(player);
			sword.setPlayerUUID(player.getUUID());
			sword.setReturnGameTime(server.getGameTime() + 200L);
			BlockPos blockpos = sword.blockPosition();
			server.addFreshEntity(sword);
			sword.setItemInHand(InteractionHand.MAIN_HAND, itemstack.copy());

			player.setItemInHand(hand, ItemStack.EMPTY);
			player.getCooldowns().addCooldown(this, 10);
			player.getPersistentData().putUUID("WoopieUUID", sword.getUUID());
		}
		return ar;
	}
}
