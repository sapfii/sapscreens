package net.sapfii.sapscreens.screens.widgets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.sapfii.sapscreens.SapScreens;
import net.sapfii.sapscreens.screens.widgets.interfaces.WidgetContainer;

import java.util.ArrayList;
import java.util.List;

public class TextDisplayWidget extends Widget<TextDisplayWidget>{
    public enum Alignment {
        LEFT, CENTER, RIGHT
    }


    private Text text;
    private List<OrderedText> sectionedTexts = new ArrayList<>();
    private int linePadding;
    private Alignment alignment;

    public TextDisplayWidget(Text text, int linePadding, Alignment alignment) {
        super();
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        List<Text> texts = SapScreens.splitTextNewline(text);
        for (Text splitText : texts) {
            sectionedTexts.addAll(textRenderer.wrapLines(splitText, width));
        }
        this.height = (sectionedTexts.size() * textRenderer.fontHeight) + ((sectionedTexts.size() - 1) * linePadding);
        this.text = text;
        this.linePadding = linePadding;
        this.alignment = alignment;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (centerWidth) this.withPosition(parent.getWidth() / 2 - width / 2, 0);
        if (centerHeight) this.withPosition(this.x, parent.getHeight() / 2 - height / 2);
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        sectionedTexts = new ArrayList<>();
        List<Text> texts = SapScreens.splitTextNewline(text);
        for (Text splitText : texts) {
            sectionedTexts.addAll(textRenderer.wrapLines(splitText, width));
        }
        this.height = (sectionedTexts.size() * textRenderer.fontHeight) + ((sectionedTexts.size() - 1) * linePadding);
        int textX = 0;
        int textY = 0;
        for (OrderedText line : sectionedTexts) {
            if (alignment == Alignment.LEFT) textX = 0;
            if (alignment == Alignment.CENTER) textX = (width / 2) - (textRenderer.getWidth(line) / 2);
            if (alignment == Alignment.RIGHT) textX = width - textRenderer.getWidth(line);
            context.drawTextWithShadow(textRenderer, line, textX + this.xOffset, textY + this.yOffset, 0xFFFFFFFF);
            textY += textRenderer.fontHeight + linePadding;
        }
    }

    @Override
    public TextDisplayWidget getThis() {
        return this;
    }
}
