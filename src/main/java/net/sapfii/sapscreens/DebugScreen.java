package net.sapfii.sapscreens;

import net.minecraft.text.Text;
import net.sapfii.sapscreens.screens.WidgetContainerScreen;
import net.sapfii.sapscreens.screens.widgets.ButtonWidget;
import net.sapfii.sapscreens.screens.widgets.TextDisplayWidget;
import net.sapfii.sapscreens.screens.widgets.WidgetList;
import net.sapfii.sapscreens.screens.widgets.WidgetPos;

public class DebugScreen extends WidgetContainerScreen {

    public DebugScreen() {
        super();
        container.addWidgets(
                new WidgetList(
                        new TextDisplayWidget(Text.literal("wow test")).withTextAlignment(TextDisplayWidget.TextAlignment.LEFT),
                        new ButtonWidget(),
                        new ButtonWidget(),
                        new ButtonWidget(),
                        new ButtonWidget(),
                        new ButtonWidget()
                ).withAlignment(WidgetPos.Alignment.CENTER).withDimensions(200, 200)
        );
    }


}
