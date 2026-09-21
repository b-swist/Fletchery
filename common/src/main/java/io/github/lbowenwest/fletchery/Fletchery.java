package io.github.lbowenwest.fletchery;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.lbowenwest.fletchery.client.gui.handler.FletchingTableContainerMenu;
import io.github.lbowenwest.fletchery.registry.FletcheryRecipeSerializer;
import io.github.lbowenwest.fletchery.registry.FletcheryRecipeType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

public class Fletchery {
    public static final Registrar<MenuType<?>> MENUS = DeferredRegister.create(FletcheryIdentifier.MOD_ID, Registries.MENU).getRegistrar();

    public static final RegistrySupplier<MenuType<FletchingTableContainerMenu>> FLETCHING_TABLE_MENU_HANDLER = MENUS.register(
            FletcheryIdentifier.of("fletching_table_menu"),
            () -> new MenuType<>(FletchingTableContainerMenu::new, FeatureFlags.VANILLA_SET)
    );

    public static void init() {
        FletcheryRecipeType.init();
        FletcheryRecipeSerializer.init();
    }
}
