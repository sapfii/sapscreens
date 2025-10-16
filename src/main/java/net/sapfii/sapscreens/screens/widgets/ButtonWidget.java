package net.sapfii.sapscreens.screens.widgets;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.sapfii.sapscreens.SapScreens;
import net.sapfii.sapscreens.UtilSS;
import net.sapfii.sapscreens.screens.WidgetContainerScreen;
import net.sapfii.sapscreens.screens.widgets.interfaces.ClickableWidget;

public class ButtonWidget extends Widget<ButtonWidget> implements ClickableWidget {
    protected ClickProcessor processor = (button) -> {};
    protected Text tooltipText = Text.literal("");
    protected TextDisplayWidget text = new TextDisplayWidget(Text.literal(""));
    protected boolean hovered = false;

    public ButtonWidget() {
        position.x = 0;
        position.y = 0;
        position.width = 16;
        position.height = 16;
        text.withDimensions(width(), height());
        text.withTextAlignment(TextDisplayWidget.TextAlignment.CENTER);
    }

    @Override
    public void render(DrawContext context, float mouseX, float mouseY, float delta, Widget<?> renderer) {
        TextRenderer textRenderer = SapScreens.MC.textRenderer;
        position.updateAnchors(renderer);
        hovered = isHovered(mouseX, mouseY, renderer);
        context.getMatrices().pushMatrix();
        context.getMatrices().translate(x(), y());
        context.enableScissor(0, 0, width(), height());
        if (hovered) {
            if (SapScreens.MC.currentScreen instanceof WidgetContainerScreen screen && !tooltipText.getString().isEmpty()) {
                screen.showToolTip(true);
                screen.setTooltipText(tooltipText);
            }
            context.fill(0, 0, width(), height(), 0x88222222);
            context.drawBorder(0, 0, width(), height(), 0xBBFFFFFF);
        } else context.fill(0, 0, width(), height(), 0x88000000);
        context.getMatrices().pushMatrix();
        context.getMatrices().translate(0, height() / 2 - textRenderer.fontHeight / 2);
        text.withDimensions(width(), height());
        text.render(context, mouseX, mouseY, delta, getThis());
        context.getMatrices().popMatrix();
        context.disableScissor();
        context.getMatrices().popMatrix();
    }

    private boolean isHovered(float mouseX, float mouseY, Widget<?> renderer) {
        int parentWidth = renderer == null ? SapScreens.MC.getWindow().getScaledWidth() : renderer.width();
        int parentHeight = renderer == null ? SapScreens.MC.getWindow().getScaledHeight() : renderer.height();
        boolean inBoundsX = mouseX >= x() && mouseX < x() + width();
        boolean inBoundsY = mouseY >= y() && mouseY < y() + height();
        boolean inRenderBoundsX = mouseX >= 0 && mouseX < parentWidth;
        boolean inRenderBoundsY = mouseY >= 0 && mouseY < parentHeight;
        return inBoundsX && inBoundsY && inRenderBoundsX && inRenderBoundsY;
    }

    @Override
    protected ButtonWidget getThis() {
        return this;
    }

    public ButtonWidget withEvent(ClickProcessor processor) {
        this.processor = processor;
        return getThis();
    }

    public ButtonWidget withTooltip(Text tooltipText) {
        this.tooltipText = tooltipText;
        return getThis();
    }

    public ButtonWidget withText(Text text) {
        this.text.setLines(UtilSS.splitTextNewline(text));
        return getThis();
    }

    public ButtonWidget withTooltip() {
        return this;
    }

    @Override
    public void onClick(float mouseX, float mouseY) {
        if (hovered) processor.onClick(getThis());
    }

    @Override
    public void onRelease(float mouseX, float mouseY) {

    }
}
