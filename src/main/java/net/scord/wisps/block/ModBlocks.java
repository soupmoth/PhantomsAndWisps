package net.scord.wisps.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.scord.wisps.WispsMod;

public class ModBlocks {

    public final static int ASTRAL_RIVER_BRIGHTNESS = 2;

    public static final Block GLOWING_WHITE_CONCRETE = registerBlock("glowing_white_concrete", new Block(AbstractBlock.Settings.of(Material.STONE, DyeColor.WHITE).requiresTool().strength(1.8f).luminance(state -> 6)), ItemGroup.BUILDING_BLOCKS);
    public static final Block GLOWING_ORANGE_CONCRETE = registerBlock("glowing_orange_concrete", new Block(AbstractBlock.Settings.of(Material.STONE, DyeColor.ORANGE).requiresTool().strength(1.8f).luminance(state -> 6)), ItemGroup.BUILDING_BLOCKS);
    public static final Block GLOWING_MAGENTA_CONCRETE = registerBlock("glowing_magenta_concrete", new Block(AbstractBlock.Settings.of(Material.STONE, DyeColor.MAGENTA).requiresTool().strength(1.8f).luminance(state -> 6)), ItemGroup.BUILDING_BLOCKS);
    public static final Block GLOWING_LIGHT_BLUE_CONCRETE = registerBlock("glowing_light_blue_concrete", new Block(AbstractBlock.Settings.of(Material.STONE, DyeColor.LIGHT_BLUE).requiresTool().strength(1.8f).luminance(state -> 6)), ItemGroup.BUILDING_BLOCKS);
    public static final Block GLOWING_YELLOW_CONCRETE = registerBlock("glowing_yellow_concrete", new Block(AbstractBlock.Settings.of(Material.STONE, DyeColor.YELLOW).requiresTool().strength(1.8f).luminance(state -> 6)), ItemGroup.BUILDING_BLOCKS);
    public static final Block GLOWING_LIME_CONCRETE = registerBlock("glowing_lime_concrete", new Block(AbstractBlock.Settings.of(Material.STONE, DyeColor.LIME).requiresTool().strength(1.8f).luminance(state -> 6)), ItemGroup.BUILDING_BLOCKS);
    public static final Block GLOWING_PINK_CONCRETE = registerBlock("glowing_pink_concrete", new Block(AbstractBlock.Settings.of(Material.STONE, DyeColor.PINK).requiresTool().strength(1.8f).luminance(state -> 6)), ItemGroup.BUILDING_BLOCKS);
    public static final Block GLOWING_GRAY_CONCRETE = registerBlock("glowing_gray_concrete", new Block(AbstractBlock.Settings.of(Material.STONE, DyeColor.GRAY).requiresTool().strength(1.8f).luminance(state -> 6)), ItemGroup.BUILDING_BLOCKS);
    public static final Block GLOWING_LIGHT_GRAY_CONCRETE = registerBlock("glowing_light_gray_concrete", new Block(AbstractBlock.Settings.of(Material.STONE, DyeColor.LIGHT_GRAY).requiresTool().strength(1.8f).luminance(state -> 6)), ItemGroup.BUILDING_BLOCKS);
    public static final Block GLOWING_CYAN_CONCRETE = registerBlock("glowing_cyan_concrete", new Block(AbstractBlock.Settings.of(Material.STONE, DyeColor.CYAN).requiresTool().strength(1.8f).luminance(state -> 6)), ItemGroup.BUILDING_BLOCKS);
    public static final Block GLOWING_PURPLE_CONCRETE = registerBlock("glowing_purple_concrete", new Block(AbstractBlock.Settings.of(Material.STONE, DyeColor.PURPLE).requiresTool().strength(1.8f).luminance(state -> 6)), ItemGroup.BUILDING_BLOCKS);
    public static final Block GLOWING_BLUE_CONCRETE = registerBlock("glowing_blue_concrete", new Block(AbstractBlock.Settings.of(Material.STONE, DyeColor.BLUE).requiresTool().strength(1.8f).luminance(state -> 6)), ItemGroup.BUILDING_BLOCKS);
    public static final Block GLOWING_BROWN_CONCRETE = registerBlock("glowing_brown_concrete", new Block(AbstractBlock.Settings.of(Material.STONE, DyeColor.BROWN).requiresTool().strength(1.8f).luminance(state -> 6)), ItemGroup.BUILDING_BLOCKS);
    public static final Block GLOWING_GREEN_CONCRETE = registerBlock("glowing_green_concrete", new Block(AbstractBlock.Settings.of(Material.STONE, DyeColor.GREEN).requiresTool().strength(1.8f).luminance(state -> 6)), ItemGroup.BUILDING_BLOCKS);
    public static final Block GLOWING_RED_CONCRETE = registerBlock("glowing_red_concrete", new Block(AbstractBlock.Settings.of(Material.STONE, DyeColor.RED).requiresTool().strength(1.8f).luminance(state -> 6)), ItemGroup.BUILDING_BLOCKS);
    public static final Block GLOWING_BLACK_CONCRETE = registerBlock("glowing_black_concrete", new Block(AbstractBlock.Settings.of(Material.STONE, DyeColor.BLACK).requiresTool().strength(1.8f).luminance(state -> 6)), ItemGroup.BUILDING_BLOCKS);
    public static final Block GLOWING_WHITE_CONCRETE_POWDER = registerBlock("glowing_white_concrete_powder", new ConcretePowderBlock(GLOWING_WHITE_CONCRETE, AbstractBlock.Settings.of(Material.AGGREGATE, DyeColor.WHITE).strength(0.5f).sounds(BlockSoundGroup.SAND).luminance(state -> 6)), ItemGroup.BUILDING_BLOCKS);
    public static final Block GLOWING_ORANGE_CONCRETE_POWDER = registerBlock("glowing_orange_concrete_powder", new ConcretePowderBlock(GLOWING_ORANGE_CONCRETE, AbstractBlock.Settings.of(Material.AGGREGATE, DyeColor.ORANGE).strength(0.5f).sounds(BlockSoundGroup.SAND).luminance(state -> 6)), ItemGroup.BUILDING_BLOCKS);
    public static final Block GLOWING_MAGENTA_CONCRETE_POWDER = registerBlock("glowing_magenta_concrete_powder", new ConcretePowderBlock(GLOWING_MAGENTA_CONCRETE, AbstractBlock.Settings.of(Material.AGGREGATE, DyeColor.MAGENTA).strength(0.5f).sounds(BlockSoundGroup.SAND).luminance(state -> 6)), ItemGroup.BUILDING_BLOCKS);
    public static final Block GLOWING_LIGHT_BLUE_CONCRETE_POWDER = registerBlock("glowing_light_blue_concrete_powder", new ConcretePowderBlock(GLOWING_LIGHT_BLUE_CONCRETE, AbstractBlock.Settings.of(Material.AGGREGATE, DyeColor.LIGHT_BLUE).strength(0.5f).sounds(BlockSoundGroup.SAND).luminance(state -> 6)), ItemGroup.BUILDING_BLOCKS);
    public static final Block GLOWING_YELLOW_CONCRETE_POWDER = registerBlock("glowing_yellow_concrete_powder", new ConcretePowderBlock(GLOWING_YELLOW_CONCRETE, AbstractBlock.Settings.of(Material.AGGREGATE, DyeColor.YELLOW).strength(0.5f).sounds(BlockSoundGroup.SAND).luminance(state -> 6)), ItemGroup.BUILDING_BLOCKS);
    public static final Block GLOWING_LIME_CONCRETE_POWDER = registerBlock("glowing_lime_concrete_powder", new ConcretePowderBlock(GLOWING_LIME_CONCRETE, AbstractBlock.Settings.of(Material.AGGREGATE, DyeColor.LIME).strength(0.5f).sounds(BlockSoundGroup.SAND).luminance(state -> 6)), ItemGroup.BUILDING_BLOCKS);
    public static final Block GLOWING_PINK_CONCRETE_POWDER = registerBlock("glowing_pink_concrete_powder", new ConcretePowderBlock(GLOWING_PINK_CONCRETE, AbstractBlock.Settings.of(Material.AGGREGATE, DyeColor.PINK).strength(0.5f).sounds(BlockSoundGroup.SAND).luminance(state -> 6)), ItemGroup.BUILDING_BLOCKS);
    public static final Block GLOWING_GRAY_CONCRETE_POWDER = registerBlock("glowing_gray_concrete_powder", new ConcretePowderBlock(GLOWING_GRAY_CONCRETE, AbstractBlock.Settings.of(Material.AGGREGATE, DyeColor.GRAY).strength(0.5f).sounds(BlockSoundGroup.SAND).luminance(state -> 6)), ItemGroup.BUILDING_BLOCKS);
    public static final Block GLOWING_LIGHT_GRAY_CONCRETE_POWDER = registerBlock("glowing_light_gray_concrete_powder", new ConcretePowderBlock(GLOWING_LIGHT_GRAY_CONCRETE, AbstractBlock.Settings.of(Material.AGGREGATE, DyeColor.LIGHT_GRAY).strength(0.5f).sounds(BlockSoundGroup.SAND).luminance(state -> 6)), ItemGroup.BUILDING_BLOCKS);
    public static final Block GLOWING_CYAN_CONCRETE_POWDER = registerBlock("glowing_cyan_concrete_powder", new ConcretePowderBlock(GLOWING_CYAN_CONCRETE, AbstractBlock.Settings.of(Material.AGGREGATE, DyeColor.CYAN).strength(0.5f).sounds(BlockSoundGroup.SAND).luminance(state -> 6)), ItemGroup.BUILDING_BLOCKS);
    public static final Block GLOWING_PURPLE_CONCRETE_POWDER = registerBlock("glowing_purple_concrete_powder", new ConcretePowderBlock(GLOWING_PURPLE_CONCRETE, AbstractBlock.Settings.of(Material.AGGREGATE, DyeColor.PURPLE).strength(0.5f).sounds(BlockSoundGroup.SAND).luminance(state -> 6)), ItemGroup.BUILDING_BLOCKS);
    public static final Block GLOWING_BLUE_CONCRETE_POWDER = registerBlock("glowing_blue_concrete_powder", new ConcretePowderBlock(GLOWING_BLUE_CONCRETE, AbstractBlock.Settings.of(Material.AGGREGATE, DyeColor.BLUE).strength(0.5f).sounds(BlockSoundGroup.SAND).luminance(state -> 6)), ItemGroup.BUILDING_BLOCKS);
    public static final Block GLOWING_BROWN_CONCRETE_POWDER = registerBlock("glowing_brown_concrete_powder", new ConcretePowderBlock(GLOWING_BROWN_CONCRETE, AbstractBlock.Settings.of(Material.AGGREGATE, DyeColor.BROWN).strength(0.5f).sounds(BlockSoundGroup.SAND).luminance(state -> 6)), ItemGroup.BUILDING_BLOCKS);
    public static final Block GLOWING_GREEN_CONCRETE_POWDER = registerBlock("glowing_green_concrete_powder", new ConcretePowderBlock(GLOWING_GREEN_CONCRETE, AbstractBlock.Settings.of(Material.AGGREGATE, DyeColor.GREEN).strength(0.5f).sounds(BlockSoundGroup.SAND).luminance(state -> 6)), ItemGroup.BUILDING_BLOCKS);
    public static final Block GLOWING_RED_CONCRETE_POWDER = registerBlock("glowing_red_concrete_powder", new ConcretePowderBlock(GLOWING_RED_CONCRETE, AbstractBlock.Settings.of(Material.AGGREGATE, DyeColor.RED).strength(0.5f).sounds(BlockSoundGroup.SAND).luminance(state -> 6)), ItemGroup.BUILDING_BLOCKS);
    public static final Block GLOWING_BLACK_CONCRETE_POWDER = registerBlock("glowing_black_concrete_powder", new ConcretePowderBlock(GLOWING_BLACK_CONCRETE, AbstractBlock.Settings.of(Material.AGGREGATE, DyeColor.BLACK).strength(0.5f).sounds(BlockSoundGroup.SAND).luminance(state -> 6)), ItemGroup.BUILDING_BLOCKS);

