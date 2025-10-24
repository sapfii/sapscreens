package net.sapfii.sapscreens;

import net.minecraft.text.Text;
import net.sapfii.sapscreens.screens.WidgetContainerScreen;
import net.sapfii.sapscreens.screens.widgets.*;

import java.util.ArrayList;
import java.util.List;

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
//        container.addWidgets(
//                WidgetList.create(
//                        TextDisplayWidget.create(test)
//                                .withTextAlignment(TextDisplayWidget.TextAlignment.LEFT),
//                        ButtonWidget.create().withTooltip(Text.literal("TOOLTIP!\ntest\ntons of lines\nto view and break\nqwq")),
//                        WidgetList.create(
//                                ButtonWidget.create().withTooltip(Text.literal("this is inside\nanother box!")),
//                                ButtonWidget.create(),
//                                ButtonWidget.create().withDimensions(0, 50),
//                                WidgetList.create(
//                                        ButtonWidget.create(),
//                                        ButtonWidget.create(),
//                                        ButtonWidget.create(),
//                                        ButtonWidget.create(),
//                                        ButtonWidget.create(),
//                                        ButtonWidget.create(),
//                                        ButtonWidget.create()
//                                )
//                        ),
//                        ButtonWidget.create().withText(Text.literal("this is a button with text")),
//                        ButtonWidget.create(),
//                        ButtonWidget.create(),
//                        ButtonWidget.create().withDimensions(0, 50)
//                )
//                        .withAlignment(WidgetPos.Alignment.CENTER)
//                        .withDimensions(500, 200)
//                        .withPadding(50, 10, 5)
//        );
//        container.addWidgets(
//                WidgetList.create(
//                        WidgetFolder.create(
//                                WidgetList.create(
//                                        TextDisplayWidget.create(Text.literal("pages")),
//                                        WidgetList.create(
//                                                WidgetList.create(
//                                                        TextDisplayWidget.create(Text.literal("Dev; dev\nPlay; play\nBuild; build"))
//                                                )
//                                        )
//                                )
//                        )
//                                .withTitle(Text.literal("command_wheel"))
//                                .withDimensions(0, 0)
//                )
//                        .withDimensions(500, 300)
//                        .withAlignment(WidgetPos.Alignment.CENTER)
//                        .withPadding(50, 20, 2)
//                        .wrapToContents(true)
//        );
    }

    @Override
    protected void init() {
        WidgetList settingsList = WidgetList.create().withAlignment(WidgetPos.Alignment.CENTER).withDimensions(400, 200);
        FeatureHandlerMM.forEach(feature -> {
            FeatureConfig config = feature.getConfig();

            WidgetFolder folder = WidgetFolder.create()
                    .withTitle(Text.translatable(feature.getId()))
                    .withDimensions(0, 0);

            config.forEach(configValue -> {
                WidgetList setting = WidgetList.create(
                        TextDisplayWidget.create(Text.translatable(configValue.getKey())),
                        TextDisplayWidget.create(Text.literal("name; command"))
                );
                folder.addWidget(setting);
            });

            settingsList.addWidget(folder);
        });
        container.addWidget(settingsList);
        super.init();
    }
}
