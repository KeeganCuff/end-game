package net.keegancuff.endgame.world.feature;

import net.fabricmc.fabric.api.biome.v1.*;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.keegancuff.endgame.EndGame;
import net.keegancuff.endgame.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;
import org.apache.logging.log4j.util.BiConsumer;

import java.util.Arrays;

public class ModOreConfiguredFeatures {


    public static void registerConfiguredFeatures() {
        EndGame.LOGGER.debug("Registering the ModConfiguredFeatures for " + EndGame.MOD_ID);
    }
}
