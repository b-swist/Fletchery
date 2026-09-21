package io.github.lbowenwest.fletchery.client;

import dev.architectury.registry.menu.MenuRegistry;
import io.github.lbowenwest.fletchery.client.gui.FletchingTableScreen;
import io.github.lbowenwest.fletchery.registry.FletcheryMenu;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class FletcheryClient {
    public static void onInitializeClient() {
        MenuRegistry.registerScreenFactory(FletcheryMenu.FLETCHING_TABLE.get(), FletchingTableScreen::new);
    }
}
