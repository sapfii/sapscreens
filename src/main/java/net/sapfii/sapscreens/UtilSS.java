package net.sapfii.sapscreens;

import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class UtilSS {
    public static List<Text> splitTextNewline(Text text) {
        List<Text> result = new ArrayList<>();
        List<Text> siblings = new ArrayList<>(text.getSiblings());
        siblings.addFirst(text.copyContentOnly().setStyle(text.getStyle()));
        MutableText currentStrand = Text.empty();
        for (Text sibling : siblings) {
            String[] parts = sibling.getString().split("\n", -1);
            Style style = sibling.getStyle();
            List<String> partsList = new ArrayList<>(List.of(parts));
            for (String part : partsList) {
                if (partsList.indexOf(part) != 0) {
                    result.add(currentStrand);
                    currentStrand = Text.empty();
                }
                currentStrand.append(Text.literal(part).setStyle(style));
            }
        }
        result.add(currentStrand);
        return result;
    }

    public static float lerp(float a, float b, float t) {
        t = Math.clamp(t, 0, 1);
        return a + t * (b - a);
    }

}
