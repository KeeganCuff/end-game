package net.keegancuff.endgame;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.keegancuff.endgame.block.ModBlocks;
import net.keegancuff.endgame.screen.ArtificerStationScreen;
import net.keegancuff.endgame.screen.ModScreenHandlers;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;

public class EndGameClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.VARIANT_METAL_ORE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DEEPSLATE_VARIANT_METAL_ORE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.VARIANT_GEM_ORE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DEEPSLATE_VARIANT_GEM_ORE, RenderLayer.getCutout());
        HandledScreens.register(ModScreenHandlers.ARTIFICER_STATION_SCREEN_HANDLER, ArtificerStationScreen::new);
    }
}
