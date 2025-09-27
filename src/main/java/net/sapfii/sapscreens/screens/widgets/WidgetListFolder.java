package net.sapfii.sapscreens.screens.widgets;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.sapfii.sapscreens.SapScreens;
import net.sapfii.sapscreens.screens.widgets.interfaces.ClickableWidget;
import net.sapfii.sapscreens.screens.widgets.interfaces.ScrollableWidget;
import net.sapfii.sapscreens.screens.widgets.interfaces.WidgetContainer;

public class WidgetListFolder extends WidgetListBox {
    protected boolean open = false;
    protected int fullHeight = 16;
    protected int targetHeight = 16;
    protected float subpixelHeight = 16f;
    protected TextDisplayWidget title = new TextDisplayWidget(Text.literal(""), 0, TextDisplayWidget.Alignment.CENTER);
    protected ButtonWidget dropdownButton = new ButtonWidget(Text.literal("⌄"), (button) -> {
        open = !open;
    }).withDimensions(16, 16);

    public WidgetListFolder(Widget<?>... widgets) {
        super(widgets);
        dropdownButton.parent = this;
        title.parent = this;
    }


    public WidgetListBox withTitle(Text title) {
        this.title.withText(title);
        return getThis();
    }

    @Override
    public WidgetListBox withDimensions(int width, int height) {
        this.width = width;
        this.fullHeight = height;
        return getThis();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        dropdownButton = open ? dropdownButton.withText(Text.literal("^")) : dropdownButton.withText(Text.literal("∨"));
        checkHovered(context, mouseX, mouseY);
        context.enableScissor(0, 0, width, height);
        targetHeight = open ? fullHeight : dropdownButton.height + verticalPadding * 2;
        subpixelHeight = SapScreens.lerp(subpixelHeight, targetHeight, 0.05f);
        height = Math.round(subpixelHeight);
        context.fill(0, 0, width, height, 0x55000000);
        title.x = 0;
        title.y = verticalPadding + 4;
        title.width = width;
        title.height = 16;
        context.getMatrices().pushMatrix();
        context.getMatrices().translate(title.x, title.y);
        title.render(context, mouseX, mouseY, delta);
        context.getMatrices().popMatrix();

        dropdownButton.x = horizontalPadding;
        dropdownButton.y = verticalPadding;
        context.getMatrices().pushMatrix();
        context.getMatrices().translate(dropdownButton.x, dropdownButton.y);
        dropdownButton.render(context, mouseX, mouseY, delta);
        context.getMatrices().popMatrix();
        int yOffset = 0;
        int lowestPoint = 0;
            yOffset += dropdownButton.height + itemPadding;
            lowestPoint = Math.max(lowestPoint, verticalPadding + yOffset - itemPadding);
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
            if (!open) maxScrollAmount = 0;
            scrollScaleFactor = (float) height / (lowestPoint + verticalPadding);
            if (lowestPoint > height - verticalPadding && open && height == fullHeight) {
                context.fill(width - 4, 0, width, height, 0xAA000000);
                context.fill(width - 4, (int) (scrollAmount * scrollScaleFactor), width, (int) (height - ((maxScrollAmount - scrollAmount) * scrollScaleFactor)), 0xFFFFFFFF);
                if (holdingScrollBar) {
                    setScrollAmount((int) (scrollAnchor + (mouseY - mouseAnchor) / scrollScaleFactor));
                }
        }
        context.disableScissor();
    }

    @Override
    public boolean onClick() {
        dropdownButton.onClick();
        return super.onClick();
    }
}
