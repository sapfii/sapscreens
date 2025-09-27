package net.sapfii.sapscreens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.sapfii.sapscreens.screens.widgets.*;

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
                        new WidgetListFolder(
                                new ButtonWidget(Text.literal("testing")),
                                new ButtonWidget(Text.literal("testing")),
                                new ButtonWidget(Text.literal("123"))
                        ).withTitle(Text.literal("BUTTONS!")).withDimensions(100, 100),
                        new ButtonWidget(Text.literal("third button")).withDimensions(0, 40),
                        new TextDisplayWidget(Text.literal("this is a long text to see if the wrapping functions properly, now i am going to newline to see if it works\n(i hope it does)"), 2, TextDisplayWidget.Alignment.LEFT),
                        new TextDisplayWidget(Text.literal("right alignment"), 7, TextDisplayWidget.Alignment.RIGHT),
                        new WidgetListFolder(
                                new ButtonWidget(Text.literal("testing 2")),
                                new ButtonWidget(Text.literal("testing 2")),
                                new ButtonWidget(Text.literal("123 4"))
                        ).withTitle(Text.literal("BUTTONS 2!")).withDimensions(100, 100),
                        new TextDisplayWidget(Text.literal("this is a long text to see if the wrapping functions properly, now i am going to newline to see if it works\n(i hope it does)"), 2, TextDisplayWidget.Alignment.LEFT),
                        new TextDisplayWidget(Text.literal("right alignment"), 7, TextDisplayWidget.Alignment.RIGHT)
                )
                        .withPosition(0, 0)
                        .withDimensions(150, 100)
                        .withPadding(10, 10, 5)
                        .withAlignment(Widget.Alignment.CENTER)
                        .useParentDimensions(true, true)
        );
    }
}
