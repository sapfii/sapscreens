package net.sapfii.sapscreens.screens.widgets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.sapfii.sapscreens.screens.widgets.interfaces.ClickableWidget;
import org.joml.Matrix3x2f;
import org.joml.Vector2f;

public class ButtonWidget extends Widget<ButtonWidget> implements ClickableWidget {
    private boolean hasText;
    private boolean hovered = false;
    private Text text;
    private int color = 0x88000000;
    private int hoverColor = 0x88222222;
    private ClickProcessor processor = (button) -> {};

    public ButtonWidget(Text text) {
        super();
        hasText = !text.getString().isEmpty();
        this.text = text;
    }

    public ButtonWidget() {
        this(Text.literal(""));
    }

    public ButtonWidget(Text text, ClickProcessor processor) {
        this(text);
        this.processor = processor;
    }

    public ButtonWidget withColor(int color, int hoverColor) {
        this.color = color;
        this.hoverColor = hoverColor;
        return this;
    }

    public ButtonWidget withText(Text text) {
        this.text = text;
        hasText = !text.getString().isEmpty();
        return this;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (centerWidth) this.width = parent.getWidth() - x * 2;
        if (centerHeight) this.height = parent.getHeight() - y * 2;
        checkHovered(context, mouseX, mouseY);
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        context.enableScissor(0, 0, width, height);
        if (hovered) context.fill(0, 0, width, height, hoverColor);
        else context.fill(0, 0, width, height, color);
        if (hasText) {
            int textX = width / 2 - textRenderer.getWidth(text) / 2;
            int textY = height / 2 - textRenderer.fontHeight / 2;
            context.drawTextWithShadow(textRenderer, text, textX, textY, 0xFFFFFFFF);
        }
        context.disableScissor();
    }

    @Override
    public ButtonWidget getThis() {
        return this;
    }

    @Override
    public boolean hovered() {
        return hovered;
    }

    protected void checkHovered(DrawContext context, float mouseX, float mouseY) {
        context.getMatrices().pushMatrix();
        Matrix3x2f matrix3x2f = context.getMatrices().invert();
        Vector2f vector2f = matrix3x2f.transformPosition(mouseX, mouseY, new Vector2f());
        hovered = false;
        if (vector2f.x >= 0 && vector2f.x < width && vector2f.x >= -x && vector2f.x < -x + parent.getWidth()) {
            if (vector2f.y >= 0 && vector2f.y < height && vector2f.y >= -y && vector2f.y < -y + parent.getHeight()) {
                hovered = true;
            }
        }
        context.getMatrices().popMatrix();
    }

    @Override
    public boolean onClick() {
        if (hovered) processor.onClick(this);
        return hovered;
    }

    @Override
    public void onRelease() {

    }

    public interface ClickProcessor {
        void onClick(ButtonWidget button);
    }
}
