package dev.q4niel.flourishing_fields.growing_flower.seeds

import dev.q4niel.flourishing_fields.FlourishingFields
import net.minecraft.item.Item
import net.minecraft.item.Item.Settings
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.Identifier
import java.util.function.Function

object GrowingFlowerSeeds {
    val POPPY: Item = reg("poppy_seeds", ::GrowingPoppySeedsItem, Settings());
    val PEONY: Item = reg("peony_seeds", ::GrowingPeonySeedsItem, Settings());

    fun init(): Unit? = null;

    private fun reg (
        name: String,
        factory: Function<Settings, Item>,
        settings: Settings
    ): Item {
        val key: RegistryKey<Item> = RegistryKey.of (
            RegistryKeys.ITEM,
            Identifier.of(FlourishingFields.MOD_ID, name)
        );

        return Registry.register (
            Registries.ITEM,
            key,
            factory.apply(settings.registryKey(key))
        );
    }
}