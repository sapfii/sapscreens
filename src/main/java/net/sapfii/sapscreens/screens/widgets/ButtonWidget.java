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
    protected TextDisplayWidget text = TextDisplayWidget.create(Text.literal(""));

    public ButtonWidget() {
        position.x = 0;
        position.y = 0;
        position.width = 16;
        position.height = 16;
        text.withDimensions(width(), height());
        text.withTextAlignment(TextDisplayWidget.TextAlignment.CENTER);
    }

    public static ButtonWidget create() {
        ButtonWidget widget = new ButtonWidget();
        widget.position.x = 0;
        widget.position.y = 0;
        widget.position.width = 16;
        widget.position.height = 16;
        widget.text.withDimensions(widget.width(), widget.height());
        widget.text.withTextAlignment(TextDisplayWidget.TextAlignment.CENTER);
        return widget;
    }

    @Override
    public void render(DrawContext context, float mouseX, float mouseY, float delta, Widget<?> renderer) {
        TextRenderer textRenderer = SapScreens.MC.textRenderer;
        position.updateAnchors(renderer);
        context.getMatrices().pushMatrix();
        context.getMatrices().translate(x(), y());
        context.enableScissor(0, 0, width(), height());
        if (position.hovered) {
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
        if (position.hovered) processor.onClick(getThis());
    }

    @Override
    public void onRelease(float mouseX, float mouseY) {

    }

    @Override
    public boolean isHovered() {
        return position.hovered;
    }

    @Override
    public void updateHovered(float mouseX, float mouseY) {
        super.updateHovered(mouseX, mouseY);
    }
}
