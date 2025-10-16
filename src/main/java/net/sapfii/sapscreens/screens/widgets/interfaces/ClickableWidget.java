package net.sapfii.sapscreens.screens.widgets.interfaces;

import net.sapfii.sapscreens.screens.widgets.ButtonWidget;

public interface ClickableWidget {
    void onClick(float mouseX, float mouseY);
    void onRelease(float mouseX, float mouseY);

    interface ClickProcessor {
        void onClick(ButtonWidget button);
    }
}
