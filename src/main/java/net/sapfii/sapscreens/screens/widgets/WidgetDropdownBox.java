package net.sapfii.sapscreens.screens.widgets;

import net.minecraft.client.gui.DrawContext;
import net.sapfii.sapscreens.screens.widgets.interfaces.ClickableWidget;
import net.sapfii.sapscreens.screens.widgets.interfaces.WidgetContainer;

import java.util.ArrayList;
import java.util.List;

public class WidgetDropdownBox extends Widget<WidgetDropdownBox> implements ClickableWidget, WidgetContainer {
    private List<Widget<?>> elements = new ArrayList<>();

    @Override
    public List<Widget<?>> getChildren() {
        return elements;
    }

    @Override
    public void clearChildren() {
        elements.clear();
    }

    @Override
    public Widget<?> addWidget(Widget<?> widget) {
        return this;
    }

    @Override
    public Widget<?> addWidgets(Widget<?>... widgets) {
        return this;
    }

    @Override
    public Widget<?> addWidgets(List<Widget<?>> widgets) {
        return this;
    }

    @Override
    public void removeWidget(Widget<?> widget) {
        elements.remove(widget);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

    }

    @Override
    public WidgetDropdownBox getThis() {
        return this;
    }

    @Override
    public boolean hovered() {
        return false;
    }

    @Override
    public boolean onClick() {
        return false;
    }

    @Override
    public void onRelease() {

    }
}
