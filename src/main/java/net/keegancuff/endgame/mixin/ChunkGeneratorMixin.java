package net.keegancuff.endgame.mixin;

import net.keegancuff.endgame.EndGame;
import net.keegancuff.endgame.world.dimension.tools.DimensionFactory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.RandomSeed;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(ChunkGenerator.class)
public class ChunkGeneratorMixin {
    @ModifyVariable(method = "generateFeatures", at = @At("STORE"), ordinal = 0)
    private long injectedSeed(long x, StructureWorldAccess swa, Chunk chunk, StructureAccessor structureAccessor){
        /*if (swa instanceof ServerWorld serverWorld){
            EndGame.LOGGER.info("ChunkGeneratorMixin: Using ServerWorld");
            if (serverWorld.getDimensionEntry().matchesKey(DimensionFactory.TYPE_KEY)){
                EndGame.LOGGER.info("ChunkGeneratorMixin: ServerWorld: Phase Dimension!");
                return serverWorld.getRandom().nextLong();
            }
        }*/
        if (swa instanceof ChunkRegion chunkRegion){
            if (chunkRegion.toServerWorld().getDimensionEntry().matchesKey(DimensionFactory.TYPE_KEY)){
                return RandomSeed.getSeed();
            }
        }
        return x;
    }
}
