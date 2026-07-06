//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.mcreator.buxin.skills.skillbook;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import yesman.epicfight.api.data.reloader.SkillManager;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.passive.PassiveSkill;
import yesman.epicfight.world.item.EpicFightCreativeTabs;

@EventBusSubscriber(
        modid = "buxin",
        bus = Bus.MOD
)
public class BuxinSkillRegister {
    public static Skill BladeBattle;

    public BuxinSkillRegister() {
    }

    @SubscribeEvent
    public static void buildSkillEvent(SkillBuildEvent build) {
        SkillBuildEvent.ModRegistryWorker modRegistry = build.createRegistryWorker("buxin");
        BladeBattle = modRegistry.build("bladebattle", MainBladeBattle::new, PassiveSkill.createPassiveBuilder().setCreativeTab(EpicFightCreativeTabs.ITEMS.get()));
    }
}