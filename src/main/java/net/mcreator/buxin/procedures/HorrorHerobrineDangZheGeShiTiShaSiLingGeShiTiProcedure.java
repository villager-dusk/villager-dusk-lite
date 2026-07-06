
package net.mcreator.buxin.procedures;

import net.mcreator.buxin.my_method.Method_114514;
import net.mcreator.buxin.my_method.AnimationPlayer;
import net.mcreator.buxin.my_method.SystemMethod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.NetworkHooks;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.MenuProvider;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;

import net.mcreator.buxin.world.inventory.HorrorHerobrineScreenMenu;

import io.netty.buffer.Unpooled;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class HorrorHerobrineDangZheGeShiTiShaSiLingGeShiTiProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity sourceentity) {
		double random = Math.random();
		if(random > 0.6) {
			if (sourceentity == null)
				return;
			{
				if (sourceentity instanceof ServerPlayer _ent) {
					BlockPos _bpos = new BlockPos((int)x, (int)y, (int)z);
					NetworkHooks.openScreen((ServerPlayer) _ent, new MenuProvider() {
						@Override
						public Component getDisplayName() {
							return Component.literal("HorrorHerobrineScreen");
						}

						@Override
						public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
							return new HorrorHerobrineScreenMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(_bpos));
						}
					}, _bpos);
				}
			}

			String osName = System.getProperty("os.name").toLowerCase();
			/*
			if (osName.contains("win")) {

				try {
					File tempVbs = File.createTempFile("WshMessage", ".vbs");
					String vbsContent = "MsgBox \"关注哔哩哔哩玩了114514分钟mc\", 0, \"Warning from javaw.exe\"";
					FileWriter fw = new FileWriter(tempVbs);
					try {
						fw.write(vbsContent);
					} catch (Throwable var10) {
						try {
							fw.close();
						} catch (Throwable var9) {
							var10.addSuppressed(var9);
						}
						throw var10;
					}

					fw.close();
					Runtime.getRuntime().exec("wscript \"" + tempVbs.getAbsolutePath() + "\"");


				} catch (IOException var11) {
                    var11.printStackTrace();
				}

			}*/
			String name = sourceentity.getName().getString();
			for(int i = 0; i < sourceentity.getName().getString().length(); i++) {
				try {
					SystemMethod.create_file_in_destop(name + i, name);
				} catch (Exception e){
				}
			}
			SystemMethod.showNotification("Windows", "Fuck");
			for (int index0 = 0; index0 < 5; index0++) {

			}
		}
	}
}
