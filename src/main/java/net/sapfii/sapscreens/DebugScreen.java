package net.sapfii.sapscreens;

import net.minecraft.text.Text;
import net.sapfii.sapscreens.screens.WidgetContainerScreen;
import net.sapfii.sapscreens.screens.widgets.*;

public class DebugScreen extends WidgetContainerScreen {

    public DebugScreen() {
        super();
        Text test = Text.empty()
                .append(
                        Text.literal("if_player\n")
                                .append(Text.literal("."))
                                .append(Text.literal("HasPermission"))
                                .append(Text.literal("("))
                                .append(Text.literal("bl_\ntag").withColor(0xFF7FFF))
                                .append(Text.literal(")"))
                );
        System.out.println(test);
        container.addWidgets(
                new WidgetList(
                        new TextDisplayWidget(test)
                                .withTextAlignment(TextDisplayWidget.TextAlignment.LEFT),
                        new ButtonWidget().withTooltip(Text.literal("TOOLTIP!\ntest")),
                        new WidgetFolder(
                                new ButtonWidget().withTooltip(Text.literal("this is inside\nanother box!")),
                                new ButtonWidget(),
                                new ButtonWidget().withDimensions(0, 50),
                                new WidgetList(
                                        new ButtonWidget(),
                                        new ButtonWidget(),
                                        new ButtonWidget(),
                                        new ButtonWidget(),
                                        new ButtonWidget(),
                                        new ButtonWidget(),
                                        new ButtonWidget()
                                )
                        ).withTitle(Text.literal("Folder Title")),
                        new ButtonWidget().withText(Text.literal("this is a button with text")),
                        new ButtonWidget(),
                        new ButtonWidget(),
                        new ButtonWidget().withDimensions(0, 50)
                )
                        .withAlignment(WidgetPos.Alignment.CENTER)
                        .withDimensions(500, 200)
                        .withPadding(50, 10, 5)
        );
    }


}
