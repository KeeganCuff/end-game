package net.keegancuff.endgame.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.keegancuff.endgame.EndGame;
import net.keegancuff.endgame.block.ModBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static BlockEntityType<CreativeArtificerStationBlockEntity> CREATIVE_ARTIFICER_STATION;

    public static void registerBlockEntities(){
    CREATIVE_ARTIFICER_STATION = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(EndGame.MOD_ID, "creative_artificier_station"),
            FabricBlockEntityTypeBuilder.create(CreativeArtificerStationBlockEntity::new, ModBlocks.CREATIVE_ARTIFICER_STATION).build());
    }
}
