
package net.mcreator.buxin.entity;

import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.procedures.AnYingHeiYaoShiFaSheQiTouZhiWuFeiXingKeProcedure;
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
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;

@OnlyIn(Dist.CLIENT)
public class AnYingHeiYaoShiFaSheQiEntity extends AbstractArrow {
    public AnYingHeiYaoShiFaSheQiEntity(PlayMessages.SpawnEntity packet, Level world) {
        super(BuxinModEntities.AN_YING_HEI_YAO_SHI_FA_SHE_QI.get(), world);
    }

    public AnYingHeiYaoShiFaSheQiEntity(EntityType<? extends AnYingHeiYaoShiFaSheQiEntity> type, Level world) {
        super(type, world);
    }

    public AnYingHeiYaoShiFaSheQiEntity(EntityType<? extends AnYingHeiYaoShiFaSheQiEntity> type, double x, double y, double z, Level world) {
        super(type, x, y, z, world);
    }

    public AnYingHeiYaoShiFaSheQiEntity(EntityType<? extends AnYingHeiYaoShiFaSheQiEntity> type, LivingEntity entity, Level world) {
        super(type, entity, world);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    @Override
    protected void doPostHurtEffects(LivingEntity entity) {
        super.doPostHurtEffects(entity);
        // Arrow count methods removed in 1.20.1
    }

    @Override
    public void tick() {
        super.tick();
        AnYingHeiYaoShiFaSheQiTouZhiWuFeiXingKeProcedure.execute(this.level, this.getX(), this.getY(), this.getZ());
        if (this.inGround)
            this.discard();
    }

    public static AnYingHeiYaoShiFaSheQiEntity shoot(Level world, LivingEntity entity, RandomSource random, float power, double damage, int knockback) {
        AnYingHeiYaoShiFaSheQiEntity entityarrow = new AnYingHeiYaoShiFaSheQiEntity(BuxinModEntities.AN_YING_HEI_YAO_SHI_FA_SHE_QI.get(), entity, world);
        entityarrow.shoot(entity.getViewVector(1).x, entity.getViewVector(1).y, entity.getViewVector(1).z, power * 2, 0);
        entityarrow.setSilent(true);
        entityarrow.setCritArrow(false);
        entityarrow.setBaseDamage(damage);
        entityarrow.setKnockback(knockback);
        world.addFreshEntity(entityarrow);
        world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:dark_obsidian_use")), SoundSource.PLAYERS, 1, 1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
        return entityarrow;
    }

    public static AnYingHeiYaoShiFaSheQiEntity shoot(LivingEntity entity, LivingEntity target) {
        AnYingHeiYaoShiFaSheQiEntity entityarrow = new AnYingHeiYaoShiFaSheQiEntity(BuxinModEntities.AN_YING_HEI_YAO_SHI_FA_SHE_QI.get(), entity, entity.level);
        double dx = target.getX() - entity.getX();
        double dy = target.getY() + target.getEyeHeight() - 1.1;
        double dz = target.getZ() - entity.getZ();
        entityarrow.shoot(dx, dy - entityarrow.getY() + Math.hypot(dx, dz) * 0.2F, dz, 0.6f * 2, 12.0F);
        entityarrow.setSilent(true);
        entityarrow.setBaseDamage(10);
        entityarrow.setKnockback(15);
        entityarrow.setCritArrow(false);
        entity.level.addFreshEntity(entityarrow);
        entity.level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:dark_obsidian_use")), SoundSource.PLAYERS, 1, 1f / (RandomSource.create().nextFloat() * 0.5f + 1));
        return entityarrow;
    }
}
