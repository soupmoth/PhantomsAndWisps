package net.scord.wisps.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.Model;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.MathHelper;
import net.scord.wisps.WispsMod;
import net.scord.wisps.effect.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;


@Mixin(LivingEntityRenderer.class)
public class PhantomRendererMixin  {


    @ModifyArg(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/EntityModel;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;IIFFFF)V"), index = 7)
    public float translucency(float par5) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();


        ClientPlayerEntity player = minecraftClient.player;

        int timeSinceLastRest = MathHelper.clamp(player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST)), 1, Integer.MAX_VALUE);
        if (player.hasStatusEffect(ModEffects.HUNT) || timeSinceLastRest > 3 * 24000) {
            float n = 0.2f;
            if (timeSinceLastRest > 3 * 24000) {
                n += 0.2f + (timeSinceLastRest-3*24000)/Math.max(1, timeSinceLastRest);
            }
            if (player.hasStatusEffect(ModEffects.HUNT)) {
                n += 0.3f + 0.05f * player.getStatusEffect(ModEffects.HUNT).getAmplifier();
            }

            return Math.min(n, 1f);
        }



        return par5;
    }
}
