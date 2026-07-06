package net.mcreator.buxin.entity.projectileblock;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.mcreator.buxin.init.BuxinModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Optional;
import java.util.UUID;

public class BuxinBlockProjectileEntity extends ThrowableProjectile {
    private static final EntityDataAccessor<BlockState> DATA_BLOCK = SynchedEntityData.defineId(BuxinBlockProjectileEntity.class, EntityDataSerializers.BLOCK_STATE);
    private static final EntityDataAccessor<Float> ROT_X = SynchedEntityData.defineId(BuxinBlockProjectileEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> ROT_Y = SynchedEntityData.defineId(BuxinBlockProjectileEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> ROT_Z = SynchedEntityData.defineId(BuxinBlockProjectileEntity.class, EntityDataSerializers.FLOAT);

    private boolean notReadyForShoot = false;
    private UUID playerUUID;

    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public void setNotReadyForShoot(boolean notReadyForShoot) {
        this.notReadyForShoot = notReadyForShoot;
    }

    public BuxinBlockProjectileEntity(EntityType<? extends BuxinBlockProjectileEntity> type, Level level) {
        super(type, level);
        initRandomRotation();
    }

    public BuxinBlockProjectileEntity(Level level, LivingEntity shooter, BlockState block) {
        super(BuxinModEntities.BLOCK_PROJECTILE.get(), shooter, level);
        setCarriedBlock(block);
        initRandomRotation();
    }

    public void setRotX(float v){ this.getEntityData().set(ROT_X, v); }
    public void setRotY(float v){ this.getEntityData().set(ROT_Y, v); }
    public void setRotZ(float v){ this.getEntityData().set(ROT_Z, v); }
    public float getRotX(){ return this.getEntityData().get(ROT_X); }
    public float getRotY(){ return this.getEntityData().get(ROT_Y); }
    public float getRotZ(){ return this.getEntityData().get(ROT_Z); }

    private void initRandomRotation() {
        if (!this.level().isClientSide) {
            var r = this.random;
            setRotX((r.nextFloat() - 0.5f) * 10f);
            setRotY((r.nextFloat() - 0.5f) * 10f);
            setRotZ((r.nextFloat() - 0.5f) * 10f);
        }
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_BLOCK, Blocks.STONE.defaultBlockState());
        this.getEntityData().define(ROT_X, 0f);
        this.getEntityData().define(ROT_Y, 0f);
        this.getEntityData().define(ROT_Z, 0f);
    }

    public void setCarriedBlock(BlockState state) {
        this.getEntityData().set(DATA_BLOCK, state);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (this.notReadyForShoot) return;
        Entity target = result.getEntity();
        final UUID ownerId = this.playerUUID;
        final boolean isHerobrine = true;

        boolean blockDamage =
                (ownerId == null && isHerobrine)
                        || (ownerId != null && target instanceof Player p
                        && p.getUUID().equals(ownerId));

        if (blockDamage) return;

        if (!target.level().isClientSide() && target.getServer() != null) {
            try {
                target.getServer().getCommands().getDispatcher().execute(
                        "execute at @s run particle epicfight:hit_blunt ^ ^1.5 ^0.8 0.1 0.1 0.1 1 1",
                        target.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException e) {

            }
        }

        if (!target.level().isClientSide()) {
            target.level().playSound(null, target.blockPosition(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin", "obsidian_hit")), SoundSource.BLOCKS, 1.0F, (float) (0.5 + Math.random() * 0.5));
        } else {
            target.level().playLocalSound(this.getX(), this.getY(), this.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin", "obsidian_hit")), SoundSource.BLOCKS, 1.0F, (float) (0.5 + Math.random() * 0.5), false);
        }
        if (this.getOwner() == null) {
            target.hurt(this.damageSources().generic(), 2.0F);
        } else {
            target.hurt(this.damageSources().indirectMagic(this, this.getOwner()), 2.0F);
        }
        if (!target.level().isClientSide() && target.getServer() != null) {
            try {
                target.getServer().getCommands().getDispatcher().execute(
                        "indestructible @s play \"epicfight:biped/combat/hit_long\" 0 10",
                        target.createCommandSourceStack().withSuppressedOutput().withPermission(4));
            } catch (CommandSyntaxException e) {

            }

            if (target instanceof LivingEntity livingEntity) {
                float strength = 1.0F;
                double dx = this.getX() - target.getX();
                double dz = this.getZ() - target.getZ();
                livingEntity.knockback(strength, dx, dz);
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        if (this.notReadyForShoot) return;
        BlockPos pos = result.getBlockPos();
        BlockState hitState = this.level().getBlockState(pos);

        if (!this.level().isClientSide) {
            if (!hitState.getFluidState().isEmpty()) {
                return;
            }
            this.discard();
        }
    }

    public BlockState getCarriedBlock() {
        return this.getEntityData().get(DATA_BLOCK);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat("RotX", getRotX());
        tag.putFloat("RotY", getRotY());
        tag.putFloat("RotZ", getRotZ());
        tag.putBoolean("NotReadyForShoot", notReadyForShoot);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setRotX(tag.contains("RotX") ? tag.getFloat("RotX") : 0f);
        setRotY(tag.contains("RotY") ? tag.getFloat("RotY") : 0f);
        setRotZ(tag.contains("RotZ") ? tag.getFloat("RotZ") : 0f);
        notReadyForShoot = tag.getBoolean("NotReadyForShoot");
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return EntityDimensions.fixed(0.9F, 0.9F);
    }

    @Override
    protected float getGravity() {
        return 0.005F;
    }
}