package net.sapfii.sapscreens;

import net.sapfii.sapscreens.screens.widgets.Widget;
import net.sapfii.sapscreens.screens.widgets.WidgetList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FeatureConfig {
    private List<ConfigValue> settings = new ArrayList<>(List.of(
            new ConfigValue(),
            new ConfigValue()
    ));

    public void forEach(Consumer<? super ConfigValue> action) {
        settings.forEach(action);
    }
}
