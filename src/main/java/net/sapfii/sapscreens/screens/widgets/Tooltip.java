package net.sapfii.sapscreens.screens.widgets;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.sapfii.sapscreens.SapScreens;
import net.sapfii.sapscreens.UtilSS;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Tooltip extends Widget<Tooltip> {
    protected List<Text> texts = new ArrayList<>();

    @Override
    public void render(DrawContext context, float mouseX, float mouseY, float delta, Widget<?> renderer) {
        TextRenderer textRenderer = SapScreens.MC.textRenderer;
        context.getMatrices().pushMatrix();
        context.fill(0, 0, width(), height(), 0x88000000);
        AtomicInteger maxSize = (new AtomicInteger());
        texts.forEach(text -> {
            context.drawText(textRenderer, text, 3, maxSize.get() + 3, 0xFFFFFFFF, true);
            maxSize.set(maxSize.get() + textRenderer.fontHeight + 2);
        });
        context.getMatrices().popMatrix();
    }

    @Override
    protected Tooltip getThis() {
        return this;
    }

    public void setTooltipText(Text tooltipText) {
        TextRenderer textRenderer = SapScreens.MC.textRenderer;
        texts = UtilSS.splitTextNewline(tooltipText);
        AtomicInteger maxSize = new AtomicInteger(0);
        texts.forEach(text -> maxSize.set(Math.max(maxSize.get(), textRenderer.getWidth(text))));
        withDimensions(6 + maxSize.get(), 4 + (textRenderer.fontHeight + 2) * texts.size());
    }
}
