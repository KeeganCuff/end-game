package net.keegancuff.endgame.server;

import net.keegancuff.endgame.EndGame;
import net.keegancuff.endgame.item.ModColorProvider;
import net.keegancuff.endgame.world.dimension.fantasy.FantasyDimensionHelper;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import xyz.nucleoid.fantasy.Fantasy;

public class VariantMetalData extends PersistentState {

    public int color = 0;

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putInt(ModColorProvider.MOD_COLOR_NBT_ID, color);
        return nbt;
    }

    public static VariantMetalData createFromNbt(NbtCompound tag){
        VariantMetalData metalData = new VariantMetalData();
        metalData.color = tag.getInt(ModColorProvider.MOD_COLOR_NBT_ID);
        return metalData;
    }

    public static VariantMetalData getWorldMetalData(ServerWorld world){
        PersistentStateManager persistentStateManager = world.getPersistentStateManager();

        VariantMetalData metalData = persistentStateManager.getOrCreate(
                VariantMetalData::createFromNbt,
                VariantMetalData::new,
                EndGame.MOD_ID);

        metalData.markDirty();

        return metalData;
    }

    public static void createRandomData(ServerWorld world) {
        EndGame.LOGGER.info("VariantMetalData: Generating material data for id: " + FantasyDimensionHelper.toId(world));
        VariantMetalData data = getWorldMetalData(world);
        data.color = colorRandom(world.getRandom());
    }

    private static int colorRandom(Random random) {
        int color = random.nextInt(ModColorProvider.NUM_VARIANT_COLORS);
        EndGame.LOGGER.info("VariantMetalData: Color: " + color);
        return color;
    }
}
