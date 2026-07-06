
package net.mcreator.buxin.capabilities;

import net.mcreator.buxin.BuxinMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

import java.util.function.Function;

public class NewLongswordWeaponCapability {
    public static final Function<Item, CapabilityItem.Builder> NEWLONGSWORD = (item) -> {
        return WeaponCapability.builder().category(CapabilityItem.WeaponCategories.GREATSWORD).styleProvider((playerpatch) -> {
            return CapabilityItem.Styles.TWO_HAND;
        }).collider(ColliderPreset.GREATSWORD).swingSound(EpicFightSounds.WHOOSH_BIG.get()).hitSound(EpicFightSounds.BLADE_HIT.get()).canBePlacedOffhand(false).newStyleCombo(CapabilityItem.Styles.TWO_HAND, new StaticAnimation[]{Animations.GREATSWORD_AUTO1, Animations.GREATSWORD_AUTO2, Animations.GREATSWORD_DASH, Animations.GREATSWORD_AIR_SLASH}).innateSkill(CapabilityItem.Styles.TWO_HAND, (itemstack) -> {
            return EpicFightSkills.STEEL_WHIRLWIND;
        }).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_GREATSWORD).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_WALK_GREATSWORD).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_GREATSWORD).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.JUMP, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.FLY, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.CREATIVE_FLY, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.CREATIVE_IDLE, Animations.BIPED_HOLD_GREATSWORD).livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, Animations.GREATSWORD_GUARD);
    };

    public NewLongswordWeaponCapability() {
    }

    public static void register(WeaponCapabilityPresetRegistryEvent event) {
        event.getTypeEntry().put(new ResourceLocation(BuxinMod.MODID,"new_longsword"), NEWLONGSWORD);
    }
}
