package net.scord.wisps.mixin;

import com.mojang.datafixers.util.Pair;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.biome.source.util.VanillaBiomeParameters;
import net.scord.wisps.world.biomes.WispsBiomes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(VanillaBiomeParameters.class)
public class BiomeParametersMixin {

    private final MultiNoiseUtil.ParameterRange defaultParameter = MultiNoiseUtil.ParameterRange.of(-1.0F, 1.0F);

    @Inject(method="writeCaveBiomes", at = @At("TAIL"))
    private void writeCaveBiomes(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, CallbackInfo ci) {
        this.writeCaveBiomeParameters(parameters,
                this.defaultParameter, //temperature
                this.defaultParameter, //humidity
                MultiNoiseUtil.ParameterRange.of(-1.2F, 0.55F), //continentialness
                this.defaultParameter, //erosion
                this.defaultParameter, //weirdness
                0.0F,
                WispsBiomes.ASTRAL_RIVER);
    }
    private void writeCaveBiomeParameters(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, MultiNoiseUtil.ParameterRange temperature, MultiNoiseUtil.ParameterRange humidity, MultiNoiseUtil.ParameterRange continentalness, MultiNoiseUtil.ParameterRange erosion, MultiNoiseUtil.ParameterRange weirdness, float offset, RegistryKey<Biome> biome) {
        parameters.accept(Pair.of(MultiNoiseUtil.createNoiseHypercube(temperature, humidity, continentalness, erosion, MultiNoiseUtil.ParameterRange.of(1f), weirdness, offset), biome));
    }

}
