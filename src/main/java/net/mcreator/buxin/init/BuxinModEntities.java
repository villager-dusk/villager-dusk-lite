package net.mcreator.buxin.init;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.entity.*;
//import net.mcreator.buxin.entity.body.PlayerMobBodyEntity;
import net.mcreator.buxin.entity.dragon.normal.DragonBeamEntity;
import net.mcreator.buxin.entity.e_null.*;
import net.mcreator.buxin.entity.dragon.baby.BabyDragonBeamEntity;
import net.mcreator.buxin.entity.dragon.baby.BabyEnderDragonEntity;
import net.mcreator.buxin.entity.fake_entity.FakeEntity;
import net.mcreator.buxin.entity.fishinghook.FishingHook;
import net.mcreator.buxin.entity.greg_hb.GregHbEntity;
//import net.mcreator.buxin.entity.player_mob.HerobrinePlayerMobEntity;
import net.mcreator.buxin.entity.projectileblock.BuxinBlockProjectileEntity;
import net.mcreator.buxin.entity.purple_obsidian_herobrine.PurpleObsidianHerobrineEntity;
import net.mcreator.buxin.entity.shadow_herorbrine.ShadowHerobrineEntity;
import net.mcreator.buxin.entity.snakeblade_test.entity.SnakeBladeEntity;
import net.mcreator.buxin.entity.woopie.WoopieEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BuxinModEntities {
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, BuxinMod.MODID);
	public static final RegistryObject<EntityType<BlackShield2Entity>> BLACK_SHIELD_2 = register("projectile_black_shield_2",
			EntityType.Builder.<BlackShield2Entity>of(BlackShield2Entity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<AnYingHeiYaoShiFaSheQiEntity>> AN_YING_HEI_YAO_SHI_FA_SHE_QI = register("projectile_an_ying_hei_yao_shi_fa_she_qi",
			EntityType.Builder.<AnYingHeiYaoShiFaSheQiEntity>of(AnYingHeiYaoShiFaSheQiEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<CryobsEntity>> CRYOBS = register("projectile_cryobs",
			EntityType.Builder.<CryobsEntity>of(CryobsEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<Obsidian2Entity>> OBSIDIAN_2 = register("projectile_obsidian_2",
			EntityType.Builder.<Obsidian2Entity>of(Obsidian2Entity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<JshamanEntity>> JSHAMAN = register("jshaman",
			EntityType.Builder.<JshamanEntity>of(JshamanEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<Ty2yEntity>> TY_2Y = register("ty_2y",
			EntityType.Builder.<Ty2yEntity>of(Ty2yEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<Niubi6Entity>> NIUBI_6 = register("grave",
			EntityType.Builder.<Niubi6Entity>of(Niubi6Entity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<Niubi9Entity>> NIUBI_9 = register("shadow_sickle_herobrine",
			EntityType.Builder.<Niubi9Entity>of(Niubi9Entity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.8f));

	public static final RegistryObject<EntityType<Niubi13Entity>> NIUBI_13 = register("steve",
			EntityType.Builder.<Niubi13Entity>of(Niubi13Entity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(144).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<Niubi14Entity>> NIUBI_14 = register("angry_steve",
			EntityType.Builder.<Niubi14Entity>of(Niubi14Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<Niubi27Entity>> NIUBI_27 = register("pvp_monster",
			EntityType.Builder.<Niubi27Entity>of(Niubi27Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<ChangdaohimEntity>> CHANGDAOHIM = register("changdaohim",
			EntityType.Builder.<ChangdaohimEntity>of(ChangdaohimEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<ChangdaohimnbEntity>> CHANGDAOHIMNB = register("changdaohimnb",
			EntityType.Builder.<ChangdaohimnbEntity>of(ChangdaohimnbEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(144).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<LanemobentiEntity>> LANEMOBENTI = register("lanemobenti", EntityType.Builder.<LanemobentiEntity>of(LanemobentiEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
			.setTrackingRange(144).setUpdateInterval(3).fireImmune().sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<PurpleDemonEntity>> PURPLEDEMON = register("purple_demon", EntityType.Builder.<PurpleDemonEntity>of(PurpleDemonEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
			.setTrackingRange(144).setUpdateInterval(3).fireImmune().sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<ZhanshenzhirenemoEntity>> ZHANSHENZHIRENEMO = register("zhanshenzhirenemo",
			EntityType.Builder.<ZhanshenzhirenemoEntity>of(ZhanshenzhirenemoEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(99).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<YingchuihimEntity>> YINGCHUIHIM = register("yingchuihim",
			EntityType.Builder.<YingchuihimEntity>of(YingchuihimEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<MrEntity>> MR = register("mr",
			EntityType.Builder.<MrEntity>of(MrEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<ShifangEntity>> SHIFANG = register("shifang",
			EntityType.Builder.<ShifangEntity>of(ShifangEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<Jshaman2Entity>> JSHAMAN_2 = register("jshaman_2",
			EntityType.Builder.<Jshaman2Entity>of(Jshaman2Entity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<FengSuiTaErQuEntity>> FENG_SUI_TA_ER_QU = register("feng_sui_ta_er_qu",
			EntityType.Builder.<FengSuiTaErQuEntity>of(FengSuiTaErQuEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<SteveNbEntity>> STEVE_NB = register("steve_nb",
			EntityType.Builder.<SteveNbEntity>of(SteveNbEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(144).setUpdateInterval(3).fireImmune().sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<ZanZhu2Entity>> ZAN_ZHU_2 = register("zan_zhu_2",
			EntityType.Builder.<ZanZhu2Entity>of(ZanZhu2Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<ZanZhu3Entity>> ZAN_ZHU_3 = register("zan_zhu_3",
			EntityType.Builder.<ZanZhu3Entity>of(ZanZhu3Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.8f));

	public static final RegistryObject<EntityType<ZanZhu4Entity>> ZAN_ZHU_4 = register("zan_zhu_4",
			EntityType.Builder.<ZanZhu4Entity>of(ZanZhu4Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<ZanZhu5Entity>> ZAN_ZHU_5 = register("zan_zhu_5",
			EntityType.Builder.<ZanZhu5Entity>of(ZanZhu5Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<ZanZhu6Entity>> ZAN_ZHU_6 = register("zan_zhu_6",
			EntityType.Builder.<ZanZhu6Entity>of(ZanZhu6Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<ZanZhu7Entity>> ZAN_ZHU_7 = register("zan_zhu_7",
			EntityType.Builder.<ZanZhu7Entity>of(ZanZhu7Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<ZanZhu72Entity>> ZAN_ZHU_72 = register("zan_zhu_72",
			EntityType.Builder.<ZanZhu72Entity>of(ZanZhu72Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<ZanZhu8Entity>> ZAN_ZHU_8 = register("zan_zhu_8",
			EntityType.Builder.<ZanZhu8Entity>of(ZanZhu8Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<Grave2Entity>> GRAVE_2 = register("grave_2",
			EntityType.Builder.<Grave2Entity>of(Grave2Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(144).setUpdateInterval(3).fireImmune().sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<Herobrine3Entity>> HEROBRINE_3 = register("herobrine_3", EntityType.Builder.<Herobrine3Entity>of(Herobrine3Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
			.setTrackingRange(128).setUpdateInterval(3).fireImmune().sized(0.5f, 1.8f));
	public static final RegistryObject<EntityType<CunMinWeiBingEntity>> CUN_MIN_WEI_BING = register("cun_min_wei_bing",
			EntityType.Builder.<CunMinWeiBingEntity>of(CunMinWeiBingEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(164).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<ZanZhu9Entity>> ZAN_ZHU_9 = register("zan_zhu_9",
			EntityType.Builder.<ZanZhu9Entity>of(ZanZhu9Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(144).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<Zanzhu10Entity>> ZANZHU_10 = register("zanzhu_10",
			EntityType.Builder.<Zanzhu10Entity>of(Zanzhu10Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(144).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<ZanZhu11Entity>> ZAN_ZHU_11 = register("zan_zhu_11",
			EntityType.Builder.<ZanZhu11Entity>of(ZanZhu11Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(144).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<ZanZhu12Entity>> ZAN_ZHU_12 = register("zan_zhu_12",
			EntityType.Builder.<ZanZhu12Entity>of(ZanZhu12Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(144).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<Entity303Entity>> ENTITY_303 = register("entity_303", EntityType.Builder.<Entity303Entity>of(Entity303Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(128)
			.setUpdateInterval(3).fireImmune().sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<JieMoEntity>> JIE_MO = register("jie_mo",
			EntityType.Builder.<JieMoEntity>of(JieMoEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<RunGraveEntity>> RUN_GRAVE = register("run_grave",
			EntityType.Builder.<RunGraveEntity>of(RunGraveEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).fireImmune().sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<RedVillagerEntity>> RED_VILLAGER = register("red_villager",
			EntityType.Builder.<RedVillagerEntity>of(RedVillagerEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(164).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<DarkHerobrineEntity>> DARK_HEROBRINE = register("dark_herobrine", EntityType.Builder.<DarkHerobrineEntity>of(DarkHerobrineEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
			.setTrackingRange(128).setUpdateInterval(3).fireImmune().sized(0.5f, 1.8f));
	public static final RegistryObject<EntityType<HerobrineEntity>> HEROBRINE = register("herobrine", EntityType.Builder.<HerobrineEntity>of(HerobrineEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(128)
			.setUpdateInterval(3).fireImmune().sized(0.5f, 1.8f));
	public static final RegistryObject<EntityType<NotchEntity>> NOTCH = register("notch",
			EntityType.Builder.<NotchEntity>of(NotchEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(144).setUpdateInterval(3).fireImmune().sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<WL114514Entity>> WL_114514 = register("wl_114514",
			EntityType.Builder.<WL114514Entity>of(WL114514Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3)

					.sized(0.6f, 1.8f));

	public static final RegistryObject<EntityType<YongLongKnifeEntity>> YONG_LONG_KNIFE = register("yong_long_knife",
			EntityType.Builder.<YongLongKnifeEntity>of(YongLongKnifeEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(9964).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<HerobrineDsEntity>> HEROBRINE_DS = register("herobrine_ds", EntityType.Builder.<HerobrineDsEntity>of(HerobrineDsEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
			.setTrackingRange(128).setUpdateInterval(3).fireImmune().sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<IkunEntity>> IKUN = register("ikun",
			EntityType.Builder.<IkunEntity>of(IkunEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).fireImmune().sized(0.5f, 1f));
	public static final RegistryObject<EntityType<WhiteEntity>> WHITE = register("white",
			EntityType.Builder.<WhiteEntity>of(WhiteEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).fireImmune().sized(0.5f, 1f));
	public static final RegistryObject<EntityType<ByrusijkkEntity>> BYRUSIJKK = register("byrusijkk",
			EntityType.Builder.<ByrusijkkEntity>of(ByrusijkkEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).fireImmune().sized(0.5f, 1f));


	public static final RegistryObject<EntityType<JianGuoEntity>> JIAN_GUO = register("jian_guo",
			EntityType.Builder.<JianGuoEntity>of(JianGuoEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).fireImmune().sized(0.6f, 1.8f));



	public static final RegistryObject<EntityType<BlackShieldHerobrineEntity>> BLACK_SHIELD_HEROBRINE = register("black_shield_herobrine", EntityType.Builder.<BlackShieldHerobrineEntity>of(BlackShieldHerobrineEntity::new, MobCategory.MONSTER)
			.setShouldReceiveVelocityUpdates(true).setTrackingRange(144).setUpdateInterval(3).fireImmune().sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<GreenVillagerCavalryEntity>> GREEN_VILLAGER_CAVALRY = register("green_villager_cavalry",
			EntityType.Builder.<GreenVillagerCavalryEntity>of(GreenVillagerCavalryEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(164).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<PurpleVillagerCavalryEntity>> PURPLE_VILLAGER_CAVALRY = register("purple_villager_cavalry",
			EntityType.Builder.<PurpleVillagerCavalryEntity>of(PurpleVillagerCavalryEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(164).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<RedZombieEntity>> RED_ZOMBIE = register("red_zombie",
			EntityType.Builder.<RedZombieEntity>of(RedZombieEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(144).setUpdateInterval(3)

					.sized(6f, 18f));
	public static final RegistryObject<EntityType<SnakeBydEntity>> SNAKE_BYD = register("snake_byd",
			EntityType.Builder.<SnakeBydEntity>of(SnakeBydEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).fireImmune().sized(0.6f, 1.8f));

	public static final RegistryObject<EntityType<Qq3184765496Entity>> QQ_3184765496 = register("qq_3184765496",
			EntityType.Builder.<Qq3184765496Entity>of(Qq3184765496Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(144).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<HeiYaoShiEntity>> HEI_YAO_SHI = register("projectile_hei_yao_shi",
			EntityType.Builder.<HeiYaoShiEntity>of(HeiYaoShiEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<BigHeiYaoShiEntity>> BIG_HEI_YAO_SHI = register("projectile_big_hei_yao_shi",
			EntityType.Builder.<BigHeiYaoShiEntity>of(BigHeiYaoShiEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<BaozhajianEntity>> BAOZHAJIAN = register("projectile_baozhajian",
			EntityType.Builder.<BaozhajianEntity>of(BaozhajianEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<MoYingJianEntity>> MO_YING_JIAN = register("projectile_mo_ying_jian",
			EntityType.Builder.<MoYingJianEntity>of(MoYingJianEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));

	public static final RegistryObject<EntityType<PoisonEggEntity>> POISON_EGG = register("projectile_poison_egg",
			EntityType.Builder.<PoisonEggEntity>of(PoisonEggEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<TntEntity>> TNT = register("projectile_tnt",
			EntityType.Builder.<TntEntity>of(TntEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<StarstseaEntity>> STARSTSEA = register("starstsea",
			EntityType.Builder.<StarstseaEntity>of(StarstseaEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(99).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<DengZiEntity>> DENG_ZI = register("deng_zi",
			EntityType.Builder.<DengZiEntity>of(DengZiEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(144).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<Herobrine7Entity>> HEROBRINE_7 = register("herobrine_7", EntityType.Builder.<Herobrine7Entity>of(Herobrine7Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(128)
			.setUpdateInterval(3).fireImmune().sized(0.5f, 1f));
	public static final RegistryObject<EntityType<AlexEntity>> ALEX = register("alex",
			EntityType.Builder.<AlexEntity>of(AlexEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(99).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<TridentBlueDemonEntity>> TRIDENT_BLUE_DEMON = register("projectile_trident_blue_demon", EntityType.Builder.<TridentBlueDemonEntity>of(TridentBlueDemonEntity::new, MobCategory.MISC)
			.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<Herobrine1Entity>> HEROBRINE_1 = register("herobrine_1", EntityType.Builder.<Herobrine1Entity>of(Herobrine1Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(128)
			.setUpdateInterval(3).fireImmune().sized(0.5f, 1f));

	public static final RegistryObject<EntityType<RedGolem2Entity>> RED_GOLEM = register("red_golem", EntityType.Builder.<RedGolem2Entity>of(RedGolem2Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(128)
			.setUpdateInterval(3).fireImmune().sized(1.4F, 2.7F).clientTrackingRange(10));;

	public static final RegistryObject<EntityType<IceGolemEntity>> ICE_GOLEM = register("ice_golem",
			EntityType.Builder.<IceGolemEntity>of(IceGolemEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).fireImmune().sized(1.4F, 2.7F).clientTrackingRange(10));;
	public static final RegistryObject<EntityType<ObsGolemEntity>> OBS_GOLEM = register("obs_golem",
			EntityType.Builder.<ObsGolemEntity>of(ObsGolemEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).fireImmune().sized(1.4F, 2.7F).clientTrackingRange(10));;
	public static final RegistryObject<EntityType<SummonIronGolemEntity>> SUMMON_IRON_GOLEM = register("summon_iron_golem",
			EntityType.Builder.<SummonIronGolemEntity>of(SummonIronGolemEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<IllagerKingMobEntity>> ILLAGER_KING_MOB = register("illager_king_mob",
			EntityType.Builder.<IllagerKingMobEntity>of(IllagerKingMobEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(200).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<HorrorHerobrineEntity>> HORROR_HEROBRINE = register("horror_herobrine",
			EntityType.Builder.<HorrorHerobrineEntity>of(HorrorHerobrineEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(144).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<WhatShitEntity>> WHAT_SHIT = register("test",
			EntityType.Builder.<WhatShitEntity>of(WhatShitEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(666).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<WhatShitPhase2Entity>> WHAT_SHIT_PHASE_2 = register("test2",
			EntityType.Builder.<WhatShitPhase2Entity>of(WhatShitPhase2Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(0.6f, 1.8f));

	public static final RegistryObject<EntityType<VillagerScoutEntity>> VILLAGER_SCOUT = register("villager_scout",
			EntityType.Builder.<VillagerScoutEntity>of(VillagerScoutEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(164).setUpdateInterval(3)
					.sized(0.6f, 1.8f));

	public static final RegistryObject<EntityType<EnchanterEntity>> ENCHANTER = register("enchanter",
			EntityType.Builder.<EnchanterEntity>of(EnchanterEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(164).setUpdateInterval(3)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<NullEntity>> NULL = register("null", EntityType.Builder.<NullEntity>of(NullEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).fireImmune().sized(0.6F, 1.8F));
	public static final RegistryObject<EntityType<NullSwordEntity>> NULL_SWORD = register("null_sword", EntityType.Builder.<NullSwordEntity>of(NullSwordEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).fireImmune().sized(0.6F, 1.8F));
	public static final RegistryObject<EntityType<NullAxeEntity>> NULL_AXE = register("null_axe", EntityType.Builder.<NullAxeEntity>of(NullAxeEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).fireImmune().sized(0.6F, 1.8F));
	public static final RegistryObject<EntityType<NullPickaxeEntity>> NULL_PICKAXE = register("null_pickaxe", EntityType.Builder.<NullPickaxeEntity>of(NullPickaxeEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).fireImmune().sized(0.6F, 1.8F));
	public static final RegistryObject<EntityType<NullShovelEntity>> NULL_SHOVEL = register("null_shovel", EntityType.Builder.<NullShovelEntity>of(NullShovelEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).fireImmune().sized(0.6F, 1.8F));
	public static final RegistryObject<EntityType<NullHoeEntity>> NULL_HOE = register("null_hoe", EntityType.Builder.<NullHoeEntity>of(NullHoeEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).fireImmune().sized(0.6F, 1.8F));

	public static final RegistryObject<EntityType<BabyDragonBeamEntity>> BABY_DRAGON_BEAM = register("baby_dragon_beam", EntityType.Builder.<BabyDragonBeamEntity>of(BabyDragonBeamEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20));

	public static final RegistryObject<EntityType<BabyEnderDragonEntity>> BABY_ENDER_DRAGON = register("baby_ender_dragon", EntityType.Builder.<BabyEnderDragonEntity>of(BabyEnderDragonEntity::new, MobCategory.CREATURE).sized(0.9F, 0.5F).clientTrackingRange(8).updateInterval(3));

	public static final RegistryObject<EntityType<ShadowHerobrineEntity>> SHADOW_HEROBRINE = register("shadow_herobrine", EntityType.Builder.<ShadowHerobrineEntity>of(ShadowHerobrineEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
			.setTrackingRange(128).setUpdateInterval(3).fireImmune().sized(0.5f, 1.8f));


	public static final RegistryObject<EntityType<BuxinBlockProjectileEntity>> BLOCK_PROJECTILE = register("block_projectile", EntityType.Builder.<BuxinBlockProjectileEntity>of(BuxinBlockProjectileEntity::new, MobCategory.MONSTER).sized(0.9F, 0.9F).clientTrackingRange(64).updateInterval(2).fireImmune());

	public static final RegistryObject<EntityType<GregHbEntity>> GREG_HB = register("greg_hb",
			EntityType.Builder.<GregHbEntity>of(GregHbEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(164).setUpdateInterval(3)
					.sized(0.6f, 1.8f));

	public static final RegistryObject<EntityType<VillagerPrisonerEntity>> VillagerPrisoner = register("villager_prisoner",EntityType.Builder.<VillagerPrisonerEntity>of(VillagerPrisonerEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).sized(0.6F, 1.8F));


	public static final RegistryObject<EntityType<WoopieEntity>> WOOPIE = register("woopie", EntityType.Builder.<WoopieEntity>of(WoopieEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).fireImmune().sized(0.6F, 1.8F));

	public static final RegistryObject<EntityType<SnakeBladeEntity>> SNAKE_BLADE = register("snake_blade", EntityType.Builder.of(SnakeBladeEntity::new, MobCategory.MISC).sized(0.35F, 0.35F));

	public static final RegistryObject<EntityType<FishingHook>> FISHING_HOOK = REGISTRY.register("fishing_hook", () -> EntityType.Builder.<FishingHook>of(FishingHook::new, MobCategory.MISC)
			.noSave()
			.noSummon()
			.sized(0.25F, 0.25F)
			.clientTrackingRange(4)
			.updateInterval(5)
			.build("fishing_hook"));

	public static final RegistryObject<EntityType<TheMostMoistBurrit0Entity>> TheMostMoistBurrit0 = register("the_most_moist_burrit0",
			EntityType.Builder.<TheMostMoistBurrit0Entity>of(TheMostMoistBurrit0Entity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).fireImmune().sized(0.6f, 1.8f));

	public static final RegistryObject<EntityType<PurpleObsidianHerobrineEntity>> PURPLE_OBSIDIAN_HEROBRINE = register("purple_obsidian_herobrine",
			EntityType.Builder.<PurpleObsidianHerobrineEntity>of(PurpleObsidianHerobrineEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(128).setUpdateInterval(3).fireImmune().sized(0.6f, 1.8f));

	public static final RegistryObject<EntityType<DragonBeamEntity>> DRAGON_BEAM = register("dragon_beam", EntityType.Builder.<DragonBeamEntity>of(DragonBeamEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20));

	public static final RegistryObject<EntityType<FakeEntity>> FAKE_ENTITY = REGISTRY.register("fake_entity", () -> EntityType.Builder.<FakeEntity>of(FakeEntity::new, MobCategory.CREATURE).fireImmune().sized(0.6F, 1.8F).clientTrackingRange(10).noSave().build("fake_entity"));

	public static final RegistryObject<EntityType<ZombieVillagerScoutEntity>> ZOMBIE_VILLAGER_SCOUT = register("zombie_villager_scout",
			EntityType.Builder.<ZombieVillagerScoutEntity>of(ZombieVillagerScoutEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(164).setUpdateInterval(3));


	/*public static final RegistryObject<EntityType<HerobrineSpawnEntity>> HEROBRINE_SPAWN_ENTITY = register("herobrine_spawn_entity",
			EntityType.Builder.<HerobrineSpawnEntity>of(HerobrineSpawnEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(160).setUpdateInterval(3).setCustomClientFactory(HerobrineSpawnEntity::new)
					.sized(0.6f, 1.8f));

	 */

	private static <T extends Entity> RegistryObject<EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
		return REGISTRY.register(registryname, () -> (EntityType<T>) entityTypeBuilder.build(registryname));
	}

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			JshamanEntity.init();
			Ty2yEntity.init();
			Niubi6Entity.init();
			Niubi9Entity.init();

			Niubi13Entity.init();
			Niubi14Entity.init();
			Niubi27Entity.init();
			ChangdaohimEntity.init();
			ChangdaohimnbEntity.init();
			LanemobentiEntity.init();
			ZhanshenzhirenemoEntity.init();
			YingchuihimEntity.init();
			MrEntity.init();
			ShifangEntity.init();
			Jshaman2Entity.init();
			FengSuiTaErQuEntity.init();
			SteveNbEntity.init();
			ZanZhu2Entity.init();
			ZanZhu3Entity.init();

			ZanZhu4Entity.init();
			ZanZhu5Entity.init();
			ZanZhu6Entity.init();
			ZanZhu7Entity.init();
			ZanZhu72Entity.init();
			ZanZhu8Entity.init();
			Grave2Entity.init();
			Herobrine3Entity.init();
			CunMinWeiBingEntity.init();

			Zanzhu10Entity.init();
			ZanZhu11Entity.init();
			ZanZhu12Entity.init();
			Entity303Entity.init();
			JieMoEntity.init();
			RunGraveEntity.init();
			RedVillagerEntity.init();
			DarkHerobrineEntity.init();

			NotchEntity.init();
			WL114514Entity.init();

			YongLongKnifeEntity.init();
			HerobrineDsEntity.init();
			IkunEntity.init();
			WhiteEntity.init();
			ByrusijkkEntity.init();

			JianGuoEntity.init();

			BlackShieldHerobrineEntity.init();
			GreenVillagerCavalryEntity.init();
			PurpleVillagerCavalryEntity.init();
			RedZombieEntity.init();
			SnakeBydEntity.init();

			Qq3184765496Entity.init();
			StarstseaEntity.init();
			DengZiEntity.init();

			AlexEntity.init();
			Herobrine1Entity.init();
			RedGolem2Entity.init();
			IceGolemEntity.init();
			ObsGolemEntity.init();
			SummonIronGolemEntity.init();
			IllagerKingMobEntity.init();
			HorrorHerobrineEntity.init();
			WhatShitEntity.init();
			WhatShitPhase2Entity.init();
			VillagerScoutEntity.init();
			EnchanterEntity.init();
			BabyEnderDragonEntity.init();
			BabyDragonBeamEntity.init();
			NullSwordEntity.init();
			NullPickaxeEntity.init();
			NullShovelEntity.init();
			NullHoeEntity.init();
			NullEntity.init();
			PurpleDemonEntity.init();
			ShadowHerobrineEntity.init();
			//HerobrinePlayerMobEntity.init();
			GregHbEntity.init();
			WoopieEntity.init();
			VillagerPrisonerEntity.init();
			ZombieVillagerScoutEntity.init();
			//	HerobrineSpawnEntity.init();
		});
	}

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(JSHAMAN.get(), JshamanEntity.createAttributes().build());
		event.put(TY_2Y.get(), Ty2yEntity.createAttributes().build());
		event.put(NIUBI_6.get(), Niubi6Entity.createAttributes().build());
		event.put(NIUBI_9.get(), Niubi9Entity.createAttributes().build());

		event.put(NIUBI_13.get(), Niubi13Entity.createAttributes().build());
		event.put(NIUBI_14.get(), Niubi14Entity.createAttributes().build());
		event.put(NIUBI_27.get(), Niubi27Entity.createAttributes().build());
		event.put(CHANGDAOHIM.get(), ChangdaohimEntity.createAttributes().build());
		event.put(CHANGDAOHIMNB.get(), ChangdaohimnbEntity.createAttributes().build());
		event.put(LANEMOBENTI.get(), LanemobentiEntity.createAttributes().build());
		event.put(ZHANSHENZHIRENEMO.get(), ZhanshenzhirenemoEntity.createAttributes().build());
		event.put(YINGCHUIHIM.get(), YingchuihimEntity.createAttributes().build());
		event.put(MR.get(), MrEntity.createAttributes().build());
		event.put(SHIFANG.get(), ShifangEntity.createAttributes().build());
		event.put(JSHAMAN_2.get(), Jshaman2Entity.createAttributes().build());
		event.put(FENG_SUI_TA_ER_QU.get(), FengSuiTaErQuEntity.createAttributes().build());
		event.put(STEVE_NB.get(), SteveNbEntity.createAttributes().build());
		event.put(ZAN_ZHU_2.get(), ZanZhu2Entity.createAttributes().build());
		event.put(ZAN_ZHU_3.get(), ZanZhu3Entity.createAttributes().build());

		event.put(ZAN_ZHU_4.get(), ZanZhu4Entity.createAttributes().build());
		event.put(ZAN_ZHU_5.get(), ZanZhu5Entity.createAttributes().build());
		event.put(ZAN_ZHU_6.get(), ZanZhu6Entity.createAttributes().build());
		event.put(ZAN_ZHU_7.get(), ZanZhu7Entity.createAttributes().build());
		event.put(ZAN_ZHU_72.get(), ZanZhu72Entity.createAttributes().build());
		event.put(ZAN_ZHU_8.get(), ZanZhu8Entity.createAttributes().build());
		event.put(GRAVE_2.get(), Grave2Entity.createAttributes().build());
		event.put(HEROBRINE_3.get(), Herobrine3Entity.createAttributes().build());
		event.put(CUN_MIN_WEI_BING.get(), CunMinWeiBingEntity.createAttributes().build());
		event.put(ZAN_ZHU_9.get(), ZanZhu9Entity.createAttributes().build());
		event.put(ZANZHU_10.get(), Zanzhu10Entity.createAttributes().build());
		event.put(ZAN_ZHU_11.get(), ZanZhu11Entity.createAttributes().build());
		event.put(ZAN_ZHU_12.get(), ZanZhu12Entity.createAttributes().build());
		event.put(ENTITY_303.get(), Entity303Entity.createAttributes().build());
		event.put(JIE_MO.get(), JieMoEntity.createAttributes().build());
		event.put(RUN_GRAVE.get(), RunGraveEntity.createAttributes().build());
		event.put(RED_VILLAGER.get(), RedVillagerEntity.createAttributes().build());
		event.put(DARK_HEROBRINE.get(), DarkHerobrineEntity.createAttributes().build());
		event.put(HEROBRINE.get(), HerobrineEntity.createAttributes().build());
		event.put(NOTCH.get(), NotchEntity.createAttributes().build());
		event.put(WL_114514.get(), WL114514Entity.createAttributes().build());
		event.put(YONG_LONG_KNIFE.get(), YongLongKnifeEntity.createAttributes().build());
		event.put(HEROBRINE_DS.get(), HerobrineDsEntity.createAttributes().build());
		event.put(IKUN.get(), IkunEntity.createAttributes().build());
		event.put(WHITE.get(), WhiteEntity.createAttributes().build());
		event.put(BYRUSIJKK.get(), ByrusijkkEntity.createAttributes().build());


		event.put(JIAN_GUO.get(), JianGuoEntity.createAttributes().build());



		event.put(BLACK_SHIELD_HEROBRINE.get(), BlackShieldHerobrineEntity.createAttributes().build());
		event.put(GREEN_VILLAGER_CAVALRY.get(), GreenVillagerCavalryEntity.createAttributes().build());
		event.put(PURPLE_VILLAGER_CAVALRY.get(), PurpleVillagerCavalryEntity.createAttributes().build());
		event.put(RED_ZOMBIE.get(), RedZombieEntity.createAttributes().build());
		event.put(SNAKE_BYD.get(), SnakeBydEntity.createAttributes().build());

		event.put(QQ_3184765496.get(), Qq3184765496Entity.createAttributes().build());
		event.put(STARSTSEA.get(), StarstseaEntity.createAttributes().build());
		event.put(DENG_ZI.get(), DengZiEntity.createAttributes().build());
		event.put(HEROBRINE_7.get(), Herobrine7Entity.createAttributes().build());
		event.put(ALEX.get(), AlexEntity.createAttributes().build());
		event.put(HEROBRINE_1.get(), Herobrine1Entity.createAttributes().build());
		event.put(RED_GOLEM.get(), RedGolem2Entity.createAttributes().build());
		event.put(ICE_GOLEM.get(), IceGolemEntity.createAttributes().build());
		event.put(OBS_GOLEM.get(), ObsGolemEntity.createAttributes().build());
		event.put(SUMMON_IRON_GOLEM.get(), SummonIronGolemEntity.createAttributes().build());
		event.put(ILLAGER_KING_MOB.get(), IllagerKingMobEntity.createAttributes().build());
		event.put(HORROR_HEROBRINE.get(), HorrorHerobrineEntity.createAttributes().build());
		event.put(WHAT_SHIT.get(), WhatShitEntity.createAttributes().build());
		event.put(WHAT_SHIT_PHASE_2.get(), WhatShitPhase2Entity.createAttributes().build());
		event.put(VILLAGER_SCOUT.get(), VillagerScoutEntity.createAttributes().build());
		event.put(ENCHANTER.get(), EnchanterEntity.createAttributes().build());
		event.put(BABY_ENDER_DRAGON.get(), BabyEnderDragonEntity.createAttributes().build());
		event.put(BuxinModEntities.NULL.get(), NullEntity.createAttributes().build());
		event.put(BuxinModEntities.NULL_SWORD.get(), NullSwordEntity.createAttributes().build());
		event.put(BuxinModEntities.NULL_AXE.get(), NullAxeEntity.createAttributes().build());
		event.put(BuxinModEntities.NULL_PICKAXE.get(), NullPickaxeEntity.createAttributes().build());
		event.put(BuxinModEntities.NULL_SHOVEL.get(), NullShovelEntity.createAttributes().build());
		event.put(BuxinModEntities.NULL_HOE.get(), NullHoeEntity.createAttributes().build());
		event.put(BuxinModEntities.PURPLEDEMON.get(),PurpleDemonEntity.createAttributes().build());
		event.put(BuxinModEntities.SHADOW_HEROBRINE.get(),ShadowHerobrineEntity.createMobAttributes().build());
		//event.put(BuxinModEntities.Infected_PlayerMOB.get(), HerobrinePlayerMobEntity.createAttributes().build());
		event.put(BuxinModEntities.GREG_HB.get(), GregHbEntity.createAttributes().build());
		event.put(BuxinModEntities.WOOPIE.get(),WoopieEntity.createAttributes().build());
		//event.put(BuxinModEntities.PLAYER_MOB_BODY.get(),PlayerMobBodyEntity.createAttributes().build());
		event.put(BuxinModEntities.VillagerPrisoner.get(),VillagerPrisonerEntity.createAttributes().build());
		event.put(BuxinModEntities.TheMostMoistBurrit0.get(),TheMostMoistBurrit0Entity.createAttributes().build());
		event.put(BuxinModEntities.PURPLE_OBSIDIAN_HEROBRINE.get(),PurpleObsidianHerobrineEntity.createMobAttributes().build());
		event.put(BuxinModEntities.FAKE_ENTITY.get(),FakeEntity.createAttributes().build());
		event.put(BuxinModEntities.ZOMBIE_VILLAGER_SCOUT.get(), ZombieVillagerScoutEntity.createAttributes().build());
		//event.put(BuxinModEntities.HEROBRINE_SPAWN_ENTITY.get(),HerobrineSpawnEntity.createAttributes().build());
	}
}