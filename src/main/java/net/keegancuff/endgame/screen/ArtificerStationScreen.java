package net.keegancuff.endgame.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.keegancuff.endgame.EndGame;
import net.keegancuff.endgame.world.dimension.fantasy.VariantMaterialHelper;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.EditBoxWidget;
import net.minecraft.client.gui.widget.PressableTextWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.Backoff;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;

public class ArtificerStationScreen extends HandledScreen<ArtificerStationScreenHandler> {

    private static final Identifier TEXTURE = new Identifier(EndGame.MOD_ID, "textures/gui/artificer_station_gui.png");

    private EditBoxWidget textBox;
    private ButtonWidget button;

    public ArtificerStationScreen(ArtificerStationScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        playerInventoryTitleY += 4;
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
        textBox = this.addDrawable(new EditBoxWidget(this.textRenderer, x + 37, y + 14, 109, 61, Text.of("<--- place a variant item here to learn more about it!"), Text.of("Test Widget")));
        textBox = this.addSelectableChild(textBox);
        textBox.setChangeListener(info -> {
            handler.setInfo(info);
        });
        button = this.addDrawable(new PressableTextWidget(x + 152, y + 59, 18, 18, Text.of("X"), button -> handler.onButtonClick(client.player, 356), this.textRenderer));
        textBox.setText(handler.getInfo());
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        textBox.mouseClicked(mouseX, mouseY, button);
        this.button.mouseClicked(mouseX, mouseY, button);
        return super.mouseClicked(mouseX, mouseY, button);
    }

//    @Override
//    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
//        if (textBox.isFocused()){
//            boolean ret = textBox.keyPressed(keyCode, scanCode, modifiers);
//            String info = textBox.getText();
//            EndGame.LOGGER.info(info);
//            handler.setInfo(info);
//            return ret;
//        }
//        return super.keyPressed(keyCode, scanCode, modifiers);
//    }

    @Override
    public void close() {
        super.close();
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        if (handler.getInfo().equals("") || !handler.showingInfo()){
            textBox.setText("");
        } else if (handler.showingInfo() && textBox.getText().equals("")) {
            textBox.setText(handler.getInfo());
        }
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

}
