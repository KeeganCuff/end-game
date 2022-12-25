package net.keegancuff.endgame.util;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.keegancuff.endgame.command.DimensionWarpCommand;

public class ModRegistries {
    public static void registerModStuffs(){
        registerCommands();
    }

    private static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(DimensionWarpCommand::register);
    }
}
