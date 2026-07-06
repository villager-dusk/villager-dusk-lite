
package net.mcreator.buxin.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.mcreator.buxin.entity.fake_entity.FakeEntity;
import net.mcreator.buxin.item.father.CustomEnchantableItem;
import net.mcreator.buxin.my_method.Method_114514;
import net.mcreator.buxin.procedures.NiubiDangYo3Procedure;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class HuiMieZhiJianItem extends CustomEnchantableItem {
	public HuiMieZhiJianItem() {
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
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Item modifier", 17d, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Item modifier", -2.4, AttributeModifier.Operation.ADDITION));
			return builder.build();
		}
		return super.getDefaultAttributeModifiers(equipmentSlot);
	}
/*
	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
		InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
		ItemStack itemstack = ar.getObject();
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		if(entity instanceof ServerPlayer serverPlayer && true){
			ServerLevel serverLevel = serverPlayer.level();
			Vec3 startPos = serverPlayer.position();
			int particleCount = 5;
			float radius = 3F;
			Vec3 particleOrigin = startPos.subtract(0, 1, 0);
			for (int i = 0; i < particleCount; i++) {
				float angle = (float) i / particleCount * (float) Math.PI * 2;
				float xOffset = radius * (float) Math.cos(angle);
				float zOffset = radius * (float) Math.sin(angle);
				Vec3 particlePos = particleOrigin.add(xOffset, 0, zOffset);
				serverLevel.sendParticles(ParticleTypes.POOF, particlePos.x, particlePos.y + 2, particlePos.z, 20, 0, 0, 0, 0.1);
				Method_114514.play_sound(entity,"entity.item.pickup");
				FakeEntity fakeEntity = new FakeEntity(serverPlayer);
				fakeEntity.setPos(particleOrigin.add(xOffset, 1, zOffset));
				fakeEntity.setCustomName(serverPlayer.getName());
				serverPlayer.level().addFreshEntity(fakeEntity);
			}
			entity.getCooldowns().addCooldown(itemstack.getItem(), 195);
		}
		NiubiDangYo3Procedure.execute();
		return ar;
	}
	
 */

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
		boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
		
		return retval;
	}

	@Override
	public void onCraftedBy(ItemStack itemstack, Level world, Player entity) {
		super.onCraftedBy(itemstack, world, entity);
		
	}
}
