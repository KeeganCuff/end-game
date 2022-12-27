package net.keegancuff.endgame.mixin;

import net.keegancuff.endgame.EndGame;
import net.keegancuff.endgame.world.dimension.tools.DimensionFactory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.util.math.random.RandomSeed;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.placement.StructurePlacementCalculator;
import net.minecraft.world.gen.noise.NoiseConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(ChunkGenerator.class)
public class ChunkGeneratorMixin {
    @ModifyVariable(method = "generateFeatures", at = @At("STORE"), ordinal = 0)
    private long injectFeatureSeed(long x, StructureWorldAccess swa, Chunk chunk, StructureAccessor structureAccessor) {
        if (swa instanceof ChunkRegion chunkRegion) {
            if (chunkRegion.toServerWorld().getRegistryKey().getValue().toString().contains("phase_dimension_")) {
                //EndGame.LOGGER.info("ChunkGeneratorMixin: we in here");
                return x + Long.parseLong(chunkRegion.toServerWorld().getRegistryKey().getValue().toString().substring(24));
            }
        }
        return x;
    }
}
