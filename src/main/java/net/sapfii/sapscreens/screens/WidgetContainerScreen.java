package net.sapfii.sapscreens.screens;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.sapfii.sapscreens.screens.widgets.Widget;
import net.sapfii.sapscreens.screens.widgets.WidgetContainer;

public class WidgetContainerScreen extends Screen {
    protected WidgetContainer container = new WidgetContainer();

    public WidgetContainerScreen() {
        super(Text.literal(""));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        super.render(context, mouseX, mouseY, deltaTicks);
        container.render(context, mouseX, mouseY, deltaTicks);
    }
}
