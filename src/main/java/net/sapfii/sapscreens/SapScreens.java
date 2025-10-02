package net.sapfii.sapscreens;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;
import net.sapfii.sapscreens.command.SapScreensCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SapScreens implements ClientModInitializer {
    public static MinecraftClient MC = MinecraftClient.getInstance();
    public static final String MOD_ID = "sapscreens";
    public static final Logger LOGGER = LoggerFactory.getLogger("SapScreens");

    @Override
    public void onInitializeClient() {
        ScreenHandler.init();
        SapScreensCommands.init();
        LOGGER.info("INITIALIZING SCREENS >x3c");
    }
}
