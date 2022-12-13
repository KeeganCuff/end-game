package net.keegancuff.endgame.item.custom;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.util.Rarity;
import org.jetbrains.annotations.Nullable;

public class ModFloatingItem extends Item {
    private boolean floats;
    public ModFloatingItem(Settings settings) {
        super(settings);
        floats = true;
    }


}
