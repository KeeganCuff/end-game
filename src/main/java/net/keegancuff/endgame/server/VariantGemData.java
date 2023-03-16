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

public class VariantGemData extends PersistentState {

    public static final String DATA_TAG = ".gem";
    public int color = 0;

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putInt(ModColorProvider.MOD_COLOR_NBT_ID, color);
        return nbt;
    }

    public static VariantGemData createFromNbt(NbtCompound tag){
        VariantGemData gemData = new VariantGemData();
        gemData.color = tag.getInt(ModColorProvider.MOD_COLOR_NBT_ID);
        return gemData;
    }

    public static VariantGemData getWorldGemData(ServerWorld world){
        PersistentStateManager persistentStateManager = world.getPersistentStateManager();

        VariantGemData gemData = persistentStateManager.getOrCreate(
                VariantGemData::createFromNbt,
                VariantGemData::new,
                EndGame.MOD_ID + DATA_TAG);

        gemData.markDirty();

        return gemData;
    }

    public static void createRandomData(ServerWorld world) {
        EndGame.LOGGER.info("VariantGemData: Generating material data for id: " + FantasyDimensionHelper.toId(world));
        VariantGemData data = getWorldGemData(world);
        data.color = colorRandom(world.getRandom());
    }

    private static int colorRandom(Random random) {
        int color = random.nextInt(ModColorProvider.NUM_VARIANT_COLORS);
        EndGame.LOGGER.info("VariantGemData: Color: " + color);
        return color;
    }
}
