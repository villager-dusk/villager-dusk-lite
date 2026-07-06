package net.mcreator.buxin.procedures;

import net.mcreator.buxin.init.BuxinModGameRules;
import net.mcreator.buxin.init.BuxinModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

public class PlayermobsProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null || world.isClientSide())
			return;
		if ((ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString()).equals("player_mobs:player_mob")) {
			if (world.getLevelData().getGameRules().getBoolean(BuxinModGameRules.DO_PLAYER_MOBS_USE_WEAPONS) == true) {
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
						Entity _entity = entity;
						if (_entity instanceof Player _player) {
							_player.getInventory().armor.set(3, new ItemStack(Items.DIAMOND_HELMET));
							_player.getInventory().setChanged();
						} else if (_entity instanceof LivingEntity _living) {
							_living.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.DIAMOND_HELMET));
						}
					}
					if (Math.random() > 0.15) {
						if (entity instanceof LivingEntity _entity) {
							ItemStack _setstack = new ItemStack(BuxinModItems.RED_RUBY_GREAT_SWORD.get());
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
							Entity _entity = entity;
							if (_entity instanceof Player _player) {
								_player.getInventory().armor.set(3, new ItemStack(BuxinModItems.PINK_DIAMOND_HELMET.get()));
								_player.getInventory().setChanged();
							} else if (_entity instanceof LivingEntity _living) {
								_living.setItemSlot(EquipmentSlot.HEAD, new ItemStack(BuxinModItems.PINK_DIAMOND_HELMET.get()));
							}
						}
					}
					if (Math.random() > 0.2) {
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
							{
								Entity _entity = entity;
								if (_entity instanceof Player _player) {
									_player.getInventory().armor.set(3, new ItemStack(Items.IRON_HELMET));
									_player.getInventory().setChanged();
								} else if (_entity instanceof LivingEntity _living) {
									_living.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
								}
							}
						}
						if (Math.random() > 0.3) {
							if (entity instanceof LivingEntity _entity) {
								ItemStack _setstack = new ItemStack(BuxinModItems.IRON_DUAL_SWORD.get());
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
								{
									Entity _entity = entity;
									if (_entity instanceof Player _player) {
										_player.getInventory().armor.set(3, new ItemStack(BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_HELMET.get()));
										_player.getInventory().setChanged();
									} else if (_entity instanceof LivingEntity _living) {
										_living.setItemSlot(EquipmentSlot.HEAD, new ItemStack(BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_HELMET.get()));
									}
								}
							}
							if (Math.random() > 0.4) {
								if (entity instanceof LivingEntity _entity) {
									ItemStack _setstack = new ItemStack(BuxinModItems.DIAMOND_INSULT_SWORD.get());
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
									Entity _entity = entity;
									if (_entity instanceof Player _player) {
										_player.getInventory().armor.set(3, new ItemStack(BuxinModItems.OBSIDIAN_HELMET.get()));
										_player.getInventory().setChanged();
									} else if (_entity instanceof LivingEntity _living) {
										_living.setItemSlot(EquipmentSlot.HEAD, new ItemStack(BuxinModItems.OBSIDIAN_HELMET.get()));
									}
								}
								if (Math.random() > 0.45) {
									if (entity instanceof LivingEntity _entity) {
										ItemStack _setstack = new ItemStack(BuxinModItems.IRON_AXE.get());
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
										ItemStack _setstack = new ItemStack(BuxinModItems.C_SWORD.get());
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
										Entity _entity = entity;
										if (_entity instanceof Player _player) {
											_player.getInventory().armor.set(3, new ItemStack(BuxinModItems.BLUE_DIAMOND_ARMOR_HELMET.get()));
											_player.getInventory().setChanged();
										} else if (_entity instanceof LivingEntity _living) {
											_living.setItemSlot(EquipmentSlot.HEAD, new ItemStack(BuxinModItems.BLUE_DIAMOND_ARMOR_HELMET.get()));
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
											_player.getInventory().armor.set(0, new ItemStack(BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_BOOTS.get()));
											_player.getInventory().setChanged();
										} else if (_entity instanceof LivingEntity _living) {
											_living.setItemSlot(EquipmentSlot.FEET, new ItemStack(BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_BOOTS.get()));
										}
									}
									if ((entity.getDisplayName().getString()).equals("jshaman")) {
										if (entity instanceof LivingEntity _entity) {
											ItemStack _setstack = new ItemStack(BuxinModItems.LEGENDARY_SWORD.get());
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
												_living.setItemSlot(EquipmentSlot.HEAD, new ItemStack(BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_HELMET.get()));
											}
										}
										((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY)).enchant(Enchantments.SHARPNESS, 10);
										((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY)).enchant(Enchantments.SHARPNESS, 10);
										((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY)).enchant(Enchantments.SHARPNESS, 10);
										((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY)).enchant(Enchantments.SHARPNESS, 10);
										((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY)).enchant(Enchantments.SHARPNESS, 10);
									}
								}
							}
						}
					}
				}
				if (Math.random() < 0.9) {
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
								ItemStack _setstack = new ItemStack(BuxinModItems.IRON_HALBERD.get());
								_setstack.setCount(1);
								_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
								if (_entity instanceof Player _player)
									_player.getInventory().setChanged();
							}
							{
								Entity _entity = entity;
								if (_entity instanceof Player _player) {
									_player.getInventory().armor.set(2, new ItemStack(BuxinModItems.OBSIDIAN_CHESTPLATE.get()));
									_player.getInventory().setChanged();
								} else if (_entity instanceof LivingEntity _living) {
									_living.setItemSlot(EquipmentSlot.CHEST, new ItemStack(BuxinModItems.OBSIDIAN_CHESTPLATE.get()));
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
								Entity _entity = entity;
								if (_entity instanceof Player _player) {
									_player.getInventory().armor.set(3, new ItemStack(Items.GOLDEN_HELMET));
									_player.getInventory().setChanged();
								} else if (_entity instanceof LivingEntity _living) {
									_living.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.GOLDEN_HELMET));
								}
							}
							if (Math.random() < 0.65) {
								if (entity instanceof LivingEntity _entity) {
									ItemStack _setstack = new ItemStack(BuxinModItems.ZUAN_SHI_DAO.get());
									_setstack.setCount(1);
									_entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
									if (_entity instanceof Player _player)
										_player.getInventory().setChanged();
								}
								{
									Entity _entity = entity;
									if (_entity instanceof Player _player) {
										_player.getInventory().armor.set(2, new ItemStack(Items.CHAINMAIL_CHESTPLATE));
										_player.getInventory().setChanged();
									} else if (_entity instanceof LivingEntity _living) {
										_living.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.CHAINMAIL_CHESTPLATE));
									}
								}
								{
									Entity _entity = entity;
									if (_entity instanceof Player _player) {
										_player.getInventory().armor.set(3, new ItemStack(Items.CHAINMAIL_HELMET));
										_player.getInventory().setChanged();
									} else if (_entity instanceof LivingEntity _living) {
										_living.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.CHAINMAIL_HELMET));
									}
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
									Entity _entity = entity;
									if (_entity instanceof Player _player) {
										_player.getInventory().armor.set(3, new ItemStack(BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_HELMET.get()));
										_player.getInventory().setChanged();
									} else if (_entity instanceof LivingEntity _living) {
										_living.setItemSlot(EquipmentSlot.HEAD, new ItemStack(BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_HELMET.get()));
									}
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
									{
										Entity _entity = entity;
										if (_entity instanceof Player _player) {
											_player.getInventory().armor.set(3, new ItemStack(Items.CHAINMAIL_HELMET));
											_player.getInventory().setChanged();
										} else if (_entity instanceof LivingEntity _living) {
											_living.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.CHAINMAIL_HELMET));
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
									if ((entity.getDisplayName().getString()).equals("jshaman")) {
										if (entity instanceof LivingEntity _entity) {
											ItemStack _setstack = new ItemStack(BuxinModItems.LEGENDARY_SWORD.get());
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
												_living.setItemSlot(EquipmentSlot.HEAD, new ItemStack(BuxinModItems.CHUN_CUI_DE_DIMOAND_HU_JIA_HELMET.get()));
											}
										}
										((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY)).enchant(Enchantments.SHARPNESS, 10);
										((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY)).enchant(Enchantments.SHARPNESS, 10);
										((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY)).enchant(Enchantments.SHARPNESS, 10);
										((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY)).enchant(Enchantments.SHARPNESS, 10);
										((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY)).enchant(Enchantments.SHARPNESS, 10);
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
