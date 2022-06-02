package net.scord.wisps.mixin;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;
import net.scord.wisps.effect.ModEffects;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
abstract class HuntRenderChangesMixin {
    @Shadow
    @Final
    private MinecraftClient client;
    @Shadow
    private ClientWorld world;
    @Shadow
    private VertexBuffer lightSkyBuffer;
    @Shadow
    private VertexBuffer darkSkyBuffer;
    @Shadow
    private VertexBuffer starsBuffer;
    @Final
    private static final Identifier MOON_PHASES = new Identifier("textures/environment/moon_phases.png");

    /**This class changes the rendering of the sky based on if the player has Hunt.
     * The Hunt effect changes the Sky Texture
     *
     */

    //TODO Red Moon

    @Inject(method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/math/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld;getSkyColor(Lnet/minecraft/util/math/Vec3d;F)Lnet/minecraft/util/math/Vec3d;"), cancellable = true)
    private void RenderHuntSky(MatrixStack matrices, Matrix4f projectionMatrix, float tickDelta, Camera camera, boolean bl, Runnable runnable, CallbackInfo ci) {


        if (this.client.world.getDimensionEffects().getSkyType() == DimensionEffects.SkyType.NORMAL) {

            Entity ent;

            if ((ent = camera.getFocusedEntity()) instanceof LivingEntity && ((LivingEntity)ent).hasStatusEffect(ModEffects.HUNT)) {
                float q;
                float p;
                float o;
                int m;
                float k;
                float i;

                final float RED_MIN = 0.1f;
                final float RED_MAX = 0.3f;
                final float BLUE_MIN = 0.025f;
                final float BLUE_MAX = 0.05f;


                LivingEntity livingEntity = ((LivingEntity)ent);
                Entity entity;

                StatusEffectInstance hunt = livingEntity.getStatusEffect(ModEffects.HUNT);



                float f = (RED_MIN + (((RED_MAX-RED_MIN)/10)  * (1 + hunt.getAmplifier()))) * Math.min(1f, hunt.getDuration() / 20f);
                float g = 0.0f;
                float h = (BLUE_MIN + (((BLUE_MAX-BLUE_MIN)/10)  * (1 + hunt.getAmplifier()))) * Math.min(1f, hunt.getDuration() / 20f);
                BackgroundRenderer.setFogBlack();
                BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
                RenderSystem.depthMask(false);
                RenderSystem.setShaderColor(f, g, h, 1.0f);
                Shader shader = RenderSystem.getShader();
                this.lightSkyBuffer.setShader(matrices.peek().getPositionMatrix(), projectionMatrix, shader);
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                float[] fs = this.world.getDimensionEffects().getFogColorOverride(this.world.getSkyAngle(tickDelta), tickDelta);
                if (fs != null) {
                    RenderSystem.setShader(GameRenderer::getPositionColorShader);
                    RenderSystem.disableTexture();
                    RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                    matrices.push();
                    matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90.0f));
                    i = MathHelper.sin(this.world.getSkyAngleRadians(tickDelta)) < 0.0f ? 180.0f : 0.0f;
                    matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(i));
                    matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90.0f));
                    float j = fs[0];
                    k = fs[1];
                    float l = fs[2];
                    Matrix4f matrix4f = matrices.peek().getPositionMatrix();
                    bufferBuilder.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
                    bufferBuilder.vertex(matrix4f, 0.0f, 100.0f, 0.0f).color(j, k, l, fs[3]).next();
                    m = 16;
                    for (int n = 0; n <= 16; ++n) {
                        o = (float)n * ((float)Math.PI * 2) / 16.0f;
                        p = MathHelper.sin(o);
                        q = MathHelper.cos(o);
                        bufferBuilder.vertex(matrix4f, p * 120.0f, q * 120.0f, -q * 40.0f * fs[3]).color(fs[0], fs[1], fs[2], 0.0f).next();
                    }
                    bufferBuilder.end();
                    BufferRenderer.draw(bufferBuilder);
                    matrices.pop();
                }
                RenderSystem.enableTexture();
                RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
                matrices.push();
                i = 1.0f - this.world.getRainGradient(tickDelta);
                RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, i);
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-90.0f));
                matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(this.world.getSkyAngle(tickDelta) * 360.0f));
                Matrix4f matrix4f2 = matrices.peek().getPositionMatrix();

                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                k = 20.0f;
                RenderSystem.setShaderTexture(0, MOON_PHASES);
                int r = this.world.getMoonPhase();
                int s = r % 4;
                m = r / 4 % 2;
                float t = (float)(s + 0) / 4.0f;
                o = (float)(m + 0) / 2.0f;
                p = (float)(s + 1) / 4.0f;
                q = (float)(m + 1) / 2.0f;
                bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
                bufferBuilder.vertex(matrix4f2, -k, -100.0f, k).texture(p, q).next();
                bufferBuilder.vertex(matrix4f2, k, -100.0f, k).texture(t, q).next();
                bufferBuilder.vertex(matrix4f2, k, -100.0f, -k).texture(t, o).next();
                bufferBuilder.vertex(matrix4f2, -k, -100.0f, -k).texture(p, o).next();
                bufferBuilder.end();
                BufferRenderer.draw(bufferBuilder);
                RenderSystem.disableTexture();
                float u = this.world.method_23787(tickDelta) * i;
                if (u > 0.0f) {
                    RenderSystem.setShaderColor(u, u, u, u);
                    BackgroundRenderer.clearFog();
                    this.starsBuffer.setShader(matrices.peek().getPositionMatrix(), projectionMatrix, GameRenderer.getPositionShader());
                    runnable.run();
                }
                RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                RenderSystem.disableBlend();
                matrices.pop();
                RenderSystem.disableTexture();
                RenderSystem.setShaderColor(0.0f, 0.0f, 0.0f, 1.0f);
                double d = this.client.player.getCameraPosVec((float)tickDelta).y - this.world.getLevelProperties().getSkyDarknessHeight(this.world);
                if (d < 0.0) {
                    matrices.push();
                    matrices.translate(0.0, 12.0, 0.0);
                    this.darkSkyBuffer.setShader(matrices.peek().getPositionMatrix(), projectionMatrix, shader);
                    matrices.pop();
                }
                if (this.world.getDimensionEffects().isAlternateSkyColor()) {
                    RenderSystem.setShaderColor(f * 0.2f + 0.04f, g * 0.2f + 0.04f, h * 0.6f + 0.1f, 1.0f);
                } else {
                    RenderSystem.setShaderColor(f, g, h, 1.0f);
                }
                RenderSystem.enableTexture();
                RenderSystem.depthMask(true);

                ci.cancel();
            }

        }
    }

}
