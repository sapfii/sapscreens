package net.sapfii.sapscreens.screens.widgets;

import net.minecraft.client.gui.DrawContext;
import net.sapfii.sapscreens.screens.widgets.interfaces.ClickableWidget;

public class ButtonWidget extends Widget<ButtonWidget> implements ClickableWidget {
    protected ClickProcessor processor = () -> {};

    public ButtonWidget() {
        this.x = 0;
        this.y = 0;
        this.width = 16;
        this.height = 16;
    }

    @Override
    public void render(DrawContext context, float mouseX, float mouseY, float delta, WidgetContainer renderer) {
        context.getMatrices().pushMatrix();
        context.getMatrices().translate(x, y);
        context.enableScissor(0, 0, (int) width, (int) height);
        if (isHovered(mouseX, mouseY, renderer)) {
            context.fill(0, 0, Math.round(width), Math.round(height), 0x88222222);
            context.drawBorder(0, 0, Math.round(width), Math.round(height), 0xBBFFFFFF);
        } else context.fill(0, 0, Math.round(width), Math.round(height), 0x88000000);
        context.disableScissor();
        context.getMatrices().popMatrix();
    }

    private boolean isHovered(float mouseX, float mouseY, WidgetContainer renderer) {
        boolean inBoundsX = mouseX >= x && mouseX < x + width;
        boolean inBoundsY = mouseY >= y && mouseY < y + height;
        boolean inRenderBoundsX = mouseX >= 0 && mouseX < renderer.width;
        boolean inRenderBoundsY = mouseY >= 0 && mouseY < renderer.height;
        return inBoundsX && inBoundsY && inRenderBoundsX && inRenderBoundsY;
    }

    @Override
    public ButtonWidget withPosition(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    @Override
    public ButtonWidget withDimensions(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    @Override
    public ButtonWidget withAlignment(Alignment alignment) {
        this.alignment = alignment;
        return this;
    }

    public ButtonWidget withEvent(ClickProcessor processor) {
        this.processor = processor;
        return this;
    }

    @Override
    public void onClick() {
        processor.onClick();
    }

    @Override
    public void onRelease() {

    }
}
