package net.sapfii.sapscreens.screens.widgets;

import net.minecraft.client.gui.DrawContext;

import java.util.List;

public class WidgetList extends WidgetContainer {
    int horizontalPadding, verticalPadding, itemPadding;
    float scrollAmount, visScrollAmount, maxScrollAmount;

    public WidgetList(Widget<?>... widgets) {
        this.widgets.addAll(List.of(widgets));
        this.x = 0;
        this.y = 0;
        this.width = 100;
        this.height = 100;
        this.horizontalPadding = 10;
        this.verticalPadding = 10;
        this.itemPadding = 5;
    }

    @Override
    public void render(DrawContext context, float mouseX, float mouseY, float delta) {
        context.getMatrices().pushMatrix();
        context.getMatrices().translate(x, y);
        context.enableScissor(0, 0, (int) width, (int) height);
        context.fill(0, 0, (int) width, (int) height, 0x66000000);
        int currentOffset = verticalPadding + (int) scrollAmount;
        for (Widget<?> widget : widgets) {
            widget.x = horizontalPadding;
            widget.width = width - horizontalPadding * 2;
            widget.y = currentOffset;
            currentOffset += (int) (widget.height + itemPadding);
            widget.render(context, mouseX - x, mouseY - y, delta);
        }
        context.disableScissor();
        if (currentOffset - itemPadding > height) {
            maxScrollAmount = currentOffset - itemPadding - height;
            context.fill((int) width, 0, (int) width + 3, (int) height, 0x88000000);

        }
        context.getMatrices().popMatrix();
    }

    public void setScrollAmount(float amt) {
        scrollAmount = Math.clamp(amt, 0, maxScrollAmount);
    }
}
