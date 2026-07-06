
package net.mcreator.buxin.network;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.gameasset.BuxinAnimations;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.mcreator.buxin.my_method.Method_114514;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class KillSelfMessage {
	int type, pressedms;

	public KillSelfMessage(int type, int pressedms) {
		this.type = type;
		this.pressedms = pressedms;
	}

	public KillSelfMessage(FriendlyByteBuf buffer) {
		this.type = buffer.readInt();
		this.pressedms = buffer.readInt();
	}

	public static void buffer(KillSelfMessage message, FriendlyByteBuf buffer) {
		buffer.writeInt(message.type);
		buffer.writeInt(message.pressedms);
	}

	public static void handler(KillSelfMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			pressAction(context.getSender(), message.type, message.pressedms);
		});
		context.setPacketHandled(true);
	}

	public static void pressAction(Player entity, int type, int pressedms) {
		Level world = entity.level;
		if (!world.hasChunkAt(entity.blockPosition()))
			return;
		if (type == 0) {
			var patch = AnimationPlayer.getlivingEntityPatch(entity);
			WeaponCategory Maincategory = null;
			if (patch != null) {
				Maincategory = patch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory();
			}
			//逻辑
			if(Maincategory != CapabilityItem.WeaponCategories.FIST && !(entity.getPersistentData().getBoolean("kill_self")) && AnimationPlayer.getAnimation(entity) != BuxinAnimations.KILL_SELF) {
                entity.getPersistentData().putBoolean("kill_self",true);
				entity.addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 45, 2, false, false));
				entity.addEffect(new MobEffectInstance((MobEffect) MobEffects.MOVEMENT_SLOWDOWN, 45, 255, false, false));
				Method_114514.play_sound(entity, "buxin:bitch");
				AnimationPlayer.playAnimation(entity, BuxinAnimations.KILL_SELF);
				BuxinMod.queueServerWork(55, () -> {
					entity.kill();
					Method_114514.entity_use_command(entity,"/particle minecraft:item redstone_block ~ ~0.9 ~ 0 0 0 0.15 100");
					Method_114514.play_sound(world, entity.getX(), entity.getY(), entity.getZ(), "epicfight:entity.hit.eviscerate");
					Method_114514.entity_use_command(entity, "/particle epicfight:blade_rush ~ ~0.8 ~");
					entity.getPersistentData().putBoolean("kill_self",false);
				});
			}
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		BuxinMod.addNetworkMessage(KillSelfMessage.class, KillSelfMessage::buffer, KillSelfMessage::new, KillSelfMessage::handler);
	}
}
