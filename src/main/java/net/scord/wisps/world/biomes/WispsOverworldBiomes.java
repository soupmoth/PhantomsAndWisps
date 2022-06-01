package net.scord.wisps.world.biomes;

import net.minecraft.client.sound.MusicType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.NetherPlacedFeatures;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.scord.wisps.world.feature.ModPlacedFeatures;
import net.scord.wisps.world.feature.WispsConfiguredFeatures;

public class WispsOverworldBiomes {

    protected static int calculateSkyColor(float color) {
        float $$1 = color / 3.0F;
        $$1 = MathHelper.clamp($$1, -1.0F, 1.0F);
        return MathHelper.hsvToRgb(0.62222224F - $$1 * 0.05F, 0.5F + $$1 * 0.1F, 1.0F);
    }

    private static void addBasicFeatures(GenerationSettings.Builder builder) {
        DefaultBiomeFeatures.addLandCarvers(builder);
        DefaultBiomeFeatures.addAmethystGeodes(builder);
        DefaultBiomeFeatures.addDungeons(builder);
        DefaultBiomeFeatures.addMineables(builder);
        DefaultBiomeFeatures.addSprings(builder);
        DefaultBiomeFeatures.addFrozenTopLayer(builder);
    }


    public static Biome astralRiver() {
        SpawnSettings.Builder spawnBuilder = new SpawnSettings.Builder();

        DefaultBiomeFeatures.addCaveMobs(spawnBuilder);

        GenerationSettings.Builder generationBuilder = new GenerationSettings.Builder();
        addBasicFeatures(generationBuilder);
        DefaultBiomeFeatures.addFrozenLavaSpring(generationBuilder);
        DefaultBiomeFeatures.addDefaultOres(generationBuilder);
        DefaultBiomeFeatures.addDefaultDisks(generationBuilder);
        DefaultBiomeFeatures.addEmeraldOre(generationBuilder);
        DefaultBiomeFeatures.addInfestedStone(generationBuilder);
        DefaultBiomeFeatures.addPlainsFeatures(generationBuilder);
        DefaultBiomeFeatures.addDefaultMushrooms(generationBuilder);
        DefaultBiomeFeatures.addDefaultVegetation(generationBuilder);

        generationBuilder.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ModPlacedFeatures.STARBLE_PLACED);
        generationBuilder.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ModPlacedFeatures.MOONSLATE_PLACED);
        generationBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.ASTRAL_RIVER_CEILING_FOILAGE);



        return (new Biome.Builder())
                .precipitation(Biome.Precipitation.SNOW)
                .category(Biome.Category.UNDERGROUND)
                .temperature(0.2F)
                .downfall(1F)
                .effects((new BiomeEffects.Builder())
                        .music(MusicType.GAME)
                        .fogColor(0)
                        .skyColor(0)
                        .waterColor(0)
                        .waterFogColor(0)
                        .build())
                .spawnSettings(spawnBuilder.build())
                .generationSettings(generationBuilder.build())
                .build();
    }
}
