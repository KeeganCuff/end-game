package net.keegancuff.endgame.world.dimension.fantasy;

import net.keegancuff.endgame.block.ModBlocks;
import net.keegancuff.endgame.block.custom.VariantMetalBlock;
import net.keegancuff.endgame.server.VariantMetalData;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.StructureWorldAccess;

public class VariantMaterialHelper {


    public static void initRandomMaterials(ServerWorld world){
        VariantMetalData.createRandomData(world);
    }

    public static BlockState getMetalData(StructureWorldAccess world, Block block) {
        //EndGame.LOGGER.info("VariantMetalHelper: grabbing metal data");
        ServerWorld serverWorld;
        if (world instanceof ChunkRegion region){
            serverWorld = region.toServerWorld();
        } else {
            serverWorld = (ServerWorld) world;
        }
        VariantMetalData metal = VariantMetalData.getWorldMetalData(serverWorld);
        BlockState state = block.getDefaultState().with(VariantMetalBlock.COLOR, metal.color);
        return state;
    }
}
