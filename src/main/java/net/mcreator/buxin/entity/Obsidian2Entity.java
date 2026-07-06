
package net.mcreator.buxin.entity;

import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.procedures.Obsidian2TouZhiWuFeiXingKeProcedure;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class Obsidian2Entity extends AbstractArrow implements ItemSupplier {
	public Obsidian2Entity(PlayMessages.SpawnEntity packet, Level world) {
		super(BuxinModEntities.OBSIDIAN_2.get(), world);
	}

	public Obsidian2Entity(EntityType<? extends Obsidian2Entity> type, Level world) {
		super(type, world);
	}

	public Obsidian2Entity(EntityType<? extends Obsidian2Entity> type, double x, double y, double z, Level world) {
		super(type, x, y, z, world);
	}

	public Obsidian2Entity(EntityType<? extends Obsidian2Entity> type, LivingEntity entity, Level world) {
		super(type, entity, world);
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public ItemStack getItem() {
		return new ItemStack(BuxinModItems.AIR.get());
	}

	@Override
	protected ItemStack getPickupItem() {
		return ItemStack.EMPTY;
	}

	@Override
	protected void doPostHurtEffects(LivingEntity entity) {
		super.doPostHurtEffects(entity);
		entity.setArrowCount(entity.getArrowCount() - 1);
	}

	@Override
	public void tick() {
		super.tick();
		Obsidian2TouZhiWuFeiXingKeProcedure.execute(this,this.level, this.getX(), this.getY(), this.getZ());
		if (this.inGround)
			this.discard();
	}

	public static Obsidian2Entity shoot(Level world, LivingEntity entity, RandomSource random, float power, double damage, int knockback) {
		Obsidian2Entity entityarrow = new Obsidian2Entity(BuxinModEntities.OBSIDIAN_2.get(), entity, world);
		entityarrow.shoot(entity.getViewVector(1).x, entity.getViewVector(1).y, entity.getViewVector(1).z, power * 2, 0);
		entityarrow.setSilent(true);
		entityarrow.setCritArrow(false);
		entityarrow.setBaseDamage(damage);
		entityarrow.setKnockback(knockback);
		world.addFreshEntity(entityarrow);
		world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:dark_obsidian_use")), SoundSource.PLAYERS, 1, 1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
		return entityarrow;
	}

	public static Obsidian2Entity shoot(LivingEntity entity, LivingEntity target) {
		Obsidian2Entity entityarrow = new Obsidian2Entity(BuxinModEntities.OBSIDIAN_2.get(), entity, entity.level);
		double dx = target.getX() - entity.getX();
		double dy = target.getY() + target.getEyeHeight() - 1.1;
		double dz = target.getZ() - entity.getZ();
		entityarrow.shoot(dx, dy - entityarrow.getY() + Math.hypot(dx, dz) * 0.2F, dz, 0.6f * 2, 12.0F);
		entityarrow.setSilent(true);
		entityarrow.setBaseDamage(10);
		entityarrow.setKnockback(15);
		entityarrow.setCritArrow(false);
		entity.level.addFreshEntity(entityarrow);
		entity.level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("buxin:dark_obsidian_use")), SoundSource.PLAYERS, 1, 1f / (RandomSource.create().nextFloat() * 0.5f + 1));
		return entityarrow;
	}
}
