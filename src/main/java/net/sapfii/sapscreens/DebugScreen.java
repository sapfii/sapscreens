package net.sapfii.sapscreens;

import net.sapfii.sapscreens.screens.WidgetContainerScreen;
import net.sapfii.sapscreens.screens.widgets.ButtonWidget;
import net.sapfii.sapscreens.screens.widgets.WidgetList;

public class DebugScreen extends WidgetContainerScreen {

    public DebugScreen() {
        super();
        container.addWidgets(
                new WidgetList(
                        new ButtonWidget(),
                        new ButtonWidget(),
                        new ButtonWidget(),
                        new ButtonWidget(),
                        new ButtonWidget()
                ).withPosition(100, 100)
        );
    }


}
