package net.sapfii.sapscreens.screens.widgets;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.sapfii.sapscreens.SapScreens;
import net.sapfii.sapscreens.UtilSS;

import java.util.ArrayList;
import java.util.List;

public class TextDisplayWidget extends Widget<TextDisplayWidget> {
    public enum TextAlignment {
        LEFT, CENTER, RIGHT
    }

    protected List<Text> text = new ArrayList<>();
    protected TextAlignment textAlignment;
    protected int textPadding = 2;

    public TextDisplayWidget(Text text) {
        addLine(text);
        this.textAlignment = TextAlignment.LEFT;
    }

    @Override
    public void render(DrawContext context, float mouseX, float mouseY, float delta, WidgetContainer renderer) {
        position.updateAnchors(renderer);
        TextRenderer textRenderer = SapScreens.MC.textRenderer;
        int yOffset = 0;
        for (Text line : text) {
            int xOffset = 0;
            if (textAlignment == TextAlignment.CENTER) xOffset = width() / 2 - textRenderer.getWidth(line) / 2;
            if (textAlignment == TextAlignment.RIGHT) xOffset = width() - textRenderer.getWidth(line);
            context.drawText(textRenderer, line, x() + xOffset, y() + yOffset, 0xFFFFFFFF, true);
            yOffset += textRenderer.fontHeight + textPadding;
        }
        position.height = yOffset - textPadding;
    }

    @Override
    protected TextDisplayWidget getThis() {
        return this;
    }

    protected TextDisplayWidget addLine(Text line) {
        this.text.addAll(UtilSS.splitTextNewline(line));
        return getThis();
    }

    protected TextDisplayWidget removeLine(int ind) {
        this.text.remove(ind);
        return getThis();
    }

    protected TextDisplayWidget setLines(List<Text> lines) {
        this.text = lines;
        return getThis();
    }

    public TextDisplayWidget withTextAlignment(TextAlignment alignment) {
        this.textAlignment = alignment;
        return getThis();
    }
}
