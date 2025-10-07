package net.sapfii.sapscreens.screens.widgets.interfaces;

public interface ClickableWidget {
    void onClick();
    void onRelease();

    interface ClickProcessor {
        void onClick();
    }
}
