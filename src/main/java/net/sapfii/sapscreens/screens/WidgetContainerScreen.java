package net.sapfii.sapscreens.screens;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.sapfii.sapscreens.screens.widgets.WidgetContainer;
import net.sapfii.sapscreens.screens.widgets.interfaces.ScrollableWidget;

public class WidgetContainerScreen extends Screen {
    protected WidgetContainer container = new WidgetContainer();

    protected boolean showTooltip = false;

    public WidgetContainerScreen() {
        super(Text.literal(""));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        super.render(context, mouseX, mouseY, deltaTicks);
        showTooltip = false;
        container.render(context, mouseX, mouseY, deltaTicks, null);
//
//        if (showTooltip) {
//
//        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        container.getWidgets().forEach(widget -> { if (widget instanceof ScrollableWidget w) w.onScroll(verticalAmount); });
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }
}
