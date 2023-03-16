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

    @ModifyArgs(method = "generate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/feature/OreFeature;generateVeinPart(Lnet/minecraft/world/StructureWorldAccess;Lnet/minecraft/util/math/random/Random;Lnet/minecraft/world/gen/feature/OreFeatureConfig;DDDDDDIIIII)Z"))
    private void endgame$modifyOreFeatureConfig(Args args){
        StructureWorldAccess world = args.get(0);
        OreFeatureConfig config = args.get(2);
        ServerWorld serverWorld = world instanceof ServerWorld? ((ServerWorld)world) : world.toServerWorld();
        if (serverWorld.getRegistryKey().getValue().toString().contains("phase_dimension_")){
            List<OreFeatureConfig.Target> list;
            if (config.targets.get(0).state.isOf(ModBlocks.VARIANT_METAL_ORE)){
                list = new ArrayList<>();
                for(OreFeatureConfig.Target target : config.targets){
                    list.add(OreFeatureConfig.createTarget(target.target, VariantMaterialHelper.getMetalData(serverWorld, target.state.getBlock())));
                }
                args.set(2, new OreFeatureConfig(list, config.size, config.discardOnAirChance));
            } else if (config.targets.get(0).state.isOf(ModBlocks.VARIANT_GEM_ORE)){
                list = new ArrayList<>();
                for (OreFeatureConfig.Target target : config.targets){
                    list.add(OreFeatureConfig.createTarget(target.target, VariantMaterialHelper.getGemData(serverWorld, target.state.getBlock())));
                }
                args.set(2, new OreFeatureConfig(list, config.size, config.discardOnAirChance));
            }
        }
    }

    /*
    @ModifyVariable(method = "generateVeinPart", at = @At(value = "HEAD"), ordinal = 0)
    private OreFeatureConfig injected(OreFeatureConfig config){
        if (config.targets.get(0).state.isOf(ModBlocks.VARIANT_METAL_ORE)){

        }
    }*/
}
