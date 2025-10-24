package net.sapfii.sapscreens.screens.widgets;

import net.minecraft.client.gui.DrawContext;
import net.sapfii.sapscreens.SapScreens;
import net.sapfii.sapscreens.UtilSS;
import net.sapfii.sapscreens.screens.widgets.interfaces.ClickableWidget;
import net.sapfii.sapscreens.screens.widgets.interfaces.ScrollableWidget;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class WidgetList extends WidgetContainer implements ScrollableWidget, ClickableWidget {
    int horizontalPadding, verticalPadding, itemPadding;
    float scrollAmount, visScrollAmount, maxScrollAmount, mouseAnchor, scrollAnchor;
    boolean barHovered, holdingBar, allowScrolling;
    float maxHeight;

    public static WidgetList create(Widget<?>... widgets) {
        WidgetList widget = new WidgetList();
        widget.widgets.addAll(List.of(widgets));
        widget.position.x = 0;
        widget.position.y = 0;
        widget.position.width = 100;
        widget.position.height = 100;
        widget.maxHeight = 100;
        widget.horizontalPadding = 10;
        widget.verticalPadding = 10;
        widget.itemPadding = 5;
        widget.visScrollAmount = 0;
        widget.allowScrolling = true;
        return widget;
    }

    @Override
    public void render(DrawContext context, float mouseX, float mouseY, float delta, Widget<?> renderer) {
        position.updateAnchors(renderer);
        barHovered = false;
        context.getMatrices().pushMatrix();
        context.getMatrices().translate(x(), y());
        AtomicInteger maxHeight = new AtomicInteger(verticalPadding * 2);
        widgets.forEach(widget -> maxHeight.set(maxHeight.get() + widget.height() + itemPadding));
        int newMaxHeight = maxHeight.get() - itemPadding;
        if (newMaxHeight < this.maxHeight) position.height = Math.clamp(newMaxHeight, this.maxHeight / 3, 99999);
        else position.height = this.maxHeight;
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
        this.maxHeight = height;
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
        if (!position.hovered) return;
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
        if (!position.hovered || barHovered) return;
        widgets.forEach(widget -> {
            if (widget instanceof ScrollableWidget w) {
                w.onScroll(amt);
                allowScrolling = !widget.isHovered();
                if (widget instanceof WidgetList l) {
                    allowScrolling = (l.isHovered() && (!l.canScroll() && l.allowScrolling)) || !l.isHovered();
                }
            }
        });
        if (allowScrolling) setScrollAmount((float) (scrollAmount - amt * 15));
    }

    public boolean canScroll() {
        return maxScrollAmount > 0;
    }


}
