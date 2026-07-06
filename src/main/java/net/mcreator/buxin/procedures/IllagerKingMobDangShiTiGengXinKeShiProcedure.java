
package net.mcreator.buxin.procedures;

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

public class IllagerKingMobDangShiTiGengXinKeShiProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null || world.isClientSide())
            return;
        if (Math.random() > 0.7) {
            {
                final Vec3 _center = new Vec3(x, y, z);
                List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(6 / 2d), e -> true).stream()
                        .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                        .collect(Collectors.toList());
                for (Entity entityiterator : _entfound) {
                    if ((entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) == entityiterator) {
                        double distance = entity.distanceTo(entityiterator);
                        if (entityiterator.distanceTo(entity) > 6.0) {
                            if (entity instanceof LivingEntity _entity) {
                                ItemStack _setstack = new ItemStack(BuxinModItems.FUZI_2.get());
                                _setstack.setCount(1);
                                _entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
                                if (_entity instanceof Player _player)
                                    _player.getInventory().setChanged();
                            }
                        } else {
                            if (entity instanceof LivingEntity _entity) {
                                ItemStack _setstack = new ItemStack(BuxinModItems.FUZI.get());
                                _setstack.setCount(1);
                                _entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
                                if (_entity instanceof Player _player)
                                    _player.getInventory().setChanged();
                            }
                        }
                    }
                }
            }
        }
    }
}
