package net.keegancuff.endgame.mixin;

import net.keegancuff.endgame.EndGame;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import net.minecraft.world.gen.noise.NoiseConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(NoiseChunkGenerator.class)
public class NoiseChunkGeneratorMixin extends ChunkGeneratorMixin{

    @ModifyVariable(method = "carve", at = @At("STORE"), ordinal = 3)
    private int injectedCarverSeed(int x, ChunkRegion chunkRegion, long seed, NoiseConfig noiseConfig, BiomeAccess biomeAccess, StructureAccessor structureAccessor, Chunk chunk2, GenerationStep.Carver carverStep){
        if (chunkRegion.toServerWorld().getRegistryKey().getValue().toString().contains("phase_dimension_")){
            //EndGame.LOGGER.info("NoiseChunkGeneratorMixin: we in here");
            return x + Integer.parseInt(chunkRegion.toServerWorld().getRegistryKey().getValue().toString().substring(24));
        }
        return x;
    }
}
