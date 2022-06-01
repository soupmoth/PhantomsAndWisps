package net.scord.wisps.world.biomes;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.scord.wisps.WispsMod;

public class WispsBiomes {
    public static final RegistryKey<Biome> ASTRAL_RIVER = register("astral_river");

    private static RegistryKey<Biome> register(String name)
    {
        return RegistryKey.of(Registry.BIOME_KEY, new Identifier(WispsMod.MOD_ID, name));
    }
}
