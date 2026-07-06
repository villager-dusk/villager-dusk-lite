
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.init.BuxinModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class Herobrine3DangShiTiShouShangShi2Procedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null || world.isClientSide())
			return;
		if (!entity.getPersistentData().getBoolean("player")) {
			if (entity.getPersistentData().getBoolean("OP")) {
				if(Math.random() > 0.75){
					if(Math.random() > 0.1){
						if(Math.random() > 0.5) {
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:metal2")), SoundSource.NEUTRAL, 1, 1);
								} else {
									_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:metal2")), SoundSource.NEUTRAL, 1, 1, false);
								}
							}
						} else {
							if (world instanceof Level _level) {
								if (!_level.isClientSide()) {
									_level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:block_place")), SoundSource.NEUTRAL, 1, 1);
								} else {
									_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:block_place")), SoundSource.NEUTRAL, 1, 1, false);
								}
							}
						}
						BuxinMod.queueServerWork(1, () -> {
							BuxinMod.queueServerWork(35, () -> {
								if (entity instanceof LivingEntity _entity) {
									ItemStack _setstack = new ItemStack(BuxinModItems.NIUBI_20_HELMET.get());
									_setstack.setCount(1);
									_entity.setItemSlot(EquipmentSlot.HEAD, _setstack);
									if (_entity instanceof Player _player)
										_player.getInventory().setChanged();
								}
								if (entity instanceof LivingEntity _entity) {
									ItemStack _setstack = new ItemStack(BuxinModItems.NIUBI_19_CHESTPLATE.get());
									_setstack.setCount(1);
									_entity.setItemSlot(EquipmentSlot.CHEST, _setstack);
									if (_entity instanceof Player _player)
										_player.getInventory().setChanged();
								}
							});
						});
					}
				}
			}
		}
	}
}
