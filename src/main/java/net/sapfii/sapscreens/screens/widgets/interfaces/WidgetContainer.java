package net.sapfii.sapscreens.screens.widgets.interfaces;

import net.minecraft.client.gui.DrawContext;
import net.sapfii.sapscreens.screens.widgets.Widget;

import java.util.List;

public interface WidgetContainer {
    List<Widget<?>> getChildren();
    void clearChildren();
    Widget<?> addWidget(Widget<?> widget);
    Widget<?> addWidgets(Widget<?>... widgets);
    Widget<?> addWidgets(List<Widget<?>> widgets);
    void removeWidget(Widget<?> widget);
    int getWidth();
    int getHeight();
    void render(DrawContext context, int mouseX, int mouseY, float delta);
}
