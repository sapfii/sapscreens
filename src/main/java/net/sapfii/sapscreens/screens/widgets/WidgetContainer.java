package net.sapfii.sapscreens.screens.widgets;

import net.minecraft.client.gui.DrawContext;

import java.util.ArrayList;
import java.util.List;

public class WidgetContainer extends Widget<WidgetContainer> {
    protected List<Widget<?>> widgets = new ArrayList<>();

    public WidgetContainer(Widget<?>... widgets) {
        this.widgets.addAll(List.of(widgets));
        this.x = 0;
        this.y = 0;
        this.width = 0;
        this.height = 0;
    }

    @Override
    public void render(DrawContext context, float mouseX, float mouseY, float delta) {
        widgets.forEach(widget -> widget.render(context, mouseX - x, mouseY - y, delta));
    }

    @Override
    public WidgetContainer withPosition(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    @Override
    public WidgetContainer withDimensions(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public List<Widget<?>> getWidgets() {
        return widgets;
    }

    public WidgetContainer addWidget(Widget<?> widget) {
        widgets.add(widget);
        return this;
    }

    public WidgetContainer addWidgets(Widget<?>... widgets) {
        this.widgets.addAll(List.of(widgets));
        return this;
    }

    public WidgetContainer addWidgets(List<Widget<?>> widgets) {
        this.widgets.addAll(widgets);
        return this;
    }

    public WidgetContainer removeWidget(Widget<?> widget) {
        widgets.remove(widget);
        return this;
    }

    public WidgetContainer removeWidget(int index) {
        widgets.remove(index);
        return this;
    }
}
