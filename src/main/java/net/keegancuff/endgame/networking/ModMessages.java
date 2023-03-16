package net.keegancuff.endgame.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.keegancuff.endgame.EndGame;
import net.keegancuff.endgame.networking.packet.ModifyVariantC2SPacket;
import net.minecraft.util.Identifier;

public class ModMessages {
    public static final Identifier MODIFY_VARIANT = new Identifier(EndGame.MOD_ID, "modify_variant");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(MODIFY_VARIANT, ModifyVariantC2SPacket::recieve);
    }

    public static void registerS2CPackets() {

    }
}
