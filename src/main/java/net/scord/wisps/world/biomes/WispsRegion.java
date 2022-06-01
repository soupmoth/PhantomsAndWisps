package net.scord.wisps.world.biomes;

import com.eliotlash.mclib.math.functions.classic.Mod;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import static terrablender.api.ParameterUtils.*;

import terrablender.api.ParameterUtils;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.List;
import java.util.function.Consumer;

public class WispsRegion extends Region {
    public WispsRegion(Identifier name, int weight)
    {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> mapper)
    {

        this.addModifiedVanillaOverworldBiomes(mapper, builder -> {
            builder.replaceBiome(WispsBiomes.ASTRAL_RIVER, WispsBiomes.ASTRAL_RIVER);
        });



    }
}
