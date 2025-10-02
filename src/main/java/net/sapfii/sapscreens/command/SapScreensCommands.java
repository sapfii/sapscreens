package net.sapfii.sapscreens.command;

import com.mojang.brigadier.Command;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.sapfii.sapscreens.ScreenHandler;
import net.sapfii.sapscreens.screens.widgets.ButtonWidget;

public class SapScreensCommands {
    public static void init() {
        registerSimpleCommand("debugscreen", context -> {
            //ScreenHandler.openScreen(new TestScreen(MinecraftClient.getInstance().currentScreen));
            return 1;
        });
    }


    private static void registerSimpleCommand(String id, Command<FabricClientCommandSource> command) {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal(id).executes(command));
        });
    }
}
