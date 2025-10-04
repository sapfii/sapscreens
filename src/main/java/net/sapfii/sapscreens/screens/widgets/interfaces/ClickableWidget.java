package net.sapfii.sapscreens.screens.widgets.interfaces;

public interface ClickableWidget {
    void onClick();
    void onRelease();

    public interface ClickProcessor {
        void onClick();
    }
}
