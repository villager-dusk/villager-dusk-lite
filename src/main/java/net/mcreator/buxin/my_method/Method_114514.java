package net.mcreator.buxin.my_method;

import com.mojang.authlib.GameProfile;
import net.mcreator.buxin.BuxinMod;
import net.mcreator.buxin.DelayedTask;
import net.mcreator.buxin.config.common.VFXParticleConfig;
import net.mcreator.buxin.entity.*;
import net.mcreator.buxin.entity.projectileblock.BuxinBlockProjectileEntity;
import net.mcreator.buxin.gameasset.BuxinAnimations;
import net.mcreator.buxin.init.BuxinModItems;
import net.mcreator.buxin.init.BuxinModMobEffects;
import net.mcreator.buxin.init.BuxinModParticleTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.server.ServerLifecycleHooks;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Method_114514 {
    //以下皆为哔哩哔哩@玩了114514分钟mc所写的一些简化部分操作的方法 最后更新日期 () -> 2025/11/28/21:48:07

    private static boolean performDrinkingHealingPotionAction(Entity entity, LevelAccessor levelaccessor, DynamicAnimation dynamicanimation, LivingEntityPatch livingEntityPatch) {
        if (!(dynamicanimation instanceof AttackAnimation) && !(dynamicanimation instanceof LongHitAnimation) && !(dynamicanimation instanceof HitAnimation)) {
            if (!entity.level.isClientSide() && entity.getServer() != null) {
                livingEntityPatch.playAnimationSynchronized(Animations.BIPED_EAT, 0.0F);
            }

            if (levelaccessor instanceof Level level) {
                if (!level.isClientSide()) {
                    level.playSound((Player) null, new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ()), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.drink"))), SoundSource.NEUTRAL, 1.0F, 1.0F);
                } else {
                    level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), (SoundEvent) Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.generic.drink"))), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public static Vec3 getJointWithTranslation(Entity entity, Vec3f translation, Joint joint, float handToTip, double yOffset) {
        LivingEntityPatch entitypatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (entitypatch == null) return null;

        float interpolation = 0.0F;
        OpenMatrix4f m = entitypatch.getArmature().getBindedTransformFor(entitypatch.getAnimator().getPose(interpolation), joint);

        if (translation != null) {
            OpenMatrix4f tLocal = new OpenMatrix4f().translate(translation);
            OpenMatrix4f.mul(m, tLocal, m);
        }

        if (handToTip != 0.0f) {
            OpenMatrix4f tipOffset = new OpenMatrix4f().translate(new Vec3f(0.0F, 0.0F, -handToTip));
            OpenMatrix4f.mul(m, tipOffset, m);
        }

        float yawRad = (float) -Math.toRadians(((LivingEntity) entitypatch.getOriginal()).yBodyRotO + 180.0F);
        OpenMatrix4f worldYaw = new OpenMatrix4f().rotate(yawRad, new Vec3f(0.0F, 1.0F, 0.0F));
        OpenMatrix4f.mul(worldYaw, m, m);

        LivingEntity base = (LivingEntity) entitypatch.getOriginal();
        return new Vec3(
                m.m30 + base.getX(),
                m.m31 + (base.getY() + (entity.getBbHeight() / 1.8) - 1.0) + yOffset,
                m.m32 + base.getZ()
        );
    }

    public static boolean isWearingAnyArmor(LivingEntity entity) {
        for (ItemStack armor : entity.getArmorSlots()) {
            if (!armor.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static void diamond_protect(Entity entity) {
        LivingEntityPatch<?> livingEntityPatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        LevelAccessor levelaccessor = entity.level;
        if (livingEntityPatch != null) {
            final DynamicAnimation dynamicAnimation = livingEntityPatch.getAnimator().getPlayerFor((DynamicAnimation) null).getAnimation();
            if (entity instanceof LivingEntity livingEntity) {
                if (entity.isPassenger()) {
                    entity.stopRiding();
                }
                ItemStack oldItem = livingEntity.getMainHandItem();
                ItemStack potionItem = PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.STRONG_HEALING);;
                new DelayedTask(20) {
                    @Override
                    public void run() {
                        if (entity.isAlive()) {
                            ItemStack mainhand = livingEntity.getMainHandItem();
                            if (mainhand.getItem() != potionItem.getItem()) {
                                livingEntity.setItemInHand(InteractionHand.MAIN_HAND, potionItem);
                            }
                            if (performDrinkingHealingPotionAction(entity, levelaccessor, dynamicAnimation, livingEntityPatch)) {
                                new DelayedTask(4) {
                                    @Override
                                    public void run() {
                                        if (entity.isAlive()) {
                                            if (performDrinkingHealingPotionAction(entity, levelaccessor, dynamicAnimation, livingEntityPatch)) {
                                                new DelayedTask(4) {
                                                    public void run() {
                                                        if (entity.isAlive()) {
                                                            if (performDrinkingHealingPotionAction(entity, levelaccessor, dynamicAnimation, livingEntityPatch)) {
                                                                new DelayedTask(4) {
                                                                    public void run() {
                                                                        if (entity.isAlive()) {
                                                                            if (performDrinkingHealingPotionAction(entity, levelaccessor, dynamicAnimation, livingEntityPatch)) {
                                                                                new DelayedTask(4) {
                                                                                    public void run() {
                                                                                        if (entity.isAlive()) {
                                                                                            if (performDrinkingHealingPotionAction(entity, levelaccessor, dynamicAnimation, livingEntityPatch)) {
                                                                                                new DelayedTask(4) {
                                                                                                    public void run() {
                                                                                                        if (entity.isAlive()) {
                                                                                                            if (performDrinkingHealingPotionAction(entity, levelaccessor, dynamicAnimation, livingEntityPatch)) {
                                                                                                                new DelayedTask(4) {
                                                                                                                    public void run() {
                                                                                                                        if (entity.isAlive()) {
                                                                                                                            if (performDrinkingHealingPotionAction(entity, levelaccessor, dynamicAnimation, livingEntityPatch)) {
                                                                                                                                new DelayedTask(4) {
                                                                                                                                    public void run() {
                                                                                                                                        if (entity.isAlive()) {
                                                                                                                                            if (performDrinkingHealingPotionAction(entity, levelaccessor, dynamicAnimation, livingEntityPatch)) {
                                                                                                                                                new DelayedTask(20) {
                                                                                                                                                    public void run() {
                                                                                                                                                        if (entity.isAlive()) {
                                                                                                                                                            livingEntity.setItemInHand(InteractionHand.MAIN_HAND, oldItem);
                                                                                                                                                            entity.setItemSlot(EquipmentSlot.MAINHAND, BuxinModItems.NIUBI_2DANSHOU.get().getDefaultInstance());
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
                                                                                                                                                            ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY)).enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
                                                                                                                                                            ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY)).enchant(Enchantments.BINDING_CURSE, 5);
                                                                                                                                                            ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY)).enchant(Enchantments.BINDING_CURSE, 5);
                                                                                                                                                            ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY)).enchant(Enchantments.BINDING_CURSE, 5);
                                                                                                                                                            ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY)).enchant(Enchantments.BINDING_CURSE, 5);
                                                                                                                                                            ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY)).enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
                                                                                                                                                            ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.CHEST) : ItemStack.EMPTY)).enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
                                                                                                                                                            ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.HEAD) : ItemStack.EMPTY)).enchant(Enchantments.ALL_DAMAGE_PROTECTION, 5);
                                                                                                                                                        }
                                                                                                                                                    }
                                                                                                                                                };
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                };
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }
                                                                                                                };
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                };
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                };
                                                                            }
                                                                        }
                                                                    }
                                                                };
                                                            }
                                                        }
                                                    }
                                                };
                                            }
                                        }
                                    }
                                };
                            }
                        }
                    }
                };
            }
        }
    }

    public static Minecraft mc = Minecraft.getInstance();

    public static void add_entity(Entity entity, Level level,double x,double y,double z){
        if (level instanceof ServerLevel _level) {
            entity.moveTo(x, y, z, level.getRandom().nextFloat() * 360F, 0);
            if (entity instanceof Mob _mobToSpawn)
                _mobToSpawn.finalizeSpawn(_level, level.getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
            level.addFreshEntity(entity);
        }
    }
    //例如：
    //Method_114514.entity_use_command(entity,"/weather clear");
    //要加斜杠
    public static void entity_use_command(Entity entity,String command){
        if(entity == null){
            return;
        }
        try {
            if (!entity.level.isClientSide() && entity.getServer() != null && entity.level.getServer() != null) {
                entity.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, entity.position(), entity.getRotationVector(), entity.level instanceof ServerLevel ? (ServerLevel) entity.level : null, 4, entity.getName().getString(), entity.getDisplayName(), entity.level.getServer(), entity), command);
            }
        } catch (Exception e){
            BuxinMod.LOGGER.error("{} was failed to use command : {} , err in : {}", entity.getDisplayName().getString(), command, e);
        }
    }

    public static void level_use_command(Level level,Vec3 pos,String command){
        if (level instanceof ServerLevel _level)
            _level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, pos, Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(), command);
    }
    //指定路径播放声音(modid + sound-name)
    public static void play_sound(LevelAccessor level,double x,double y,double z,String soundpath){
        try {
            if (level instanceof Level _level) {
                if (!_level.isClientSide()) {
                    _level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(soundpath)), SoundSource.NEUTRAL, 1, 1);
                } else {
                    _level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(soundpath)), SoundSource.NEUTRAL, 1, 1, false);
                }
            }
        } catch (Exception e){
            System.err.println("failed to playsonund : " + soundpath + " , err in : " + e);
        }
    }

    public static void play_sound(Entity entity,String soundpath){
        if(entity == null){
            return;
        }
        Level _level = entity.level();
        double x = entity.getX(),y = entity.getY(),z = entity.getZ();
        try {
            if (!_level.isClientSide()) {
                _level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(soundpath)), SoundSource.NEUTRAL, 1.5f, 1);
            } else {
                _level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(soundpath)), SoundSource.NEUTRAL, 1.5f, 1, false);
            }
        } catch (Exception e){
            System.err.println("failed to playsonund : " + soundpath + " , err in : " + e);
        }
    }

    public static void play_sound(Entity entity,String soundpath,float xd,float yd){
        if(entity == null){
            return;
        }
        Level _level = entity.level();
        double x = entity.getX(),y = entity.getY(),z = entity.getZ();
        try {
            if (!_level.isClientSide()) {
                _level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(soundpath)), SoundSource.NEUTRAL, xd, yd);
            } else {
                _level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(soundpath)), SoundSource.NEUTRAL, xd, yd, false);
            }
        } catch (Exception e){
            System.err.println("failed to playsonund : " + soundpath + " , err in : " + e);
        }
    }

    public static void play_sound(Level world,Vec3 pos,String soundpath,float xd,float yd){
        if(world == null || pos == null){
            return;
        }
        double x = pos.x,y = pos.y,z = pos.z;
        try {
            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(soundpath)), SoundSource.NEUTRAL, xd, yd);
            } else {
                world.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(soundpath)), SoundSource.NEUTRAL, xd, yd, false);
            }
        } catch (Exception e){
            System.err.println("failed to playsonund : " + soundpath + " , err in : " + e);
        }
    }

    public static String getEntityTeamName(Entity entity){
        return entity != null && entity.getTeam() != null ? entity.getTeam().getName() : "null";
    }

    public static Vec3 getPositionInFront(Entity entity, double distance) {
        // 获取实体的视线方向
        Vec3 lookVector = entity.getLookAngle();

        // 获取实体眼睛位置
        Vec3 eyePosition = entity.getEyePosition();

        // 计算前方位置
        return eyePosition.add(lookVector.scale(distance));
    }

    public static void moveToPosition(Entity entity,Vec3 pos, double speed) {
        if (entity == null || entity.level().isClientSide || pos == null) return;
        if (entity instanceof Mob mob) {
            mob.getNavigation().moveTo(pos.x,pos.y,pos.z, speed);
        }
    }

    public static void moveToEntity(Entity sourceEntity,Entity moveToEntity, double speed) {
        if (sourceEntity == null || sourceEntity.level().isClientSide || moveToEntity == null) return;
        if (sourceEntity instanceof Mob mob) {
            mob.getNavigation().moveTo(moveToEntity.getX(),moveToEntity.getY(),moveToEntity.getZ(), speed);
        }
    }


    public static void play_sound(Level level,Vec3 pos,String soundpath){
        double x = pos.x,y = pos.y,z = pos.z;
        try {
            if (!level.isClientSide()) {
                level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(soundpath)), SoundSource.NEUTRAL, 1, 1);
            } else {
                level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(soundpath)), SoundSource.NEUTRAL, 1, 1, false);
            }
        } catch (Exception e){
            System.err.println("failed to playsonund : " + soundpath + " , err in : " + e);
        }
    }

    //处决
    public static void execute_event(Entity attackentity,Entity underattackentity,StaticAnimation attackanimation,StaticAnimation underattackanimation,float time,int kill_wait_time,int bloodvalue,boolean enablesound,boolean enablelog,double moveParticleX,double moveParticleY,double moveParticleZ,double moveParticleSpeed,boolean kill){
        try {
            if(attackentity instanceof LivingEntity && underattackentity instanceof LivingEntity && attackentity != underattackentity && !attackentity.level.isClientSide) {
                if(SystemMethod.isWindows()) {
                    VFXTool.addVFXParticle(new Vec3(underattackentity.getX(),underattackentity.getY() + 2.2,underattackentity.getZ()),"buxin","danger",underattackentity.level());
                    Method_114514.play_sound(underattackentity,"buxin:danger");
                }
                BuxinMod.queueServerWork(10,() -> {
                    LevelAccessor world = attackentity.level();
                    double dx = underattackentity.getX(), dy = underattackentity.getY(), dz = underattackentity.getZ();
                    if (enablelog) {
                        System.out.println("execute successfully");
                    }
                    attackentity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(EntityPatch -> {
                        if (EntityPatch instanceof LivingEntityPatch<?> LivingEntityPatch) {
                            LivingEntityPatch.playAnimationSynchronized(attackanimation, time);
                        }
                    });
                    underattackentity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(EntityPatch -> {
                        if (EntityPatch instanceof LivingEntityPatch<?> LivingEntityPatch) {
                            LivingEntityPatch.playAnimationSynchronized(underattackanimation, time);
                        }
                    });
                    if (underattackentity instanceof Player) {
                        //underattackentity.sendSystemMessage(Component.literal("§e" + "你被" + attackentity.getDisplayName().getString() + "残忍的处决了!"));
                    }
                    attackentity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((underattackentity.getX()), (underattackentity.getY()), (underattackentity.getZ())));
                    underattackentity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(attackentity.getX(), attackentity.getY() + 1.0, attackentity.getZ()));
                    Vec3 viewVec = ((LivingEntity) underattackentity).getViewVector(1.0F);
                    attackentity.teleportTo(((LivingEntity) underattackentity).getX() + viewVec.x() * 1.5, ((LivingEntity) underattackentity).getY(), ((LivingEntity) underattackentity).getZ() + viewVec.z() * 1.51);
                    if (underattackentity instanceof LivingEntity _entity && !_entity.level.isClientSide())
                        _entity.addEffect(new MobEffectInstance(BuxinModMobEffects.ENTITY_CANT_BLOCK.get(), 125, 1, false, false));
                    ((LivingEntity) underattackentity).addEffect(new MobEffectInstance((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get(), 45, 2, false, false));
                    ((LivingEntity) attackentity).addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 70, 50, false, false));
                    ((LivingEntity) underattackentity).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 45, 50, false, false));
                    ((LivingEntity) attackentity).addEffect(new MobEffectInstance((MobEffect) MobEffects.MOVEMENT_SLOWDOWN, 130, 0, false, false));
                    Method_114514.play_sound(attackentity.level(), underattackentity.getX(), underattackentity.getY(), underattackentity.getZ(), "buxin:bitch");
                    BuxinMod.queueServerWork(kill_wait_time, () -> {
                        Method_114514.entity_use_command(attackentity, "/impactful @s shake 30 3 3");
                        if (world instanceof ServerLevel _level)
                            _level.sendParticles((SimpleParticleType) (EpicFightParticles.BLADE_RUSH_SKILL.get()), dx, (dy + 1.3), dz, 1, 0.25, 0.3, 0.25, 0.2);
                        if (attackentity instanceof Player && kill) {
                            underattackentity.hurt(underattackentity.damageSources().generic(), (((LivingEntity) underattackentity).getMaxHealth() * 5) + 4);
                        } else {
                            underattackentity.hurt(underattackentity.damageSources().generic(), (float) (((LivingEntity) underattackentity).getMaxHealth() * 0.15));
                        }
                        if (!attackentity.level.isClientSide() && attackentity.getServer() != null) {
                            if (enablesound) {
                                Method_114514.play_sound(attackentity.level(), underattackentity.getX(), underattackentity.getY(), underattackentity.getZ(), "epicfight:entity.hit.eviscerate");
                            }
                            if (attackentity instanceof Player) {
                                if (underattackentity instanceof ServerPlayer) {
                                    //NetWorkManger.sendToPlayer(new CameraShake(60, 7, 3), (ServerPlayer) underattackentity);
                                    Method_114514.entity_use_command(attackentity, "/impactful @s shake 60 7 3");
                                }
                            }
                        }
                        if (world instanceof ServerLevel _level)
                            _level.sendParticles((SimpleParticleType) (BuxinModParticleTypes.BLOOD.get()), underattackentity.getX(), (underattackentity.getY() + 1.3), underattackentity.getZ(), (bloodvalue), moveParticleX, moveParticleY, moveParticleZ, moveParticleSpeed);
                    });
                });
            }
        } catch (Exception e) {
        }
    }

    //字面意思
    public static int getAnimationId(StaticAnimation animation){
        return animation.getId();
    }

    //字面意思
    public static float getAnimationConvertTime(StaticAnimation animation){
        return animation.getConvertTime();
    }

    //字面意思
    public static Entity getTarget(Entity entity){
        return ((Mob) entity).getTarget();
    }

    //在实体骨骼上渲染粒子(对于史诗战斗)
    public static void render_particle_on_weapons(Entity entity, boolean mainhand, boolean offhand, SimpleParticleType particle, int particlevalue, float max_d, float min_d){
        entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(entitypatch -> {
            if(entitypatch instanceof LivingEntityPatch<?>){
                int degree;
                float min;
                float max;
                float rz;
                OpenMatrix4f transformMatrixx;
                byte nL;
                int iL;
                for(degree = 0; degree < particlevalue; ++degree) {
                    if(offhand) {
                        min = min_d;
                        max = max_d;
                        rz = min + (max - min) * (new Random()).nextFloat();
                        transformMatrixx = ((LivingEntityPatch<?>) entitypatch).getArmature().getBindedTransformFor(((LivingEntityPatch<?>) entitypatch).getAnimator().getPose(0.0F), Armatures.BIPED.toolL);
                        transformMatrixx.translate(new Vec3f(0.0F, 0.0F, -rz));
                        OpenMatrix4f.mul((new OpenMatrix4f()).rotate(-((float) Math.toRadians((double) (((LivingEntity) entitypatch.getOriginal()).yBodyRotO + 180.0F))), new Vec3f(0.0F, 1.0F, 0.0F)), transformMatrixx, transformMatrixx);
                        nL = 12;
                        for (iL = 0; iL < nL; ++iL) {
                            ((LivingEntity) entitypatch.getOriginal()).level().addParticle((ParticleOptions) particle, (double) transformMatrixx.m30 + ((LivingEntity) entitypatch.getOriginal()).getX(), (double) transformMatrixx.m31 + ((LivingEntity) entitypatch.getOriginal()).getY(), (double) transformMatrixx.m32 + ((LivingEntity) entitypatch.getOriginal()).getZ(), 0.0, -0.009999999776482582, 0.0);
                        }
                    }
                }

                for(degree = 0; degree < particlevalue; ++degree) {
                    if(mainhand) {
                        min = min_d;
                        max = max_d;
                        rz = min + (max - min) * (new Random()).nextFloat();
                        transformMatrixx = ((LivingEntityPatch<?>) entitypatch).getArmature().getBindedTransformFor(((LivingEntityPatch<?>) entitypatch).getAnimator().getPose(0.0F), Armatures.BIPED.toolR);
                        transformMatrixx.translate(new Vec3f(0.0F, 0.0F, -rz));
                        OpenMatrix4f.mul((new OpenMatrix4f()).rotate(-((float) Math.toRadians((double) (((LivingEntity) entitypatch.getOriginal()).yBodyRotO + 180.0F))), new Vec3f(0.0F, 1.0F, 0.0F)), transformMatrixx, transformMatrixx);
                        nL = 10;

                        for (iL = 0; iL < nL; ++iL) {
                            ((LivingEntity) entitypatch.getOriginal()).level().addParticle((ParticleOptions) particle, (double) transformMatrixx.m30 + ((LivingEntity) entitypatch.getOriginal()).getX(), (double) transformMatrixx.m31 + ((LivingEntity) entitypatch.getOriginal()).getY(), (double) transformMatrixx.m32 + ((LivingEntity) entitypatch.getOriginal()).getZ(), 0.0, -0.009999999776482582, 0.0);
                        }
                    }
                }
            }
        });
    }


    //空间距离
    public static double entity1_distance_to_entity2_xyz(Entity entity1, Entity entity2){
        float deltaX = (float)(entity1.getX() - entity2.getX());
        float deltaY = (float)(entity1.getY() - entity2.getY());
        float deltaZ = (float)(entity1.getZ() - entity2.getZ());
        float squared_deltaX = deltaX * deltaX;
        float squared_deltaY = deltaY * deltaY;
        float squared_deltaZ = deltaZ * deltaZ;
        return Math.sqrt(squared_deltaX + squared_deltaY + squared_deltaZ);
    }

    //平面距离
    public static double entity1_distance_to_entity2_xy(Entity entity1, Entity entity2){
        float deltaX = (float)(entity1.getX() - entity2.getX());
        float deltaZ = (float)(entity1.getZ() - entity2.getZ());
        float squared_deltaX = deltaX * deltaX;
        float squared_deltaZ = deltaZ * deltaZ;
        return Math.sqrt(squared_deltaX + squared_deltaZ);
    }

    public static void herobrine_born(Entity entity){
        if(entity instanceof Mob m && !(entity instanceof HerobrineDsEntity)){
            m.setNoAi(true);
            BuxinMod.queueServerWork(65,() -> {
                m.setNoAi(false);
            });
        }
        entity.getPersistentData().putBoolean("hbb",true);
        AnimationPlayer.playAnimation(entity,BuxinAnimations.HB_born);
        Method_114514.send_message_to_all_over_the_world(entity.level(),entity.getDisplayName().getString() + "§2已降临在 " + (int)entity.getX() + " " + (int)entity.getY() + " " + (int)entity.getZ());
        Method_114514.play_sound(entity.level(),entity.getX(),entity.getY(),entity.getZ(),"buxin:hb_summon");
        Method_114514.entity_use_command(entity, "/effect give @s minecraft:slowness 3 255");
        if(BuxinMod.isWindows() && VFXParticleConfig.VFXParticleConfig.get()) {
            VFXTool.addVFXParticle(entity.position(), BuxinMod.MODID, "herobrine_portal_red", entity.level());
        }
    }

    public static void send_message_to_all_over_the_world(Level world,String message){

    }

    public static void send_message_to_all_over_the_world_by_sb_self(Entity entity,Level world,String message,boolean enable_translate){
        String send_message = "<" + entity.getDisplayName().getString() + "> " + message;
        if(enable_translate) {

        } else {

        }
    }

    public static Entity NearestEntity(Entity sourceEntity, double searchRadius) {
        /*if (sourceEntity.level.isClientSide()) {
            return sourceEntity;
        }
         */
        List<Entity> nearbyEntities = sourceEntity.level.getEntities(sourceEntity, sourceEntity.getBoundingBox().inflate(searchRadius), entity -> entity != sourceEntity && !(entity instanceof ItemEntity) && !(entity instanceof Projectile) && entity.isAlive());
        Entity nearestEntity = null;
        double nearestDistance = Double.MAX_VALUE;

        for (Entity entity : nearbyEntities) {
            double distance = sourceEntity.distanceToSqr(entity);
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestEntity = entity;
            }
        }
        return nearestEntity;
    }

    public static Entity NearestPlayer(Entity sourceEntity, double searchRadius) {
        /*if (sourceEntity.level.isClientSide()) {
            return sourceEntity;
        }
         */
        List<Entity> nearbyEntities = sourceEntity.level.getEntities(sourceEntity, sourceEntity.getBoundingBox().inflate(searchRadius), entity -> entity instanceof Player && entity.isAlive());
        Entity nearestEntity = null;
        double nearestDistance = Double.MAX_VALUE;

        for (Entity entity : nearbyEntities) {
            double distance = sourceEntity.distanceToSqr(entity);
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestEntity = entity;
            }
        }
        return nearestEntity;
    }

    //更改实体属性
    public static void setEntityAttributes(LivingEntity entity, Attribute attribute, double value) {
        AttributeInstance Attribute = entity.getAttribute(attribute);
        if (Attribute != null) {
            Attribute.setBaseValue(value);
        }
    }

    //发射暗影的大型黑曜石(不限于黑曜石方块)
    public static void shootChain(Entity shooter, BlockState block, float velocity, int length) {
        Level level = shooter.level();
        if (level.isClientSide) return;

        double eyeY = shooter.getEyeY();
        Vec3 look = shooter.getLookAngle().normalize();
        RandomSource rand = level.getRandom();

        for (int i = 0; i < length; i++) {
            BuxinBlockProjectileEntity proj = new BuxinBlockProjectileEntity(level, shooter instanceof LivingEntity ? (LivingEntity) shooter : null, block);

            Vec3 forward = look.scale(i * 1.0);

            double sideX = (rand.nextDouble() - 0.5) * 2.0;
            double sideY = (rand.nextDouble() - 0.5) * 2.0;
            double sideZ = (rand.nextDouble() - 0.5) * 2.0;

            proj.setPos(shooter.getX() + forward.x + sideX, eyeY + forward.y + sideY, shooter.getZ() + forward.z + sideZ);
            proj.setDeltaMovement(look.scale(velocity));

            level.addFreshEntity(proj);
        }
    }

    public static Vec3 getArmPosition(Entity entity, Vec3f translation, Joint joint, float handToTip, double yOffset) {
        LivingEntityPatch entitypatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (entitypatch == null) return null;

        float interpolation = 0.0F;
        OpenMatrix4f m = entitypatch.getArmature().getBindedTransformFor(entitypatch.getAnimator().getPose(interpolation), joint);

        if (translation != null) {
            OpenMatrix4f tLocal = new OpenMatrix4f().translate(translation);
            OpenMatrix4f.mul(m, tLocal, m);
        }

        if (handToTip != 0.0f) {
            OpenMatrix4f tipOffset = new OpenMatrix4f().translate(new Vec3f(0.0F, 0.0F, -handToTip));
            OpenMatrix4f.mul(m, tipOffset, m);
        }

        float yawRad = (float) -Math.toRadians(((LivingEntity) entitypatch.getOriginal()).yBodyRotO + 180.0F);
        OpenMatrix4f worldYaw = new OpenMatrix4f().rotate(yawRad, new Vec3f(0.0F, 1.0F, 0.0F));
        OpenMatrix4f.mul(worldYaw, m, m);

        LivingEntity base = (LivingEntity) entitypatch.getOriginal();
        return new Vec3(
                m.m30 + base.getX(),
                m.m31 + (base.getY() + (entity.getBbHeight() / 1.8) - 1.0) + yOffset,
                m.m32 + base.getZ()
        );
    }

    public static void throwEffect_up(LivingEntity entity,Potion potion){
        Vec3 $$2 = entity.getDeltaMovement();
        double $$3 = entity.getX() + $$2.x - entity.getX();
        double $$4 = entity.getEyeY() - 1.100000023841858 - entity.getY();
        double $$5 = entity.getZ() + $$2.z - entity.getZ();
        double d = Math.sqrt($$3 * $$3 + $$5 * $$5);
        ThrownPotion $$8 = new ThrownPotion(entity.level(), entity);
        $$8.setItem(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), potion));
        $$8.setXRot($$8.getXRot() - -20.0F);
        $$8.shoot($$3, $$4 + d * 0.2 , $$5, 0.45F, 4.0F);
        if (!entity.isSilent()) {
            entity.level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.WITCH_THROW, entity.getSoundSource(), 1.0F, 0.8F + entity.getRandom().nextFloat() * 0.4F);
        }
        entity.level.addFreshEntity($$8);
    }

    public static boolean checkGameRendererShader() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.gameRenderer != null) {
            // 检查是否有后期处理效果
            if (mc.gameRenderer.currentEffect() != null) {
                return true;
            }

            // 检查着色器实例
            try {
                Field shaderEffectField = GameRenderer.class.getDeclaredField("shaderEffect");
                shaderEffectField.setAccessible(true);
                return shaderEffectField.get(mc.gameRenderer) != null;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public static void spawn_prisoner(Entity entity){
        Method_114514.entity_use_command(entity,"/summon buxin:villager_prisoner ^ ^ ^ {VillagerData:{level:2,profession:\"minecraft:weaponsmith\"}}");
        Method_114514.entity_use_command(entity,"/team join villager @e[type=buxin:villager_prisoner]");
    }
    public static void shootArrowAi(Entity entity){
        if(entity instanceof Mob && ((Mob) entity).getTarget() != null && Math.random() > 0.5 && AnimationPlayer.entity_getAnimation(entity) != null && !(AnimationPlayer.entity_getAnimation(entity) instanceof LongHitAnimation) && !(AnimationPlayer.entity_getAnimation(entity) instanceof HitAnimation) && ((Mob) entity).getTarget() != null){
            if(Method_114514.entity1_distance_to_entity2_xyz(entity,((Mob) entity).getTarget()) > 6 || entity.isPassenger()){

                //  double x = entity.getX(),y = entity.getY(),z = entity.getZ();
                if(((Mob) entity).getMainHandItem().getItem() != Items.BOW) {
                    entity.setItemSlot(EquipmentSlot.MAINHAND, Items.BOW.getDefaultInstance());
                }
                /*
                Random random1 = new Random();
                for (int i = 0; i < 1 + random1.nextInt(2); i++) {
                    entity.lookAt(EntityAnchorArgument.Anchor.EYES,((Mob) entity).getTarget().getLookAngle());
                    entity.lookAt(EntityAnchorArgument.Anchor.EYES,((Mob) entity).getTarget().position());
                    BuxinMod.queueServerWork((int) (5 * i + Math.random() * 3),() -> {
                        Level level = entity.level;
                        if (!level.isClientSide() && ((Mob) entity).getTarget() != null) {
                            Vec3 turretPos = entity.getEyePosition();
                            Vec3 targetPos = ((Mob) entity).getTarget().getEyePosition();
                            Vec3 direction = targetPos.subtract(turretPos);
                            Arrow projectile = new Arrow(EntityType.ARROW, level);
                            projectile.setOwner(entity);
                            projectile.setBaseDamage(2.0);
                            projectile.setKnockback(1);
                            projectile.setPierceLevel((byte)1);
							/*
							projectile.setPos(entity.getX(), entity.getEyeY() - 0.1, entity.getZ());
							projectile.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, 3.0F, 0.0F);


                            projectile.setPos(entity.getX(), entity.getEyeY() + 0.2, entity.getZ());
                            projectile.shoot(direction.x, direction.y, direction.z, 3, 1);
                            level.addFreshEntity(projectile);
                        }

                        if ((LevelAccessor) level instanceof Level level1) {
                            if (!level1.isClientSide()) {
                                level1.playSound((Player)null, new BlockPos((int)x, (int)y, (int)z), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.arrow.shoot")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                            } else {
                                level1.playLocalSound(x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.arrow.shoot")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                            }
                        }
                    });
                    Method_114514.entity_use_command(entity,"/indestructible @s play \"epicfight:biped/combat/bow_shot_mid\" 0 1");

                }

                 */

                BuxinMod.queueServerWork(55,() -> {
                    if(entity instanceof Mob mob && ((Mob) entity).getTarget() != null && !(Method_114514.entity1_distance_to_entity2_xyz(entity,((Mob) entity).getTarget()) > 6) && !(mob.isPassenger())) {
                        if (entity instanceof CunMinWeiBingEntity || entity instanceof PurpleVillagerCavalryEntity) {
                            entity.setItemSlot(EquipmentSlot.MAINHAND, BuxinModItems.QIBINJIAN.get().getDefaultInstance());
                        } else if (entity instanceof GreenVillagerCavalryEntity || entity instanceof RedVillagerEntity) {
                            entity.setItemSlot(EquipmentSlot.MAINHAND, BuxinModItems.PALADINSWORD.get().getDefaultInstance());
                        } else if (entity instanceof VillagerScoutEntity) {
                            entity.setItemSlot(EquipmentSlot.MAINHAND, Items.DIAMOND_SWORD.getDefaultInstance());
                        } else if (entity instanceof Niubi13Entity entity1) {
                            if (Math.random() > 0.5) {
                                entity1.setItemSlot(EquipmentSlot.MAINHAND, BuxinModItems.WOOPIE.get().getDefaultInstance());
                            } else {
                                entity1.setItemSlot(EquipmentSlot.MAINHAND, Items.DIAMOND_SWORD.getDefaultInstance());
                            }
                        }
                        mob.getMainHandItem().enchant(Enchantments.SHARPNESS, 1);
                    }
                });
            }
        }
    }

    public static boolean isFront(Entity entity, DamageSource damageSource) {
        Vec3 sourceLocation = damageSource.getSourcePosition();
        Vec3 viewVector = entity.getViewVector(1.0F);
        if (sourceLocation != null) {
            Vec3 toSourceLocation = sourceLocation.subtract(entity.position()).normalize();
            return toSourceLocation.dot(viewVector) < 0.0;
        } else {
            return true;
        }
    }

    public static boolean isHerobrine(Entity entity){
        return (ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString()).contains("herobrine") || (ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString()).contains("him") || (ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString()).contains("herobrine") || entity instanceof ShifangEntity || entity instanceof HerobrineDsEntity;
    }

    public static void AngrySteveshootArrowAi(Entity entity){
        if(entity instanceof Mob && ((Mob) entity).getTarget() != null&& AnimationPlayer.entity_getAnimation(entity) != null && !(AnimationPlayer.entity_getAnimation(entity) instanceof LongHitAnimation) && !(AnimationPlayer.entity_getAnimation(entity) instanceof HitAnimation) && ((Mob) entity).getTarget() != null && entity.onGround() && !(((Mob) entity).isNoAi())){
            if(Method_114514.entity1_distance_to_entity2_xyz(entity,((Mob) entity).getTarget()) > 6 || entity.isPassenger()){

                //  double x = entity.getX(),y = entity.getY(),z = entity.getZ();
                if(((Mob) entity).getMainHandItem().getItem() != Items.BOW) {
                    entity.setItemSlot(EquipmentSlot.MAINHAND, Items.BOW.getDefaultInstance());
                }
                /*
                Random random1 = new Random();
                for (int i = 0; i < 1 + random1.nextInt(2); i++) {
                    entity.lookAt(EntityAnchorArgument.Anchor.EYES,((Mob) entity).getTarget().getLookAngle());
                    entity.lookAt(EntityAnchorArgument.Anchor.EYES,((Mob) entity).getTarget().position());
                    BuxinMod.queueServerWork((int) (5 * i + Math.random() * 3),() -> {
                        Level level = entity.level;
                        if (!level.isClientSide() && ((Mob) entity).getTarget() != null) {
                            Vec3 turretPos = entity.getEyePosition();
                            Vec3 targetPos = ((Mob) entity).getTarget().getEyePosition();
                            Vec3 direction = targetPos.subtract(turretPos);
                            Arrow projectile = new Arrow(EntityType.ARROW, level);
                            projectile.setOwner(entity);
                            projectile.setBaseDamage(2.0);
                            projectile.setKnockback(1);
                            projectile.setPierceLevel((byte)1);
							/*
							projectile.setPos(entity.getX(), entity.getEyeY() - 0.1, entity.getZ());
							projectile.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, 3.0F, 0.0F);


                            projectile.setPos(entity.getX(), entity.getEyeY() + 0.2, entity.getZ());
                            projectile.shoot(direction.x, direction.y, direction.z, 3, 1);
                            level.addFreshEntity(projectile);
                        }

                        if ((LevelAccessor) level instanceof Level level1) {
                            if (!level1.isClientSide()) {
                                level1.playSound((Player)null, new BlockPos((int)x, (int)y, (int)z), (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.arrow.shoot")), SoundSource.NEUTRAL, 1.0F, 1.0F);
                            } else {
                                level1.playLocalSound(x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("minecraft", "entity.arrow.shoot")), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                            }
                        }
                    });
                    Method_114514.entity_use_command(entity,"/indestructible @s play \"epicfight:biped/combat/bow_shot_mid\" 0 1");

                }

                 */

                BuxinMod.queueServerWork(55,() -> {
                    if(entity instanceof Mob mob && ((Mob) entity).getTarget() != null && !(Method_114514.entity1_distance_to_entity2_xyz(entity,((Mob) entity).getTarget()) > 6) && !(mob.isPassenger())) {
                        if (entity instanceof CunMinWeiBingEntity || entity instanceof PurpleVillagerCavalryEntity) {
                            entity.setItemSlot(EquipmentSlot.MAINHAND, BuxinModItems.NIUBI_2DANSHOU.get().getDefaultInstance());
                        }
                        mob.getMainHandItem().enchant(Enchantments.SHARPNESS, 5);
                    }
                });
            }
        }
    }
    /*
    public static void 逐渐扩散开执行逻辑(int 循环次数,boolean 是否等待,double 等待时间,Runnable 逻辑){
        for (int i = 1; i <= 循环次数; i++) {
            BuxinMod.queueServerWork((int) (循环次数 * 等待时间),() -> {
                逻辑.run();
            });
        }
    }

     */
}