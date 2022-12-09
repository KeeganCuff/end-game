package net.keegancuff.endgame.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.keegancuff.endgame.EndGame;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {

    public static final Item ENDERIUM_INGOT = registerItem("enderium_ingot",
            new Item(new FabricItemSettings().rarity(Rarity.RARE)));
    public static final Item RAW_ENDERIUM = registerItem("raw_enderium",
            new Item(new FabricItemSettings().rarity(Rarity.RARE)));
    public static final Item ENDERIUM_PICKAXE = registerItem("enderium_pickaxe",
            new PickaxeItem(ModToolMaterials.ENDERIUM, 1, -2.8F, (new Item.Settings()).rarity(Rarity.RARE)));
    public static final Item ENDERIUM_AXE = registerItem("enderium_axe",
            new AxeItem(ModToolMaterials.ENDERIUM, 5.0F, -3.0F, (new Item.Settings()).rarity(Rarity.RARE)));
    public static final Item ENDERIUM_SHOVEL = registerItem("enderium_shovel",
            new ShovelItem(ModToolMaterials.ENDERIUM, 1.5F, -3.0F, (new Item.Settings()).rarity(Rarity.RARE)));
    public static final Item ENDERIUM_SWORD = registerItem("enderium_sword",
            new SwordItem(ModToolMaterials.ENDERIUM, 3, -2.4F, (new Item.Settings()).rarity(Rarity.RARE)));
    public static final Item ENDERIUM_HOE = registerItem("enderium_hoe",
            new SwordItem(ModToolMaterials.ENDERIUM, -4, 0.0F, (new Item.Settings()).rarity(Rarity.RARE)));


    private static Item registerItem(String name, Item item){
        //ItemGroupEvents.modifyEntriesEvent(ModItemGroup.ENDGAME).register(entries -> entries.add(item));
        return Registry.register(Registries.ITEM, new Identifier(EndGame.MOD_ID, name), item);
    }

    public static void registerModItems() {
        EndGame.LOGGER.debug("Registering Mod Items for " + EndGame.MOD_ID);
    }
}
