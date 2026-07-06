package net.mcreator.buxin.entity;

import net.mcreator.buxin.common_attackgoals.AddCommonAttackGoal;
import net.mcreator.buxin.init.BuxinModEntities;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.my_method.Method_114514;
import net.mcreator.buxin.procedures.IsBuxinEntityProcedure;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.world.item.EpicFightItems;

import javax.annotation.Nullable;

public class HerobrineDsEntity extends Monster {
	private boolean summoned = false;
	public HerobrineDsEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(BuxinModEntities.HEROBRINE_DS.get(), world);
	}

	public HerobrineDsEntity(EntityType<HerobrineDsEntity> type, Level world) {
		super(type, world);
		maxUpStep = 0.6f;
		xpReward = 0;
		setNoAi(false);
		this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(BuxinModItems.HEROBRINE_DIAMOND_SWORD.get()));
	}
	public void setSummoned(boolean summoned) {
		this.summoned = summoned;
	}

	@Override
	public void readAdditionalSaveData(CompoundTag pCompound) {
		super.readAdditionalSaveData(pCompound);
		summoned = pCompound.getBoolean("Summoned");
	}
	@Override
	public void addAdditionalSaveData(CompoundTag pCompound) {
		super.addAdditionalSaveData(pCompound);
		pCompound.putBoolean("Summoned", summoned);
	}
	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, false, false));
		this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.4, false) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth();
			}
		});
		AddCommonAttackGoal.Herobrine(this);
		this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1));
		this.targetSelector.addGoal(7, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(9, new FloatGoal(this));
		this.goalSelector.addGoal(10, new RandomStrollGoal(this, 1));
		this.goalSelector.addGoal(11, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(12, new FloatGoal(this));
		this.goalSelector.addGoal(13, new LeapAtTargetGoal(this, (float) 0.5));
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEFINED;
	}

	@Override
	public double getMyRidingOffset() {
		return -0.35D;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {

		if (source.getMsgId().equals("witherSkull"))
			return false;
		return super.hurt(source, amount);
	}
	@Override
	public void baseTick() {
		super.baseTick();
	}
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata, tag);
		IsBuxinEntityProcedure.execute(this);
		Method_114514.herobrine_born(this);
		Entity entity = this;
		if (Math.random() > 0.1) {
			if (entity instanceof LivingEntity _entity) {
				ItemStack _setstack = new ItemStack(BuxinModItems.RUBY_SWORD.get());
				_setstack.setCount(1);
				_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
				if (_entity instanceof Player _player)
					_player.getInventory().setChanged();
			}
			{
				Entity _entity = entity;
				if (_entity instanceof Player _player) {
					_player.getInventory().armor.set(2, new ItemStack(Items.DIAMOND_CHESTPLATE));
					_player.getInventory().setChanged();
				} else if (_entity instanceof LivingEntity _living) {
					_living.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.DIAMOND_CHESTPLATE));
				}
			}
			{
				if (entity instanceof LivingEntity _entity) {
					ItemStack _setstack = new ItemStack(EpicFightItems.DIAMOND_SPEAR.get());
					_setstack.setCount(1);
					_entity.setItemInHand(InteractionHand.OFF_HAND, _setstack);
					if (_entity instanceof Player _player)
						_player.getInventory().setChanged();
				}
			}
			if (Math.random() > 0.15) {
				if (entity instanceof LivingEntity _entity) {
					ItemStack _setstack = new ItemStack(BuxinModItems.ZUAN_JIAN_2.get());
					_setstack.setCount(1);
					_entity.setItemInHand(InteractionHand.OFF_HAND, _setstack);
					if (_entity instanceof Player _player)
						_player.getInventory().setChanged();
				}
				if (entity instanceof LivingEntity _entity) {
					ItemStack _setstack = new ItemStack(BuxinModItems.PALADINSWORD.get());
					_setstack.setCount(1);
					_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
					if (_entity instanceof Player _player)
						_player.getInventory().setChanged();
				}
				{
					Entity _entity = entity;
					if (_entity instanceof Player _player) {
						_player.getInventory().armor.set(2, new ItemStack(BuxinModItems.PINK_DIAMOND_CHESTPLATE.get()));
						_player.getInventory().setChanged();
					} else if (_entity instanceof LivingEntity _living) {
						_living.setItemSlot(EquipmentSlot.CHEST, new ItemStack(BuxinModItems.PINK_DIAMOND_CHESTPLATE.get()));
					}
				}
				{
				}
			}
			if (Math.random() > 0.2) {
				if (entity instanceof LivingEntity _entity) {
					ItemStack _setstack = new ItemStack(BuxinModItems.NIUBI_7.get());
					_setstack.setCount(1);
					_entity.setItemInHand(InteractionHand.OFF_HAND, _setstack);
					if (_entity instanceof Player _player)
						_player.getInventory().setChanged();
				}
				if (entity instanceof LivingEntity _entity) {
					ItemStack _setstack = new ItemStack(BuxinModItems.DIAMOND_SICKLE.get());
					_setstack.setCount(1);
					_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
					if (_entity instanceof Player _player)
						_player.getInventory().setChanged();
				}
				{
					Entity _entity = entity;
					if (_entity instanceof Player _player) {
						_player.getInventory().armor.set(2, new ItemStack(BuxinModItems.BLUE_DIAMOND_ARMOR_CHESTPLATE.get()));
						_player.getInventory().setChanged();
					} else if (_entity instanceof LivingEntity _living) {
						_living.setItemSlot(EquipmentSlot.CHEST, new ItemStack(BuxinModItems.BLUE_DIAMOND_ARMOR_CHESTPLATE.get()));
					}
				}
				if (Math.random() > 0.25) {
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack = new ItemStack(BuxinModItems.IRON_SICKLE.get());
						_setstack.setCount(1);
						_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
						if (_entity instanceof Player _player)
							_player.getInventory().setChanged();
					}
					{
						Entity _entity = entity;
						if (_entity instanceof Player _player) {
							_player.getInventory().armor.set(2, new ItemStack(Items.IRON_CHESTPLATE));
							_player.getInventory().setChanged();
						} else if (_entity instanceof LivingEntity _living) {
							_living.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
						}
					}
				}
				if (Math.random() > 0.3) {
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack = new ItemStack(EpicFightItems.DIAMOND_SPEAR.get());
						_setstack.setCount(1);
						_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
						if (_entity instanceof Player _player)
							_player.getInventory().setChanged();
					}
					{
						Entity _entity = entity;
						if (_entity instanceof Player _player) {
							_player.getInventory().armor.set(2, new ItemStack(BuxinModItems.SUN_CHESTPLATE.get()));
							_player.getInventory().setChanged();
						} else if (_entity instanceof LivingEntity _living) {
							_living.setItemSlot(EquipmentSlot.CHEST, new ItemStack(BuxinModItems.SUN_CHESTPLATE.get()));
						}
					}
					if (Math.random() > 0.35) {
						if (entity instanceof LivingEntity _entity) {
							ItemStack _setstack = new ItemStack(BuxinModItems.RUBY_SWORD.get());
							_setstack.setCount(1);
							_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
							if (_entity instanceof Player _player)
								_player.getInventory().setChanged();
						}
						{
							Entity _entity = entity;
							if (_entity instanceof Player _player) {
								_player.getInventory().armor.set(2, new ItemStack(BuxinModItems.PINK_DIAMOND_CHESTPLATE.get()));
								_player.getInventory().setChanged();
							} else if (_entity instanceof LivingEntity _living) {
								_living.setItemSlot(EquipmentSlot.CHEST, new ItemStack(BuxinModItems.PINK_DIAMOND_CHESTPLATE.get()));
							}
						}
					}
					if (Math.random() > 0.4) {
						if (entity instanceof LivingEntity _entity) {
							ItemStack _setstack = new ItemStack(Items.IRON_SWORD);
							_setstack.setCount(1);
							_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
							if (_entity instanceof Player _player)
								_player.getInventory().setChanged();
						}
						{
							Entity _entity = entity;
							if (_entity instanceof Player _player) {
								_player.getInventory().armor.set(2, new ItemStack(BuxinModItems.PINK_DIAMOND_CHESTPLATE.get()));
								_player.getInventory().setChanged();
							} else if (_entity instanceof LivingEntity _living) {
								_living.setItemSlot(EquipmentSlot.CHEST, new ItemStack(BuxinModItems.PINK_DIAMOND_CHESTPLATE.get()));
							}
						}
						{
						}
						if (Math.random() > 0.45) {
							if (entity instanceof LivingEntity _entity) {
								ItemStack _setstack = new ItemStack(EpicFightItems.IRON_SPEAR.get());
								_setstack.setCount(1);
								_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
								if (_entity instanceof Player _player)
									_player.getInventory().setChanged();
							}
							{
								Entity _entity = entity;
								if (_entity instanceof Player _player) {
									_player.getInventory().armor.set(2, new ItemStack(Items.IRON_CHESTPLATE));
									_player.getInventory().setChanged();
								} else if (_entity instanceof LivingEntity _living) {
									_living.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
								}
							}
						}
						if (Math.random() >= 0.5) {
							if (entity instanceof LivingEntity _entity) {
								ItemStack _setstack = new ItemStack(EpicFightItems.GOLDEN_SPEAR.get());
								_setstack.setCount(1);
								_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
								if (_entity instanceof Player _player)
									_player.getInventory().setChanged();
							}
							{
								Entity _entity = entity;
								if (_entity instanceof Player _player) {
									_player.getInventory().armor.set(2, new ItemStack(BuxinModItems.BLUE_DIAMOND_ARMOR_CHESTPLATE.get()));
									_player.getInventory().setChanged();
								} else if (_entity instanceof LivingEntity _living) {
									_living.setItemSlot(EquipmentSlot.CHEST, new ItemStack(BuxinModItems.BLUE_DIAMOND_ARMOR_CHESTPLATE.get()));
								}
							}
							{

							}
							{
								Entity _entity = entity;
								if (_entity instanceof Player _player) {
									_player.getInventory().armor.set(1, new ItemStack(BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_LEGGINGS.get()));
									_player.getInventory().setChanged();
								} else if (_entity instanceof LivingEntity _living) {
									_living.setItemSlot(EquipmentSlot.LEGS, new ItemStack(BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_LEGGINGS.get()));
								}
							}
							{
								Entity _entity = entity;
								if (_entity instanceof Player _player) {
									_player.getInventory().armor.set(0, new ItemStack(BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_BOOTS.get()));
									_player.getInventory().setChanged();
								} else if (_entity instanceof LivingEntity _living) {
									_living.setItemSlot(EquipmentSlot.FEET, new ItemStack(BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_BOOTS.get()));
								}
							}
							if ((entity.getDisplayName().getString()).equals("jshaman")) {
								if (entity instanceof LivingEntity _entity) {
									ItemStack _setstack = new ItemStack(Items.IRON_SWORD);
									_setstack.setCount(1);
									_entity.setItemInHand(InteractionHand.OFF_HAND, _setstack);
									if (_entity instanceof Player _player)
										_player.getInventory().setChanged();
								}
								if (entity instanceof LivingEntity _entity) {
									ItemStack _setstack = new ItemStack(BuxinModItems.PALADINSWORD.get());
									_setstack.setCount(1);
									_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
									if (_entity instanceof Player _player)
										_player.getInventory().setChanged();
								}
								{
									Entity _entity = entity;
									if (_entity instanceof Player _player) {
										_player.getInventory().armor.set(0, new ItemStack(BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_BOOTS.get()));
										_player.getInventory().setChanged();
									} else if (_entity instanceof LivingEntity _living) {
										_living.setItemSlot(EquipmentSlot.FEET, new ItemStack(BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_BOOTS.get()));
									}
								}
								{
									Entity _entity = entity;
									if (_entity instanceof Player _player) {
										_player.getInventory().armor.set(1, new ItemStack(BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_LEGGINGS.get()));
										_player.getInventory().setChanged();
									} else if (_entity instanceof LivingEntity _living) {
										_living.setItemSlot(EquipmentSlot.LEGS, new ItemStack(BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_LEGGINGS.get()));
									}
								}
								{
									Entity _entity = entity;
									if (_entity instanceof Player _player) {
										_player.getInventory().armor.set(2, new ItemStack(BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_CHESTPLATE.get()));
										_player.getInventory().setChanged();
									} else if (_entity instanceof LivingEntity _living) {
										_living.setItemSlot(EquipmentSlot.CHEST, new ItemStack(BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_CHESTPLATE.get()));
									}
								}
								{
									Entity _entity = entity;
									if (_entity instanceof Player _player) {
										_player.getInventory().armor.set(3, new ItemStack(BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_HELMET.get()));
										_player.getInventory().setChanged();
									} else if (_entity instanceof LivingEntity _living) {

									}
								}
							}
						}
					}
				}
			}
		}
		if (Math.random() < 0.9) {
			if (entity instanceof LivingEntity _entity) {
				ItemStack _setstack = new ItemStack(EpicFightItems.STONE_SPEAR.get());
				_setstack.setCount(1);
				_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
				if (_entity instanceof Player _player)
					_player.getInventory().setChanged();
			}
			{
				Entity _entity = entity;
				if (_entity instanceof Player _player) {
					_player.getInventory().armor.set(2, new ItemStack(BuxinModItems.SUN_CHESTPLATE.get()));
					_player.getInventory().setChanged();
				} else if (_entity instanceof LivingEntity _living) {
					_living.setItemSlot(EquipmentSlot.CHEST, new ItemStack(BuxinModItems.SUN_CHESTPLATE.get()));
				}
			}
			if (Math.random() < 0.85) {
				if (entity instanceof LivingEntity _entity) {
					ItemStack _setstack = new ItemStack(BuxinModItems.DIAMOND_SPEAR.get());
					_setstack.setCount(1);
					_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
					if (_entity instanceof Player _player)
						_player.getInventory().setChanged();
				}
				{
					Entity _entity = entity;
					if (_entity instanceof Player _player) {
						_player.getInventory().armor.set(2, new ItemStack(BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_CHESTPLATE.get()));
						_player.getInventory().setChanged();
					} else if (_entity instanceof LivingEntity _living) {
						_living.setItemSlot(EquipmentSlot.CHEST, new ItemStack(BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_CHESTPLATE.get()));
					}
				}
			}
			if (Math.random() < 0.8) {
				if (entity instanceof LivingEntity _entity) {
					ItemStack _setstack = new ItemStack(BuxinModItems.DIAMOND_GIANT_SWORD.get());
					_setstack.setCount(1);
					_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
					if (_entity instanceof Player _player)
						_player.getInventory().setChanged();
				}
				{
					Entity _entity = entity;
					if (_entity instanceof Player _player) {
						_player.getInventory().armor.set(2, new ItemStack(BuxinModItems.DARK_CHESTPLATE.get()));
						_player.getInventory().setChanged();
					} else if (_entity instanceof LivingEntity _living) {
						_living.setItemSlot(EquipmentSlot.CHEST, new ItemStack(BuxinModItems.DARK_CHESTPLATE.get()));
					}
				}
				if (Math.random() < 0.75) {
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack = new ItemStack(EpicFightItems.IRON_GREATSWORD.get());
						_setstack.setCount(1);
						_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
						if (_entity instanceof Player _player)
							_player.getInventory().setChanged();
					}
					{
						Entity _entity = entity;
						if (_entity instanceof Player _player) {
							_player.getInventory().armor.set(2, new ItemStack(Items.DIAMOND_CHESTPLATE));
							_player.getInventory().setChanged();
						} else if (_entity instanceof LivingEntity _living) {
							_living.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.DIAMOND_CHESTPLATE));
						}
					}
				}
				if (Math.random() < 0.7) {
					if (entity instanceof LivingEntity _entity) {
						ItemStack _setstack = new ItemStack(BuxinModItems.GOLDENLONGSWORD.get());
						_setstack.setCount(1);
						_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
						if (_entity instanceof Player _player)
							_player.getInventory().setChanged();
					}
					{
						Entity _entity = entity;
						if (_entity instanceof Player _player) {
							_player.getInventory().armor.set(2, new ItemStack(Items.GOLDEN_CHESTPLATE));
							_player.getInventory().setChanged();
						} else if (_entity instanceof LivingEntity _living) {
							_living.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.GOLDEN_CHESTPLATE));
						}
					}
					{
					}
					if (Math.random() < 0.65) {
						if (entity instanceof LivingEntity _entity) {
							ItemStack _setstack = new ItemStack(Items.DIAMOND_SWORD);
							_setstack.setCount(1);
							_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
							if (_entity instanceof Player _player)
								_player.getInventory().setChanged();
						}
						if (entity instanceof LivingEntity _entity) {
							ItemStack _setstack = new ItemStack(Items.IRON_SWORD);
							_setstack.setCount(1);
							_entity.setItemInHand(InteractionHand.OFF_HAND, _setstack);
							if (_entity instanceof Player _player)
								_player.getInventory().setChanged();
						}
						{
							Entity _entity = entity;
							if (_entity instanceof Player _player) {
								_player.getInventory().armor.set(2, new ItemStack(Items.IRON_CHESTPLATE));
								_player.getInventory().setChanged();
							} else if (_entity instanceof LivingEntity _living) {
								_living.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
							}
						}
						{
						}
					}
					if (Math.random() < 0.6) {
						if (entity instanceof LivingEntity _entity) {
							ItemStack _setstack = new ItemStack(BuxinModItems.DIAMOND_SPEAR.get());
							_setstack.setCount(1);
							_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
							if (_entity instanceof Player _player)
								_player.getInventory().setChanged();
						}
						{
							Entity _entity = entity;
							if (_entity instanceof Player _player) {
								_player.getInventory().armor.set(2, new ItemStack(Items.DIAMOND_CHESTPLATE));
								_player.getInventory().setChanged();
							} else if (_entity instanceof LivingEntity _living) {
								_living.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.DIAMOND_CHESTPLATE));
							}
						}
						{
						}
						if (Math.random() < 0.55) {
							if (entity instanceof LivingEntity _entity) {
								ItemStack _setstack = new ItemStack(BuxinModItems.NIUBI_15.get());
								_setstack.setCount(1);
								_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
								if (_entity instanceof Player _player)
									_player.getInventory().setChanged();
							}
							{
								Entity _entity = entity;
								if (_entity instanceof Player _player) {
									_player.getInventory().armor.set(2, new ItemStack(Items.IRON_CHESTPLATE));
									_player.getInventory().setChanged();
								} else if (_entity instanceof LivingEntity _living) {
									_living.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
								}
							}
						}
						if (Math.random() < 0.5) {
							if (entity instanceof LivingEntity _entity) {
								ItemStack _setstack = new ItemStack(BuxinModItems.NIUBI_15.get());
								_setstack.setCount(1);
								_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
								if (_entity instanceof Player _player)
									_player.getInventory().setChanged();
							}
							{
								Entity _entity = entity;
								if (_entity instanceof Player _player) {
									_player.getInventory().armor.set(2, new ItemStack(BuxinModItems.BLUE_DIAMOND_ARMOR_CHESTPLATE.get()));
									_player.getInventory().setChanged();
								} else if (_entity instanceof LivingEntity _living) {
									_living.setItemSlot(EquipmentSlot.CHEST, new ItemStack(BuxinModItems.BLUE_DIAMOND_ARMOR_CHESTPLATE.get()));
								}
							}
						}
						Entity _entity = entity;
						if (_entity instanceof Player _player) {
							_player.getInventory().armor.set(2, new ItemStack(Items.IRON_CHESTPLATE));
							_player.getInventory().setChanged();
						} else if (_entity instanceof LivingEntity _living) {
							_living.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
						}
					}

				}
			}
		}
		return retval;
	}

	@Override
	public boolean canChangeDimensions() {
		return false;
	}

	public static void init() {
		//DungeonHooks.addDungeonMob(BuxinModEntities.HEROBRINE_DS.get(), 180);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.25);
		builder = builder.add(Attributes.MAX_HEALTH, 40);
		builder = builder.add(Attributes.ARMOR, 36);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 20);
		builder = builder.add(Attributes.FOLLOW_RANGE, 900);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 4);
		return builder;
	}
}