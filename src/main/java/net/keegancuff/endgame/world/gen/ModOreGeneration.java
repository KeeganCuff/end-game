package net.keegancuff.endgame.world.gen;

import net.fabricmc.fabric.api.biome.v1.*;
import net.keegancuff.endgame.EndGame;
import net.keegancuff.endgame.world.feature.ModPlacedFeatures;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;

import java.util.function.BiConsumer;

public class ModOreGeneration {

    public static final BiomeModification ENDERIUM_ORE = BiomeModifications.create(new Identifier(EndGame.MOD_ID, "features"))
            .add(ModificationPhase.ADDITIONS, BiomeSelectors.tag(BiomeTags.END_CITY_HAS_STRUCTURE), myOreModifier());

    private static BiConsumer<BiomeSelectionContext, BiomeModificationContext> myOreModifier() {
        return (biomeSelectionContext, biomeModificationContext) ->
                // here we can potentially narrow our biomes down
                // but here we won't
                biomeModificationContext.getGenerationSettings().addFeature(
                        // ores to ores
                        GenerationStep.Feature.UNDERGROUND_ORES,
                        // this is the key of the placed feature
                        ModPlacedFeatures.ORE_ENDERIUM_PLACED);
    }

    public static void registerOreGeneration() {
        EndGame.LOGGER.debug("Registering the ModOreGeneration for " + EndGame.MOD_ID);
    }
}
