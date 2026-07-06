package net.mcreator.buxin.item.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.mcreator.buxin.interfaces.RendersPlayerArms;
import net.mcreator.buxin.item.DiamondWuShiSwordItem;
import net.mcreator.buxin.item.model.DiamondWuShiSwordItemModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector3f;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("all")
public class DiamondWuShiSwordItemRenderer extends GeoItemRenderer<DiamondWuShiSwordItem> implements RendersPlayerArms {

    public DiamondWuShiSwordItemRenderer() {
        super(new DiamondWuShiSwordItemModel());
    }


    private static final float SCALE_RECIPROCAL = 1.0f / 16.0f;
    protected boolean renderArms = false;
    protected MultiBufferSource currentBuffer;
    protected RenderType renderType;
    public InteractionHand transformType;
    protected DiamondWuShiSwordItem animatable;
    private float aimProgress = 0.0f;
    private final Set<String> hiddenBones = new HashSet<>();
    private final Set<String> suppressedBones = new HashSet<>();
    private final Map<String, Vector3f> queuedBoneSetMovements = new HashMap<>();
    private final Map<String, Vector3f> queuedBoneSetRotations = new HashMap<>();
    private final Map<String, Vector3f> queuedBoneAddRotations = new HashMap<>();

    @Override
    public void renderByItem(ItemStack itemStack, ItemDisplayContext transformType, PoseStack matrixStack, MultiBufferSource bufferIn, int combinedLightIn, int packedOverlayIn) {
        super.renderByItem(itemStack, transformType, matrixStack, bufferIn, combinedLightIn, packedOverlayIn);
    }



    @Override
    public ResourceLocation getTextureLocation(DiamondWuShiSwordItem instance) {
        return super.getTextureLocation(instance);
    }

    public void hideBone(String name, boolean hide) {
        if (hide) {
            this.hiddenBones.add(name);
        } else {
            this.hiddenBones.remove(name);
        }
    }


    public InteractionHand getCurrentTransform() {
        return this.transformType;
    }

    public void suppressModification(String name) {
        this.suppressedBones.add(name);
    }

    public void allowModification(String name) {
        this.suppressedBones.remove(name);
    }

    public void setBonePosition(String name, float x, float y, float z) {
        this.queuedBoneSetMovements.put(name, new Vector3f(x, y, z));
    }

    public void addToBoneRotation(String name, float x, float y, float z) {
        this.queuedBoneAddRotations.put(name, new Vector3f(x, y, z));
    }

    public void setBoneRotation(String name, float x, float y, float z) {
        this.queuedBoneSetRotations.put(name, new Vector3f(x, y, z));
    }

    public ItemStack getCurrentItem() {
        return null;
    }

   
}