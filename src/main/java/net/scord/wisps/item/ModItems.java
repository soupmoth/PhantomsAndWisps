package net.scord.wisps.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import net.scord.wisps.WispsMod;
import net.scord.wisps.entity.ModEntities;
import net.scord.wisps.item.custom.*;


public class ModItems {

    public static final Item PHOSPHOR = registerItem("phosphor", new Item(new FabricItemSettings().group(ItemGroup.BREWING)));

    public static final Item WISPSPAWNEGG = registerItem("wisp_spawn_egg", new SpawnEggItem(ModEntities.WISP,
            0x7986cb, 0x673ab7, new FabricItemSettings().group(ItemGroup.MISC)));

    public static final Item MOONSTONE = registerItem("moonstone", new Item(new FabricItemSettings().group(ItemGroup.MISC)));

    public static final Item MOONLIGHT_SWORD = registerItem("moonlight_sword", new ModMoonlightSwordItem(ModItemsMaterials.MOONLIGHT,
            3, -2.4f,  new FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.RARE)));

    public static final Item MOONLIGHT_AXE = registerItem("moonlight_axe", new ModMoonlightAxeItem(ModItemsMaterials.MOONLIGHT,
            6f, -3f,  new FabricItemSettings().group(ItemGroup.TOOLS).rarity(Rarity.RARE)));

    public static final Item MOONLIGHT_PICKAXE = registerItem("moonlight_pickaxe", new ModMoonlightPickaxeItem(ModItemsMaterials.MOONLIGHT,
            1, -2.8f,  new FabricItemSettings().group(ItemGroup.TOOLS).rarity(Rarity.RARE)));

    public static final Item MOONLIGHT_SHOVEL = registerItem("moonlight_shovel", new ModMoonlightShovelItem(ModItemsMaterials.MOONLIGHT,
            1.5f, -3f,  new FabricItemSettings().group(ItemGroup.TOOLS).rarity(Rarity.RARE)));

    public static final Item MOONLIGHT_HOE = registerItem("moonlight_hoe", new ModMoonlightHoeItem(ModItemsMaterials.MOONLIGHT,
            0, -3.2f,  new FabricItemSettings().group(ItemGroup.TOOLS).rarity(Rarity.RARE)));



    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(WispsMod.MOD_ID, name), item);

    }


    public static void registerModItems() {
        WispsMod.LOGGER.info("Registering Items for " + WispsMod.MOD_ID);
    }


}
