
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.init.BuxinModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Herobrine3DangShiTiGengXinKeShiProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null || world.isClientSide())
            return;
        {
            final Vec3 _center = new Vec3(x, y, z);
            // MODIFIED: In Minecraft 1.20.1, getEntitiesOfClass no longer accepts Entity.class as first param for generic safety;
            // use a concrete type like LivingEntity.class instead, but since original logic targets any Entity,
            // and AABB.inflate() now takes double directly (no division needed), we keep Entity.class but fix inflate arg.
            // Also: getEntitiesOfClass signature unchanged in 1.20.1/Forge 47.4.10 — still valid with Entity.class.
            // However, distanceToSqr is deprecated in 1.20.1; replaced with distanceToSqr(Vec3) → use _entcnd.position().distanceToSqr(_center)
            List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(1.5), e -> true)
                    .stream()
                    .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.position().distanceToSqr(_center)))
                    .collect(Collectors.toList());
            for (Entity entityiterator : _entfound) {
                // MODIFIED: In 1.20.1+, Mob#getTarget() returns LivingEntity (not Entity), and null-check is safer.
                // Original cast `(Entity) _mobEnt.getTarget()` was unsafe; now cast to LivingEntity and compare properly.
                LivingEntity target = (entity instanceof Mob _mobEnt) ? _mobEnt.getTarget() : null;
                if (target != null && target == entityiterator) {
                    if (Math.random() < 0.07) {
                        if (entity instanceof LivingEntity _entity) {
                            ItemStack _setstack = new ItemStack(BuxinModItems.HELEMT_WEAPON.get());
                            _setstack.setCount(1);
                            _entity.setItemInHand(InteractionHand.OFF_HAND, _setstack);
                            if (_entity instanceof Player _player)
                                _player.getInventory().setChanged();
                        }
                        BuxinMod.queueServerWork(20, () -> {
                            if (entity instanceof LivingEntity _entity) {
                                ItemStack _setstack = new ItemStack(BuxinModItems.OBS_5.get());
                                _setstack.setCount(1);
                                _entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
                                if (_entity instanceof Player _player)
                                    _player.getInventory().setChanged();
                            }
                        });
                    }
                }
            }
        }
    }
}
