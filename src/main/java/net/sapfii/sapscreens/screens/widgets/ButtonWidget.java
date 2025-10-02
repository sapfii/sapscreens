package net.sapfii.sapscreens.screens.widgets;

import net.minecraft.client.gui.DrawContext;

public class ButtonWidget extends Widget<ButtonWidget> {



    @Override
    public void render(DrawContext context, float mouseX, float mouseY, float delta) {

    }

    @Override
    public ButtonWidget getThis() {
        return this;
    }

    @Override
    public ButtonWidget withPosition(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    @Override
    public ButtonWidget withDimensions(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }
}
