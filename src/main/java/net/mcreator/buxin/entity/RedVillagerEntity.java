
package net.mcreator.buxin.entity;

import net.mcreator.buxin.entity.father.BattleVillagerEntity;
import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.init.BuxinModItems;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;

public class RedVillagerEntity extends BattleVillagerEntity {
	public RedVillagerEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(BuxinModEntities.RED_VILLAGER.get(), world);
	}

	public RedVillagerEntity(EntityType<RedVillagerEntity> type, Level world) {
		super(type, world);
		maxUpStep = 0.6f;
		xpReward = 0;
		setNoAi(false);
		setPersistenceRequired();
		this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(BuxinModItems.ZUAN_SHI_DAO_PIAN.get()));
		this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(BuxinModItems.RED_VILLAGER_HEAD_HELMET.get()));
		this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(BuxinModItems.RED_VILLAGER_ARMOR_CHESTPLATE.get()));
		this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(BuxinModItems.CUN_MIN_KU_TUI_LEGGINGS.get()));
		this.setItemSlot(EquipmentSlot.FEET, new ItemStack(BuxinModItems.RED_VILLAGER_ARMOR_BOOTS.get()));
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	public static void init() {
		SpawnPlacements.register(BuxinModEntities.RED_VILLAGER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				(entityType, world, reason, pos, random) -> (world.getBlockState(pos.below()).is(net.minecraft.world.level.block.Blocks.GRASS_BLOCK) && world.getRawBrightness(pos, 0) > 4));
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 20);
		builder = builder.add(Attributes.ARMOR, 2);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 0);
		builder = builder.add(Attributes.FOLLOW_RANGE, 40);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 988);
		return builder;
	}
}
