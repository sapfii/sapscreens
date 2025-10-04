package net.sapfii.sapscreens.screens.widgets;

import net.minecraft.client.gui.DrawContext;
import net.sapfii.sapscreens.screens.widgets.interfaces.ClickableWidget;

public class ButtonWidget extends Widget<ButtonWidget> implements ClickableWidget {
    protected ClickProcessor processor = () -> {};
    protected boolean hovered = false;

    public ButtonWidget() {
        this.x = 0;
        this.y = 0;
        this.width = 16;
        this.height = 16;
    }

    @Override
    public void render(DrawContext context, float mouseX, float mouseY, float delta) {
        context.getMatrices().pushMatrix();
        context.getMatrices().translate(x, y);
        context.enableScissor(0, 0, (int) width, (int) height);
        updateHovered(mouseX, mouseY);
        if (hovered) context.fill(0, 0, Math.round(width), Math.round(height), 0x88222222);
        else context.fill(0, 0, Math.round(width), Math.round(height), 0x88000000);
        if (hovered) context.drawBorder(0, 0, Math.round(width), Math.round(height), 0xFFFFFFFF);
        context.disableScissor();
        context.getMatrices().popMatrix();
    }

    private void updateHovered(float mouseX, float mouseY) {
        hovered = (mouseX - x >= 0 && mouseX - x < width) && (mouseY - y >= 0 && mouseY - y < height);
    }

    public boolean hovered() {
        return hovered;
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
