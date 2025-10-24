package net.sapfii.sapscreens.screens.widgets;

import net.minecraft.client.gui.DrawContext;
import net.sapfii.sapscreens.SapScreens;
import net.sapfii.sapscreens.screens.widgets.interfaces.ClickableWidget;
import net.sapfii.sapscreens.screens.widgets.interfaces.ScrollableWidget;

import java.util.ArrayList;
import java.util.List;

public class WidgetContainer extends Widget<WidgetContainer> implements ScrollableWidget, ClickableWidget {
    protected List<Widget<?>> widgets = new ArrayList<>();

    public static WidgetContainer create(List<Widget<?>> widgets) {
        WidgetContainer widget = new WidgetContainer();
        widget.widgets.addAll(widgets);
        widget.position.x = 0;
        widget.position.y = 0;
        widget.position.width = 0;
        widget.position.height = 0;
        return widget;
    }

    public static WidgetContainer create(Widget<?>... widgets) {
        return WidgetContainer.create(List.of(widgets));
    }

    @Override
    public void render(DrawContext context, float mouseX, float mouseY, float delta, Widget<?> renderer) {
        position.width = SapScreens.MC.getWindow().getScaledWidth() - x();
        position.height = SapScreens.MC.getWindow().getScaledHeight() - y();
        position.updateAnchors(renderer);
        widgets.forEach(widget -> widget.render(context, mouseX - position.x, mouseY - position.y, delta, this));
    }

    @Override
    protected WidgetContainer getThis() {
        return this;
    }

    public List<Widget<?>> getWidgets() {
        return widgets;
    }

    public WidgetContainer addWidget(Widget<?> widget) {
        widgets.add(widget);
        return getThis();
    }

    public WidgetContainer addWidgets(Widget<?>... widgets) {
        this.widgets.addAll(List.of(widgets));
        return getThis();
    }

    public WidgetContainer addWidgets(List<Widget<?>> widgets) {
        this.widgets.addAll(widgets);
        return getThis();
    }

    public WidgetContainer removeWidget(Widget<?> widget) {
        widgets.remove(widget);
        return getThis();
    }

    public WidgetContainer removeWidget(int index) {
        widgets.remove(index);
        return getThis();
    }

    public WidgetContainer clearWidgets() {
        widgets.clear();
        return getThis();
    }

    @Override
    public void onClick(float mouseX, float mouseY) {
        widgets.forEach(widget -> { if (widget instanceof ClickableWidget w) w.onClick(mouseX - x(), mouseY - y()); });
    }

    @Override
    public void onRelease(float mouseX, float mouseY) {
        widgets.forEach(widget -> { if (widget instanceof ClickableWidget w) w.onRelease(mouseX - x(), mouseY - y()); });
    }

    @Override
    public void onScroll(double amt) {
        widgets.forEach(widget -> { if (widget instanceof ScrollableWidget w) { w.onScroll(amt); } });
    }


    @Override
    public void updateHovered(float mouseX, float mouseY) {
        boolean inBoundsX = mouseX >= x() && mouseX < x() + width();
        boolean inBoundsY = mouseY >= y() && mouseY < y() + height();
        position.hovered = inBoundsX && inBoundsY;
        if (position.hovered) widgets.forEach(widget -> {
            widget.updateHovered(mouseX - x(), mouseY - y());
        });
    }

    public void resetChildrenHovered() {
        widgets.forEach(widget -> {
            widget.position.hovered = false;
            if (widget instanceof WidgetContainer container) {
                container.resetChildrenHovered();
            }
        });
    }
}
