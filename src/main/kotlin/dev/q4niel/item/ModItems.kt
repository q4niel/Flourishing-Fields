package dev.q4niel.item

import dev.q4niel.FlourishingFields
import dev.q4niel.item.flower_seeds.AlliumSeedsItem
import dev.q4niel.item.tall_flower_seeds.PeonySeedsItem
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.item.Item
import net.minecraft.item.Item.Settings
import net.minecraft.item.ItemGroups
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.Identifier
import java.util.function.Function

object ModItems {
    // Short Flowers
    public val alliumSeeds_: Item = regFlowerSeeds("allium_seeds", ::AlliumSeedsItem, Settings());

    // Tall Flowers
    public val peonySeeds_: Item = regFlowerSeeds("peony_seeds", ::PeonySeedsItem, Settings());

    public fun init(): Unit {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register {
            entries: FabricItemGroupEntries ->

            entries.add(alliumSeeds_);
            entries.add(peonySeeds_);
        }
    }

    private fun regFlowerSeeds (
        name: String,
        factory: Function<Settings, Item>,
        settings: Settings
    ): Item {
        val key: RegistryKey<Item> = RegistryKey.of (
            RegistryKeys.ITEM,
            Identifier.of(FlourishingFields.modID_, name)
        );

        return Registry.register (
            Registries.ITEM,
            key,
            factory.apply(settings.registryKey(key))
        );
    }
}