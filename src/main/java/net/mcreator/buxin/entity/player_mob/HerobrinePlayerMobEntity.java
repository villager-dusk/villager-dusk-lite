
package net.mcreator.buxin.entity.player_mob;

import net.mcreator.buxin.entity.shadow_herorbrine.ShadowHerobrineEntity;
import net.mcreator.buxin.gameasset.BuxinAnimations;
import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.init.BuxinModParticleTypes;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;

import javax.annotation.Nullable;

import static net.mcreator.buxin.my_method.Method_114514.getArmPosition;

/*
public class HerobrinePlayerMobEntity extends PlayerMobEntity {
    private ShadowHerobrineEntity possessedByEntity;
    public HerobrinePlayerMobEntity(EntityType<? extends HerobrinePlayerMobEntity> type, Level level) {
        super(type, level);
        this.xpReward = 300;
        maxUpStep = 0.6f;
        this.xpReward = 7;
        this.setNoAi(true);
        this.setCustomNameVisible(false);
    }

    public HerobrinePlayerMobEntity(PlayMessages.SpawnEntity spawnEntity, Level level) {
        this((EntityType) BuxinModEntities.Infected_PlayerMOB.get(), level);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void registerGoals() {
        this.goalSelector.getAvailableGoals().clear();
        this.targetSelector.getAvailableGoals().clear();
    }

    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    public double getMyRidingOffset() {
        return -0.35D;
    }

    public SoundEvent getHurtSound(DamageSource damagesource) {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
    }


    public SoundEvent getDeathSound() {
        return (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
    }
    private static void dropLoot(LevelAccessor world, double x, double y, double z) {
        if (!(world instanceof Level level) || level.isClientSide()) return;

        Item[] drops = new Item[]{
                Items.DIAMOND, Items.DIAMOND,
                Items.MUSIC_DISC_11, Items.IRON_INGOT,
                Items.WRITABLE_BOOK, Items.EMERALD, Items.EMERALD,
                Items.ENCHANTED_GOLDEN_APPLE, Items.NETHERITE_INGOT,
                Items.ENDER_PEARL, Items.ENCHANTED_GOLDEN_APPLE,
                Items.ENDER_EYE, Items.TNT, Items.TNT, Items.ENCHANTED_BOOK,
                BuxinModItems.OBS.get(),
                Items.ENDER_PEARL
        };

        for (Item item : drops) {
            ItemEntity entity = new ItemEntity(level, x, y, z, new ItemStack(item));
            entity.setPickUpDelay(10);
            level.addFreshEntity(entity);
        }
    }
    @Override
    public void die(DamageSource damagesource) {
        super.die(damagesource);
        String possessedBy = this.getPersistentData().getString("possessed_by");
        dropLoot(this.level(),this.getX(),this.getY(),this.getZ());
        PlayermobsDead.execute(this.level,this.getX(),this.getY(),this.getZ(),this);
        LevelAccessor levelaccessor1 = this.level();
        ItemEntity itementity;
        LivingEntity livingentity = (LivingEntity)this;
        ItemStack itemstack;

        if (levelaccessor1 instanceof Level level) {
            if (!level.isClientSide()) {
                itemstack = livingentity.getItemBySlot(EquipmentSlot.FEET);
                itementity = new ItemEntity(level, this.getX(), this.getY() + 1.0D, this.getZ(), itemstack);
                itementity.setPickUpDelay(10);
                level.addFreshEntity(itementity);
            }
        }

        if (levelaccessor1 instanceof Level level) {
            if (!level.isClientSide()) {
                itemstack = livingentity.getItemBySlot(EquipmentSlot.LEGS);
                itementity = new ItemEntity(level,  this.getX(), this.getY() + 1.0D, this.getZ(), itemstack);
                itementity.setPickUpDelay(10);
                level.addFreshEntity(itementity);
            }
        }

        if (levelaccessor1 instanceof Level level) {
            if (!level.isClientSide()) {
                itemstack = livingentity.getItemBySlot(EquipmentSlot.CHEST);
                itementity = new ItemEntity(level,  this.getX(), this.getY() + 1.0D, this.getZ(), itemstack);
                itementity.setPickUpDelay(10);
                level.addFreshEntity(itementity);
            }
        }

        if (levelaccessor1 instanceof Level level) {
            if (!level.isClientSide()) {
                itemstack = livingentity.getItemBySlot(EquipmentSlot.HEAD);
                itementity = new ItemEntity(level, this.getX(), this.getY() + 1.0D, this.getZ(), itemstack);
                itementity.setPickUpDelay(10);
                level.addFreshEntity(itementity);
            }
        }
    }
    /*
    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);
        PlayermobsDead.execute(this.level,this.getX(),this.getY(),this.getZ(),this,null);
    }

     */
