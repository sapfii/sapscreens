package net.sapfii.sapscreens.screens.widgets;

import net.minecraft.client.gui.DrawContext;

public abstract class Widget<T extends Widget<T>> {
    protected float x, y;
    protected float width, height;
    public abstract void render(DrawContext context, float mouseX, float mouseY, float delta);

    public int x() { return Math.round(x); }
    public int y() { return Math.round(y); }
    public int width() { return Math.round(width); }
    public int height() { return Math.round(height); }

    public abstract T withPosition(int x, int y);
    public abstract T withDimensions(int width, int height);
}
