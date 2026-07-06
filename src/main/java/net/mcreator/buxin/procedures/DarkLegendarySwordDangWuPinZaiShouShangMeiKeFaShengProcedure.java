
package net.mcreator.buxin.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;

import net.mcreator.buxin.init.BuxinModItems;

public class DarkLegendarySwordDangWuPinZaiShouShangMeiKeFaShengProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null || world.isClientSide())
            return;
        if (world instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, x, y, z, 5, 0, 0, 0, 0.3);
        }
        if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY).getItem() == BuxinModItems.DARK_CHESTPLATE.get()) {
            if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY).getItem() == BuxinModItems.DARK_HELMET.get()) {
                if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY).getItem() == BuxinModItems.DARK_LEG_LEGGINGS.get()) {
                    if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY).getItem() == BuxinModItems.DARK_BOOTS.get()) {
                        if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                            _entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1, 2));
                        }
                    }
                }
            }
        }
    }
}
