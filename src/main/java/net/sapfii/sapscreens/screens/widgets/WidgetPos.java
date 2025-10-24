package net.sapfii.sapscreens.screens.widgets;

import net.minecraft.client.gui.screen.Screen;
import net.sapfii.sapscreens.SapScreens;

public class WidgetPos {
    public enum Alignment {
        TOPLEFT(""), TOP("hcenter"), TOPRIGHT("right"),
        LEFT("vcenter"), CENTER("hcenter_vcenter"), RIGHT("right_vcenter"),
        BOTTOMLEFT("bottom"), BOTTOM("bottom_hcenter"), BOTTOMRIGHT("bottom_right");
        private final String id;
        Alignment(String id) { this.id = id; }
        public String getId() { return this.id; }
    }
    protected float x, y;
    protected float anchorX, anchorY;
    protected float width, height;
    protected boolean useRendererWidth, useRendererHeight;
    protected Alignment alignment = Alignment.TOPLEFT;
    protected boolean hovered;

    public void updateAnchors(Widget<?> renderer) {
        anchorX = 0;
        anchorY = 0;
        Screen screen = SapScreens.MC.currentScreen;
        if (screen == null) return;
        int parentWidth = renderer == null ? screen.width : renderer.width();
        int parentHeight = renderer == null ? screen.height : renderer.height();
        if (alignment.getId().contains("hcenter")) anchorX = (float) parentWidth / 2 - width / 2;
        if (alignment.getId().contains("right")) anchorX = parentWidth - width;
        if (alignment.getId().contains("vcenter")) anchorY = (float) parentHeight / 2 - height / 2;
        if (alignment.getId().contains("bottom")) anchorY = parentHeight - height;
    }

}
