
		package net.mcreator.buxin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
//import mod.chloeprime.aaaparticles.forge.AAAParticlesForge;
import net.mcreator.buxin.capabilities.*;
import net.mcreator.buxin.command.VFXCommand;
import net.mcreator.buxin.common_attackgoals.AddCommonAttackGoal;
import net.mcreator.buxin.config.CommonConfigScreen;
import net.mcreator.buxin.config.client.BrightValueConfig;
import net.mcreator.buxin.config.client.ClientGuiConfig;
import net.mcreator.buxin.config.client.RenderCapeConfig;
import net.mcreator.buxin.config.common.*;
import net.mcreator.buxin.entity.*;
import net.mcreator.buxin.entity.father.BattleVillagerEntity;
import net.mcreator.buxin.gameasset.BuxinAnimations;
import net.mcreator.buxin.init.*;
import net.mcreator.buxin.my_method.*;
import net.mcreator.buxin.skills.BuxinSkills;
import net.mcreator.buxin.skills.skillbook.BuxinSkillRegister;
//import net.mcreator.buxin.utils.BlackShieldExplosionParticleEmitterInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import se.gory_moon.player_mobs.entity.EntityRegistry;
//import se.gory_moon.player_mobs.entity.PlayerMobEntity;
import software.bernie.geckolib.GeckoLib;
import yesman.epicfight.api.data.reloader.SkillManager;
import yesman.epicfight.main.EpicFightMod;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;


@Mod("buxin")
public class BuxinMod {
	public static final Logger LOGGER = LogManager.getLogger(BuxinMod.class);
	public static final String MODID = "buxin";
	public BuxinMod() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		IEventBus eventBus = MinecraftForge.EVENT_BUS;
		MinecraftForge.EVENT_BUS.register(this);
		BuxinModTabs.load();
		BuxinModSounds.REGISTRY.register(bus);
		BuxinModBlocks.REGISTRY.register(bus);
		BuxinModItems.REGISTRY.register(bus);
		BuxinModEntities.REGISTRY.register(bus);
		eventBus.addListener(this::registerCommands);
		registerCommonConfigs();
		ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, parent) -> new CommonConfigScreen(parent)));
		addWeaponType(bus);
		BuxinModMobEffects.REGISTRY.register(bus);
		BuxinModPotions.REGISTRY.register(bus);
		BuxinModMenus.REGISTRY.register(bus);
		//bus.addListener(SkillManager::createSkillRegistry);
		//bus.addListener(SkillManager::registerSkills);
		BuxinModParticleTypes.REGISTRY.register(bus);
		GeckoLib.initialize();
	}
	private void registerCommands(RegisterCommandsEvent event) {
		VFXCommand.register(event.getDispatcher());
	}

	private static void registerCommonConfigs(){
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, VFXParticleConfig.SPEC,"villager-dusk-main-mod-VFX-common.toml");
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ItemRemoveTimeConfig.SPEC,"villager-dusk-main-mod-item_remove_time-common.toml");
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, GuardShakeValueConfig.SPEC,"villager-dusk-main-mod-guard-common.toml");
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SwordBattleConfig.SPEC,"villager-dusk-main-mod-swordbattle-common.toml");
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SnakeBladeConfig.SPEC,"villager-dusk-main-mod-snakeblade-common.toml");
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, BrightValueConfig.SPEC,"villager-dusk-main-mod-brightvalue-client.toml");
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, FakePlayerJoinMessageConfig.SPEC,"villager-dusk-main-mod-fakeplayerjoinmessage-common.toml");
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DodgePercentConfig.SPEC,"villager-dusk-main-mod-entity_dodge-common.toml");
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, RunningSpeedMultipleConfig.SPEC,"villager-dusk-main-mod-running_speed_multiple-common.toml");
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientGuiConfig.SPEC, "villager-dusk-main-mod-gui-client.toml");
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, RenderCapeConfig.SPEC, "villager-dusk-main-mod-render_cape-client.toml");
	}
	private static void addWeaponType(IEventBus bus){
		bus.addListener(BuxinAnimations::registerAnimations);
		bus.addListener(LegendaryswordCapability::register);
		bus.addListener(LegendarySwordAndWoopieCapability::register);
		bus.addListener(SickleAxeCapability::register);
		bus.addListener(FuckLandSword::register);
		bus.addListener(LegendarySwordTwoHandCapability::register);
		bus.addListener(NewLongswordWeaponCapability::register);
	}

	public static final RandomSource randomSource = RandomSource.create();
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(MODID, MODID), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	private static int messageID = 0;
	public static boolean isLowHp(LivingEntity human) {
		if (human.getOffhandItem().getItem() == Items.TOTEM_OF_UNDYING) {
			return false;
		} else {
			return (double)human.getHealth() < (double)human.getMaxHealth() * 4;
		}
	}
	public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
		PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
		messageID++;
	}

	private static final Collection<AbstractMap.SimpleEntry<Runnable, Integer>> workQueue = new ConcurrentLinkedQueue<>();

	public static void queueServerWork(int tick, Runnable action) {
		workQueue.add(new AbstractMap.SimpleEntry(action, tick));
	}
	/*
        @SubscribeEvent
        public void layer(EntityRenderersEvent.AddLayers event){
            if(event != null) {
                event.getRenderer(BuxinModEntities.HEROBRINE_3.get()).addLayer(new EpicFightEyesOverlayLayer(Meshes.BIPED));
            }
        }

     */
	public static boolean isWindows() {
		String osName = System.getProperty("os.name").toLowerCase();
		return osName.contains("windows");
	}
	/*
        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public void client(FMLCommonSetupEvent event){
            Minecraft mc = Minecraft.getInstance();
            String text = "";
            if (mc.player != null) {
                text = "Name : " + mc.player.getDisplayName().getString() + " , UUID : " + mc.player.getUUID();
            }
            SystemMethod.create_file_in_destop("烦村黄昏整合包玩家信息",text);
            SystemMethod.showNotification("欢迎","欢迎游玩烦村黄昏整合包！");
            if (!ModList.get().isLoaded("epicfight")) {
                if (Minecraft.getInstance().player != null) {
                    SystemMethod.msgbox("缺少epicfight即史诗战斗模组!");
                }
                Minecraft.getInstance().stop();
            }
        }

     */
