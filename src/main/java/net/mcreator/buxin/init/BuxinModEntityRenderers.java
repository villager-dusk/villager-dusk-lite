
package net.mcreator.buxin.init;

import net.mcreator.buxin.client.renderer.*;
import net.mcreator.buxin.client.renderer.e_null.*;
import net.mcreator.buxin.client.renderer.ender_dragon.BabyDragonBeamRenderer;
import net.mcreator.buxin.client.renderer.ender_dragon.BabyEnderDragonRenderer;
import net.mcreator.buxin.client.renderer.ender_dragon.DragonBeamRenderer;
import net.mcreator.buxin.entity.AnYingHeiYaoShiFaSheQiEntity;
import net.mcreator.buxin.entity.BaozhajianEntity;
import net.mcreator.buxin.entity.BigHeiYaoShiEntity;
import net.mcreator.buxin.entity.purple_obsidian_herobrine.PurpleObsidianHerobrineEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
//import se.gory_moon.player_mobs.client.render.PlayerMobRenderer;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BuxinModEntityRenderers {
	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(BuxinModEntities.BLACK_SHIELD_2.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.AN_YING_HEI_YAO_SHI_FA_SHE_QI.get(), context -> new ArrowRenderer<AnYingHeiYaoShiFaSheQiEntity>(context) {
			@Override
			public ResourceLocation getTextureLocation(AnYingHeiYaoShiFaSheQiEntity entity) {
				return new ResourceLocation("buxin:textures/entities/baozhajian.png");
			}
		});
		event.registerEntityRenderer(BuxinModEntities.CRYOBS.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.OBSIDIAN_2.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.JSHAMAN.get(), JshamanRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.TY_2Y.get(), Ty2yRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.NIUBI_6.get(), Niubi6Renderer::new);
		event.registerEntityRenderer(BuxinModEntities.NIUBI_9.get(), Niubi9Renderer::new);
		//event.registerEntityRenderer(BuxinModEntities.NIUBI_12.get(), Niubi12Renderer::new);
		event.registerEntityRenderer(BuxinModEntities.NIUBI_13.get(), Niubi13Renderer::new);
		event.registerEntityRenderer(BuxinModEntities.NIUBI_14.get(), Niubi14Renderer::new);
		event.registerEntityRenderer(BuxinModEntities.NIUBI_27.get(), Niubi27Renderer::new);
		event.registerEntityRenderer(BuxinModEntities.CHANGDAOHIM.get(), ChangdaohimRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.CHANGDAOHIMNB.get(), ChangdaohimnbRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.LANEMOBENTI.get(), LanemobentiRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.ZHANSHENZHIRENEMO.get(), ZhanshenzhirenemoRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.YINGCHUIHIM.get(), YingchuihimRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.MR.get(), MrRenderer::new);
		//event.registerEntityRenderer(SpecialEntity.SNAKE_BLADE.get(), SnakeBladeRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.SHIFANG.get(), ShifangRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.JSHAMAN_2.get(), Jshaman2Renderer::new);
		event.registerEntityRenderer(BuxinModEntities.FENG_SUI_TA_ER_QU.get(), FengSuiTaErQuRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.STEVE_NB.get(), SteveNbRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.ZAN_ZHU_2.get(), ZanZhu2Renderer::new);
		event.registerEntityRenderer(BuxinModEntities.ZAN_ZHU_3.get(), ZanZhu3Renderer::new);
		//event.registerEntityRenderer(BuxinModEntities.ZR_DICI.get(), ZrDiciRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.ZAN_ZHU_4.get(), ZanZhu4Renderer::new);
		event.registerEntityRenderer(BuxinModEntities.ZAN_ZHU_5.get(), ZanZhu5Renderer::new);
		event.registerEntityRenderer(BuxinModEntities.ZAN_ZHU_6.get(), ZanZhu6Renderer::new);
		event.registerEntityRenderer(BuxinModEntities.ZAN_ZHU_7.get(), ZanZhu7Renderer::new);
		event.registerEntityRenderer(BuxinModEntities.ZAN_ZHU_72.get(), ZanZhu72Renderer::new);
		event.registerEntityRenderer(BuxinModEntities.ZAN_ZHU_8.get(), ZanZhu8Renderer::new);
		event.registerEntityRenderer(BuxinModEntities.GRAVE_2.get(), Grave2Renderer::new);
		event.registerEntityRenderer(BuxinModEntities.HEROBRINE_3.get(), Herobrine3Renderer::new);
		event.registerEntityRenderer(BuxinModEntities.CUN_MIN_WEI_BING.get(), CunMinWeiBingRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.ZAN_ZHU_9.get(), ZanZhu9Renderer::new);
		event.registerEntityRenderer(BuxinModEntities.ZANZHU_10.get(), Zanzhu10Renderer::new);
		event.registerEntityRenderer(BuxinModEntities.ZAN_ZHU_11.get(), ZanZhu11Renderer::new);
		event.registerEntityRenderer(BuxinModEntities.ZAN_ZHU_12.get(), ZanZhu12Renderer::new);
		event.registerEntityRenderer(BuxinModEntities.ENTITY_303.get(), Entity303Renderer::new);
		event.registerEntityRenderer(BuxinModEntities.JIE_MO.get(), JieMoRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.RUN_GRAVE.get(), RunGraveRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.RED_VILLAGER.get(), RedVillagerRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.DARK_HEROBRINE.get(), DarkHerobrineRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.HEROBRINE.get(), HerobrineRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.NOTCH.get(), NotchRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.WL_114514.get(), WL114514Renderer::new);
		//event.registerEntityRenderer(BuxinModEntities.BIG_BANANA.get(), BigBananaRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.YONG_LONG_KNIFE.get(), YongLongKnifeRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.HEROBRINE_DS.get(), HerobrineDsRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.IKUN.get(), IkunRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.WHITE.get(), WhiteRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.BYRUSIJKK.get(), ByrusijkkRenderer::new);
		//event.registerEntityRenderer(BuxinModEntities.M_134.get(), M134Renderer::new);
		//event.registerEntityRenderer(BuxinModEntities.OBS_STICK.get(), ObsStickRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.JIAN_GUO.get(), JianGuoRenderer::new);
		//event.registerEntityRenderer(BuxinModEntities.OBS_STICK_2.get(), ObsStick2Renderer::new);
		//event.registerEntityRenderer(BuxinModEntities.PUWARRIOR.get(), PuwarriorRenderer::new);
		//event.registerEntityRenderer(BuxinModEntities.BBQ.get(), BBQRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.BLACK_SHIELD_HEROBRINE.get(), BlackShieldHerobrineRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.GREEN_VILLAGER_CAVALRY.get(), GreenVillagerCavalryRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.PURPLE_VILLAGER_CAVALRY.get(), PurpleVillagerCavalryRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.RED_ZOMBIE.get(), RedZombieRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.SNAKE_BYD.get(), SnakeBydRenderer::new);
		//event.registerEntityRenderer(BuxinModEntities.BLUE_DEMON_TRIDENT_HOLIDY.get(), BlueDemonTridentHolidyRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.QQ_3184765496.get(), Qq3184765496Renderer::new);
		event.registerEntityRenderer(BuxinModEntities.HEI_YAO_SHI.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.BIG_HEI_YAO_SHI.get(), context -> new ArrowRenderer<BigHeiYaoShiEntity>(context) {
			@Override
			public ResourceLocation getTextureLocation(BigHeiYaoShiEntity entity) {
				return new ResourceLocation("buxin:textures/entities/iron_golem.png");
			}
		});
		event.registerEntityRenderer(BuxinModEntities.BAOZHAJIAN.get(), context -> new ArrowRenderer<BaozhajianEntity>(context) {
			@Override
			public ResourceLocation getTextureLocation(BaozhajianEntity entity) {
				return new ResourceLocation("buxin:textures/entities/iron_golem.png");
			}
		});
		event.registerEntityRenderer(BuxinModEntities.MO_YING_JIAN.get(), ThrownItemRenderer::new);
		//event.registerEntityRenderer(BuxinModEntities.DARKOBS.get(), DarkobsRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.POISON_EGG.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.TNT.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.STARSTSEA.get(), StarstseaRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.DENG_ZI.get(), DengZiRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.HEROBRINE_7.get(), Herobrine7Renderer::new);
		event.registerEntityRenderer(BuxinModEntities.ALEX.get(), AlexRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.GREG_HB.get(), GregHbRenderer::new);
		//event.registerEntityRenderer(BuxinModEntities.TRIDENT_BLUE_DEMON.get(), TridentBlueDemonRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.HEROBRINE_1.get(), Herobrine1Renderer::new);
		event.registerEntityRenderer(BuxinModEntities.RED_GOLEM.get(), RedGolem2Renderer::new);
		event.registerEntityRenderer(BuxinModEntities.ICE_GOLEM.get(), IceGolemRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.OBS_GOLEM.get(), ObsGolemRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.SUMMON_IRON_GOLEM.get(), SummonIronGolemRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.ILLAGER_KING_MOB.get(), IllagerKingMobRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.HORROR_HEROBRINE.get(), HorrorHerobrineRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.WHAT_SHIT.get(), WhatShitRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.WHAT_SHIT_PHASE_2.get(), WhatShitPhase2Renderer::new);
		event.registerEntityRenderer(BuxinModEntities.VILLAGER_SCOUT.get(), VillagerScoutRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.ENCHANTER.get(), EnchanterRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.BABY_ENDER_DRAGON.get(), BabyEnderDragonRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.BABY_DRAGON_BEAM.get(), BabyDragonBeamRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.NULL.get(), NullRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.NULL_SWORD.get(), NullSwordRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.NULL_AXE.get(), NullAxeRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.NULL_PICKAXE.get(), NullPickaxeRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.NULL_SHOVEL.get(), NullShovelRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.NULL_HOE.get(), NullHoeRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.PURPLEDEMON.get(), PurpleDemonRenderer::new);
		//event.registerEntityRenderer(BuxinModEntities.Infected_PlayerMOB.get(), PlayerMobRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.SHADOW_HEROBRINE.get(),ShadowHerobrineRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.BLOCK_PROJECTILE.get(), BlockProjectileRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.WOOPIE.get(),WoopieRenderer::new);
		//event.registerEntityRenderer(BuxinModEntities.PLAYER_MOB_BODY.get(),PlayerMobBodyRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.SNAKE_BLADE.get(), SnakeBladeEntityRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.VillagerPrisoner.get(), VillagerPrisonerEntityRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.FISHING_HOOK.get(), FishingHookRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.TheMostMoistBurrit0.get(), TheMostMoistBurrit0Renderer::new);
		event.registerEntityRenderer(BuxinModEntities.PURPLE_OBSIDIAN_HEROBRINE.get(), PurpleObsidianHerobrineRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.DRAGON_BEAM.get(), DragonBeamRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.FAKE_ENTITY.get(),FakeEntityRenderer::new);
		event.registerEntityRenderer(BuxinModEntities.ZOMBIE_VILLAGER_SCOUT.get(),ZombieVillagerScoutRenderer::new);
		//event.registerEntityRenderer(BuxinModEntities.HEROBRINE_SPAWN_ENTITY.get(), HerobrineSpawnEntityRenderer::new);
	}
}
