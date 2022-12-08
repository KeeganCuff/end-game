package net.keegancuff.endgame.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.keegancuff.endgame.EndGame;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {

    public static final Item ENDERIUM_INGOT = registerItem("enderium_ingot",
            new Item(new FabricItemSettings().rarity(Rarity.RARE)));



    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(EndGame.MOD_ID, name), item);
    }

    public static void registerModItems() {
        EndGame.LOGGER.debug("Registering Mod Items for " + EndGame.MOD_ID);
    }
}
