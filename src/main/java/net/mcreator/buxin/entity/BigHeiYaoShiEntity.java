
package net.mcreator.buxin.entity;

import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.procedures.HeiYaoShiTouZhiWuFeiXingKeProcedure;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;

public class BigHeiYaoShiEntity extends AbstractArrow {
    public BigHeiYaoShiEntity(PlayMessages.SpawnEntity packet, Level world) {
        super(BuxinModEntities.BIG_HEI_YAO_SHI.get(), world);
    }

    public BigHeiYaoShiEntity(EntityType<? extends BigHeiYaoShiEntity> type, Level world) {
        super(type, world);
    }

    public BigHeiYaoShiEntity(EntityType<? extends BigHeiYaoShiEntity> type, double x, double y, double z, Level world) {
        super(type, x, y, z, world);
    }

    public BigHeiYaoShiEntity(EntityType<? extends BigHeiYaoShiEntity> type, LivingEntity entity, Level world) {
        super(type, entity, world);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(Items.BLACK_DYE);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity entity) {
        super.doPostHurtEffects(entity);
    }

    @Override
    public void tick() {
        super.tick();
        HeiYaoShiTouZhiWuFeiXingKeProcedure.execute(this, this.level, this.getX(), this.getY(), this.getZ());
        if (this.onGround()) {
            this.discard();
        }
    }

    public static BigHeiYaoShiEntity shoot(Level world, LivingEntity entity, RandomSource random, float power, double damage, int knockback) {
        BigHeiYaoShiEntity entityarrow = new BigHeiYaoShiEntity(BuxinModEntities.BIG_HEI_YAO_SHI.get(), entity, world);
        entityarrow.shoot(entity.getViewVector(1).x, entity.getViewVector(1).y, entity.getViewVector(1).z, power * 2f, 0f);
        entityarrow.setSilent(true);
        entityarrow.setBaseDamage(damage);
        entityarrow.setKnockback(knockback);
        world.addFreshEntity(entityarrow);
        world.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:block_place")),
                SoundSource.PLAYERS, 1, 1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
        return entityarrow;
    }

    public static BigHeiYaoShiEntity shoot(LivingEntity entity, LivingEntity target) {
        BigHeiYaoShiEntity entityarrow = new BigHeiYaoShiEntity(BuxinModEntities.BIG_HEI_YAO_SHI.get(), entity, entity.level);
        double dx = target.getX() - entity.getX();
        double dy = target.getY() + target.getEyeHeight() - 1.1;
        double dz = target.getZ() - entity.getZ();
        entityarrow.shoot(dx, dy - entityarrow.getY() + Math.hypot(dx, dz) * 0.2F, dz, 1.0f, 0f);
        entityarrow.setSilent(true);
        entityarrow.setBaseDamage(10);
        entityarrow.setKnockback(1);
        entity.level.addFreshEntity(entityarrow);
        entity.level.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:block_place")),
                SoundSource.PLAYERS, 1, 1f / (RandomSource.create().nextFloat() * 0.5f + 1));
        return entityarrow;
    }
}
