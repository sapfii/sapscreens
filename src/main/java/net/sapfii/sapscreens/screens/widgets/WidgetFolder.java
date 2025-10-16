package net.sapfii.sapscreens.screens.widgets;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.sapfii.sapscreens.SapScreens;
import net.sapfii.sapscreens.UtilSS;
import net.sapfii.sapscreens.screens.widgets.interfaces.ScrollableWidget;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class WidgetFolder extends WidgetList {
    protected boolean open = false;
    protected ButtonWidget extendoPetroleum = new ButtonWidget().withEvent(
            button -> {
                open = !open;
                if (open) button.withText(Text.literal("^"));
                else button.withText(Text.literal("↓"));
            }
    ).withText(Text.literal("↓"));
    protected float visHeight = 22;
    protected TextDisplayWidget title = new TextDisplayWidget(Text.empty()).withTextAlignment(TextDisplayWidget.TextAlignment.CENTER);

    public WidgetFolder(Widget<?>... widgets) {
        super(widgets);
    }

    @Override
    public void render(DrawContext context, float mouseX, float mouseY, float delta, Widget<?> renderer) {
        position.updateAnchors(renderer);
        hovered = isHovered(mouseX, mouseY, renderer);
        context.getMatrices().pushMatrix();
        context.getMatrices().translate(x(), y());
        AtomicInteger maxHeight = new AtomicInteger(verticalPadding * 3 + 16);
        widgets.forEach(widget -> maxHeight.set(maxHeight.get() + widget.height()));
        position.height = maxHeight.get();
        if (!open) position.height = 22;
        visHeight = UtilSS.lerp(visHeight, position.height, 0.05f);
        context.enableScissor(0, 0, width(), Math.round(visHeight));
        context.fill(0, 0, width(), Math.round(visHeight), 0x44000000);
        extendoPetroleum.withPosition(3, 3);
        extendoPetroleum.render(context, mouseX - x(), mouseY - y(), delta, this);
        title.withDimensions(width(), 16);
        title.withPosition(0, 11 - SapScreens.MC.textRenderer.fontHeight / 2);
        title.render(context, mouseX, mouseY, delta, this);
        int currentOffset = verticalPadding + 16;
        for (Widget<?> widget : widgets) {
            widget.position.x = horizontalPadding;
            widget.position.width = width() - horizontalPadding * 2;
            widget.position.y = currentOffset;
            currentOffset += widget.height() + itemPadding;
            widget.render(context, mouseX - x(), mouseY - y(), delta, this);
        }
        context.disableScissor();
        context.getMatrices().popMatrix();
    }

    @Override
    public int height() {
        return Math.round(visHeight);
    }

    @Override
    public void onClick(float mouseX, float mouseY) {
        extendoPetroleum.onClick(mouseX, mouseY);
        super.onClick(mouseX, mouseY);
    }

    @Override
    public void onScroll(double amt) {
        allowScrolling = true;
        if (!hovered) return;
        widgets.forEach(widget -> {
            if (widget instanceof ScrollableWidget w) {
                w.onScroll(amt);
                allowScrolling = !w.hovered();
                if (widget instanceof WidgetList l) {
                    allowScrolling = (l.hovered() && (!l.canScroll() && l.allowScrolling)) || !l.hovered();
                }
            }
        });
    }

    @Override
    public void setScrollAmount(float amt) {
        // No scrolling functionality.
    }

    @Override
    protected WidgetFolder getThis() {
        return this;
    }

    @Override
    public WidgetFolder withAlignment(WidgetPos.Alignment alignment) {
        super.withAlignment(alignment);
        return getThis();
    }

    @Override
    public WidgetFolder withDimensions(int width, int height) {
        super.withDimensions(width, height);
        return getThis();
    }

    @Override
    public WidgetFolder withPosition(int x, int y) {
        super.withPosition(x, y);
        return getThis();
    }

    public WidgetFolder withTitle(Text title) {
        this.title.setLines(List.of(title));
        return getThis();
    }
}
