
package net.mcreator.buxin.network;

import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.procedures.ThrowEnderPeralSongKaiAnJianShiProcedure;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ThrowEnderPeralMessage {
	int type, pressedms;

	public ThrowEnderPeralMessage(int type, int pressedms) {
		this.type = type;
		this.pressedms = pressedms;
	}

	public ThrowEnderPeralMessage(FriendlyByteBuf buffer) {
		this.type = buffer.readInt();
		this.pressedms = buffer.readInt();
	}

	public static void buffer(ThrowEnderPeralMessage message, FriendlyByteBuf buffer) {
		buffer.writeInt(message.type);
		buffer.writeInt(message.pressedms);
	}

	public static void handler(ThrowEnderPeralMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			pressAction(context.getSender(), message.type, message.pressedms);
		});
		context.setPacketHandled(true);
	}

	public static void pressAction(Player entity, int type, int pressedms) {
		Level world = entity.level;
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(entity.blockPosition()))
			return;
		if (type == 1) {

			ThrowEnderPeralSongKaiAnJianShiProcedure.execute(world, entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		BuxinMod.addNetworkMessage(ThrowEnderPeralMessage.class, ThrowEnderPeralMessage::buffer, ThrowEnderPeralMessage::new, ThrowEnderPeralMessage::handler);
	}
}
