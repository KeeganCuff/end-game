package net.keegancuff.endgame.world.feature;

import net.keegancuff.endgame.EndGame;
import net.keegancuff.endgame.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.registry.*;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;

import java.util.List;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?,?>> ORE_ENDERIUM = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE,
            new Identifier(EndGame.MOD_ID, "ore_enderium"));
    public static final RegistryKey<ConfiguredFeature<?,?>> ORE_VARIANT_METAL = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE,
            new Identifier(EndGame.MOD_ID, "ore_variant_metal"));




    public static void registerConfiguredFeatures() {

        EndGame.LOGGER.debug("Registering the ModConfiguredFeatures for " + EndGame.MOD_ID);
    }
}
