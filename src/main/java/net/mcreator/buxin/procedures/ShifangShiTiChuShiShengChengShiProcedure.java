package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.my_method.Method_114514;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class ShifangShiTiChuShiShengChengShiProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null || world.isClientSide())
			return;
		BuxinMod.queueServerWork(120,() -> {
			Method_114514.entity_use_command(entity,"/indestructible @s play \"wom:biped/skill/torment_berserk_convert\" 0 1");
			Method_114514.entity_use_command(entity,"/particle epicfight:force_field_end");
			if(entity instanceof LivingEntity e){
				e.setItemSlot(EquipmentSlot.MAINHAND, BuxinModItems.SUPER_SNAKE_SWORD.get().getDefaultInstance());
			}
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:shi_fang")), SoundSource.NEUTRAL, 1, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:shi_fang")), SoundSource.NEUTRAL, 1, 1, false);
				}
			}

		});
		entity.getPersistentData().putBoolean("isfuck", false);
		entity.getPersistentData().putBoolean("iskicking", false);
		entity.getPersistentData().putDouble("battle", 0);
		entity.getPersistentData().putBoolean("isbuxinentity", true);
	}
}