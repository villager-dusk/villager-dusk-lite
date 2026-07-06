package net.mcreator.buxin.client.renderer;

import net.mcreator.buxin.entity.Entity303Entity;
import net.mcreator.buxin.entity.fake_entity.FakeEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

public class FakeEntityRenderer extends LivingEntityRenderer<FakeEntity, PlayerModel<FakeEntity>> {
    public FakeEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false), 0.5F);
        this.addLayer(new HumanoidArmorLayer<>(this,
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                context.getModelManager()));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull FakeEntity entity) {
        if(Minecraft.getInstance().level != null){
            Entity owner = entity.getOwner();
            if(owner instanceof Entity303Entity) {
                return new ResourceLocation("buxin:textures/entities/09f5855f298cf918.png");
            } else if(owner instanceof AbstractClientPlayer abstractClientPlayer){
                return abstractClientPlayer.getSkinTextureLocation();
            } else if(owner instanceof LivingEntity livingOwner){
                EntityRenderer<?> renderer = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(livingOwner);
                if(renderer instanceof LivingEntityRenderer<?,?> livingRenderer){
                    return ((LivingEntityRenderer<LivingEntity, ?>) livingRenderer).getTextureLocation(livingOwner);
                }
            }
        }
        return new ResourceLocation("minecraft:textures/entity/player/steve.png");
    }
}