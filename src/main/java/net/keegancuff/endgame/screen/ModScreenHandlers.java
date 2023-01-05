package net.keegancuff.endgame.screen;

import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers {
    public static ScreenHandlerType<ArtificerStationScreenHandler> ARTIFICER_STATION_SCREEN_HANDLER;

    public static void registerScreenHandlers(){
        ARTIFICER_STATION_SCREEN_HANDLER = new ScreenHandlerType<>(ArtificerStationScreenHandler::new);
    }
}