/*
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverlevelaccessor, DifficultyInstance difficultyinstance, MobSpawnType mobspawntype, @javax.annotation.Nullable SpawnGroupData spawngroupdata, @Nullable CompoundTag compoundtag) {
        SpawnGroupData retval = super.finalizeSpawn(serverlevelaccessor, difficultyinstance, mobspawntype, spawngroupdata, compoundtag);
        Entity entity = this;
        entity.getPersistentData().putBoolean("isbuxinentity", true);
        return spawngroupdata;
    }
    public void setPossessedByEntity(ShadowHerobrineEntity possessedByEntity) {
        this.possessedByEntity = possessedByEntity;
    }
    @Override
    public void baseTick() {
        if(this.getPersistentData().getBoolean("gp")){
            AnimationPlayer.playAnimation(this,BuxinAnimations.HB_sendpower);
        }
        if (this.possessedByEntity != null && this.getPersistentData().getBoolean("gp")) {
            ServerLevel server = (ServerLevel)this.level();
            Vec3 from = getArmPosition(this, new Vec3f(0,0,0), Armatures.BIPED.toolR, 1.2F, 0.0F);
            if (from == null) {
                return;
            }
            Vec3 to = this.possessedByEntity.getEyePosition();

            AABB box = this.possessedByEntity.getBoundingBox().inflate(0.05);
            Vec3 end = box.clip(from, to).orElse(to);

            Vec3 d = end.subtract(from);
            double len = d.length();
            if (len <= 1.0e-4) return;

            Vec3 dir = d.scale(1.0 / len);

            Vec3 any = Math.abs(dir.y) < 0.99 ? new Vec3(0,1,0) : new Vec3(1,0,0);
            Vec3 u = dir.cross(any).normalize();
            Vec3 v = dir.cross(u).normalize();

            int steps = Mth.clamp((int)(len * 6.0), 6, 72);
            double step = len / steps;

            final int stride = 4;
            int phase = (this.tickCount >> 1) % stride;
            RandomSource r = this.getRandom();

            for (int i = phase; i <= steps; i += stride) {
                if (r.nextFloat() < 0.70f) continue;

                double t = (i * step) / len;
                double R = 0.05 + 0.20 * t;
                double ang = r.nextDouble() * (Math.PI * 2);
                double rad = R * Math.sqrt(r.nextDouble());
                Vec3 off = u.scale(Math.cos(ang) * rad).add(v.scale(Math.sin(ang) * rad));

                Vec3 p = from.add(dir.scale(i * step)).add(off);

                double vx = dir.x * 0.02 + off.x * 0.10;
                double vy = dir.y * 0.02 + off.y * 0.10;
                double vz = dir.z * 0.02 + off.z * 0.10;

                server.sendParticles(BuxinModParticleTypes.POWER.get(), p.x, p.y, p.z, 1, vx, vy, vz, 0.0);
            }
        }
        super.baseTick();
    }
    public boolean isPushable() {
        return false;
    }

    protected void doPush(Entity entity) {}

    protected void pushEntities() {}
    public static void init() {
        DungeonHooks.addDungeonMob(BuxinModEntities.Infected_PlayerMOB.get(), 180);
    }
    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();

        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.26D);
        builder = builder.add(Attributes.MAX_HEALTH, 10.0D);
        builder = builder.add(Attributes.ARMOR, 0.0D);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 1.0D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 32.0D);
        return builder;
    }
}
*/