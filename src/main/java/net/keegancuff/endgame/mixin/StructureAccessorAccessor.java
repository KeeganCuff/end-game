package net.keegancuff.endgame.mixin;

import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.gen.StructureAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(StructureAccessor.class)
public interface StructureAccessorAccessor {
    @Accessor
    GeneratorOptions getOptions();
}
