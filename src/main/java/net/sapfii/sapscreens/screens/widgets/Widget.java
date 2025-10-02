package net.sapfii.sapscreens.screens.widgets;

import net.minecraft.client.gui.DrawContext;

public abstract class Widget<T extends Widget<T>> {
    protected float x, y = 0;
    protected float width, height = 16;
    public abstract void render(DrawContext context, float mouseX, float mouseY, float delta);
    public abstract T getThis();

    public int x() { return Math.round(x); }
    public int y() { return Math.round(y); }
    public int width() { return Math.round(width); }
    public int height() { return Math.round(height); }

    public abstract T withPosition(int x, int y);
    public abstract T withDimensions(int width, int height);
}
