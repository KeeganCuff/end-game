package net.keegancuff.endgame.mixin;

import net.keegancuff.endgame.EndGame;
import net.keegancuff.endgame.block.ModBlocks;
import net.keegancuff.endgame.world.dimension.fantasy.FantasyDimensionHelper;
import net.keegancuff.endgame.world.dimension.fantasy.VariantMaterialHelper;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.ChunkSectionCache;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.gen.feature.OreFeature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

@Mixin(OreFeature.class)
public class OreFeatureMixin {

    /*@ModifyArg(method = "generateVeinPart", at = @At(value = "Invoke", target = "LChunkSection;setBlockState"), index = 3, locals = LocalCapture.CAPTURE_FAILHARD)
    private BlockState injected(BlockState state){
        return state;
    }*/
    //
    //
    //
    /*
    @Inject(method = "generateVeinPart", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/chunk/ChunkSection;setBlockState(IIILnet/minecraft/block/BlockState;Z)Lnet/minecraft/block/BlockState;"),
            locals = LocalCapture.CAPTURE_FAILHARD)
    private void endgame$inject(StructureWorldAccess world, Random random, OreFeatureConfig config,
                                double startX, double endX, double startZ, double endZ, double startY,
                                double endY, int x, int y, int z, int horizontalSize, int verticalSize,
                                CallbackInfoReturnable<Boolean> ci, int i, BitSet bitSet,
                                BlockPos.Mutable mutable, int j, double[] ds, ChunkSectionCache chunkSectionCache,
                                int m, double d, double e, double g, double h, int n, int o, int p, int q, int r, int s, int t,
                                double u, int v, double w, int aa, double ab, int ac, ChunkSection chunkSection, int ad, int ae, int af,
                                BlockState blockState, Iterator iterator,
                                OreFeatureConfig.Target target){

        if (target.state.isOf(ModBlocks.VARIANT_METAL_ORE)){
            ((TargetMixin)target).setState(VariantMaterialHelper.getMetalData(world, target.state));
        }


    }

     */

    @ModifyArgs(method = "generate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/feature/OreFeature;generateVeinPart(Lnet/minecraft/world/StructureWorldAccess;Lnet/minecraft/util/math/random/Random;Lnet/minecraft/world/gen/feature/OreFeatureConfig;DDDDDDIIIII)Z"))
    private void endgame$modifyOreFeatureConfig(Args args){
        StructureWorldAccess world = args.get(0);
        OreFeatureConfig config = args.get(2);
        ServerWorld serverWorld = world instanceof ServerWorld? ((ServerWorld)world) : world.toServerWorld();
        if (config.targets.get(0).state.isOf(ModBlocks.VARIANT_METAL_ORE)){
            if (serverWorld.getRegistryKey().getValue().toString().contains("phase_dimension_")){
                List<OreFeatureConfig.Target> list = new ArrayList<>();
                for(OreFeatureConfig.Target target : config.targets){
                    list.add(OreFeatureConfig.createTarget(target.target, VariantMaterialHelper.getMetalData(serverWorld, target.state.getBlock())));
                }
                args.set(0, world);
                args.set(1, args.get(1));
                args.set(2, new OreFeatureConfig(list, config.size, config.discardOnAirChance));
                args.set(3, args.get(3));
                args.set(4, args.get(4));
                args.set(5, args.get(5));
                args.set(6, args.get(6));
                args.set(7, args.get(7));
                args.set(8, args.get(8));
                args.set(9, args.get(9));
                args.set(10, args.get(10));
                args.set(11, args.get(11));
                args.set(12, args.get(12));
                args.set(13, args.get(13));
            }
        } else {
            args.set(0, world);
            args.set(1, args.get(1));
            args.set(2, config);
            args.set(3, args.get(3));
            args.set(4, args.get(4));
            args.set(5, args.get(5));
            args.set(6, args.get(6));
            args.set(7, args.get(7));
            args.set(8, args.get(8));
            args.set(9, args.get(9));
            args.set(10, args.get(10));
            args.set(11, args.get(11));
            args.set(12, args.get(12));
            args.set(13, args.get(13));
        }
    }

    /*
    @ModifyVariable(method = "generateVeinPart", at = @At(value = "HEAD"), ordinal = 0)
    private OreFeatureConfig injected(OreFeatureConfig config){
        if (config.targets.get(0).state.isOf(ModBlocks.VARIANT_METAL_ORE)){

        }
    }*/
}
