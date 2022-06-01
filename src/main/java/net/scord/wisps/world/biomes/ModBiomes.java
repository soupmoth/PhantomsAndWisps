package net.scord.wisps.world.biomes;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.scord.wisps.world.feature.ModPlacedFeatures;
import terrablender.api.TerraBlenderApi;

public class ModBiomes implements TerraBlenderApi {

    public static void registerBiomes()
    {
        register(WispsBiomes.ASTRAL_RIVER, WispsOverworldBiomes.astralRiver());
    }

    public static void registerBiomeModifications() {
        //BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.BEACH), GenerationStep.Feature.UNDERGROUND_DECORATION,
                //ModPlacedFeatures.STARBLE_PLACED.getKey().get());
    }

    private static void register(RegistryKey<Biome> key, Biome biome)
    {
        BuiltinRegistries.add(BuiltinRegistries.BIOME, key, biome);
    }

}
