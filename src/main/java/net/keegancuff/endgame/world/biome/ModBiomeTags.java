package net.keegancuff.endgame.world.biome;

import net.keegancuff.endgame.EndGame;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class ModBiomeTags {
    public static TagKey<Biome> VARIANT_BIOME = ModBiomeTags.of("is_variant");

    private static TagKey<Biome> of(String id) {
        return TagKey.of(RegistryKeys.BIOME, new Identifier(EndGame.MOD_ID, id));
    }

    public static void modifyBiomeTags(){

    }
}
