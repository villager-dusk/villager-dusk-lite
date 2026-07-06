
package net.mcreator.buxin.entity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.procedures.HeiYaoShiTouZhiWuFeiXingKeProcedure;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class HeiYaoShiEntity extends AbstractArrow implements ItemSupplier {
    public HeiYaoShiEntity(PlayMessages.SpawnEntity packet, Level world) {
        super(BuxinModEntities.HEI_YAO_SHI.get(), world);
    }

    public HeiYaoShiEntity(EntityType<? extends HeiYaoShiEntity> type, Level world) {
        super(type, world);
    }

    public HeiYaoShiEntity(EntityType<? extends HeiYaoShiEntity> type, double x, double y, double z, Level world) {
        super(type, x, y, z, world);
    }

    public HeiYaoShiEntity(EntityType<? extends HeiYaoShiEntity> type, LivingEntity entity, Level world) {
        super(type, entity, world);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack getItem() {
        return new ItemStack(BuxinModItems.AIR.get());
    }

    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    @Override
    protected void doPostHurtEffects(LivingEntity entity) {
        super.doPostHurtEffects(entity);
    }

    @Override
    public void tick() {
        super.tick();
        HeiYaoShiTouZhiWuFeiXingKeProcedure.execute(this, this.level, this.getX(), this.getY(), this.getZ());
        if (this.inGround)
            this.discard();
    }

    public static HeiYaoShiEntity shoot(Level world, LivingEntity entity, RandomSource random, float power, double damage, int knockback) {
        HeiYaoShiEntity entityarrow = new HeiYaoShiEntity(BuxinModEntities.HEI_YAO_SHI.get(), entity, world);
        entityarrow.shoot(entity.getViewVector(1).x, entity.getViewVector(1).y, entity.getViewVector(1).z, power * 2, 0.0F);
        entityarrow.setSilent(true);
        entityarrow.setCritArrow(false);
        entityarrow.setBaseDamage((float) damage);
        entityarrow.setKnockback(knockback);
        world.addFreshEntity(entityarrow);
        world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:block_place")),
            SoundSource.PLAYERS, 1, 1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
        return entityarrow;
    }

    public static HeiYaoShiEntity shoot(LivingEntity entity, LivingEntity target) {
        HeiYaoShiEntity entityarrow = new HeiYaoShiEntity(BuxinModEntities.HEI_YAO_SHI.get(), entity, entity.level);
        double dx = target.getX() - entity.getX();
        double dy = target.getY() + target.getEyeHeight() - 1.1;
        double dz = target.getZ() - entity.getZ();
        entityarrow.shoot(dx, dy - entityarrow.getY() + Math.hypot(dx, dz) * 0.2F, dz, 0.5f * 2, 0.0F);
        entityarrow.setSilent(true);
        entityarrow.setBaseDamage(10.0F);
        entityarrow.setKnockback(15);
        entityarrow.setCritArrow(false);
        entity.level.addFreshEntity(entityarrow);
        entity.level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), 
            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:block_place")),
            SoundSource.PLAYERS, 1, 1f / (RandomSource.create().nextFloat() * 0.5f + 1));
        return entityarrow;
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        Entity vicTim = pResult.getEntity();
        if (!vicTim.level().isClientSide() && vicTim.getServer() != null) {
            try {
                vicTim.getServer().getCommands().getDispatcher().execute(
                    "execute at @s run particle epicfight:hit_blunt ^ ^1.5 ^0.8 0.1 0.1 0.1 1 1",
                    vicTim.createCommandSourceStack().withPermission(4).withSuppressedOutput()
                );
                try {
                    vicTim.getServer().getCommands().getDispatcher().execute(
                        "indestructible @s play \"epicfight:biped/combat/hit_long\" 0 10",
                        vicTim.createCommandSourceStack().withPermission(4).withSuppressedOutput()
                    );
                } catch (CommandSyntaxException e) {
                }
            } catch (CommandSyntaxException e) {
            }
        }
        super.onHitEntity(pResult);
    }
}
