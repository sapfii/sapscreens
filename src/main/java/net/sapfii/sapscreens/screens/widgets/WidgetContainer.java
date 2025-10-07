package net.sapfii.sapscreens.screens.widgets;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.sapfii.sapscreens.SapScreens;

import java.util.ArrayList;
import java.util.List;

public class WidgetContainer extends Widget<WidgetContainer> {
    protected List<Widget<?>> widgets = new ArrayList<>();

    public WidgetContainer(Widget<?>... widgets) {
        this(List.of(widgets));
    }
    
    public WidgetContainer(List<Widget<?>> widgets) {
        this.widgets.addAll(widgets);
        position.x = 0;
        position.y = 0;
        position.width = 0;
        position.height = 0;
    }

    @Override
    public void render(DrawContext context, float mouseX, float mouseY, float delta, WidgetContainer renderer) {
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
}
