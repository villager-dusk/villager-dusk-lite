
package net.mcreator.buxin.capabilities;

import net.mcreator.buxin.BuxinMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import yesman.epicfight.world.capabilities.item.WeaponCapabilityPresets;

import java.util.function.Function;

public class SickleAxeCapability {
    public static final Function<Item, CapabilityItem.Builder> SickleAxe = (item) -> {
        return WeaponCapability.builder().category(CapabilityItem.WeaponCategories.SPEAR).styleProvider((playerpatch) -> {
            return playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SHIELD ? CapabilityItem.Styles.ONE_HAND : CapabilityItem.Styles.TWO_HAND;
        }).collider(ColliderPreset.SPEAR).hitSound((SoundEvent)EpicFightSounds.BLADE_HIT.get()).canBePlacedOffhand(false).newStyleCombo(CapabilityItem.Styles.ONE_HAND, new StaticAnimation[]{Animations.SPEAR_ONEHAND_AUTO, Animations.SPEAR_DASH, Animations.SPEAR_ONEHAND_AIR_SLASH}).newStyleCombo(CapabilityItem.Styles.TWO_HAND, new StaticAnimation[]{Animations.SPEAR_TWOHAND_AUTO1, Animations.SPEAR_TWOHAND_AUTO2, Animations.SPEAR_DASH, Animations.SPEAR_TWOHAND_AIR_SLASH}).newStyleCombo(CapabilityItem.Styles.MOUNT, new StaticAnimation[]{Animations.SPEAR_MOUNT_ATTACK}).innateSkill(CapabilityItem.Styles.ONE_HAND, (itemstack) -> {
            return EpicFightSkills.HEARTPIERCER;
        }).innateSkill(CapabilityItem.Styles.TWO_HAND, (itemstack) -> {
            return EpicFightSkills.GRASPING_SPIRE;
        }).livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_SPEAR).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_SPEAR).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_WALK_SPEAR).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_SPEAR).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SPEAR_GUARD);
    };


    public SickleAxeCapability() {
    }

    public static void register(WeaponCapabilityPresetRegistryEvent event) {
        event.getTypeEntry().put(new ResourceLocation(BuxinMod.MODID,"sickle_axe"), SickleAxe);
    }
}
