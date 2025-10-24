package net.sapfii.sapscreens;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FeatureHandlerMM {
    public static List<FeatureMM> features = new ArrayList<>(List.of(
            new FeatureMM(),
            new FeatureMM()
    ));

    public static void forEach(Consumer<? super FeatureMM> action) {
        features.forEach(action);
    }

    public static void registerFeature(FeatureMM feature) {
        features.add(feature);
    }
}
