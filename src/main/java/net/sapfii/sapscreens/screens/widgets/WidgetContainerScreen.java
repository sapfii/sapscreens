package net.sapfii.sapscreens.screens.widgets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.sapfii.sapscreens.SapScreens;
import net.sapfii.sapscreens.screens.widgets.interfaces.ClickableWidget;
import net.sapfii.sapscreens.screens.widgets.interfaces.ScrollableWidget;
import net.sapfii.sapscreens.screens.widgets.interfaces.WidgetContainer;

import java.util.ArrayList;
import java.util.List;

public class WidgetContainerScreen extends Screen implements WidgetContainer {
    protected List<Widget<?>> elements = new ArrayList<>();
    protected final Screen previousScreen;
    protected float openAnimation = -15;
    protected float animationProgress = 0.0f;
    private double lastRender = System.currentTimeMillis();

    public WidgetContainerScreen(Screen previousScreen) {
        super(Text.literal(""));
        this.previousScreen = previousScreen;
        lastRender = System.currentTimeMillis();
    }

    @Override
    public void close() {
        super.close();
        MinecraftClient.getInstance().setScreen(previousScreen);
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
    public Widget<?> addWidget(Widget<?> widget) {
        elements.add(widget);
        widget.parent = this;
        return null;
    }

    @Override
    public Widget<?> addWidgets(Widget<?>... widgets) {
        elements.addAll(List.of(widgets));
        for (Widget<?> widget : elements) {
            widget.parent = this;
        }
        return null;
    }

    @Override
    public Widget<?> addWidgets(List<Widget<?>> widgets) {
        elements.addAll(widgets);
        for (Widget<?> widget : elements) {
            widget.parent = this;
        }
        return null;
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
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        super.render(context, mouseX, mouseY, deltaTicks);
        float delta = (float) (System.currentTimeMillis() - lastRender);
        if (animationProgress < 1) {
            context.getMatrices().pushMatrix();
            context.getMatrices().translate(0, Math.round(openAnimation));

            openAnimation = SapScreens.lerp(openAnimation, 0, animationProgress);
            animationProgress += 0.0001 * delta;
        }
        for (Widget<?> child : this.getChildren()) {
            child.parent = this;
            child.updateOrigin();
            context.getMatrices().pushMatrix();
            context.getMatrices().translate(child.x + child.originX, child.y + child.originY);
            child.render(context, mouseX, mouseY, deltaTicks);
            context.getMatrices().popMatrix();
        }
        if (animationProgress < 1) {
            context.getMatrices().popMatrix();
        }
        lastRender = System.currentTimeMillis();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (Widget<?> child : this.getChildren()) {
            if (child instanceof ClickableWidget w) {
                w.onClick();
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        for (Widget<?> child : this.getChildren()) {
            if (child instanceof ClickableWidget w) {
                w.onRelease();
            }
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        for (Widget<?> child : this.getChildren()) {
            if (child instanceof ScrollableWidget w) {
                w.onScroll(verticalAmount);
            }
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public void resize(MinecraftClient client, int width, int height) {
        this.width = width;
        this.height = height;
    }
}
