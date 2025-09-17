package net.sapfii.sapscreens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.sapfii.sapscreens.screens.widgets.ButtonWidget;
import net.sapfii.sapscreens.screens.widgets.TextDisplayWidget;
import net.sapfii.sapscreens.screens.widgets.WidgetContainerScreen;
import net.sapfii.sapscreens.screens.widgets.WidgetListBox;

public class TestScreen extends WidgetContainerScreen {
    public TestScreen(Screen previousScreen) {
        super(previousScreen);
        addWidgets(
                new WidgetListBox(
                        new WidgetListBox(
                                new TextDisplayWidget(Text.literal("text title"), 7, TextDisplayWidget.Alignment.CENTER),
                                new ButtonWidget(Text.literal("0th button")).withDimensions(100, 20),
                                new ButtonWidget(Text.literal("1000th button")).withDimensions(100, 30),
                                new WidgetListBox(
                                        new TextDisplayWidget(Text.literal("boxception title"), 7, TextDisplayWidget.Alignment.CENTER),
                                        new ButtonWidget(Text.literal("button")).withDimensions(100, 20),
                                        new ButtonWidget(Text.literal("button")).withDimensions(100, 20),
                                        new ButtonWidget(Text.literal("button")).withDimensions(100, 20)
                                )
                                        .withDimensions(100, 50)
                                        .withPadding(7, 7, 3)
                        )
                                .withDimensions(100, 50)
                                .withPadding(7, 7, 3),
                        new ButtonWidget(Text.literal("first button")).withDimensions(100, 20),
                        new ButtonWidget(Text.literal("second button")).withDimensions(5000, 50),
                        new ButtonWidget(Text.literal("third button")).withDimensions(0, 40),
                        new TextDisplayWidget(Text.literal("this is a long text to see if the wrapping functions properly, now i am going to newline to see if it works\n(i hope it does)"), 2, TextDisplayWidget.Alignment.LEFT),
                        new TextDisplayWidget(Text.literal("right alignment"), 7, TextDisplayWidget.Alignment.RIGHT)
                )
                        .withPosition(25, 50)
                        .withDimensions(0, 250)
                        .withPadding(10, 100, 5)
                        .centerOnParent(true, true)
        );
    }
}
