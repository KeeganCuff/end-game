package net.keegancuff.endgame.screen;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.keegancuff.endgame.item.ModColorProvider;
import net.keegancuff.endgame.networking.ModMessages;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class VariantMetalScreen extends Screen {

    private static final Text COLOR_LABEL_TEXT = Text.literal("Color: ");

    protected static final ButtonWidget.NarrationSupplier DEFAULT_NARRATION_SUPPLIER = textSupplier -> (MutableText)textSupplier.get();
    private ItemStack metal;
    private ButtonWidget modifyButton;
    private ButtonWidget cancelButton;
    private TextFieldWidget colorField;
    private TextWidget colorLabel;

    public VariantMetalScreen(ItemStack stack) {
        super(Text.literal("Variant Metal"));
        metal = stack;
    }

    @Override
    protected void init() {
        super.init();
        addElements();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        drawCenteredText(matrices, textRenderer, "Variant Metal", width/2, 30, 0xffffff);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void tick() {
        super.tick();
        checkValidInput();
    }

    private void checkValidInput() {
        colorField.tick();
        String colorText = colorField.getText();
        int colorId;
        try{
            colorId = Integer.parseInt(colorText);
            if (colorId <0 ||colorId > 31){
                throw new IllegalArgumentException();
            }
        } catch (Exception ignored){
            modifyButton.active = false;
            return;
        }
        modifyButton.active = true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        boolean ret = super.keyPressed(keyCode, scanCode, modifiers);
        return ret;
    }

    private void addElements() {
        cancelButton = addDrawableChild(ButtonWidget.builder(Text.literal("Cancel"), (onPress) -> client.setScreen(null)).build());
        cancelButton.setPos(width/2-100, height-50);
        cancelButton.setWidth(80);
        modifyButton = addDrawableChild(ButtonWidget.builder(Text.literal("Modify"), (onPress) -> {
            modifyMetal();
            client.setScreen(null);
        }).build());
        modifyButton.setPos((width/2+20), height-50);
        modifyButton.setWidth(80);

        colorField = addDrawableChild(new TextFieldWidget(textRenderer, width/2+20, 100, 100, 20, Text.empty()));
        colorField.setText("" + metal.getOrCreateNbt().getInt(ModColorProvider.MOD_COLOR_NBT_ID));
        colorLabel = addDrawableChild(new TextWidget(width/2 - textRenderer.getWidth(COLOR_LABEL_TEXT), 100, textRenderer.getWidth(COLOR_LABEL_TEXT), 20, COLOR_LABEL_TEXT, textRenderer));
    }

    private void modifyMetal(){
        String text = colorField.getText();
        int colorId = Integer.parseInt(text);
        metal.getOrCreateNbt().putInt(ModColorProvider.MOD_COLOR_NBT_ID, colorId);
        ClientPlayNetworking.send(ModMessages.MODIFY_VARIANT, PacketByteBufs.create().writeItemStack(metal));
    }
}
