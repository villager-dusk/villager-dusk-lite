
package net.mcreator.buxin.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.init.BuxinModBlocks;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.item.father.CustomEnchantableItem;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.mcreator.buxin.procedures.ObsDangYouJianDianJiKongQiShiShiTiDeWeiZhiProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;

public class ObsItem extends CustomEnchantableItem {
	public ObsItem() {
		super(new CustomEnchantableItem.Properties().stacksTo(1).rarity(Rarity.COMMON));
	}
	public static void createExpandingExplosions(Entity sourceEntity, int iterations, double baseRadius) {
		if (sourceEntity.level().isClientSide()) return;

		Level level = sourceEntity.level();
		double centerX = sourceEntity.getX();
		double centerY = sourceEntity.getY();
		double centerZ = sourceEntity.getZ();

		for (int i = 1; i <= iterations; i++) {
			final int currentIteration = i;
			final double radius = baseRadius * currentIteration;
			BuxinMod.queueServerWork((int)i,() -> {
				int points = 12 + currentIteration * 2;
				for (int j = 0; j < points; j++) {
					double angle = 2 * Math.PI * j / points;
					double offsetX = Math.cos(angle) * radius * Math.sin(Math.PI / 2);
					double offsetZ = Math.sin(angle) * radius * Math.sin(Math.PI / 2);

					double x = centerX + offsetX;
					double y = centerY;
					double z = centerZ + offsetZ;

					level.setBlock(new BlockPos((int)x, (int)y, (int)z), BuxinModBlocks.DARK_BLOCK_ZHU_2.get().defaultBlockState(), 3);
				}
			});
		}
	}
	@Override
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
		if (equipmentSlot == EquipmentSlot.MAINHAND) {
			ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.putAll(super.getDefaultAttributeModifiers(equipmentSlot));
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Item modifier", 9d, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Item modifier", -2.4, AttributeModifier.Operation.ADDITION));
			return builder.build();
		}
		return super.getDefaultAttributeModifiers(equipmentSlot);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
		InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
		ItemStack itemstack = ar.getObject();
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		if(!entity.level().isClientSide()) {
			if(entity.getMainHandItem().getItem() == BuxinModItems.OBS.get()){
				if (!true) {
					ObsDangYouJianDianJiKongQiShiShiTiDeWeiZhiProcedure.execute(entity, entity.level());
				} else {
					BuxinMod.queueServerWork(6, () -> {
						entity.getCooldowns().addCooldown(itemstack.getItem(), 70);
						createExpandingExplosions(entity, 3, 3);
						final Vec3 _center0 = new Vec3(entity.getX(), entity.getY(), entity.getZ());
						List<Entity> _entfound0 = entity.level().getEntitiesOfClass(Entity.class, new AABB(_center0, _center0).inflate(12 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center0))).toList();
						for (Entity entityiterator : _entfound0) {
							if (entityiterator != entity) {
								entityiterator.hurt(entity.level.damageSources().generic(), 8f);
							}
						}
					});
				}
			}
		}
		return ar;
	}
}
