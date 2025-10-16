package net.sapfii.sapscreens.screens.widgets;

import net.minecraft.client.gui.DrawContext;
import net.sapfii.sapscreens.SapScreens;
import net.sapfii.sapscreens.UtilSS;
import net.sapfii.sapscreens.screens.widgets.interfaces.ClickableWidget;
import net.sapfii.sapscreens.screens.widgets.interfaces.ScrollableWidget;

import java.util.List;

public class WidgetList extends WidgetContainer implements ScrollableWidget, ClickableWidget {
    int horizontalPadding, verticalPadding, itemPadding;
    float scrollAmount, visScrollAmount, maxScrollAmount, mouseAnchor, scrollAnchor;
    boolean hovered, barHovered, holdingBar, allowScrolling;

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
        this.allowScrolling = true;
    }

    @Override
    public void render(DrawContext context, float mouseX, float mouseY, float delta, Widget<?> renderer) {
        position.updateAnchors(renderer);
        hovered = isHovered(mouseX, mouseY, renderer);
        barHovered = false;
        context.getMatrices().pushMatrix();
        context.getMatrices().translate(x(), y());
        context.enableScissor(0, 0, width(), height());
        context.fill(0, 0, width(), height(), 0x44000000);
        int currentOffset = verticalPadding - (int) visScrollAmount;
        for (Widget<?> widget : widgets) {
            widget.position.x = horizontalPadding;
            widget.position.width = width() - horizontalPadding * 2;
            widget.position.y = currentOffset;
            currentOffset += widget.height() + itemPadding;
            widget.render(context, mouseX - x(), mouseY - y(), delta, this);
        }
        context.disableScissor();
        maxScrollAmount = currentOffset - itemPadding - height() + visScrollAmount + verticalPadding;
        maxScrollAmount = Math.clamp(maxScrollAmount, 0, 999999999);
        visScrollAmount = UtilSS.lerp(visScrollAmount, scrollAmount, 0.05f);
        scrollAmount = Math.clamp(scrollAmount, 0, maxScrollAmount);
        visScrollAmount = Math.clamp(visScrollAmount, 0, maxScrollAmount);
        if (currentOffset + visScrollAmount - itemPadding > height()) {
            float scaleFactor =  height() / (height() + maxScrollAmount);
            float barHeight = height() * scaleFactor;
            barHovered = isBarHovered(mouseX, mouseY, visScrollAmount * scaleFactor, barHeight, renderer);
            context.fill(width(), 0, width() + 4, height(), 0x88000000);
            context.getMatrices().pushMatrix();
            context.getMatrices().translate(0, visScrollAmount * scaleFactor);
            context.fill(width(), 0, width() + 4, (int) barHeight, 0xFFFFFFFF);
            context.getMatrices().translate(0, barHeight);
            context.fill(width(), 0, width() + 4, (int) -barHeight, 0xFFFFFFFF);
            context.getMatrices().popMatrix();
            if (holdingBar) {
                setScrollAmount((scrollAnchor + (mouseY - mouseAnchor) / scaleFactor));
            }
        }
        context.getMatrices().popMatrix();
    }

    protected boolean isHovered(float mouseX, float mouseY, Widget<?> renderer) {
        int parentWidth = renderer == null ? SapScreens.MC.getWindow().getScaledWidth() : renderer.width();
        int parentHeight = renderer == null ? SapScreens.MC.getWindow().getScaledHeight() : renderer.height();
        boolean inBoundsX = mouseX >= x() && mouseX < x() + width();
        boolean inBoundsY = mouseY >= y() && mouseY < y() + height();
        boolean inRenderBoundsX = mouseX >= 0 && mouseX < parentWidth;
        boolean inRenderBoundsY = mouseY >= 0 && mouseY < parentHeight;
        return inBoundsX && inBoundsY && inRenderBoundsX && inRenderBoundsY;
    }

    protected boolean isBarHovered(float mouseX, float mouseY, float scrollAmount, float barHeight, Widget<?> renderer) {
        int parentHeight = renderer == null ? SapScreens.MC.getWindow().getScaledHeight() : renderer.height();
        boolean inBoundsX = mouseX >= x() + width() && mouseX < x() + width() + 4;
        boolean inBoundsY = mouseY >= y() + scrollAmount && mouseY < y() + barHeight + scrollAmount;
        boolean inRenderBoundsY = mouseY >= 0 && mouseY < parentHeight;
        return inBoundsX && inBoundsY && inRenderBoundsY;
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
    public WidgetList withPosition(int x, int y) {
        super.withPosition(x, y);
        return getThis();
    }

    @Override
    public WidgetList withDimensions(int width, int height) {
        super.withDimensions(width, height);
        return getThis();
    }

    @Override
    public WidgetList withAlignment(WidgetPos.Alignment alignment) {
        super.withAlignment(alignment);
        return getThis();
    }

    @Override
    public void onClick(float mouseX, float mouseY) {
        holdingBar = barHovered;
        if (holdingBar) {
            mouseAnchor = mouseY;
            scrollAnchor = visScrollAmount;
        }
        widgets.forEach(widget -> { if (widget instanceof ClickableWidget w) w.onClick(mouseX - x(), mouseY - y()); });
    }

    @Override
    public void onRelease(float mouseX, float mouseY) {
        holdingBar = false;
        widgets.forEach(widget -> { if (widget instanceof ClickableWidget w) w.onRelease(mouseX - x(), mouseY - y()); });
    }

    @Override
    public void onScroll(double amt) {
        allowScrolling = true;
        if (!hovered || barHovered) return;
        widgets.forEach(widget -> {
            if (widget instanceof ScrollableWidget w) {
                w.onScroll(amt);
                allowScrolling = !w.hovered();
                if (widget instanceof WidgetList l) {
                    allowScrolling = (l.hovered() && (!l.canScroll() && l.allowScrolling)) || !l.hovered();
                }
            }
        });
        if (allowScrolling) setScrollAmount((float) (scrollAmount - amt * 15));
    }

    @Override
    public boolean hovered() {
        return hovered;
    }

    public boolean canScroll() {
        return maxScrollAmount > 0;
    }


}
