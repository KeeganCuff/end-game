package net.keegancuff.endgame.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.keegancuff.endgame.EndGame;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroup {
    public static final ItemGroup ENDGAME = FabricItemGroup.builder(new Identifier(EndGame.MOD_ID, "endgame"))
            .displayName(Text.literal("End Game")).icon(() -> new ItemStack(ModItems.ENDERIUM_INGOT))
            .entries(((enabledFeatures, entries, operatorEnabled) -> {
                entries.add(ModItems.ENDERIUM_INGOT);
            })).build();

    ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(ModItems.ENDERIUM_INGOT));


}
