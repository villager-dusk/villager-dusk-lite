
package net.mcreator.buxin.entity.fake_entity;

import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.mcreator.buxin.my_method.Method_114514;
import net.mcreator.buxin.procedures.YingchuihimShiTiChuShiShengChengShiProcedure;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.Objects;
import java.util.UUID;

public class FakeEntity extends TamableAnimal {
    public FakeEntity(ServerPlayer owner){
        super(BuxinModEntities.FAKE_ENTITY.get(), owner.level());
        maxUpStep = 0.6f;
        setNoAi(false);
        tame(owner);
    }

    public FakeEntity(Mob owner){
        super(BuxinModEntities.FAKE_ENTITY.get(), owner.level());
        maxUpStep = 0.6f;
        setNoAi(false);
        tame(owner);
    }

    public FakeEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
    }

    public void tame_basic(LivingEntity p_21829_) {
        this.setTame(true);
        this.setOwnerUUID(p_21829_.getUUID());
        if (p_21829_ instanceof ServerPlayer) {
            CriteriaTriggers.TAME_ANIMAL.trigger((ServerPlayer)p_21829_, this);
        }
    }

    public void tame_basic(Player p_21829_) {
        this.setTame(true);
        this.setOwnerUUID(p_21829_.getUUID());
        if (p_21829_ instanceof ServerPlayer) {
            CriteriaTriggers.TAME_ANIMAL.trigger((ServerPlayer)p_21829_, this);
        }
    }

    @Override
    public void tame(@NotNull Player player) {
        tame_basic(player);
        setItemSlot(EquipmentSlot.MAINHAND, player.getItemBySlot(EquipmentSlot.MAINHAND).copy());
        setItemSlot(EquipmentSlot.OFFHAND, player.getItemBySlot(EquipmentSlot.OFFHAND).copy());
        setItemSlot(EquipmentSlot.HEAD, player.getItemBySlot(EquipmentSlot.HEAD).copy());
        setItemSlot(EquipmentSlot.CHEST, player.getItemBySlot(EquipmentSlot.CHEST).copy());
        setItemSlot(EquipmentSlot.LEGS, player.getItemBySlot(EquipmentSlot.LEGS).copy());
        setItemSlot(EquipmentSlot.FEET, player.getItemBySlot(EquipmentSlot.FEET).copy());
        // [MC 1.20.1] EpicFight 20.9.7: EntityPatch#getTarget() now returns LivingEntity instead of Entity, and method signature changed
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
        if (patch != null && patch.getTarget() != null) {
            setTarget(patch.getTarget());
        }
    }

    //@Override
    public void tame(@NotNull Mob livingEntity) {
        tame_basic(livingEntity);
        setItemSlot(EquipmentSlot.MAINHAND, livingEntity.getItemBySlot(EquipmentSlot.MAINHAND).copy());
        setItemSlot(EquipmentSlot.OFFHAND, livingEntity.getItemBySlot(EquipmentSlot.OFFHAND).copy());
        setItemSlot(EquipmentSlot.CHEST, livingEntity.getItemBySlot(EquipmentSlot.CHEST).copy());
        setItemSlot(EquipmentSlot.LEGS, livingEntity.getItemBySlot(EquipmentSlot.LEGS).copy());
        setItemSlot(EquipmentSlot.FEET, livingEntity.getItemBySlot(EquipmentSlot.FEET).copy());
        if(livingEntity.getTarget() != null) {
            setTarget(livingEntity.getTarget());
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @javax.annotation.Nullable SpawnGroupData livingdata, @javax.annotation.Nullable CompoundTag tag) {
        SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
        this.setCustomName(Component.literal("null"));
        if(getOwner() != null) {
            Method_114514.setEntityAttributes(this, Attributes.MAX_HEALTH, getOwner().getMaxHealth());
            Method_114514.setEntityAttributes(this, Attributes.ARMOR, getOwner().getAttributeValue(Attributes.ARMOR));
            this.setHealth(getOwner().getHealth());
        } else {
            Method_114514.setEntityAttributes(this, Attributes.MAX_HEALTH, 20);
            Method_114514.setEntityAttributes(this, Attributes.ARMOR, 4);
        }
        this.setCustomNameVisible(false);
        // [MC 1.20.1] Removed setShiftKeyDown() - no longer exists in TamableAnimal or Mob base classes
        return retval;
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float p_27568_) {

        if(source.getEntity() != null){
            return false;
        }
        if(source.getEntity() == getOwner() || source.getEntity() instanceof FakeEntity){
            return false;
        }
        return super.hurt(source, p_27568_);
    }

    @Override
    protected void registerGoals() {
        this.targetSelector.addGoal(0, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this, LivingEntity.class));
        this.goalSelector.addGoal(0, new FollowOwnerGoal(this, 0.5, 15.0F, 2.0F, false));
        this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 1.0));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.4F)
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.ARMOR, 9)
                .add(Attributes.ATTACK_DAMAGE, 1)
                .add(Attributes.FOLLOW_RANGE, 99)
                // [EpicFight 20.9.7] Removed EpicFightAttributes.WEIGHT, ARMOR_NEGATION, IMPACT, MAX_STRIKES as they are either deprecated or moved/renamed
                // Note: These attributes were removed from Epic Fight API in 20.x versions; using only vanilla attributes is safe
                .add(Attributes.ATTACK_DAMAGE); // duplicate add removed (was present twice)
    }

    @Override
    protected float getEquipmentDropChance(@NotNull EquipmentSlot slot) {
        return 0;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return null;
    }

    @Override
    public void tick() {
        super.tick();
        if(getOwner() == null){
           // this.discard();
        }
        if(this.tickCount == 1){
            if(getOwner() != null) {
                Method_114514.send_message_to_all_over_the_world_by_sb_self(this, this.level(), "Hi !" + getOwner().getDisplayName().getString(), false);
            } else {
                //this.discard();
                Method_114514.send_message_to_all_over_the_world_by_sb_self(this, this.level(), "? ? ? ?", false);
            }
        }
        if(this.getOwner() != null && this.getCustomName() != null && !this.getCustomName().getString().equals(getOwner().getDisplayName().getString())){
            this.setCustomName(Component.literal(getOwner().getDisplayName().getString()));
        }

        if(getTarget() == getOwner() || getTarget() instanceof FakeEntity){
            setTarget(null);
        }
        if (this.tickCount == 800) {
            this.discard();
            Method_114514.send_message_to_all_over_the_world_by_sb_self(this,this.level(),"Bye !",false);
            level.addParticle(ParticleTypes.POOF, getX(), getY() + 2, getZ(), 0, 0, 0);
            Method_114514.play_sound(this,"entity.item.pickup");
        }

        if(this.getOwner() != null){
            LivingEntity entity = this.getOwner();
            if(!this.getMainHandItem().is(this.getMainHandItem().getItem()) ||
                    !this.getOffhandItem().is(this.getOffhandItem().getItem()) ||
                    !this.getItemBySlot(EquipmentSlot.CHEST).is(this.getItemBySlot(EquipmentSlot.CHEST).getItem()) ||
                    !this.getItemBySlot(EquipmentSlot.FEET).is(this.getItemBySlot(EquipmentSlot.FEET).getItem()) ||
                    !this.getItemBySlot(EquipmentSlot.LEGS).is(this.getItemBySlot(EquipmentSlot.LEGS).getItem()) ||
                    !this.getItemBySlot(EquipmentSlot.HEAD).is(this.getItemBySlot(EquipmentSlot.HEAD).getItem())) {
                setItemSlot(EquipmentSlot.MAINHAND, entity.getItemBySlot(EquipmentSlot.MAINHAND).copy());
                setItemSlot(EquipmentSlot.OFFHAND, entity.getItemBySlot(EquipmentSlot.OFFHAND).copy());
                setItemSlot(EquipmentSlot.HEAD, entity.getItemBySlot(EquipmentSlot.HEAD).copy());
                setItemSlot(EquipmentSlot.CHEST, entity.getItemBySlot(EquipmentSlot.CHEST).copy());
                setItemSlot(EquipmentSlot.LEGS, entity.getItemBySlot(EquipmentSlot.LEGS).copy());
                setItemSlot(EquipmentSlot.FEET, entity.getItemBySlot(EquipmentSlot.FEET).copy());
            }
        }
        if(this.getOwner() != null && AnimationPlayer.getAnimation(this) != AnimationPlayer.getAnimation(this.getOwner()) && this.getTarget() == null){
            if(AnimationPlayer.getAnimation(this.getOwner()) instanceof StaticAnimation staticAnimation){
                AnimationPlayer.playAnimation(this,staticAnimation);
            }
        }
    }
}
