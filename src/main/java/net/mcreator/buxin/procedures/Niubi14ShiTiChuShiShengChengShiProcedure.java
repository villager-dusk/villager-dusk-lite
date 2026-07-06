
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.init.BuxinModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class Niubi14ShiTiChuShiShengChengShiProcedure {
	public boolean FuckYou = false;
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null || world.isClientSide())
			return;
		entity.getPersistentData().putBoolean("isfuck", false);
		entity.getPersistentData().putBoolean("iskicking", false);
		entity.getPersistentData().putBoolean("wu", false);
		entity.getPersistentData().putDouble("battle",0);entity.getPersistentData().putBoolean("isbuxinentity", true);
		entity.getPersistentData().putBoolean("wbyd", true);
		entity.getPersistentData().putBoolean("erpl", false);
		/*
		{
			Entity _entity = entity;
			if (_entity instanceof Player _player) {
				_player.getInventory().armor.set(3, new ItemStack(BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_HELMET.get()));
				_player.getInventory().setChanged();
			} else if (_entity instanceof LivingEntity _living) {
				_living.setItemSlot(EquipmentSlot.HEAD, new ItemStack(BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_HELMET.get()));
			}
		}
		{
			Entity _entity = entity;
			if (_entity instanceof Player _player) {
				_player.getInventory().armor.set(2, new ItemStack(BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_CHESTPLATE.get()));
				_player.getInventory().setChanged();
			} else if (_entity instanceof LivingEntity _living) {
				_living.setItemSlot(EquipmentSlot.CHEST, new ItemStack(BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_CHESTPLATE.get()));
			}
		}

		 */
		((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY)).enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
		((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY)).enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
		((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY)).enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
		((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY)).enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
		entity.getPersistentData().putBoolean("BCBC", true);
		entity.getPersistentData().putBoolean("abcd", true);
		BuxinMod.queueServerWork(400, () -> {
			entity.getPersistentData().putBoolean("BCBC", false);
		});
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:wake_up")), SoundSource.NEUTRAL, 1, 1);
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:wake_up")), SoundSource.NEUTRAL, 1, 1, false);
			}
		}
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.totem.use")), SoundSource.NEUTRAL, 1, 1);
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.totem.use")), SoundSource.NEUTRAL, 1, 1, false);
			}
		}
		if (world instanceof ServerLevel _level)
			_level.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, x, y, z, 1000, 1, 1, 1, 1);

		BuxinMod.queueServerWork(25, () -> {

			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:steve_speak")), SoundSource.NEUTRAL, 1, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:steve_speak")), SoundSource.NEUTRAL, 1, 1, false);
				}
			}
			//entity.setCustomName(Component.literal("愤怒的史蒂夫"));
			BuxinMod.queueServerWork(45, () -> {

				BuxinMod.queueServerWork(15, () -> {
					if (entity instanceof LivingEntity _entity)
						_entity.swing(InteractionHand.MAIN_HAND, true);
					((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY)).enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
					BuxinMod.queueServerWork(15, () -> {
						((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY)).enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
						if (entity instanceof LivingEntity _entity) {
							ItemStack _setstack = new ItemStack(BuxinModItems.NIUBI_2DANSHOU.get());
							_setstack.setCount(1);
							_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
							if (_entity instanceof Player _player)
								_player.getInventory().setChanged();
						}
						((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY)).enchant(Enchantments.SHARPNESS, 5);
						if (entity instanceof LivingEntity _entity)
							_entity.swing(InteractionHand.MAIN_HAND, true);
					});
				});
			});
		});
	}
}
