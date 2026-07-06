
package net.mcreator.buxin.entity.father;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.ai.FishingTargetGoal;
import net.mcreator.buxin.ai.KnightAi;
import net.mcreator.buxin.ai.PearlUseGoal;
import net.mcreator.buxin.common_attackgoals.AddCommonAttackGoal;
import net.mcreator.buxin.entity.*;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.mcreator.buxin.my_method.Method_114514;
import net.mcreator.buxin.procedures.CunMinWeiBingShiTiChwuShiShengChengShiProcedure;
import net.mcreator.buxin.procedures.RedVillagerDangShiTiSiWangShiProcedure;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;
//import se.gory_moon.player_mobs.entity.PlayerMobEntity;
import yesman.epicfight.api.animation.types.HitAnimation;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import javax.annotation.Nullable;

public class BattleZombieVillagerEntity extends RangedMonster {
    private LivingEntityPatch livingEntityPatch = AnimationPlayer.getlivingEntityPatch(this);

    public LivingEntityPatch getLivingEntityPatch() {
        return livingEntityPatch;
    }

    //private final SimpleContainer inventory = new SimpleContainer(27);
    protected BattleZombieVillagerEntity(EntityType<? extends RangedMonster> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(2, new FishingTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Niubi6Entity.class, true, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Grave2Entity.class, true, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Zombie.class, true, true));
        //this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, PlayerMobEntity.class, true, true));
        ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);
        this.goalSelector.addGoal(2, new PearlUseGoal(this));
        AddCommonAttackGoal.Herobrine(this);
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
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public double getMyRidingOffset() {
        return -0.35D;
    }

    public SoundEvent getAmbientSound() {
        return SoundEvents.ZOMBIE_VILLAGER_AMBIENT;
    }

    public SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.ZOMBIE_VILLAGER_HURT;
    }

    public SoundEvent getDeathSound() {
        return SoundEvents.ZOMBIE_VILLAGER_DEATH;
    }

    public SoundEvent getStepSound() {
        return SoundEvents.ZOMBIE_VILLAGER_STEP;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        //
        return super.hurt(source, amount);
    }

    private static void dropLoot(Level world, double x, double y, double z) {
        if (world.isClientSide()) return;

        Item[] drops = new Item[]{Items.DIAMOND, Items.EMERALD, Items.ENDER_PEARL,Items.ENDER_EYE, Items.ENDER_PEARL,Items.BOOK,Items.ARROW,Items.ARROW,Items.ARROW};

        for (Item item : drops) {
            ItemEntity entity = new ItemEntity(world, x, y, z, new ItemStack(item));
            entity.setPickUpDelay(10);
            world.addFreshEntity(entity);
        }
    }
    private static void dropLoot2(Level world, double x, double y, double z) {
        if (world.isClientSide()) return;

        Item[] drops = new Item[]{
                Items.ENDER_PEARL, Items.ENCHANTED_GOLDEN_APPLE, Items.IRON_INGOT,Items.DIRT,Items.DIRT,Items.DIRT,Items.REDSTONE,Items.REDSTONE,Items.FIREWORK_ROCKET

        };

        for (Item item : drops) {
            ItemEntity entity = new ItemEntity(world, x, y, z, new ItemStack(item));
            entity.setPickUpDelay(10);
            world.addFreshEntity(entity);
        }
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
        dropLoot(this.level(),this.getX(),this.getY(),this.getZ());
        dropLoot2(this.level(),this.getX(),this.getY(),this.getZ());
        /*
        for (int i = 0; i < this.inventory.getContainerSize(); i++) {
            ItemStack stack = this.inventory.getItem(i);
            if (!stack.isEmpty()) {
                this.spawnAtLocation(stack);
            }
        }

         */
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
        SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
        this.setItemSlot(EquipmentSlot.OFFHAND,Items.ENDER_PEARL.getDefaultInstance());
        if(Math.random() > 0.5){
            this.setItemSlot(EquipmentSlot.MAINHAND,Items.DIAMOND_SWORD.getDefaultInstance());
            this.getMainHandItem().enchant(Enchantments.SHARPNESS,2);
        }
        //CunMinWeiBingShiTiChwuShiShengChengShiProcedure.execute(world, this.getX(), this.getY(), this.getZ(), this);
        return retval;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void tick() {
        super.tick();
        Mob entity = this;
        shootArrowAi(entity);
    }
    private static void shootArrowAi(Entity entity){
        if(entity instanceof Mob && ((Mob) entity).getTarget() != null && Math.random() > 0.5 && AnimationPlayer.entity_getAnimation(entity) != null && !(AnimationPlayer.entity_getAnimation(entity) instanceof LongHitAnimation) && !(AnimationPlayer.entity_getAnimation(entity) instanceof HitAnimation) && ((Mob) entity).getTarget() != null){
            if(Method_114514.entity1_distance_to_entity2_xyz(entity,((Mob) entity).getTarget()) > 6 || entity.isPassenger()){
                
                if(((Mob) entity).getMainHandItem().getItem() != Items.BOW) {
                    entity.setItemSlot(EquipmentSlot.MAINHAND, Items.BOW.getDefaultInstance());
                }
                BuxinMod.queueServerWork(55,() -> {
                    if(entity instanceof Mob mob && ((Mob) entity).getTarget() != null && !(Method_114514.entity1_distance_to_entity2_xyz(entity,((Mob) entity).getTarget()) > 6) && !(mob.isPassenger())) {
                        entity.setItemSlot(EquipmentSlot.MAINHAND,new ItemStack(Items.DIAMOND_SWORD));
                        mob.getMainHandItem().enchant(Enchantments.SHARPNESS, 2);
                    }
                });
            }
        }
    }
}
