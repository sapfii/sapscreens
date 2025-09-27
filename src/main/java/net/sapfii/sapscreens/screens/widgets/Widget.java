package net.sapfii.sapscreens.screens.widgets;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.Vector2f;
import net.sapfii.sapscreens.screens.widgets.interfaces.WidgetContainer;

public abstract class Widget<T extends Widget<T>> {

    protected Alignment alignment = Alignment.TOPLEFT;
    public enum Alignment {
        TOPLEFT("top_left"), TOP("top_hcenter"), TOPRIGHT("top_right"),
        LEFT("left_vcenter"), CENTER("vcenter_hcenter"), RIGHT("right_vcenter"),
        BOTTOMLEFT("bottom_left"), BOTTOM("bottom_hcenter"), BOTTOMRIGHT("bottom_right");
        private String id;
        private Alignment(String id) {
            this.id = id;
        }
        public String getId() {
            return this.id;
        }
    }

    protected WidgetContainer parent;
    protected int originX, originY = 0;
    protected int x, xOffset = 0;
    protected int y, yOffset = 0;
    protected int width, setWidth = 16;
    protected int height, setHeight = 16;
    protected boolean useParentWidth = false;
    protected boolean useParentHeight = false;

    protected Widget() {
        withDimensions(16, 16);
    }

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
        this.setWidth = width;
        this.height = height;
        this.setHeight = height;
        return getThis();
    }

    public T withAlignment(Alignment alignment) {
        this.alignment = alignment;
        return getThis();
    }

    public T useParentDimensions(boolean useParentWidth, boolean useParentHeight) {
        this.useParentWidth = useParentWidth;
        this.useParentHeight = useParentHeight;
        return getThis();
    }

    protected void updateOrigin() {
        if (useParentWidth) width = parent.getWidth() - (setWidth);
        if (useParentHeight) height = parent.getHeight() - (setHeight);
        if (alignment.getId().contains("vcenter")) originY = parent.getHeight() / 2 - height / 2;
        if (alignment.getId().contains("hcenter")) originX = parent.getWidth() / 2 - width / 2;
        if (alignment.getId().contains("top")) originY = 0;
        if (alignment.getId().contains("bottom")) originY = parent.getHeight() - height;
        if (alignment.getId().contains("left")) originX = 0;
        if (alignment.getId().contains("right")) originX = parent.getWidth() - width;
    }

    public abstract void render(DrawContext context, int mouseX, int mouseY, float delta);
    public abstract T getThis();
}
