package net.sapfii.sapscreens.screens.widgets;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.Vector2f;
import net.sapfii.sapscreens.screens.widgets.interfaces.WidgetContainer;

public abstract class Widget<T extends Widget<T>> {
    protected WidgetContainer parent;
    protected int x, xOffset = 0;
    protected int y, yOffset = 0;
    protected int width = 16;
    protected int height = 16;
    protected boolean centerWidth = false;
    protected boolean centerHeight = false;

    protected Widget() {}

    public Vector2f position() {
        return new Vector2f(x, y);
    }

    public Vector2f offset() {
        return new Vector2f(xOffset, yOffset);
    }

    public Vector2f dimensions() {
        return new Vector2f(width, height);
    }

    public T withPosition(int x, int y) {
        this.x = x;
        this.y = y;
        return getThis();
    }

    public T withOffset(int x, int y) {
        this.xOffset = x;
        this.yOffset = y;
        return getThis();
    }

    public T withDimensions(int width, int height) {
        this.width = width;
        this.height = height;
        return getThis();
    }

    public T centerOnParent(boolean centerWidth, boolean centerHeight) {
        this.centerWidth = centerWidth;
        this.centerHeight = centerHeight;
        return getThis();
    }

    public abstract void render(DrawContext context, int mouseX, int mouseY, float delta);
    public abstract T getThis();
}
