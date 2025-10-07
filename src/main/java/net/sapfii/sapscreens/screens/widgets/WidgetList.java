package net.sapfii.sapscreens.screens.widgets;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.sapfii.sapscreens.SapScreens;
import net.sapfii.sapscreens.UtilSS;
import net.sapfii.sapscreens.screens.widgets.interfaces.ClickableWidget;
import net.sapfii.sapscreens.screens.widgets.interfaces.ScrollableWidget;

import java.util.List;

public class WidgetList extends WidgetContainer implements ScrollableWidget, ClickableWidget {
    int horizontalPadding, verticalPadding, itemPadding;
    float scrollAmount, visScrollAmount, maxScrollAmount;
    boolean hovered;

    public WidgetList(Widget<?>... widgets) {
        this.widgets.addAll(List.of(widgets));
        position.x = 0;
        position.y = 0;
        position.width = 100;
        position.height = 100;
        this.horizontalPadding = 10;
        this.verticalPadding = 10;
        this.itemPadding = 5;
        this.visScrollAmount = 0;
    }

    @Override
    public void render(DrawContext context, float mouseX, float mouseY, float delta, WidgetContainer renderer) {
        position.updateAnchors(renderer);
        hovered = isHovered(mouseX, mouseY, renderer);
        context.getMatrices().pushMatrix();
        context.getMatrices().translate(x(), y());
        context.enableScissor(0, 0, width(), height());
        context.fill(0, 0, width(), height(), 0x66000000);
        int currentOffset = verticalPadding - (int) visScrollAmount;
        for (Widget<?> widget : widgets) {
            widget.position.x = horizontalPadding;
            widget.position.width = width() - horizontalPadding * 2;
            widget.position.y = currentOffset;
            currentOffset += widget.height() + itemPadding;
            widget.render(context, mouseX - x(), mouseY - y(), delta, this);
        }
        context.disableScissor();
        if (currentOffset + visScrollAmount - itemPadding > height()) {
            maxScrollAmount = currentOffset - itemPadding - height() + visScrollAmount + verticalPadding;
            visScrollAmount = UtilSS.lerp(visScrollAmount, scrollAmount, 0.05f);
            context.fill(width(), 0, width() + 3, height(), 0x88000000);

        }
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

    public void setScrollAmount(float amt) {
        scrollAmount = Math.clamp(amt, 0, maxScrollAmount);
    }

    public WidgetList withPadding(int horizontalPadding, int verticalPadding, int itemPadding) {
        this.horizontalPadding = horizontalPadding;
        this.verticalPadding = verticalPadding;
        this.itemPadding = itemPadding;
        return getThis();
    }

    @Override
    protected WidgetList getThis() {
        return this;
    }

    @Override
    public void onClick() {
        widgets.forEach(widget -> { if (widget instanceof ClickableWidget w) w.onClick(); });
    }

    @Override
    public void onRelease() {
        widgets.forEach(widget -> { if (widget instanceof ClickableWidget w) w.onRelease(); });
    }

    @Override
    public void onScroll(double amt) {
        if (!hovered) return;
        setScrollAmount((float) (scrollAmount - amt * 15));
        widgets.forEach(widget -> { if (widget instanceof ScrollableWidget w) w.onScroll(amt); });
    }
}
