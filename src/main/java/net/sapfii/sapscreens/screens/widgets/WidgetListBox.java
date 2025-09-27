package net.sapfii.sapscreens.screens.widgets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.sapfii.sapscreens.SapScreens;
import net.sapfii.sapscreens.screens.widgets.interfaces.ClickableWidget;
import net.sapfii.sapscreens.screens.widgets.interfaces.ScrollableWidget;
import net.sapfii.sapscreens.screens.widgets.interfaces.WidgetContainer;
import org.joml.Matrix3x2f;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class WidgetListBox extends Widget<WidgetListBox> implements ScrollableWidget, ClickableWidget, WidgetContainer {
    protected final List<Widget<?>> elements = new ArrayList<>();
    protected int verticalPadding = 5;
    protected int horizontalPadding = 5;
    protected int itemPadding = 5;
    protected int scrollAmount = 0;
    protected float subpixelScrollAmount = 0;
    protected int targetScrollAmount = 0;
    protected int maxScrollAmount = 0;
    protected float scrollScaleFactor = 0f;
    protected boolean hovered = false;
    protected boolean scrollHovered = false;
    protected boolean useScrollBinds = false;
    protected float mouseAnchor = 0f;
    protected int scrollAnchor = 0;
    protected boolean holdingScrollBar = false;

    public WidgetListBox(Widget<?>... widgets) {
        super();
        elements.addAll(List.of(widgets));
        for (Widget<?> widget : elements) {
            widget.parent = this;
        }
    }

    public WidgetListBox(List<Widget<?>> widgets) {
        super();
        elements.addAll(widgets);
        for (Widget<?> widget : elements) {
            widget.parent = this;
        }
    }

    public WidgetListBox withPadding(int verticalPadding, int horizontalPadding, int itemPadding) {
        this.verticalPadding = verticalPadding;
        this.horizontalPadding = horizontalPadding;
        this.itemPadding = itemPadding;
        return this;
    }

    @Override
    public List<Widget<?>> getChildren() {
        return elements;
    }

    @Override
    public void clearChildren() {
        elements.clear();
    }

    @Override
    public Widget<WidgetListBox> addWidget(Widget<?> widget) {
        elements.add(widget);
        widget.parent = this;
        return this;
    }

    @Override
    public Widget<WidgetListBox> addWidgets(Widget<?>... widgets) {
        elements.addAll(List.of(widgets));
        for (Widget<?> widget : elements) {
            widget.parent = this;
        }
        return this;
    }

    @Override
    public Widget<WidgetListBox> addWidgets(List<Widget<?>> widgets) {
        elements.addAll(widgets);
        for (Widget<?> widget : elements) {
            widget.parent = this;
        }
        return this;
    }

    @Override
    public void removeWidget(Widget<?> widget) {
        elements.remove(widget);
        widget.parent = null;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        checkHovered(context, mouseX, mouseY);
        context.enableScissor(0, 0, width, height);
        context.fill(0, 0, width, height, 0x55000000);
        int yOffset = 0;
        int lowestPoint = 0;
        for (Widget<?> child : this.getChildren()) {
            child.updateOrigin();
            child.x = horizontalPadding;
            child.y = verticalPadding + yOffset - scrollAmount;
            child.width = width - horizontalPadding * 2;
            context.getMatrices().pushMatrix();
            context.getMatrices().translate(child.x + child.originX, child.y + child.originY);
            child.render(context, mouseX, mouseY, delta);
            if (child instanceof WidgetContainer w && w instanceof ClickableWidget cw && cw instanceof ScrollableWidget sw) {
                if (cw.hovered() && sw.getMaxScrollAmount() > 0) {
                    useScrollBinds = false;
                }
            }
            context.getMatrices().popMatrix();
            yOffset += child.height + itemPadding;
            lowestPoint = Math.max(lowestPoint, verticalPadding + yOffset - itemPadding);
        }
        maxScrollAmount = lowestPoint - height + verticalPadding;
        scrollScaleFactor = (float) height / (lowestPoint + verticalPadding);
        if (lowestPoint > height - verticalPadding) {
            context.fill(width - 4, 0, width, height, 0xAA000000);
            context.fill(width - 4, (int) (scrollAmount * scrollScaleFactor), width, (int) (height - ((maxScrollAmount - scrollAmount) * scrollScaleFactor)), 0xFFFFFFFF);
            if (holdingScrollBar) {
                setScrollAmount((int) (scrollAnchor + (mouseY - mouseAnchor) / scrollScaleFactor));
            }
        }
        if (maxScrollAmount < 0) maxScrollAmount = 0;
        targetScrollAmount = Math.clamp(targetScrollAmount, 0, maxScrollAmount);
        subpixelScrollAmount = Math.clamp(subpixelScrollAmount, 0, maxScrollAmount);
        subpixelScrollAmount = SapScreens.lerp(subpixelScrollAmount, targetScrollAmount, 0.05f);
        scrollAmount = Math.round(subpixelScrollAmount);
        context.disableScissor();
    }

    @Override
    public WidgetListBox getThis() {
        return this;
    }

    @Override
    public void onScroll(double amount) {
        for (Widget<?> child : this.getChildren()) {
            if (child instanceof ScrollableWidget w) {
                w.onScroll(amount);
            }
        }
        if (!useScrollBinds) return;
        setScrollAmount((int) (scrollAmount - amount * 25));
    }

    @Override
    public int getScrollAmount() {
        return scrollAmount;
    }

    @Override
    public int getMaxScrollAmount() {
        return maxScrollAmount;
    }

    @Override
    public void setScrollAmount(int y) {
        targetScrollAmount = y;
        if (maxScrollAmount < 0) maxScrollAmount = 0;
        targetScrollAmount = Math.clamp(targetScrollAmount, 0, maxScrollAmount);
    }

    @Override
    public boolean hovered() {
        return hovered;
    }

    protected void checkHovered(DrawContext context, float mouseX, float mouseY) {
        context.getMatrices().pushMatrix();
        Matrix3x2f matrix3x2f = context.getMatrices().invert();
        Vector2f vector2f = matrix3x2f.transformPosition(mouseX, mouseY, new Vector2f());
        hovered = false;
        scrollHovered = false;
        useScrollBinds = false;
        if (!(vector2f.x >= -x) || !(vector2f.x < -x + parent.getWidth()) || !(vector2f.y >= -y) || !(vector2f.y < -y + parent.getHeight())) {
            context.getMatrices().popMatrix();
            return;
        }
        if (vector2f.x >= width - 4 && vector2f.x < width) {
            if (vector2f.y >= (int) (scrollAmount * scrollScaleFactor) && vector2f.y < (int) (height - ((maxScrollAmount - scrollAmount) * scrollScaleFactor))) {
                scrollHovered = true;
            }
        }
        if (vector2f.x >= 0 && vector2f.x < width) {
            if (vector2f.y >= 0 && vector2f.y < height) {
                hovered = true;
                useScrollBinds = true;
            }
        }
        context.getMatrices().popMatrix();
    }

    @Override
    public boolean onClick() {
        for (Widget<?> child : this.getChildren()) {
            if (child instanceof ClickableWidget w) {
                w.onClick();
            }
        }
        if (scrollHovered) {
            holdingScrollBar = true;
            scrollAnchor = scrollAmount;
            MinecraftClient client = MinecraftClient.getInstance();
            mouseAnchor = (float) (client.mouse.getY() * client.getWindow().getScaledHeight() / client.getWindow().getHeight());
        }
        return hovered;
    }

    @Override
    public void onRelease() {
        holdingScrollBar = false;
        for (Widget<?> child : this.getChildren()) {
            if (child instanceof ClickableWidget w) {
                w.onRelease();
            }
        }
    }
}