    public static final Block STARBLE = registerBlock("starble", new Block(AbstractBlock.Settings.of(Material.STONE).requiresTool().strength(1.5f, 6f).sounds(BlockSoundGroup.CALCITE).luminance(state -> ASTRAL_RIVER_BRIGHTNESS)), ItemGroup.BUILDING_BLOCKS);
    public static final Block SMOOTH_STARBLE = registerBlock("smooth_starble", new Block(AbstractBlock.Settings.of(Material.STONE).requiresTool().strength(1.5f, 6f).sounds(BlockSoundGroup.CALCITE).luminance(state -> ASTRAL_RIVER_BRIGHTNESS)), ItemGroup.BUILDING_BLOCKS);
    public static final Block STARBLE_BRICKS = registerBlock("starble_bricks", new Block(AbstractBlock.Settings.of(Material.STONE).requiresTool().strength(1.5f, 6f).sounds(BlockSoundGroup.CALCITE).luminance(state -> ASTRAL_RIVER_BRIGHTNESS)), ItemGroup.BUILDING_BLOCKS);
    public static final Block CRACKED_STARBLE_BRICKS = registerBlock("cracked_starble_bricks", new Block(AbstractBlock.Settings.of(Material.STONE).requiresTool().strength(1.5f, 6f).sounds(BlockSoundGroup.CALCITE).luminance(state -> ASTRAL_RIVER_BRIGHTNESS)), ItemGroup.BUILDING_BLOCKS);
    public static final Block MOSSY_STARBLE_BRICKS = registerBlock("mossy_starble_bricks", new Block(AbstractBlock.Settings.of(Material.STONE).requiresTool().strength(1.5f, 6f).sounds(BlockSoundGroup.CALCITE).luminance(state -> ASTRAL_RIVER_BRIGHTNESS)), ItemGroup.BUILDING_BLOCKS);
    public static final Block CHISELED_STARBLE = registerBlock("chiseled_starble", new Block(AbstractBlock.Settings.of(Material.STONE).requiresTool().strength(1.5f, 6f).sounds(BlockSoundGroup.CALCITE).luminance(state -> 6)), ItemGroup.BUILDING_BLOCKS);

