package net.keegancuff.endgame.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.keegancuff.endgame.EndGame;
import net.keegancuff.endgame.block.custom.PhaseShifterBlock;
import net.keegancuff.endgame.block.custom.VariantMetalBlock;
import net.keegancuff.endgame.item.custom.PhaseShifterBlockItem;
import net.keegancuff.endgame.item.custom.VariantMetalBlockItem;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class ModBlocks {

    //Registering Blocks
    public static final Block ENDERIUM_ORE = registerBlock("enderium_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.of(Material.STONE).strength(4f).requiresTool(),
                    UniformIntProvider.create(5, 10)));
    public static final Block ENDERIUM_BLOCK = registerBlock("enderium_block",
            new Block(FabricBlockSettings.of(Material.METAL).strength(4f).requiresTool()));

    public static final Block VARIANT_METAL_ORE = registerBlock("variant_metal_ore",
            new VariantMetalBlock(FabricBlockSettings.of(Material.STONE).strength(4f).requiresTool()));
    public static final Block VARIANT_METAL_BLOCK= registerBlock("variant_metal_block",
            new VariantMetalBlock(FabricBlockSettings.of(Material.METAL).strength(4f).requiresTool()));

    public static final Block PHASE_SHIFTER = registerBlock("phase_shifter",
            new PhaseShifterBlock(FabricBlockSettings.of(Material.METAL).strength(8f).requiresTool()));

    //Registering Block Items
    //This is the same for items with no special properties.
    public static final Item ENDERIUM_ORE_ITEM = registerBlockItem("enderium_ore", ENDERIUM_ORE);
    public static final Item ENDERIUM_BLOCK_ITEM = registerBlockItem("enderium_block", ENDERIUM_BLOCK);
    public static final Item VARIANT_METAL_ORE_ITEM = Registry.register(Registries.ITEM, new Identifier(EndGame.MOD_ID, "variant_metal_ore"),
            new VariantMetalBlockItem(VARIANT_METAL_ORE, new FabricItemSettings().rarity(Rarity.RARE)));
    public static final Item VARIANT_METAL_BLOCK_ITEM = Registry.register(Registries.ITEM, new Identifier(EndGame.MOD_ID, "variant_metal_block"),
            new VariantMetalBlockItem(VARIANT_METAL_BLOCK, new FabricItemSettings().rarity(Rarity.RARE)));
    public static final Item PHASE_SHIFTER_ITEM = Registry.register(Registries.ITEM, new Identifier(EndGame.MOD_ID, "phase_shifter"),
            new PhaseShifterBlockItem(PHASE_SHIFTER, new FabricItemSettings().rarity(Rarity.RARE)));


    private static Block registerBlock(String name, Block block){
        return Registry.register(Registries.BLOCK, new Identifier(EndGame.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block){
        return Registry.register(Registries.ITEM, new Identifier(EndGame.MOD_ID, name), new BlockItem(block, new FabricItemSettings().rarity(Rarity.RARE)));
    }

    public static void registerModBlocks() {

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> entries.add(ENDERIUM_ORE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> entries.add(VARIANT_METAL_ORE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ENDERIUM_BLOCK));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> entries.add(PHASE_SHIFTER));
        EndGame.LOGGER.debug("Registering ModBlocks for " + EndGame.MOD_ID);
    }
}
