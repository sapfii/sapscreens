package net.sapfii.sapscreens.screens.widgets;

import net.minecraft.client.gui.DrawContext;

public abstract class Widget<T extends Widget<T>> {
    public enum Alignment {
        TOPLEFT(""), TOP("vcenter"), TOPRIGHT("right"),
        LEFT("vcenter"), CENTER("hcenter_vcenter"), RIGHT("right_vcenter"),
        BOTTOMLEFT("bottom"), BOTTOM("bottom_hcenter"), BOTTOMRIGHT("bottom_right");
        final String id;
        Alignment(String id) { this.id = id; }
        public String getId() { return this.id; }
    }
    protected float x, y;
    protected float width, height;
    protected Alignment alignment;
    public abstract void render(DrawContext context, float mouseX, float mouseY, float delta, WidgetContainer renderer);

    public int x() { return Math.round(x); }
    public int y() { return Math.round(y); }
    public int width() { return Math.round(width); }
    public int height() { return Math.round(height); }

    public abstract T withPosition(int x, int y);
    public abstract T withDimensions(int width, int height);
    public abstract T withAlignment(Alignment alignment);
}
