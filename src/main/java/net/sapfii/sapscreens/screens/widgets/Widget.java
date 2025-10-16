package net.sapfii.sapscreens.screens.widgets;

import net.minecraft.client.gui.DrawContext;

public abstract class Widget<T extends Widget<?>> {
    protected final WidgetPos position = new WidgetPos();

    public abstract void render(DrawContext context, float mouseX, float mouseY, float delta, Widget<?> renderer);

    public int x() { return Math.round(position.x + position.anchorX); }
    public int y() { return Math.round(position.y + position.anchorY); }
    public int width() { return Math.round(position.width); }
    public int height() { return Math.round(position.height); }

    protected abstract T getThis();

    public T withPosition(int x, int y) {
        position.x = x;
        position.y = y;
        return getThis();
    }
    public T withDimensions(int width, int height) {
        position.width = width;
        position.height = height;
        return getThis();
    }
    public T withAlignment(WidgetPos.Alignment alignment) {
        position.alignment = alignment;
        return getThis();
    }
}
