package net.sapfii.sapscreens.screens.widgets;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.sapfii.sapscreens.SapScreens;
import net.sapfii.sapscreens.screens.WidgetContainerScreen;
import net.sapfii.sapscreens.screens.widgets.interfaces.ClickableWidget;

public class ButtonWidget extends Widget<ButtonWidget> implements ClickableWidget {
    protected ClickProcessor processor = () -> {};

    public ButtonWidget() {
        position.x = 0;
        position.y = 0;
        position.width = 16;
        position.height = 16;
    }

    @Override
    public void render(DrawContext context, float mouseX, float mouseY, float delta, WidgetContainer renderer) {
        position.updateAnchors(renderer);
        context.getMatrices().pushMatrix();
        context.getMatrices().translate(x(), y());
        context.enableScissor(0, 0, width(), height());
        if (isHovered(mouseX, mouseY, renderer)) {
//            if (SapScreens.MC.currentScreen instanceof WidgetContainerScreen screen) {
//
//            }
            context.fill(0, 0, width(), height(), 0x88222222);
            context.drawBorder(0, 0, width(), height(), 0xBBFFFFFF);
        } else context.fill(0, 0, width(), height(), 0x88000000);
        context.disableScissor();
        context.getMatrices().popMatrix();
    }

    private boolean isHovered(float mouseX, float mouseY, WidgetContainer renderer) {
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
        return this;
    }

    public ButtonWidget withTooltip() {
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
