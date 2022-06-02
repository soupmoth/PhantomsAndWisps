package net.scord.wisps.entity.client.renderers;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.passive.GlowSquidEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.scord.wisps.WispsMod;
import net.scord.wisps.entity.client.models.WispModel;
import net.scord.wisps.entity.custom.WispEntity;
import software.bernie.example.entity.BikeEntity;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
Wisp renderer. Has some custom behaviour.
 */
public class WispRenderer extends GeoEntityRenderer<WispEntity> {
    public WispRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new WispModel());

    }

    @Override
    public Identifier getTextureLocation(WispEntity instance) {
        return new Identifier(WispsMod.MOD_ID, "textures/entity/wisp/wisp.png");
    }

    @Override
    public RenderLayer getRenderType(WispEntity animatable, float partialTicks, MatrixStack stack,
                                     VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder,
                                     int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }

    /*
    Red Shift when getting Angry
    Fade when Despawning.
     */
    @Override
    public Color getRenderColor(WispEntity animatable, float partialTicks, MatrixStack stack,
                                VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder,
                                int packedLightIn) {

        //shift to red as we get angy
        float redShift = ((float) animatable.getRedShift())/100f;

        float red = 1F;
        float green =  MathHelper.lerp(1-redShift, 0.25f, 1f);
        float blue = MathHelper.lerp(1-redShift, 0.25f, 1f);
        float alpha = MathHelper.lerp(animatable.getFadeTime()/100f, 1f, 0f);

        return Color.ofRGBA(red, green, blue, alpha);
    }

    @Override
    protected int getBlockLight(WispEntity wispEntity, BlockPos blockPos) {
        return 10;
    }

}
