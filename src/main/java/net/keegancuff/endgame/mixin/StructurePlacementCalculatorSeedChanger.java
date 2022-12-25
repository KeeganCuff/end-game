package net.keegancuff.endgame.mixin;

import net.minecraft.world.gen.chunk.placement.StructurePlacementCalculator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(StructurePlacementCalculator.class)
public interface StructurePlacementCalculatorSeedChanger {
    @Accessor("structureSeed")
    void setStructureSeed(long structureSeed);
}