    public static final Block MOONSLATE = registerBlock("moonslate", new Block(AbstractBlock.Settings.of(Material.STONE).requiresTool().strength(3.5f, 4f).sounds(BlockSoundGroup.BONE).luminance(state -> ASTRAL_RIVER_BRIGHTNESS)), ItemGroup.BUILDING_BLOCKS);

    public static final Block NOVA_LICHEN = registerBlock("nova_lichen", new HangingRootsBlock(AbstractBlock.Settings.of(Material.PLANT).requiresTool().strength(0.2f).sounds(BlockSoundGroup.WEEPING_VINES).luminance(state -> 6).dynamicBounds().nonOpaque()), ItemGroup.DECORATIONS);
    public static final Block NEBULA_MOSS = registerBlock("nebula_moss", new NebulaMossBlock(AbstractBlock.Settings.of(Material.PLANT).requiresTool().strength(1.5f).sounds(BlockSoundGroup.SLIME).luminance(state -> 3)), ItemGroup.DECORATIONS);




    private static  Block registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(WispsMod.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup group) {
        return Registry.register(Registry.ITEM, new Identifier(WispsMod.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(group)));
    }

    public static void registerModBlocks() {
        WispsMod.LOGGER.info("Registering ModBlocks for " + WispsMod.MOD_ID);
    }

}
