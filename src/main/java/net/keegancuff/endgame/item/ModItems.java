package net.keegancuff.endgame.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.keegancuff.endgame.EndGame;
import net.keegancuff.endgame.item.custom.ModArmorItem;
import net.minecraft.entity.EquipmentSlot;
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
    public static final Item ENDERIUM_HELMET = registerItem("enderium_helmet",
            new ModArmorItem(ModArmorMaterials.ENDERIUM, EquipmentSlot.HEAD, (new Item.Settings()).rarity(Rarity.RARE)));
    public static final Item ENDERIUM_CHESTPLATE = registerItem("enderium_chestplate",
            new ArmorItem(ModArmorMaterials.ENDERIUM, EquipmentSlot.CHEST, (new Item.Settings()).rarity(Rarity.RARE)));
    public static final Item ENDERIUM_LEGGINGS = registerItem("enderium_leggings",
            new ArmorItem(ModArmorMaterials.ENDERIUM, EquipmentSlot.LEGS, (new Item.Settings()).rarity(Rarity.RARE)));
    public static final Item ENDERIUM_BOOTS = registerItem("enderium_boots",
            new ArmorItem(ModArmorMaterials.ENDERIUM, EquipmentSlot.FEET, (new Item.Settings()).rarity(Rarity.RARE)));


    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(EndGame.MOD_ID, name), item);
    }

    public static void registerModItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(ENDERIUM_INGOT));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(RAW_ENDERIUM));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ENDERIUM_SWORD));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ENDERIUM_AXE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ENDERIUM_SHOVEL));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ENDERIUM_PICKAXE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ENDERIUM_AXE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(ENDERIUM_HOE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ENDERIUM_HELMET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ENDERIUM_CHESTPLATE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ENDERIUM_LEGGINGS));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(ENDERIUM_BOOTS));
        EndGame.LOGGER.debug("Registering Mod Items for " + EndGame.MOD_ID);
    }
}
