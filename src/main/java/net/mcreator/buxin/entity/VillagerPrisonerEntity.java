
package net.mcreator.buxin.entity;

import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.my_method.Method_114514;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.world.item.EpicFightItems;

import javax.annotation.Nullable;

public class VillagerPrisonerEntity extends Villager {
    public VillagerPrisonerEntity(PlayMessages.SpawnEntity packet, Level world) {
        this(BuxinModEntities.VillagerPrisoner.get(), world);
    }

    public VillagerPrisonerEntity(EntityType<VillagerPrisonerEntity> type, Level world) {
        super(type, world);
        this.maxUpStep = 0.6F;
        this.xpReward = 0;
        this.setNoAi(false);
        this.setCustomNameVisible(false);
        this.setPersistenceRequired();
        initializeCustomData();
    }


    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false) {
            protected double getAttackReachSqr(LivingEntity entity) {
                return (double)(this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth());
            }
        });
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new MoveBackToVillageGoal(this, 0.6, false));
        this.goalSelector.addGoal(6, new FloatGoal(this));
        this.goalSelector.addGoal(7, new AvoidEntityGoal<>(this, Monster.class, 6.0F, 1.0, 1.2));
        this.goalSelector.addGoal(8, new AvoidEntityGoal<>(this, Player.class, 6.0F, 1.0, 1.2));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, CunMinWeiBingEntity.class, 20.0F));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, GreenVillagerCavalryEntity.class, 20.0F));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, RedVillagerEntity.class, 20.0F));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, PurpleVillagerCavalryEntity.class, 20.0F));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, VillagerScoutEntity.class, 20.0F));
        this.goalSelector.addGoal(11, new FollowMobGoal(this, 1.0, 10.0F, 5.0F));
    }
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
        SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
        Method_114514.spawn_prisoner(this);
        this.remove(RemovalReason.DISCARDED);
        return retval;
    }
    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    public SoundEvent getAmbientSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.ambient"));
    }

    public SoundEvent getHurtSound(DamageSource ds) {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.hurt"));
    }

    public SoundEvent getDeathSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.death"));
    }


    public static void init() {
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
        builder = builder.add(Attributes.MAX_HEALTH, 20.0);
        builder = builder.add(Attributes.ARMOR, 0.0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 0.0);
        builder = builder.add(Attributes.FOLLOW_RANGE, 16.0);
        return builder;
    }

    private void initializeCustomData() {
        if (!this.level().isClientSide()) {
            VillagerData data = this.getVillagerData();
            this.setVillagerData(new VillagerData(data.getType(), VillagerProfession.WEAPONSMITH, 1 ));
            this.getPersistentData().putBoolean("ShowAllTrades", true);
            addLevel1Trades();
        }
    }

    private void addLevel1Trades() {
        MerchantOffers offers = this.getOffers();
        offers.add(new MerchantOffer(
                new ItemStack(Items.IRON_INGOT, 17),
                ItemStack.EMPTY,
                new ItemStack(BuxinModItems.IRON_LONG_SWORD.get(), 1),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(Items.EMERALD, 13),
                ItemStack.EMPTY,
                new ItemStack(BuxinModItems.LU_FZ.get(), 1),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 15),
                ItemStack.EMPTY,
                new ItemStack(BuxinModItems.NIUBI_15.get(), 1),
                999, 1, 0.0F
        ));

        ItemStack sword = new ItemStack(BuxinModItems.NIUBI_2DANSHOU.get());
        sword.enchant(Enchantments.SHARPNESS, 5);
        offers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 64),
                new ItemStack(BuxinModItems.BETTER_DIAMOND_BLOCK.get(), 64),
                sword,
                12, 2, 0.05F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 29),
                ItemStack.EMPTY,
                new ItemStack(EpicFightItems.DIAMOND_GREATSWORD.get(), 1),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 24),
                ItemStack.EMPTY,
                new ItemStack(EpicFightItems.DIAMOND_TACHI.get(), 1),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(Items.GOLD_INGOT, 15),
                ItemStack.EMPTY,
                new ItemStack(EpicFightItems.GOLDEN_TACHI.get(), 1),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 15),
                new ItemStack(BuxinModItems.DIAMOND_2.get(), 15),
                new ItemStack(BuxinModItems.PALADINSWORD.get(), 1),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(BuxinModItems.QIBINJIAN.get(), 1),
                new ItemStack(BuxinModItems.DIAMOND_2.get(), 15),
                new ItemStack(BuxinModItems.WOOPIE.get(), 1),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 5),
                ItemStack.EMPTY,
                new ItemStack(BuxinModItems.QIBINJIAN.get(), 1),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 10),
                ItemStack.EMPTY,
                new ItemStack(BuxinModItems.HUO_QBJ.get(), 1),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(Items.GOLD_INGOT, 8),
                ItemStack.EMPTY,
                new ItemStack(BuxinModItems.GOLDENLONGSWORD.get(), 1),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 19),
                ItemStack.EMPTY,
                new ItemStack(BuxinModItems.NIUBI_7.get(), 1),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(BuxinModItems.SNAKEBS.get(), 56),
                ItemStack.EMPTY,
                new ItemStack(BuxinModItems.SUPER_SNAKE_SWORD.get(), 1),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(BuxinModItems.SNAKEBS.get(), 30),
                ItemStack.EMPTY,
                new ItemStack(BuxinModItems.CHANGDAO.get(), 1),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(BuxinModItems.SNAKEBS.get(), 40),
                ItemStack.EMPTY,
                new ItemStack(BuxinModItems.NIUBI_8.get(), 1),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(BuxinModItems.SNAKEBS.get(), 32),
                ItemStack.EMPTY,
                new ItemStack(BuxinModItems.YINGCHUI.get(), 1),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(BuxinModItems.BLACK_SHIELD_2.get(), 32),
                ItemStack.EMPTY,
                new ItemStack(BuxinModItems.BLACK_SHIELD_HEROBRINE_SPAWN_EGG.get(), 1),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(BuxinModItems.SNAKEBS.get(), 25),
                ItemStack.EMPTY,
                new ItemStack(BuxinModItems.BLACK_SHIELD_2.get(), 1),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 14),
                ItemStack.EMPTY,
                new ItemStack(BuxinModItems.JIANDUN.get(), 1),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 11),
                ItemStack.EMPTY,
                new ItemStack(BuxinModItems.C_SWORD.get(), 1),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 8),
                ItemStack.EMPTY,
                new ItemStack(BuxinModItems.HEROBRINE_DIAMOND_SWORD.get(), 1),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(Items.IRON_INGOT, 36),
                ItemStack.EMPTY,
                new ItemStack(BuxinModItems.HUI_MIE_ZHI_JIAN.get(), 1),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(Items.EMERALD, 2),
                new ItemStack(Items.DIAMOND, 1),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 1),
                new ItemStack(Items.EMERALD),
                new ItemStack(BuxinModItems.DIAMOND_2.get(), 1),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(BuxinModItems.DIAMOND_2.get(), 6),
                new ItemStack(Items.DIAMOND, 12),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(BuxinModItems.BETTER_DIAMOND_BLOCK.get(), 1),
                new ItemStack(BuxinModItems.DIAMOND_2.get(), 9),
                999, 1, 0.0F
        ));

        /*----------------生物蛋-开启----------------*/

        offers.add(new MerchantOffer(
                new ItemStack(Items.EMERALD, 15),
                new ItemStack(Items.DIAMOND, 15),
                new ItemStack(BuxinModItems.RED_ZOMBIE_SPAWN_EGG.get(), 1),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(BuxinModItems.RED_ZOMBIE_SPAWN_EGG.get(), 1),
                new ItemStack(Items.OBSIDIAN, 2),
                new ItemStack(BuxinModItems.HEROBRINE_SPAWN_EGG.get(), 1),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(BuxinModItems.RED_ZOMBIE_SPAWN_EGG.get(), 1),
                new ItemStack(Items.OBSIDIAN, 4),
                new ItemStack(BuxinModItems.HEROBRINE_3_SPAWN_EGG.get(), 1),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(BuxinModItems.RED_ZOMBIE_SPAWN_EGG.get(), 1),
                new ItemStack(BuxinModItems.SUPER_SNAKE_SWORD.get(), 1),
                new ItemStack(BuxinModItems.SHIFANG_SPAWN_EGG.get(), 1),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(BuxinModItems.HEROBRINE_3_SPAWN_EGG.get(), 1),
                new ItemStack(Items.OBSIDIAN, 4),
                new ItemStack(BuxinModItems.DARK_HEROBRINE_SPAWN_EGG.get(), 1),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(BuxinModItems.RED_ZOMBIE_SPAWN_EGG.get(), 1),
                new ItemStack(BuxinModItems.CHANGDAO.get(), 1),
                new ItemStack(BuxinModItems.CHANGDAOHIM_SPAWN_EGG.get(), 1),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(BuxinModItems.RED_ZOMBIE_SPAWN_EGG.get(), 1),
                new ItemStack(BuxinModItems.NIUBI_8.get(), 1),
                new ItemStack(BuxinModItems.NIUBI_9_SPAWN_EGG.get(), 1),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(BuxinModItems.RED_ZOMBIE_SPAWN_EGG.get(), 1),
                new ItemStack(BuxinModItems.YINGCHUI.get(), 1),
                new ItemStack(BuxinModItems.YINGCHUIHIM_SPAWN_EGG.get(), 1),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(BuxinModItems.JIANDUN.get(), 1),
                new ItemStack(Items.DIAMOND_CHESTPLATE, 1),
                new ItemStack(BuxinModItems.MR_SPAWN_EGG.get(), 1),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(BuxinModItems.NIUBI_7.get(), 1),
                new ItemStack(BuxinModItems.ZUAN_JIAN_2.get(), 1),
                new ItemStack(BuxinModItems.NIUBI_6_SPAWN_EGG.get(), 1),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(BuxinModItems.WOOPIE.get(), 1),
                new ItemStack(Items.ENDER_PEARL, 1),
                new ItemStack(BuxinModItems.NIUBI_13_SPAWN_EGG.get(), 1),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(BuxinModItems.SNAKEBS.get(), 30),
                new ItemStack(Items.ENDER_PEARL, 10),
                new ItemStack(BuxinModItems.GREG_HB_EGG.get(), 1),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(BuxinModItems.HUO_QBJ.get(), 1),
                new ItemStack(BuxinModItems.NOTCH_SPAWN_EGG.get(), 1),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(BuxinModItems.RED_ZOMBIE_SPAWN_EGG.get(), 1),
                new ItemStack(BuxinModItems.SNAKEBS.get(), 1),
                new ItemStack(BuxinModItems.Null_EGG.get(), 1),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(Items.BOW, 1),
                new ItemStack(Items.ENCHANTED_GOLDEN_APPLE, 1),
                new ItemStack(BuxinModItems.TheMostMoistBurrit0_SPAWN_EGG.get(), 1),
                999, 1, 0.0F
        ));

        offers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 20),
                new ItemStack(Items.ENDER_PEARL, 25),
                new ItemStack(BuxinModItems.SNAKEBS.get(), 1),
                999, 1, 0.0F
        ));
    }
}