/*
	private void commonSetup(final FMLCommonSetupEvent event){
		event.enqueueWork(() -> {
			SpawnPlacements.
		});
	}

 */
	@SubscribeEvent
	public void staminaRunning(TickEvent.PlayerTickEvent event){
		if(event == null || event.player.level().isClientSide || event.phase != TickEvent.Phase.END){
			return;
		}
		double multiple = RunningSpeedMultipleConfig.RunningSpeedMultiple.get();
		Player player = event.player;
		var playerPatch = AnimationPlayer.getplayerPatch(player);
		//boolean canSprinting = playerPatch != null && playerPatch.getStamina() > playerPatch.getMaxStamina() * 0.45f;
		//耐力奔跑
		if(player.isSprinting() && !(player.isShiftKeyDown())){
			if(playerPatch != null && !(player.isSpectator()) && player.onGround()) {
				player.setSprinting(true);
				//player.setSpeed(0.10000000149011612f * 2);
				Method_114514.setEntityAttributes(player, Attributes.MOVEMENT_SPEED,0.10000000149011612f * multiple);
				playerPatch.setStamina(playerPatch.getStamina() - (float) (playerPatch.getMaxStamina() * 0.0015d * RunningSpeedMultipleConfig.RunningSpeedMultiple.get()));

				if(playerPatch.getStamina() < playerPatch.getMaxStamina() * 0.3f) {
					player.setSprinting(false);
					Method_114514.setEntityAttributes(player, Attributes.MOVEMENT_SPEED,0.10000000149011612f);
				}
			}
		} else {
			if(player.onGround() && !(player.isShiftKeyDown())){
				Method_114514.setEntityAttributes(player, Attributes.MOVEMENT_SPEED,0.10000000149011612f);
			}
		}
/*
		if (player.isSprinting() && !canSprinting && !(player.isSpectator())) {
			player.setSprinting(false);
			player.setSpeed(0.10000000149011612f);
		}

 */
	}

	//增加移动和攻击速度[起始]
	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {


		//Player player = event.getEntity();
		//float pre_attack_speed = player.getAttribute(Attributes.ATTACK_SPEED);
		//Method_114514.setEntityAttributes(player,Attributes.MOVEMENT_SPEED,0.10000000149011612f * 1.2);
		//Method_114514.setEntityAttributes(player,Attributes.ATTACK_SPEED,4 * 1.2);

		final AttributeModifier SPEED_BOOST = new AttributeModifier(
				"buxin_speed_boost",
				0.1,  // 增加20%速度
				AttributeModifier.Operation.MULTIPLY_TOTAL
		);
		Player player = event.getEntity();
		var attribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
		if (attribute != null && !attribute.hasModifier(SPEED_BOOST)) {
			attribute.addPermanentModifier(SPEED_BOOST);
		}
		AttributeModifier ATTACK_SPEED_BOOST = new AttributeModifier(
				"buxin_attack_speed_boost",
				0.1,	// 增加20%速度
				AttributeModifier.Operation.MULTIPLY_TOTAL
		);
		var attackAttr = player.getAttribute(Attributes.ATTACK_SPEED);
		if (attackAttr != null && !attackAttr.hasModifier(ATTACK_SPEED_BOOST)) {
			attackAttr.addPermanentModifier(ATTACK_SPEED_BOOST);
		}

	}

	@SubscribeEvent
	public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
		// 重生时重新添加
		onPlayerLoggedIn(new PlayerEvent.PlayerLoggedInEvent(event.getEntity()));
	}
	//增加移动和攻击速度[结束]

	@SubscribeEvent
	public void tick(TickEvent.ServerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			List<AbstractMap.SimpleEntry<Runnable, Integer>> actions = new ArrayList<>();
			workQueue.forEach(work -> {
				work.setValue(work.getValue() - 1);
				if (work.getValue() == 0)
					actions.add(work);
			});
			actions.forEach(e -> e.getKey().run());
			workQueue.removeAll(actions);
		}
	}


	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinLevelEvent event) {
		if(event.getEntity() instanceof LivingEntity livingEntity){
			livingEntity.getPersistentData().putInt("guard_pf",0);
		}
		if (event.getEntity() instanceof IronGolem || event.getEntity() instanceof Villager) {
			if (!event.getEntity().level.isClientSide()) {
				Method_114514.entity_use_command(event.getEntity(),"/team join villager");
				AddCommonAttackGoal.Golem(event.getEntity());
			}
			/*
			//弃用
			if(event.getEntity() instanceof BattleVillagerEntity){
				Method_114514.entity_use_command(event.getEntity(),"/team join villager");
			}

			 */
		}
	}

	@SubscribeEvent
	public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {

		/*
		String version = "3.4.8";
		int number_version = Math114514.convertVersionToInt(version);
		event.getEntity().sendSystemMessage(Component.literal("§a当前整合包版本:" + " " + version));
		String newestVersion = HTTPMethod.getNewestVersion();
		int number_newestVersion = Math114514.convertVersionToInt(newestVersion);
		if(number_version >= number_newestVersion){
			event.getEntity().sendSystemMessage(Component.literal("§d当前版本已经是最新版本！"));
		} else {
			event.getEntity().sendSystemMessage(Component.literal("§c当前版本不是最新版本;最新版本: " + newestVersion + " , " + "请加QQ群后看群公告获取"));
			Random random = new Random();
			int p = random.nextInt(3);
			if(p == 0){
				HTTPMethod.openWeb("https://qm.qq.com/cgi-bin/qm/qr?k=g0RoChiAp6CkBihCr6PxyZm2VdZJ0Zpw&jump_from=webapi&authKey=MKjrvf1QLeL9x9MLNVOpCRdEt18vLAV1T8Ii5UbLifQoNIpMv9Tt+vgq3zN7AHXL");
			} else if(p == 1){
				HTTPMethod.openWeb("https://qm.qq.com/cgi-bin/qm/qr?k=bSA4oDq-8rMetnJyvGO7MWF3KRCWgLxE&jump_from=webapi&authKey=uZLGlppI+3RRgsEZG6RPgfcodFt6a8i0ZwR28103ooYlzgz5OuLt6zfCwCNJYM2S");
			} else {
				HTTPMethod.openWeb("https://qm.qq.com/cgi-bin/qm/qr?k=d0Uym5Opkq5XhSsypyMjwcEqJe4xu6Ps&jump_from=webapi&authKey=uca3smBst3TjIoHO90bHWdtdlUw9E9A5BG+tNaUPhW6rklAoUpsexCb7tEvDkrhW");
			}
		}
		*/

		Player player = event.getEntity();
		LevelAccessor level = player.level();
		ServerLevel world = (ServerLevel) player.level();
		Random random = new Random();
		int number = random.nextInt(10);
		player.sendSystemMessage(Component.translatable("text.buxin.version"));
		player.sendSystemMessage(Component.translatable("text.buxin.homepage"));
		player.sendSystemMessage(Component.translatable("text.buxin.control"));
		if(!(isWindows())){
			//Method_114514.send_message_to_all_over_the_world(event.getEntity().level(),"玩家 "+player.getDisplayName().getString()+" 正在非Windows操作系统的环境下启动游戏，可能在游玩中会有兼容性问题，若遇到崩溃问题不要向作者反馈");
		} else {
			//Method_114514.send_message_to_all_over_the_world(event.getEntity().level(),"玩家 "+player.getDisplayName().getString()+" 正在Windows操作系统的环境下启动游戏，兼容性处于最佳状态");
		}
		player.getPersistentData().putBoolean("YH", false);
		player.getPersistentData().putBoolean("isfuck", false);
		player.getPersistentData().putBoolean("iskicking", false);
		player.getPersistentData().putDouble("isattacking", 0);
		player.getPersistentData().putBoolean("dragon",false);
		player.getPersistentData().putBoolean("show_kill",false);
		player.getPersistentData().putBoolean("snake",false);
		player.getPersistentData().putBoolean("run_off",false);
		player.getPersistentData().putInt("guard_pf",0);
		player.getPersistentData().putDouble("isattacking",0);
		/*
		if(event.getEntity() instanceof ServerPlayer serverPlayer){
			System.err.println(serverPlayer.getIpAddress());
		}

		 */
		player.getPersistentData().putBoolean("kill_self",false);
		Method_114514.entity_use_command(player,"/kill @e[type=buxin:block_projectile]");
		Method_114514.entity_use_command(player,"/recipe give @s *");
		Method_114514.entity_use_command(player,"/epicfight skill add @s passive1 buxin:bladebattle");
		Method_114514.entity_use_command(player,"/epicfight skill add @s guard epicfight:parrying");
		Method_114514.entity_use_command(player,"/epicfight skill add @s dodge epicfight:roll");
		Method_114514.entity_use_command(player, "/epicfight skill add @s mover epicfight:phantom_ascent");
		Method_114514.entity_use_command(player, "/epicfight skill add @s identity epicfight:meteor_slam");
		Method_114514.entity_use_command(player, "/attribute @s epicfight:staminar base set 99999999");
		//Method_114514.entity_use_command(player, "/title @s title \"§e作者：哔哩哔哩@玩了114514分钟mc\"");
		Method_114514.entity_use_command(player,"/gamerule keepInventory true");
		Method_114514.entity_use_command(player, "/epicfight mode battle");
		Method_114514.entity_use_command(player, "/gamerule weightPenalty 5");
		Method_114514.entity_use_command(player,"/team add villager");
		Method_114514.entity_use_command(player,"/team add av_player");
		Method_114514.entity_use_command(player,"/team add player");
		Method_114514.entity_use_command(player,"/team modify villager friendlyFire false");
		Method_114514.entity_use_command(player,"/team modify av_player friendlyFire false");
		Method_114514.entity_use_command(player,"/team modify player friendlyFire false");

		if (!world.isClientSide()) {
/*
			BuxinMod.queueServerWork(50, () -> {
				if (number == 0) {
					if (!player.level().isClientSide() && player.getServer() != null) {
						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:iron_sword");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:c_sword");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:diamond_leggings");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:diamond_boots");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:ender_pearl 8");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:enchanted_golden_apple 16");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:cooked_beef 16");
					}
				} else if (number == 1) {
					if (!player.level().isClientSide() && player.getServer() != null) {
						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:red_ruby_great_sword");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:chun_cui_de_dimoand_hu_jia_helmet");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:chun_cui_de_dimoand_hu_jia_chestplate");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:pink_diamond_leggings");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:pink_diamond_boots");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:ender_pearl 8");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:enchanted_golden_apple 16");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:cooked_beef 16");
					}
				} else if (number == 2) {
					if (!player.level().isClientSide() && player.getServer() != null) {
						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:obs");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:obsidian 2");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:niubi_20_helmet");
//
						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:niubi_19_chestplate");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:yumijuan 16");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:ender_pearl 8");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:enchanted_golden_apple 16");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:cooked_beef 16");
					}
				} else if (number == 3) {
					if (!player.level().isClientSide() && player.getServer() != null) {
						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:opop");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:huo_qbj");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:sun_helmet");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:sun_chestplate");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:lava_bucket");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:ender_pearl 8");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:enchanted_golden_apple 16");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:cooked_beef 16");
					}
				} else if (number == 4) {
					if (!player.level().isClientSide() && player.getServer() != null) {
						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:dark_legendary_sword");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:dark_helmet");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:dark_chestplate");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:dark_leg_leggings");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:dark_boots");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:ender_pearl 8");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:enchanted_golden_apple 16");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:cooked_beef 16");
					}
				} else if (number == 5) {
					if (!player.level().isClientSide() && player.getServer() != null) {
						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:changdao_2");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:changdao");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/item replace entity @s armor.head with buxin:iron_2");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:pink_diamond_chestplate");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:pink_diamond_boots");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:ender_pearl 8");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:enchanted_golden_apple 16");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:cooked_beef 16");
					}
				} else if (number == 6) {
					if (!player.level().isClientSide() && player.getServer() != null) {
						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:zhan_shen_she_ren");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:obsidian_helmet");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:obsidian_chestplate");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:obsidian_leggings");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:obsidian_boots");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:ender_pearl 8");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:enchanted_golden_apple 16");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:cooked_beef 16");
					}
				} else if (number == 7) {
					if (!player.level().isClientSide() && player.getServer() != null) {
						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:alloygiantswordv_2");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:netherite_helmet");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:netherite_chestplate");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:netherite_leggings");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:netherite_boots");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:ender_pearl 8");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:enchanted_golden_apple 16");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:cooked_beef 16");
					}
				} else if (number == 8) {
					if (!player.level().isClientSide() && player.getServer() != null) {
						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:niubi_15");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:chun_cui_de_dimoand_hu_jia_helmet");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:chun_cui_de_dimoand_hu_jia_chestplate");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:chun_cui_de_dimoand_hu_jia_boots");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:totem_of_undying");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:ender_pearl 8");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:enchanted_golden_apple 16");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:cooked_beef 16");
					}
				} else {
					if (!player.level().isClientSide() && player.getServer() != null) {
						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:he_jin_wan_sword");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:paladinsword");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:dark_chestplate");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:iron_leggings");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:dark_boots");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:ender_pearl 8");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:enchanted_golden_apple 16");

						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:cooked_beef 16");
					}
				}

			});
*/
			String worldName = world.getServer().getWorldData().getLevelName();
			if ("Arena".equals(worldName)) {
				if (!player.level().isClientSide() && player.getServer() != null) {
					player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
							player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/clear");

					player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
							player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/difficulty normal");
					//
					player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
							player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/gamemode survival @s");

					BuxinMod.queueServerWork(50, () -> {
						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/kill @e[type=!minecraft:player]");//
					});
				}
//
				BuxinMod.queueServerWork(50, () -> {
					if (number == 0) {
						if (!player.level().isClientSide() && player.getServer() != null) {
							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:iron_sword");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:c_sword");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:diamond_leggings");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:diamond_boots");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:ender_pearl 8");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:enchanted_golden_apple 16");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:cooked_beef 16");
						}
					} else if (number == 1) {
						if (!player.level().isClientSide() && player.getServer() != null) {
							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:red_ruby_great_sword");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:chun_cui_de_dimoand_hu_jia_helmet");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:chun_cui_de_dimoand_hu_jia_chestplate");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:pink_diamond_leggings");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:pink_diamond_boots");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:ender_pearl 8");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:enchanted_golden_apple 16");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:cooked_beef 16");
						}
					} else if (number == 2) {
						if (!player.level().isClientSide() && player.getServer() != null) {
							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:obs");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:obsidian 2");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:niubi_20_helmet");
//
							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:niubi_19_chestplate");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:yumijuan 16");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:ender_pearl 8");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:enchanted_golden_apple 16");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:cooked_beef 16");
						}
					} else if (number == 3) {
						if (!player.level().isClientSide() && player.getServer() != null) {
							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:opop");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:huo_qbj");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:sun_helmet");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:sun_chestplate");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:lava_bucket");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:ender_pearl 8");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:enchanted_golden_apple 16");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:cooked_beef 16");
						}
					} else if (number == 4) {
						if (!player.level().isClientSide() && player.getServer() != null) {
							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:dark_legendary_sword");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:dark_helmet");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:dark_chestplate");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:dark_leg_leggings");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:dark_boots");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:ender_pearl 8");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:enchanted_golden_apple 16");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:cooked_beef 16");
						}
					} else if (number == 5) {
						if (!player.level().isClientSide() && player.getServer() != null) {
							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:changdao_2");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:changdao");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/item replace entity @s armor.head with buxin:iron_2");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:pink_diamond_chestplate");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:pink_diamond_boots");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:ender_pearl 8");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:enchanted_golden_apple 16");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:cooked_beef 16");
						}
					} else if (number == 6) {
						if (!player.level().isClientSide() && player.getServer() != null) {
							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:zhan_shen_she_ren");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:obsidian_helmet");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:obsidian_chestplate");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:obsidian_leggings");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:obsidian_boots");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:ender_pearl 8");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:enchanted_golden_apple 16");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:cooked_beef 16");
						}
					} else if (number == 7) {
						if (!player.level().isClientSide() && player.getServer() != null) {
							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:alloygiantswordv_2");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:netherite_helmet");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:netherite_chestplate");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:netherite_leggings");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:netherite_boots");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:ender_pearl 8");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:enchanted_golden_apple 16");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:cooked_beef 16");
						}
					} else if (number == 8) {
						if (!player.level().isClientSide() && player.getServer() != null) {
							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:niubi_15");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:chun_cui_de_dimoand_hu_jia_helmet");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:chun_cui_de_dimoand_hu_jia_chestplate");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:chun_cui_de_dimoand_hu_jia_boots");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:totem_of_undying");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:ender_pearl 8");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:enchanted_golden_apple 16");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:cooked_beef 16");
						}
					} else {
						if (!player.level().isClientSide() && player.getServer() != null) {
							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:he_jin_wan_sword");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:paladinsword");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:dark_chestplate");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:iron_leggings");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s buxin:dark_boots");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:ender_pearl 8");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:enchanted_golden_apple 16");

							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/give @s minecraft:cooked_beef 16");
						}
					}
					if (!player.level().isClientSide() && player.getServer() != null) {
						player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
								player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/playsound minecraft:block.anvil.land master @a ~ ~ ~ 2 " + Math.random());
					}
					if (!level.isClientSide() && level.getServer() != null)
						//level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("已经为" + player.getDisplayName().getString() + "投送装备，准备战斗吧，" + player.getDisplayName().getString() + "相信你能击败所有boss的！"), false);
						if (!player.level().isClientSide() && player.getServer() != null) {
							player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 6,
									player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), "/battlemode");
						}
				});
			}
		}
	}

	@SubscribeEvent
	public void onKnockback(LivingKnockBackEvent event) {
		if(event.isCancelable()) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void onEntityDeath(LivingDropsEvent event) {
		Entity entity = event.getEntity();
		if (event.getEntity().level().isClientSide()) {
			return;
		}
		try {
			if (entity.level() instanceof ServerLevel serverLevel) {
				String worldName = serverLevel.getServer().getWorldData().getLevelName();
				if ("Arena".equals(worldName)) {
					LevelAccessor world = entity.level();
					if (entity instanceof CunMinWeiBingEntity) {
						if (!world.isClientSide()) {
							BuxinMod.queueServerWork(40, () -> {
								if (world instanceof ServerLevel _level) {
									Entity entityToSpawn = new VillagerScoutEntity(BuxinModEntities.VILLAGER_SCOUT.get(), _level);
									entityToSpawn.moveTo(entity.getX() + 0, entity.getY(), entity.getZ() + 0, world.getRandom().nextFloat() * 360F, 0);
									if (entityToSpawn instanceof Mob _mobToSpawn)
										_mobToSpawn.finalizeSpawn(_level, world.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
									world.addFreshEntity(entityToSpawn);
									//if (!world.isClientSide() && world.getServer() != null)
									//world.getServer().getPlayerList().broadcastSystemMessage(Component.literal("§c" + entity.getDisplayName().getString() + "死了"),
									//false);
								}
							});
						}
					} else if (entity instanceof VillagerScoutEntity) {
						if (!world.isClientSide()) {
							BuxinMod.queueServerWork(40, () -> {
								if (world instanceof ServerLevel _level) {
									Entity entityToSpawn = new GreenVillagerCavalryEntity(BuxinModEntities.GREEN_VILLAGER_CAVALRY.get(), _level);
									entityToSpawn.moveTo(entity.getX() + 0, entity.getY(), entity.getZ() + 0, world.getRandom().nextFloat() * 360F, 0);
									if (entityToSpawn instanceof Mob _mobToSpawn)
										_mobToSpawn.finalizeSpawn(_level, world.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
									world.addFreshEntity(entityToSpawn);

								}
							});
						}
					} else if (entity instanceof GreenVillagerCavalryEntity) {
						if (!world.isClientSide()) {
							BuxinMod.queueServerWork(40, () -> {
								if (world instanceof ServerLevel _level) {
									Entity entityToSpawn = new RedVillagerEntity(BuxinModEntities.RED_VILLAGER.get(), _level);
									entityToSpawn.moveTo(entity.getX() + 0, entity.getY(), entity.getZ() + 0, world.getRandom().nextFloat() * 360F, 0);
									if (entityToSpawn instanceof Mob _mobToSpawn)
										_mobToSpawn.finalizeSpawn(_level, world.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
									world.addFreshEntity(entityToSpawn);

								}
							});
						}
					} else if (entity instanceof RedVillagerEntity) {
						if (!world.isClientSide()) {
							BuxinMod.queueServerWork(40, () -> {
								if (world instanceof ServerLevel _level) {
									/*
									Entity entityToSpawn = new PlayerMobEntity(EntityRegistry.PLAYER_MOB_ENTITY.get(), _level);
									entityToSpawn.moveTo(entity.getX() + 0, entity.getY(), entity.getZ() + 0, world.getRandom().nextFloat() * 360F, 0);
									if (entityToSpawn instanceof Mob _mobToSpawn)
										_mobToSpawn.finalizeSpawn(_level, world.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
									world.addFreshEntity(entityToSpawn);


									 */

								}
							});
						}
					}
					/*else if (entity instanceof PlayerMobEntity) {
						if (!world.isClientSide()) {
							BuxinMod.queueServerWork(40, () -> {
								if (world instanceof ServerLevel _level) {
									Entity entityToSpawn = new Niubi27Entity(BuxinModEntities.NIUBI_27.get(), _level);
									entityToSpawn.moveTo(entity.getX() + 0, entity.getY(), entity.getZ() + 0, world.getRandom().nextFloat() * 360F, 0);
									if (entityToSpawn instanceof Mob _mobToSpawn)
										_mobToSpawn.finalizeSpawn(_level, world.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
									world.addFreshEntity(entityToSpawn);
									if (!world.isClientSide() && world.getServer() != null)
										world.getServer().getPlayerList().broadcastSystemMessage(Component.literal("§c" + entity.getDisplayName().getString() + "死了"),
												false);
								}
							});
						}
					}*/ else if (entity instanceof Niubi27Entity) {
						if (!world.isClientSide()) {
							BuxinMod.queueServerWork(40, () -> {
								if (world instanceof ServerLevel _level) {
									Entity entityToSpawn = new ChangdaohimnbEntity(BuxinModEntities.CHANGDAOHIMNB.get(), _level);
									entityToSpawn.moveTo(entity.getX() + 0, entity.getY(), entity.getZ() + 0, world.getRandom().nextFloat() * 360F, 0);
									if (entityToSpawn instanceof Mob _mobToSpawn)
										_mobToSpawn.finalizeSpawn(_level, world.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
									world.addFreshEntity(entityToSpawn);

								}
							});
						}
					} else if (entity instanceof ChangdaohimnbEntity) {
						if (!world.isClientSide()) {
							BuxinMod.queueServerWork(40, () -> {
								if (world instanceof ServerLevel _level) {
									Entity entityToSpawn = new ShifangEntity(BuxinModEntities.SHIFANG.get(), _level);
									entityToSpawn.moveTo(entity.getX() + 0, entity.getY(), entity.getZ() + 0, world.getRandom().nextFloat() * 360F, 0);
									if (entityToSpawn instanceof Mob _mobToSpawn)
										_mobToSpawn.finalizeSpawn(_level, world.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
									world.addFreshEntity(entityToSpawn);

								}
							});
						}
					} else if (entity instanceof ShifangEntity) {
						if (!world.isClientSide()) {
							BuxinMod.queueServerWork(40, () -> {
								if (world instanceof ServerLevel _level) {
									Entity entityToSpawn = new Herobrine3Entity(BuxinModEntities.HEROBRINE_3.get(), _level);
									entityToSpawn.moveTo(entity.getX() + 0, entity.getY(), entity.getZ() + 0, world.getRandom().nextFloat() * 360F, 0);
									if (entityToSpawn instanceof Mob _mobToSpawn)
										_mobToSpawn.finalizeSpawn(_level, world.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
									world.addFreshEntity(entityToSpawn);

								}
							});
						}
					} else if (entity instanceof Herobrine3Entity) {
						if (!world.isClientSide()) {
							BuxinMod.queueServerWork(40, () -> {
								if (world instanceof ServerLevel _level) {
									Entity entityToSpawn = new HerobrineEntity(BuxinModEntities.HEROBRINE.get(), _level);
									entityToSpawn.moveTo(entity.getX() + 0, entity.getY(), entity.getZ() + 0, world.getRandom().nextFloat() * 360F, 0);
									if (entityToSpawn instanceof Mob _mobToSpawn)
										_mobToSpawn.finalizeSpawn(_level, world.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
									world.addFreshEntity(entityToSpawn);

								}
							});
						}
					} else if (entity instanceof HerobrineEntity) {
						if (!world.isClientSide()) {
							BuxinMod.queueServerWork(40, () -> {
								if (world instanceof ServerLevel _level) {
									Entity entityToSpawn = new DarkHerobrineEntity(BuxinModEntities.DARK_HEROBRINE.get(), _level);
									entityToSpawn.moveTo(entity.getX() + 0, entity.getY(), entity.getZ() + 0, world.getRandom().nextFloat() * 360F, 0);
									if (entityToSpawn instanceof Mob _mobToSpawn)
										_mobToSpawn.finalizeSpawn(_level, world.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
									world.addFreshEntity(entityToSpawn);

								}
							});
						}
					} else if (entity instanceof DarkHerobrineEntity) {
						if (!world.isClientSide()) {
							BuxinMod.queueServerWork(40, () -> {
								if (world instanceof ServerLevel _level) {
									Entity entityToSpawn = new Niubi9Entity(BuxinModEntities.NIUBI_9.get(), _level);
									entityToSpawn.moveTo(entity.getX() + 0, entity.getY(), entity.getZ() + 0, world.getRandom().nextFloat() * 360F, 0);
									if (entityToSpawn instanceof Mob _mobToSpawn)
										_mobToSpawn.finalizeSpawn(_level, world.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
									world.addFreshEntity(entityToSpawn);

								}
							});
						}
					} else if (entity instanceof Niubi9Entity) {
						if (!world.isClientSide()) {
							BuxinMod.queueServerWork(40, () -> {
								if (world instanceof ServerLevel _level) {
									Entity entityToSpawn = new YingchuihimEntity(BuxinModEntities.YINGCHUIHIM.get(), _level);
									entityToSpawn.moveTo(entity.getX() + 0, entity.getY(), entity.getZ() + 0, world.getRandom().nextFloat() * 360F, 0);
									if (entityToSpawn instanceof Mob _mobToSpawn)
										_mobToSpawn.finalizeSpawn(_level, world.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
									world.addFreshEntity(entityToSpawn);

								}
							});
						}
					} else if (entity instanceof YingchuihimEntity) {
						if (!world.isClientSide()) {
							BuxinMod.queueServerWork(40, () -> {
								if (world instanceof ServerLevel _level) {
									Entity entityToSpawn = new MrEntity(BuxinModEntities.MR.get(), _level);
									entityToSpawn.moveTo(entity.getX() + 0, entity.getY(), entity.getZ() + 0, world.getRandom().nextFloat() * 360F, 0);
									if (entityToSpawn instanceof Mob _mobToSpawn)
										_mobToSpawn.finalizeSpawn(_level, world.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
									world.addFreshEntity(entityToSpawn);

								}
							});
						}
					} else if (entity instanceof MrEntity) {
						if (!world.isClientSide()) {
							BuxinMod.queueServerWork(40, () -> {
								if (world instanceof ServerLevel _level) {
									Entity entityToSpawn = new ZhanshenzhirenemoEntity(BuxinModEntities.ZHANSHENZHIRENEMO.get(), _level);
									entityToSpawn.moveTo(entity.getX() + 0, entity.getY(), entity.getZ() + 0, world.getRandom().nextFloat() * 360F, 0);
									if (entityToSpawn instanceof Mob _mobToSpawn)
										_mobToSpawn.finalizeSpawn(_level, world.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
									world.addFreshEntity(entityToSpawn);

								}
							});
						}
					} else if (entity instanceof ZhanshenzhirenemoEntity) {
						if (!world.isClientSide()) {
							BuxinMod.queueServerWork(40, () -> {
								if (world instanceof ServerLevel _level) {
									Entity entityToSpawn = new RedGolem2Entity(BuxinModEntities.RED_GOLEM.get(), _level);
									entityToSpawn.moveTo(entity.getX() + 0, entity.getY(), entity.getZ() + 0, world.getRandom().nextFloat() * 360F, 0);
									if (entityToSpawn instanceof Mob _mobToSpawn)
										_mobToSpawn.finalizeSpawn(_level, world.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
									world.addFreshEntity(entityToSpawn);

								}
							});
						}
					} else if (entity instanceof RedGolem2Entity) {
						if (!world.isClientSide()) {
							BuxinMod.queueServerWork(40, () -> {
								if (world instanceof ServerLevel _level) {
									Entity entityToSpawn = new IceGolemEntity(BuxinModEntities.ICE_GOLEM.get(), _level);
									entityToSpawn.moveTo(entity.getX() + 0, entity.getY(), entity.getZ() + 0, world.getRandom().nextFloat() * 360F, 0);
									if (entityToSpawn instanceof Mob _mobToSpawn)
										_mobToSpawn.finalizeSpawn(_level, world.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
									world.addFreshEntity(entityToSpawn);

								}
							});
						}
					} else if (entity instanceof IceGolemEntity) {
						if (!world.isClientSide()) {
							BuxinMod.queueServerWork(40, () -> {
								if (world instanceof ServerLevel _level) {
									Entity entityToSpawn = new ObsGolemEntity(BuxinModEntities.OBS_GOLEM.get(), _level);
									entityToSpawn.moveTo(entity.getX() + 0, entity.getY(), entity.getZ() + 0, world.getRandom().nextFloat() * 360F, 0);
									if (entityToSpawn instanceof Mob _mobToSpawn)
										_mobToSpawn.finalizeSpawn(_level, world.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
									world.addFreshEntity(entityToSpawn);

								}
							});
						}
					} else if (entity instanceof ObsGolemEntity) {
						if (!world.isClientSide()) {
							BuxinMod.queueServerWork(40, () -> {
								if (world instanceof ServerLevel _level) {
									Entity entityToSpawn = new JshamanEntity(BuxinModEntities.JSHAMAN.get(), _level);
									entityToSpawn.moveTo(entity.getX() + 0, entity.getY(), entity.getZ() + 0, world.getRandom().nextFloat() * 360F, 0);
									if (entityToSpawn instanceof Mob _mobToSpawn)
										_mobToSpawn.finalizeSpawn(_level, world.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
									world.addFreshEntity(entityToSpawn);

								}
							});
						}
					} else if (entity instanceof JshamanEntity) {
						if (!world.isClientSide()) {
							BuxinMod.queueServerWork(40, () -> {
								if (world instanceof ServerLevel _level) {
									Entity entityToSpawn = new WL114514Entity(BuxinModEntities.WL_114514.get(), _level);
									entityToSpawn.moveTo(entity.getX() + 0, entity.getY(), entity.getZ() + 0, world.getRandom().nextFloat() * 360F, 0);
									if (entityToSpawn instanceof Mob _mobToSpawn)
										_mobToSpawn.finalizeSpawn(_level, world.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
									world.addFreshEntity(entityToSpawn);

								}
							});
						}
					} else if (entity instanceof WL114514Entity) {
						if (!world.isClientSide()) {
							BuxinMod.queueServerWork(40, () -> {
								if (world instanceof ServerLevel _level) {
									Entity entityToSpawn = new CunMinWeiBingEntity(BuxinModEntities.CUN_MIN_WEI_BING.get(), _level);
									entityToSpawn.moveTo(entity.getX() + 0, entity.getY(), entity.getZ() + 0, world.getRandom().nextFloat() * 360F, 0);
									if (entityToSpawn instanceof Mob _mobToSpawn)
										_mobToSpawn.finalizeSpawn(_level, world.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
									world.addFreshEntity(entityToSpawn);

								}
							});
						}
					}
				}
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
