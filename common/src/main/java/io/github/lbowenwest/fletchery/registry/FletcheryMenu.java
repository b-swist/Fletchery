package io.github.lbowenwest.fletchery.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.lbowenwest.fletchery.FletcheryIdentifier;
import io.github.lbowenwest.fletchery.client.gui.handler.FletchingTableContainerMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

public class FletcheryMenu {
    private static final Registrar<MenuType<?>> MENUS = DeferredRegister.
            create(FletcheryIdentifier.MOD_ID, Registries.MENU)
            .getRegistrar();

    public static final RegistrySupplier<MenuType<FletchingTableContainerMenu>> FLETCHING_TABLE = MENUS.register(
            FletcheryIdentifier.of("fletching_table"),
            () -> new MenuType<>(FletchingTableContainerMenu::new, FeatureFlags.VANILLA_SET)
    );
}
