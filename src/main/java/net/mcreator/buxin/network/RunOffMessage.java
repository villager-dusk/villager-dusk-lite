
package net.mcreator.buxin.network;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.init.BuxinModSounds;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.gameasset.Animations;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RunOffMessage {
	int type, pressedms;

	public RunOffMessage(int type, int pressedms) {
		this.type = type;
		this.pressedms = pressedms;
	}

	public RunOffMessage(FriendlyByteBuf buffer) {
		this.type = buffer.readInt();
		this.pressedms = buffer.readInt();
	}

	public static void buffer(RunOffMessage message, FriendlyByteBuf buffer) {
		buffer.writeInt(message.type);
		buffer.writeInt(message.pressedms);
	}

	public static void handler(RunOffMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
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
			if (!(AnimationPlayer.entity_getAnimation(entity) instanceof AttackAnimation) && !entity.getPersistentData().getBoolean("run_off")) {
				entity.getPersistentData().putBoolean("run_off",true);
				BuxinMod.queueServerWork(15,() -> entity.getPersistentData().putBoolean("run_off",false));
				entity.playSound(BuxinModSounds.KILL.get(),1,1);
				if(Math.random() > 0.5){
					AnimationPlayer.playAnimation(entity, Animations.BIPED_KNOCKDOWN_WAKEUP_LEFT);
				} else {
					AnimationPlayer.playAnimation(entity,Animations.BIPED_KNOCKDOWN_WAKEUP_RIGHT);
				}
			}
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		BuxinMod.addNetworkMessage(RunOffMessage.class, RunOffMessage::buffer, RunOffMessage::new, RunOffMessage::handler);
	}
}
