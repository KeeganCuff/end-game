package net.keegancuff.endgame.mixin;

import com.mojang.datafixers.DataFixer;
import net.keegancuff.endgame.EndGame;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.util.thread.ThreadExecutor;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.chunk.ChunkProvider;
import net.minecraft.world.chunk.ChunkStatusChangeListener;
import net.minecraft.world.gen.chunk.placement.StructurePlacementCalculator;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.world.gen.noise.NoiseConfig;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.Executor;
import java.util.function.Supplier;

@Mixin(ThreadedAnvilChunkStorage.class)
public class ThreadedAnvilChunkStorageSeedChanger {

    @Shadow private ServerWorld world;
    @Shadow private StructurePlacementCalculator structurePlacementCalculator;
    /*
    //@ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/chunk/ChunkGenerator;createStructurePlacementCalculator(" +
    //        "Lnet/minecraft/registry/RegistryWrapper;Lnet/minecraft/world/gen/noise/NoiseConfig;J)Lnet/minecraft/world/gen/chunk/placement/StructurePlacementCalculator"),
    //        index = 2)
    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = ("LChunkGenerator;createStructurePlacementCalculator(LRegistryWrapper;LNoiseConfig;J)LStructurePlacementCalculator")), index = 2)
    private long injectStructureSeed(long x){
        if (world.getRegistryKey().getValue().toString().contains("phase_dimension_")){
            EndGame.LOGGER.info("TACSSC Mixin: structure seed changed!");
            return x + Long.parseLong(world.getRegistryKey().getValue().toString().substring(24));
        }
        return x;
    }*/
    @Inject(method = "<init>", at = @At("TAIL"))
    private void changeStructureSeed(ServerWorld world, LevelStorage.Session session, DataFixer dataFixer, StructureTemplateManager structureTemplateManager, Executor executor, ThreadExecutor<Runnable> mainThreadExecutor, ChunkProvider chunkProvider, ChunkGenerator chunkGenerator, WorldGenerationProgressListener worldGenerationProgressListener, ChunkStatusChangeListener chunkStatusChangeListener, Supplier<PersistentStateManager> persistentStateManagerFactory, int viewDistance, boolean dsync,
                                     CallbackInfo info){
        if (world.getRegistryKey().getValue().toString().contains("phase_dimension_")){
            EndGame.LOGGER.info("TACSSC Mixin: structure seed changed!");
            ((StructurePlacementCalculatorSeedChanger)structurePlacementCalculator).setStructureSeed(world.getSeed() + Long.parseLong(world.getRegistryKey().getValue().toString().substring(24)));
        }
    }
}
