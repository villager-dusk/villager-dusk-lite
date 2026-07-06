package net.mcreator.buxin.capabilities;

import com.mojang.datafixers.util.Pair;
import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.gameasset.BuxinAnimations;
import net.mcreator.buxin.gameasset.BuxinModColliders;
import net.mcreator.buxin.init.BuxinModSounds;
import net.mcreator.buxin.skills.BuxinSkills;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import yesman.epicfight.world.capabilities.item.WeaponCapabilityPresets;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.function.Function;

public class FuckLandSword {
    public static final Function<Item, CapabilityItem.Builder> FIST = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder().category(CapabilityItem.WeaponCategories.SWORD).swingSound(BuxinModSounds.SWORD_WHOOSH.get()).styleProvider((playerpatch) -> {
            return playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != CapabilityItem.WeaponCategories.SWORD && playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != CapabilityItem.WeaponCategories.AXE && playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != CapabilityItem.WeaponCategories.TACHI ? CapabilityItem.Styles.ONE_HAND : CapabilityItem.Styles.TWO_HAND;
        }).collider(ColliderPreset.SWORD).newStyleCombo(CapabilityItem.Styles.ONE_HAND, new StaticAnimation[]{Animations.FIST_AUTO1, Animations.FIST_AUTO2, Animations.FIST_AUTO3, Animations.FIST_DASH, Animations.FIST_AIR_SLASH}).newStyleCombo(CapabilityItem.Styles.TWO_HAND, new StaticAnimation[]{Animations.FIST_AUTO1, Animations.FIST_AUTO2, Animations.FIST_AUTO3, Animations.FIST_DASH, Animations.FIST_AIR_SLASH}).innateSkill(CapabilityItem.Styles.ONE_HAND, (itemstack) -> {
            return BuxinSkills.HEAVY_DASH;
        }).innateSkill(CapabilityItem.Styles.TWO_HAND, (itemstack) -> {
            return BuxinSkills.HEAVY_DASH;
        }).livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_DUAL).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.FLOAT, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.FALL, Animations.BIPED_HOLD_DUAL_WEAPON).weaponCombinationPredicator((entitypatch) ->  {
            return true;
        });
        if (item instanceof TieredItem tieredItem) {
            int harvestLevel = tieredItem.getTier().getLevel();
            builder.addStyleAttibutes(CapabilityItem.Styles.COMMON, Pair.of((Attribute)EpicFightAttributes.IMPACT.get(), EpicFightAttributes.getImpactModifier(0.5 + 0.2 * (double)harvestLevel)));
            builder.addStyleAttibutes(CapabilityItem.Styles.COMMON, Pair.of((Attribute)EpicFightAttributes.MAX_STRIKES.get(), EpicFightAttributes.getMaxStrikesModifier(1)));
            builder.hitParticle((HitParticleType) EpicFightParticles.HIT_BLADE.get());
        }
        return builder;
    };
    public static final Function<Item, CapabilityItem.Builder> Dark_Obsidian = (item) -> {
        return WeaponCapability.builder().newStyleCombo(CapabilityItem.Styles.ONE_HAND, new StaticAnimation[]{Animations.FIST_AUTO1, Animations.FIST_AUTO2, Animations.FIST_AUTO3, Animations.FIST_DASH, Animations.FIST_AIR_SLASH}).innateSkill(CapabilityItem.Styles.ONE_HAND, (itemstack) -> {
            return BuxinSkills.DARK_OBSIDIAN;
        }).category(CapabilityItem.WeaponCategories.FIST).constructor(GloveCapability::new);
    };

    public static final Function<Item, CapabilityItem.Builder> FUCKLANDSWORD = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder().category(CapabilityItem.WeaponCategories.SWORD).swingSound(BuxinModSounds.SWORD_WHOOSH.get()).styleProvider((playerpatch) -> {
            return playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != CapabilityItem.WeaponCategories.GREATSWORD && playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != CapabilityItem.WeaponCategories.AXE && playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != CapabilityItem.WeaponCategories.TACHI ? CapabilityItem.Styles.ONE_HAND : CapabilityItem.Styles.TWO_HAND;
        }).collider(ColliderPreset.GREATSWORD).newStyleCombo(CapabilityItem.Styles.ONE_HAND, new StaticAnimation[]{Animations.SWORD_AUTO1, Animations.SWORD_AUTO2, Animations.SWORD_AUTO3, Animations.SWORD_AUTO2, BuxinAnimations.DUAL_SWORD_DASH, Animations.VINDICATOR_SWING_AXE3}).newStyleCombo(CapabilityItem.Styles.TWO_HAND, new StaticAnimation[]{Animations.SWORD_DUAL_AUTO1, Animations.SWORD_DUAL_AUTO3, BuxinAnimations.SWORD_DUAL_AUTO_3_BETTER,Animations.SWORD_AUTO2, BuxinAnimations.SWORD_DUAL_AUTO1, BuxinAnimations.SWORD_DUAL_AUTO2,BuxinAnimations.D3, BuxinAnimations.D4,Animations.DAGGER_DUAL_DASH,Animations.SWORD_DUAL_DASH, Animations.VINDICATOR_SWING_AXE3}).innateSkill(CapabilityItem.Styles.ONE_HAND, (itemstack) -> {
            return null;
        }).innateSkill(CapabilityItem.Styles.TWO_HAND, (itemstack) -> {
            return null;
        }).livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD).livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.RUN, BuxinAnimations.DUAL_SWORD_RUN).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_RUN_DUAL).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_DUAL).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.FLOAT, Animations.BIPED_HOLD_DUAL_WEAPON).weaponCombinationPredicator((entitypatch) ->  {
            return true;
        });
        if (item instanceof TieredItem tieredItem) {
            int harvestLevel = tieredItem.getTier().getLevel();
            builder.addStyleAttibutes(CapabilityItem.Styles.COMMON, Pair.of((Attribute)EpicFightAttributes.IMPACT.get(), EpicFightAttributes.getImpactModifier(0.5 + 0.2 * (double)harvestLevel)));
            builder.addStyleAttibutes(CapabilityItem.Styles.COMMON, Pair.of((Attribute)EpicFightAttributes.MAX_STRIKES.get(), EpicFightAttributes.getMaxStrikesModifier(1)));
            builder.hitSound(EpicFightSounds.BLADE_HIT.get());
            builder.hitParticle(EpicFightParticles.HIT_BLADE.get());
        }

        return builder;
    };

    public static final Function<Item, CapabilityItem.Builder> SingleSparkCanStartPrairieFire = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder().category(CapabilityItem.WeaponCategories.SWORD).swingSound(BuxinModSounds.SWORD_WHOOSH.get()).styleProvider((playerpatch) -> {
            return playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != CapabilityItem.WeaponCategories.SWORD && playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != CapabilityItem.WeaponCategories.AXE && playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != CapabilityItem.WeaponCategories.TACHI ? CapabilityItem.Styles.ONE_HAND : CapabilityItem.Styles.TWO_HAND;
        }).collider(ColliderPreset.SWORD).newStyleCombo(CapabilityItem.Styles.ONE_HAND, new StaticAnimation[]{Animations.SWORD_AUTO1, Animations.SWORD_AUTO2, Animations.SWORD_AUTO3, Animations.SWORD_AUTO2, BuxinAnimations.DUAL_SWORD_DASH, Animations.VINDICATOR_SWING_AXE3}).newStyleCombo(CapabilityItem.Styles.TWO_HAND, new StaticAnimation[]{Animations.SWORD_DUAL_AUTO1, Animations.SWORD_DUAL_AUTO3, BuxinAnimations.SWORD_DUAL_AUTO_3_BETTER,Animations.SWORD_AUTO2, BuxinAnimations.SWORD_DUAL_AUTO1, BuxinAnimations.SWORD_DUAL_AUTO2,BuxinAnimations.D3, BuxinAnimations.D4,Animations.DAGGER_DUAL_DASH,Animations.SWORD_DUAL_DASH, Animations.VINDICATOR_SWING_AXE3}).innateSkill(CapabilityItem.Styles.ONE_HAND, (itemstack) -> {
            return null;
        }).innateSkill(CapabilityItem.Styles.TWO_HAND, (itemstack) -> {
            return null;
        }).livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD).livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.RUN, BuxinAnimations.DUAL_SWORD_RUN).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_RUN_DUAL).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_DUAL).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.FLOAT, Animations.BIPED_HOLD_DUAL_WEAPON).weaponCombinationPredicator((entitypatch) ->  {
            return true;
        });
        if (item instanceof TieredItem tieredItem) {
            int harvestLevel = tieredItem.getTier().getLevel();
            builder.addStyleAttibutes(CapabilityItem.Styles.COMMON, Pair.of((Attribute)EpicFightAttributes.IMPACT.get(), EpicFightAttributes.getImpactModifier(0.5 + 0.2 * (double)harvestLevel)));
            builder.addStyleAttibutes(CapabilityItem.Styles.COMMON, Pair.of((Attribute)EpicFightAttributes.MAX_STRIKES.get(), EpicFightAttributes.getMaxStrikesModifier(1)));
            builder.hitSound(EpicFightSounds.BLADE_HIT.get());
            builder.hitParticle(EpicFightParticles.HIT_BLADE.get());
        }

        return builder;
    };
    public static final Function<Item, CapabilityItem.Builder> HB_SICKLE = (item) -> {
        return WeaponCapability.builder().category(CapabilityItem.WeaponCategories.SPEAR).styleProvider((playerpatch) -> {
            return playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SHIELD ? CapabilityItem.Styles.ONE_HAND : CapabilityItem.Styles.TWO_HAND;
        }).collider(ColliderPreset.SPEAR).hitSound((SoundEvent)EpicFightSounds.BLADE_HIT.get()).canBePlacedOffhand(false).newStyleCombo(CapabilityItem.Styles.ONE_HAND, new StaticAnimation[]{Animations.SPEAR_ONEHAND_AUTO, Animations.SPEAR_DASH, Animations.SPEAR_ONEHAND_AIR_SLASH}).newStyleCombo(CapabilityItem.Styles.TWO_HAND, new StaticAnimation[]{Animations.SPEAR_TWOHAND_AUTO1, Animations.SPEAR_TWOHAND_AUTO2, Animations.SPEAR_DASH, Animations.SPEAR_TWOHAND_AIR_SLASH}).newStyleCombo(CapabilityItem.Styles.MOUNT, new StaticAnimation[]{Animations.SPEAR_MOUNT_ATTACK}).innateSkill(CapabilityItem.Styles.ONE_HAND, (itemstack) -> {
            return EpicFightSkills.HEARTPIERCER;
        }).innateSkill(CapabilityItem.Styles.TWO_HAND, (itemstack) -> {
            return EpicFightSkills.GRASPING_SPIRE;
        }).livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_SPEAR).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_SPEAR).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_WALK_SPEAR).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_SPEAR).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SPEAR_GUARD);
    };

    public static final Function<Item, CapabilityItem.Builder> SWORD = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder().category(CapabilityItem.WeaponCategories.SWORD).swingSound(BuxinModSounds.SWORD_WHOOSH.get()).styleProvider((playerpatch) -> {
            return playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != CapabilityItem.WeaponCategories.SWORD && playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != CapabilityItem.WeaponCategories.AXE && playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != CapabilityItem.WeaponCategories.TACHI ? CapabilityItem.Styles.ONE_HAND : CapabilityItem.Styles.TWO_HAND;
        }).collider(ColliderPreset.SWORD).newStyleCombo(CapabilityItem.Styles.ONE_HAND, new StaticAnimation[]{Animations.SWORD_AUTO1, Animations.SWORD_AUTO2, Animations.SWORD_AUTO3, Animations.SWORD_AUTO2, BuxinAnimations.DUAL_SWORD_DASH, Animations.VINDICATOR_SWING_AXE3}).newStyleCombo(CapabilityItem.Styles.TWO_HAND, new StaticAnimation[]{Animations.SWORD_DUAL_AUTO1, Animations.SWORD_DUAL_AUTO3, BuxinAnimations.SWORD_DUAL_AUTO_3_BETTER,Animations.SWORD_AUTO2, BuxinAnimations.SWORD_DUAL_AUTO1, BuxinAnimations.SWORD_DUAL_AUTO2,BuxinAnimations.D3, BuxinAnimations.D4,Animations.DAGGER_DUAL_DASH,Animations.SWORD_DUAL_DASH, Animations.VINDICATOR_SWING_AXE3}).innateSkill(CapabilityItem.Styles.ONE_HAND, (itemstack) -> {
            return EpicFightSkills.SWEEPING_EDGE;
        }).innateSkill(CapabilityItem.Styles.TWO_HAND, (itemstack) -> {
            return EpicFightSkills.DANCING_EDGE;
        }).livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_DUAL).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.FLOAT, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.FALL, Animations.BIPED_HOLD_DUAL_WEAPON).weaponCombinationPredicator((entitypatch) ->  {
            return true;
        });
        builder.addStyleAttibutes(CapabilityItem.Styles.COMMON, Pair.of((Attribute)EpicFightAttributes.MAX_STRIKES.get(), EpicFightAttributes.getMaxStrikesModifier(1)));
        builder.hitSound(EpicFightSounds.BLADE_HIT.get());
        builder.hitParticle(EpicFightParticles.HIT_BLADE.get());

        return builder;
    };

    public static final Function<Item, CapabilityItem.Builder> AXE = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder().category(CapabilityItem.WeaponCategories.AXE).swingSound(EpicFightSounds.WHOOSH_BIG.get()).styleProvider((playerpatch) -> {
            return playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != CapabilityItem.WeaponCategories.AXE && playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != CapabilityItem.WeaponCategories.AXE && playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != CapabilityItem.WeaponCategories.TACHI ? CapabilityItem.Styles.ONE_HAND : CapabilityItem.Styles.TWO_HAND;
        }).collider(ColliderPreset.SWORD).newStyleCombo(CapabilityItem.Styles.ONE_HAND, new StaticAnimation[]{Animations.SWORD_AUTO1, Animations.SWORD_AUTO2, Animations.SWORD_AUTO3, Animations.SWORD_AUTO2, BuxinAnimations.DUAL_SWORD_DASH, Animations.VINDICATOR_SWING_AXE3}).newStyleCombo(CapabilityItem.Styles.TWO_HAND, new StaticAnimation[]{Animations.SWORD_DUAL_AUTO1, Animations.SWORD_DUAL_AUTO3, BuxinAnimations.SWORD_DUAL_AUTO_3_BETTER,Animations.SWORD_AUTO2,BuxinAnimations.D3, BuxinAnimations.D4,Animations.DAGGER_DUAL_DASH,Animations.SWORD_DUAL_DASH, Animations.VINDICATOR_SWING_AXE3}).innateSkill(CapabilityItem.Styles.ONE_HAND, (itemstack) -> {
            return EpicFightSkills.SWEEPING_EDGE;
        }).innateSkill(CapabilityItem.Styles.TWO_HAND, (itemstack) -> {
            return EpicFightSkills.DANCING_EDGE;
        }).livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_DUAL).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.FLOAT, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.FALL, Animations.BIPED_HOLD_DUAL_WEAPON).weaponCombinationPredicator((entitypatch) ->  {
            return true;
        });
        builder.addStyleAttibutes(CapabilityItem.Styles.COMMON, Pair.of((Attribute)EpicFightAttributes.MAX_STRIKES.get(), EpicFightAttributes.getMaxStrikesModifier(1)));
        builder.hitSound(EpicFightSounds.BLADE_HIT.get());
        builder.hitParticle(EpicFightParticles.HIT_BLADE.get());

        return builder;
    };

    public static final Function<Item, CapabilityItem.Builder> ENTITY_303_SWORD = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder().category(CapabilityItem.WeaponCategories.SWORD).swingSound(BuxinModSounds.SWORD_WHOOSH.get()).styleProvider((playerpatch) -> {
            return playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != CapabilityItem.WeaponCategories.SWORD && playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != CapabilityItem.WeaponCategories.AXE && playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != CapabilityItem.WeaponCategories.TACHI ? CapabilityItem.Styles.ONE_HAND : CapabilityItem.Styles.TWO_HAND;
        }).collider(ColliderPreset.SWORD).newStyleCombo(CapabilityItem.Styles.ONE_HAND, new StaticAnimation[]{Animations.SWORD_AUTO1, Animations.SWORD_AUTO2, Animations.SWORD_AUTO3, Animations.SWORD_AUTO2, BuxinAnimations.DUAL_SWORD_DASH, Animations.VINDICATOR_SWING_AXE3}).newStyleCombo(CapabilityItem.Styles.TWO_HAND, new StaticAnimation[]{Animations.SWORD_DUAL_AUTO1, Animations.SWORD_DUAL_AUTO3, BuxinAnimations.SWORD_DUAL_AUTO_3_BETTER,Animations.SWORD_AUTO2, BuxinAnimations.SWORD_DUAL_AUTO1, BuxinAnimations.SWORD_DUAL_AUTO2,BuxinAnimations.D3, BuxinAnimations.D4,Animations.DAGGER_DUAL_DASH,Animations.SWORD_DUAL_DASH, Animations.VINDICATOR_SWING_AXE3}).innateSkill(CapabilityItem.Styles.ONE_HAND, (itemstack) -> {
            return BuxinSkills.ENTITY_303_SKILL;
        }).innateSkill(CapabilityItem.Styles.TWO_HAND, (itemstack) -> {
            return BuxinSkills.DOUBLE_ENTITY_303_SKILL;
        }).livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_DUAL).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.FLOAT, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.FALL, Animations.BIPED_HOLD_DUAL_WEAPON).weaponCombinationPredicator((entitypatch) ->  {
            return true;
        });
        builder.addStyleAttibutes(CapabilityItem.Styles.COMMON, Pair.of((Attribute)EpicFightAttributes.MAX_STRIKES.get(), EpicFightAttributes.getMaxStrikesModifier(1)));
        builder.hitSound(EpicFightSounds.BLADE_HIT.get());
        builder.hitParticle(EpicFightParticles.HIT_BLADE.get());

        return builder;
    };

    public static final Function<Item, CapabilityItem.Builder> WOOPIE = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder().category(CapabilityItem.WeaponCategories.SWORD).swingSound(BuxinModSounds.SWORD_WHOOSH.get()).styleProvider((playerpatch) -> {
            return playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != CapabilityItem.WeaponCategories.SWORD && playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != CapabilityItem.WeaponCategories.AXE && playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() != CapabilityItem.WeaponCategories.TACHI ? CapabilityItem.Styles.ONE_HAND : CapabilityItem.Styles.TWO_HAND;
        }).collider(ColliderPreset.SWORD).newStyleCombo(CapabilityItem.Styles.ONE_HAND, new StaticAnimation[]{Animations.SWORD_AUTO1, Animations.SWORD_AUTO2, Animations.SWORD_AUTO3, Animations.SWORD_AUTO2, BuxinAnimations.DUAL_SWORD_DASH, Animations.VINDICATOR_SWING_AXE3}).newStyleCombo(CapabilityItem.Styles.TWO_HAND, new StaticAnimation[]{Animations.SWORD_DUAL_AUTO1, Animations.SWORD_DUAL_AUTO3, BuxinAnimations.SWORD_DUAL_AUTO_3_BETTER,Animations.SWORD_AUTO2, BuxinAnimations.SWORD_DUAL_AUTO1, BuxinAnimations.SWORD_DUAL_AUTO2,BuxinAnimations.D3, BuxinAnimations.D4,Animations.DAGGER_DUAL_DASH,Animations.SWORD_DUAL_DASH, Animations.VINDICATOR_SWING_AXE3}).innateSkill(CapabilityItem.Styles.ONE_HAND, (itemstack) -> {
            return BuxinSkills.WOOPIE;
        }).innateSkill(CapabilityItem.Styles.TWO_HAND, (itemstack) -> {
            return BuxinSkills.WOOPIE;
        }).livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_DUAL).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.FLOAT, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.FALL, Animations.BIPED_HOLD_DUAL_WEAPON).weaponCombinationPredicator((entitypatch) ->  {
            return true;
        });
        if (item instanceof TieredItem tieredItem) {
            int harvestLevel = tieredItem.getTier().getLevel();
            builder.addStyleAttibutes(CapabilityItem.Styles.COMMON, Pair.of((Attribute)EpicFightAttributes.IMPACT.get(), EpicFightAttributes.getImpactModifier(0.5 + 0.2 * (double)harvestLevel)));
            builder.addStyleAttibutes(CapabilityItem.Styles.COMMON, Pair.of((Attribute)EpicFightAttributes.MAX_STRIKES.get(), EpicFightAttributes.getMaxStrikesModifier(1)));
            builder.hitSound(EpicFightSounds.BLADE_HIT.get());
            builder.hitParticle(EpicFightParticles.HIT_BLADE.get());
        }

        return builder;
    };



    public static final Function<Item, CapabilityItem.Builder> TRIDENT = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder().category(CapabilityItem.WeaponCategories.SWORD).styleProvider((playerpatch) -> {
            return playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SWORD ? CapabilityItem.Styles.TWO_HAND : CapabilityItem.Styles.ONE_HAND;
        }).collider(ColliderPreset.SWORD).newStyleCombo(CapabilityItem.Styles.ONE_HAND, new StaticAnimation[]{Animations.SWORD_AUTO1, Animations.SWORD_AUTO2, Animations.SWORD_AUTO3, Animations.SWORD_DASH, Animations.SWORD_AIR_SLASH}).newStyleCombo(CapabilityItem.Styles.TWO_HAND, new StaticAnimation[]{Animations.SWORD_DUAL_AUTO1, Animations.SWORD_DUAL_AUTO2, Animations.SWORD_DUAL_AUTO3, Animations.SWORD_DUAL_DASH, Animations.SWORD_DUAL_AIR_SLASH}).newStyleCombo(CapabilityItem.Styles.MOUNT, new StaticAnimation[]{Animations.SWORD_MOUNT_ATTACK}).innateSkill(CapabilityItem.Styles.ONE_HAND, (itemstack) -> {
            return EpicFightSkills.SWEEPING_EDGE;
        }).innateSkill(CapabilityItem.Styles.TWO_HAND, (itemstack) -> {
            return EpicFightSkills.DANCING_EDGE;
        }).livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_DUAL).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.FLOAT, Animations.BIPED_HOLD_DUAL_WEAPON).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.FALL, Animations.BIPED_HOLD_DUAL_WEAPON).weaponCombinationPredicator((entitypatch) -> {
            return EpicFightCapabilities.getItemStackCapability(((LivingEntity)entitypatch.getOriginal()).getOffhandItem()).getWeaponCategory() == CapabilityItem.WeaponCategories.SWORD;
        });
        if (item instanceof TieredItem tieredItem) {
            builder.hitSound(tieredItem.getTier() == Tiers.WOOD ? (SoundEvent)EpicFightSounds.BLUNT_HIT.get() : (SoundEvent)EpicFightSounds.BLADE_HIT.get());
            builder.hitParticle(tieredItem.getTier() == Tiers.WOOD ? (HitParticleType)EpicFightParticles.HIT_BLUNT.get() : (HitParticleType)EpicFightParticles.HIT_BLADE.get());
        }

        return builder;
    };
    public FuckLandSword() {
    }
    public static void register(WeaponCapabilityPresetRegistryEvent event) {
        event.getTypeEntry().put(new ResourceLocation(BuxinMod.MODID,"fist2"), FIST);
        event.getTypeEntry().put(new ResourceLocation(BuxinMod.MODID,"dark_obsidian"),Dark_Obsidian);
        event.getTypeEntry().put(new ResourceLocation(BuxinMod.MODID,"single_spark_can_start_prairie_fire"),SingleSparkCanStartPrairieFire);
        event.getTypeEntry().put(new ResourceLocation(BuxinMod.MODID,"trident"), TRIDENT);
        event.getTypeEntry().put(new ResourceLocation(BuxinMod.MODID,"fuck_land_sword"), FUCKLANDSWORD);
        event.getTypeEntry().put(new ResourceLocation(BuxinMod.MODID,"sword"), SWORD);
        event.getTypeEntry().put(new ResourceLocation(BuxinMod.MODID,"woopie"), WOOPIE);
        event.getTypeEntry().put(new ResourceLocation(BuxinMod.MODID,"hb_sickle"), HB_SICKLE);
        event.getTypeEntry().put(new ResourceLocation(BuxinMod.MODID,"axe"), AXE);
        event.getTypeEntry().put(new ResourceLocation(BuxinMod.MODID,"entity_303_sword"), ENTITY_303_SWORD);
    }
}