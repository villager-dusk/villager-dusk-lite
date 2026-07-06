package net.mcreator.buxin.skills;

import net.mcreator.buxin.skill_main.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.Skill.ActivateType;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;

@EventBusSubscriber(
        modid = "buxin",
        bus = Bus.MOD
)
public class BuxinSkills {
    public static Skill TRIDENT_HOLIDAY;
    public static Skill SINGLE_SPARK_CAN_START_PRAIRIE_FIRE;
    public static Skill WOOPIE;
    public static Skill HEAVY_DASH;
    public static Skill SUMMON_BABY_ENDER_DRAGON;
    public static Skill DARK_OBSIDIAN;
    public static Skill SNAKEBLADE;
    public static Skill ENTITY_303_SKILL;
    public static Skill DOUBLE_ENTITY_303_SKILL;
    public static Skill HEAVY_ATTACK;

    public BuxinSkills() {
    }

    @SubscribeEvent
    public static void buildSkillEvent(SkillBuildEvent build) {
        SkillBuildEvent.ModRegistryWorker modRegistry = build.createRegistryWorker("buxin");
        TRIDENT_HOLIDAY = modRegistry.build("trident_holiday", TridentHolidaySkillArt::new, WeaponInnateSkill.createWeaponInnateBuilder().setActivateType(ActivateType.ONE_SHOT));
        SINGLE_SPARK_CAN_START_PRAIRIE_FIRE = modRegistry.build("single_spark_can_start_prairie_fire", SingleSparkCanStartPrairieFireArt::new, WeaponInnateSkill.createWeaponInnateBuilder().setActivateType(ActivateType.ONE_SHOT));
        WOOPIE = modRegistry.build("woopie", WoopieSwordSkill::new, WeaponInnateSkill.createWeaponInnateBuilder().setActivateType(ActivateType.ONE_SHOT));
        HEAVY_DASH = modRegistry.build("heavy_dash", HeavyDashSkill::new, WeaponInnateSkill.createWeaponInnateBuilder().setActivateType(ActivateType.CHARGING));
        SUMMON_BABY_ENDER_DRAGON = modRegistry.build("summon_baby_ender_dragon_skill", SummonBabyEnderDragonSkill::new, WeaponInnateSkill.createWeaponInnateBuilder().setActivateType(ActivateType.CHARGING));
        DARK_OBSIDIAN = modRegistry.build("dark_obsidian", DarkObsidianSkill::new, WeaponInnateSkill.createWeaponInnateBuilder().setActivateType(ActivateType.CHARGING));
        SNAKEBLADE = modRegistry.build("snakeblade", SnakeBladeSkill::new, WeaponInnateSkill.createWeaponInnateBuilder().setActivateType(ActivateType.CHARGING));
        ENTITY_303_SKILL = modRegistry.build("entity_303_skill", Entity303SwordSkill::new, WeaponInnateSkill.createWeaponInnateBuilder().setActivateType(ActivateType.ONE_SHOT));
        DOUBLE_ENTITY_303_SKILL = modRegistry.build("double_entity_303_skill", DoubleEntity303SwordSkill::new, WeaponInnateSkill.createWeaponInnateBuilder().setActivateType(ActivateType.ONE_SHOT));
        HEAVY_ATTACK = modRegistry.build("heavy_attack", HeavyAttackSkill::new, WeaponInnateSkill.createWeaponInnateBuilder().setActivateType(ActivateType.ONE_SHOT));
    }
}