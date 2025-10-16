package net.sapfii.sapscreens;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.sapfii.sapscreens.command.SapScreensCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
