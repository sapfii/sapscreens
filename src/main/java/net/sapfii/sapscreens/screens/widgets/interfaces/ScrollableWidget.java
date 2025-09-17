package net.sapfii.sapscreens.screens.widgets.interfaces;

public interface ScrollableWidget extends WidgetContainer {
    void onScroll(double amount);
    int getScrollAmount();
    int getMaxScrollAmount();

    void setScrollAmount(int y);
}
