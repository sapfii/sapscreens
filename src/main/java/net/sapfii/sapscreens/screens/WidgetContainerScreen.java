package net.sapfii.sapscreens.screens;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.sapfii.sapscreens.screens.widgets.Tooltip;
import net.sapfii.sapscreens.screens.widgets.WidgetContainer;
import net.sapfii.sapscreens.screens.widgets.interfaces.ClickableWidget;
import net.sapfii.sapscreens.screens.widgets.interfaces.ScrollableWidget;

public class WidgetContainerScreen extends Screen {
    protected WidgetContainer container = new WidgetContainer();

    protected boolean showTooltip = false;
    protected Tooltip tooltip = new Tooltip();

    public WidgetContainerScreen() {
        super(Text.literal(""));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        super.render(context, mouseX, mouseY, deltaTicks);
        showTooltip = false;
        container.render(context, mouseX, mouseY, deltaTicks, null);
        if (showTooltip) {
            context.getMatrices().pushMatrix();
            context.getMatrices().translate((mouseX + 5), mouseY);
            tooltip.render(context, mouseX, mouseY, deltaTicks, null);
            context.getMatrices().popMatrix();
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        container.getWidgets().forEach(widget -> { if (widget instanceof ScrollableWidget w) w.onScroll(verticalAmount); });
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        container.getWidgets().forEach(widget -> { if (widget instanceof ClickableWidget w) w.onClick((float) mouseX, (float) mouseY); });
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        container.getWidgets().forEach(widget -> { if (widget instanceof ClickableWidget w) w.onRelease((float) mouseX, (float) mouseY); });
        return super.mouseReleased(mouseX, mouseY, button);
    }

    public void showToolTip(boolean showTooltip) {
        this.showTooltip = showTooltip;
    }

    public void setTooltipText(Text tooltipText) {
        tooltip.setTooltipText(tooltipText);
    }
}
