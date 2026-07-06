
package net.mcreator.buxin.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.mcreator.buxin.init.BuxinModParticleTypes;
import net.mcreator.buxin.item.father.CustomEnchantableItem;

import net.mcreator.buxin.procedures.YingchuiDangYouJianDianJiKongQiShiShiTiDeWeiZhiProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
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
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;
import java.util.Random;

public class YingchuiItem extends CustomEnchantableItem {
	public YingchuiItem() {
		super(new CustomEnchantableItem.Properties().durability(120).rarity(Rarity.COMMON));
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
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Item modifier", 12d, AttributeModifier.Operation.ADDITION));
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

		YingchuiDangYouJianDianJiKongQiShiShiTiDeWeiZhiProcedure.execute(world, x, y, z, entity, itemstack);
		return ar;
	}

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
		boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
		
		return retval;
	}

	@Override
	public void onCraftedBy(ItemStack itemstack, Level world, Player entity) {
		super.onCraftedBy(itemstack, world, entity);
		
	}

	@Override
	public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(itemstack, world, entity, slot, selected);
		if (selected) {
			entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(entitypatch -> {
				if(entitypatch instanceof LivingEntityPatch<?> livingEntityPatch){
					DynamicAnimation animation = livingEntityPatch.getAnimator().getPlayerFor(null).getAnimation();
					if(!(animation instanceof AttackAnimation)){
						int value = 1;
						float min = 0.5F;
						float max = 1.9F;
						float rz;
						OpenMatrix4f transformMatrixx;
						byte nL;
						for(int i = 0; i < value; ++i) {
							rz = min + (max - min) * (new Random()).nextFloat();
							transformMatrixx = livingEntityPatch.getArmature().getBindedTransformFor(livingEntityPatch.getAnimator().getPose(0.0F), Armatures.BIPED.toolR);
							transformMatrixx.translate(new Vec3f(0.0F, 0.0F, -rz));
							OpenMatrix4f.mul((new OpenMatrix4f()).rotate(-((float) Math.toRadians(((LivingEntity) entitypatch.getOriginal()).yBodyRotO + 180.0F)), new Vec3f(0.0F, 1.0031415927F, 0.0F)), transformMatrixx, transformMatrixx);
							nL = 3;
							for (int j = 0; j < nL; ++j) {
								if(Math.random() > 0.75) {
									entitypatch.getOriginal().level().addParticle(BuxinModParticleTypes.NIUBI_22.get(), 
										(double) transformMatrixx.m30 + entitypatch.getOriginal().getX(), 
										(double) transformMatrixx.m31 + entitypatch.getOriginal().getY(), 
										(double) transformMatrixx.m32 + entitypatch.getOriginal().getZ(), 
										0.0, -0.009999999776482582, 0.0);
								}
							}
						}
					}
				}
			});
		}
	}
}
