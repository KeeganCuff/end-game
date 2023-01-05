package net.keegancuff.endgame.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.keegancuff.endgame.EndGame;
import net.keegancuff.endgame.block.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroup {
    public static final ItemGroup ENDGAME = FabricItemGroup.builder(new Identifier(EndGame.MOD_ID, "endgame"))
            .displayName(Text.literal("End Game")).icon(() -> new ItemStack(ModItems.ENDERIUM_INGOT))
            .entries(((enabledFeatures, entries, operatorEnabled) -> {
                entries.add(ModBlocks.PHASE_SHIFTER);
                entries.add(ModBlocks.CREATIVE_ARTIFICER_STATION);
                entries.add(ModBlocks.ENDERIUM_ORE);
                entries.add(ModBlocks.ENDERIUM_BLOCK);
                entries.add(ModItems.ENDERIUM_INGOT);
                entries.add(ModItems.RAW_ENDERIUM);
                entries.add(ModItems.ENDERIUM_SWORD);
                entries.add(ModItems.ENDERIUM_PICKAXE);
                entries.add(ModItems.ENDERIUM_SHOVEL);
                entries.add(ModItems.ENDERIUM_AXE);
                entries.add(ModItems.ENDERIUM_HOE);
                entries.add(ModItems.ENDERIUM_HELMET);
                entries.add(ModItems.ENDERIUM_CHESTPLATE);
                entries.add(ModItems.ENDERIUM_LEGGINGS);
                entries.add(ModItems.ENDERIUM_BOOTS);
                entries.add(ModBlocks.VARIANT_METAL_ORE);
                entries.add(ModItems.VARIANT_METAL_INGOT);
                entries.add(ModItems.RAW_VARIANT_METAL);
            })).build();

    public static void registerModGroups(){
        EndGame.LOGGER.debug("Registering ModGroups for " + EndGame.MOD_ID);
    }


}
