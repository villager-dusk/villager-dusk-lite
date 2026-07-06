
package net.mcreator.buxin.network;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.procedures.KickAndShieldBash;
import net.mcreator.buxin.special_key.SpecialKeyPressEvent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class SpecialSkillMessage {
	int type, pressedms;

	public SpecialSkillMessage(int type, int pressedms) {
		this.type = type;
		this.pressedms = pressedms;
	}

	public SpecialSkillMessage(FriendlyByteBuf buffer) {
		this.type = buffer.readInt();
		this.pressedms = buffer.readInt();
	}

	public static void buffer(SpecialSkillMessage message, FriendlyByteBuf buffer) {
		buffer.writeInt(message.type);
		buffer.writeInt(message.pressedms);
	}

	public static void handler(SpecialSkillMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
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
			if(Math.random() > 0.5) {
				KickAndShieldBash.execute(entity.level, entity);
			} else {
				SpecialKeyPressEvent.event(entity);
			}
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		BuxinMod.addNetworkMessage(SpecialSkillMessage.class, SpecialSkillMessage::buffer, SpecialSkillMessage::new, SpecialSkillMessage::handler);
	}
}
