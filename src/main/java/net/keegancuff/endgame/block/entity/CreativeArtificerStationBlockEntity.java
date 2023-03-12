package net.keegancuff.endgame.block.entity;

import net.keegancuff.endgame.item.custom.VariantMetalBlockItem;
import net.keegancuff.endgame.item.custom.VariantMetalItem;
import net.keegancuff.endgame.screen.ArtificerStationScreenHandler;
import net.keegancuff.endgame.world.dimension.fantasy.VariantMaterialHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CreativeArtificerStationBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
    private ScreenHandler handler;

    public CreativeArtificerStationBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CREATIVE_ARTIFICER_STATION, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Artificer's Station (Creative)");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        handler = new ArtificerStationScreenHandler(syncId, inv, this, ScreenHandlerContext.create(world, pos));
        return handler;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
    }

    @Override
    public void markDirty() {
        if (this.handler != null){
            handler.onContentChanged(this);
        }
        super.markDirty();
    }

    public static <E extends BlockEntity> void tick(World world, BlockPos blockPos, BlockState state, E e) {
    }
}
