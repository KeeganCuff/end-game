package net.keegancuff.endgame.block;

import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.keegancuff.endgame.EndGame;
import net.minecraft.block.Block;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class ModBlocks {

    public static final Block ENDERIUM_ORE = registerBlock("enderium_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.of(Material.STONE).strength(4f).requiresTool(),
                    UniformIntProvider.create(5, 10)));

    public static final Block ENDERIUM_BLOCK = registerBlock("enderium_block",
            new Block(FabricBlockSettings.of(Material.METAL).strength(4f).requiresTool()));


    private static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(EndGame.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block){
        return Registry.register(Registries.ITEM, new Identifier(EndGame.MOD_ID, name), new BlockItem(block, new FabricItemSettings().rarity(Rarity.RARE)));
    }


    public static void registerModBlocks() {
        EndGame.LOGGER.debug("Registering ModBlocks for " + EndGame.MOD_ID);
    }
}
