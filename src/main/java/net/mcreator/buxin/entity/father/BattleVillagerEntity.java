
package net.mcreator.buxin.entity.father;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.ai.FishingTargetGoal;
import net.mcreator.buxin.ai.KnightAi;
import net.mcreator.buxin.common_attackgoals.AddCommonAttackGoal;
import net.mcreator.buxin.entity.Grave2Entity;
import net.mcreator.buxin.entity.Niubi6Entity;
import net.mcreator.buxin.entity.father.cape_entity.RenderCapeRangedPathfinderMob;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.mcreator.buxin.my_method.Method_114514;
import net.mcreator.buxin.procedures.CunMinWeiBingShiTiChwuShiShengChengShiProcedure;
import net.mcreator.buxin.procedures.RedVillagerDangShiTiSiWangShiProcedure;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.animation.types.LongHitAnimation;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class BattleVillagerEntity extends RenderCapeRangedPathfinderMob{
    public int shieldCoolDown;

    protected BattleVillagerEntity(EntityType<? extends RenderCapeRangedPathfinderMob> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        this.shieldCoolDown = compound.getInt("ShieldCooldown");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        compound.putInt("ShieldCooldown", this.shieldCoolDown);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(2, new FishingTargetGoal(this));

        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Zombie.class, true, true));
        ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);
        AddCommonAttackGoal.Villager(this);
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.2, false) {
            @Override
            protected double getAttackReachSqr(LivingEntity entity) {
                return this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth();
            }
        });
        this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1));
        this.targetSelector.addGoal(7, new HurtByTargetGoal(this).setAlertOthers());
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(9, new FloatGoal(this));
        this.goalSelector.addGoal(10, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(11, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(12, new FloatGoal(this));
        this.goalSelector.addGoal(13, new LeapAtTargetGoal(this, (float) 0.5));
    }
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getMainHandItem();
        if (this.level().isClientSide) {
            return InteractionResult.SUCCESS;
        }
        if (itemStack.getItem() == Items.OBSIDIAN && this.getHealth() < 10 || AnimationPlayer.entity_getAnimation(this) instanceof LongHitAnimation && itemStack.getItem() == Items.OBSIDIAN || player.isCreative() && itemStack.getItem() == Items.OBSIDIAN && this.getHealth() < 10) {
            return handleStickInteraction(this, player);
        }
        return InteractionResult.FAIL;
    }
    private InteractionResult handleStickInteraction(Entity entity, Player player) {
        this.playSound(SoundEvents.ITEM_PICKUP, 1.0F, 1.0F);
        player.sendSystemMessage(Component.literal("§2玩家 " + player.getDisplayName().getString() + " 俘虏了" + entity.getDisplayName().getString()));
        Method_114514.play_sound(entity.level(),player.getX(),player.getY(),player.getZ(),"entity.item.pickup");
        player.giveExperiencePoints(5);
        entity.remove(RemovalReason.DISCARDED);
        Method_114514.spawn_prisoner(entity);
        return InteractionResult.SUCCESS;
    }
    @Override
    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public double getMyRidingOffset() {
        return -0.35D;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.villager.ambient"));
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.villager.hurt"));
    }

    @Override
    public SoundEvent getDeathSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.villager.death"));
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return super.hurt(source, amount);
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
        RedVillagerDangShiTiSiWangShiProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(),this,source);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
        SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
        CunMinWeiBingShiTiChwuShiShengChengShiProcedure.execute(world, this.getX(), this.getY(), this.getZ(), this);
        return retval;
    }

    @Override
    public void baseTick() {
        super.baseTick();
        KnightAi.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
        if(!this.level().isClientSide){
            if(this.getTeam() == null && this.tickCount % 120 == 0){
                Method_114514.entity_use_command(this,"/team join villager");
            }
            if(this.tickCount % 25 == 0){
                if(this.getTarget() != null && this.getTarget() instanceof BattleVillagerEntity){
                    this.setTarget(null);
                }
            }
        }
        if (this.shieldCoolDown > 0) {
            --this.shieldCoolDown;
        }
    }

    public void disableShield(boolean increase) {
        float chance = 0.25F + (float) EnchantmentHelper.getBlockEfficiency(this) * 0.05F;
        if (increase) chance += 0.75F;
        if (this.random.nextFloat() < chance) {
            this.shieldCoolDown = 100;
            this.stopUsingItem();
            this.level().broadcastEntityEvent(this, (byte) 30);
        }
    }

    @Override
    protected void blockUsingShield(LivingEntity entityIn) {
        super.blockUsingShield(entityIn);
        if (entityIn.getMainHandItem().canDisableShield(this.useItem, this, entityIn)) this.disableShield(true);
    }

    public ItemStack getProjectile(ItemStack p_33038_) {
        if (p_33038_.getItem() instanceof ProjectileWeaponItem) {
            Predicate<ItemStack> predicate = ((ProjectileWeaponItem)p_33038_.getItem()).getSupportedHeldProjectiles();
            ItemStack itemstack = ProjectileWeaponItem.getHeldProjectile(this, predicate);
            return itemstack.isEmpty() ? new ItemStack(Items.ARROW) : itemstack;
        } else {
            return ItemStack.EMPTY;
        }
    }
}
