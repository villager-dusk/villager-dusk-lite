
package net.mcreator.buxin.capabilities;

import com.mojang.datafixers.util.Pair;
import net.mcreator.buxin.BuxinMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TieredItem;
import net.minecraft.sounds.SoundEvent;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Styles;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.function.Function;

public class LegendarySwordAndWoopieCapability {
    public static final Function<Item, CapabilityItem.Builder> LEGENDARYSWORD2 = (item) -> {
        return WeaponCapability.builder().category(WeaponCategories.GREATSWORD).styleProvider((playerpatch) -> {
            return Styles.TWO_HAND;
        }).collider(ColliderPreset.GREATSWORD).swingSound((SoundEvent)EpicFightSounds.WHOOSH_BIG.get()).hitSound((SoundEvent)EpicFightSounds.BLADE_HIT.get()).canBePlacedOffhand(false).newStyleCombo(Styles.TWO_HAND, new StaticAnimation[]{Animations.GREATSWORD_AUTO1, Animations.GREATSWORD_AUTO2, Animations.GREATSWORD_DASH, Animations.GREATSWORD_AIR_SLASH}).innateSkill(Styles.TWO_HAND, (itemstack) -> {
            return EpicFightSkills.STEEL_WHIRLWIND;
        }).livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_GREATSWORD).livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_WALK_GREATSWORD).livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_GREATSWORD).livingMotionModifier(Styles.TWO_HAND, LivingMotions.JUMP, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(Styles.TWO_HAND, LivingMotions.FLY, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(Styles.TWO_HAND, LivingMotions.CREATIVE_FLY, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(Styles.TWO_HAND, LivingMotions.CREATIVE_IDLE, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.GREATSWORD_GUARD);
    };

    public static final Function<Item, CapabilityItem.Builder> IRONFIST = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder().category(WeaponCategories.FIST).collider(ColliderPreset.TOOLS).newStyleCombo(Styles.ONE_HAND, new StaticAnimation[]{Animations.FIST_AUTO1, Animations.FIST_AUTO2, Animations.FIST_AUTO3, Animations.FIST_DASH, Animations.FIST_AIR_SLASH}).innateSkill(Styles.ONE_HAND, (itemstack) -> {
            return EpicFightSkills.RELENTLESS_COMBO;
        }).newStyleCombo(Styles.MOUNT, new StaticAnimation[]{Animations.FIST_AUTO1, Animations.FIST_AUTO2, Animations.FIST_AUTO3}).livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.UCHIGATANA_GUARD);

        return builder;
    };

    public LegendarySwordAndWoopieCapability() {
    }

    public static void register(WeaponCapabilityPresetRegistryEvent event) {
        event.getTypeEntry().put(new ResourceLocation(BuxinMod.MODID, "legendarysword2"), LEGENDARYSWORD2);
        event.getTypeEntry().put(new ResourceLocation(BuxinMod.MODID, "ironfist"), IRONFIST);
    }
}
