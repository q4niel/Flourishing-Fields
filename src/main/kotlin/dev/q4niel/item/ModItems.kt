package dev.q4niel.item

import dev.q4niel.FlourishingFields
import dev.q4niel.item.flower_seeds.*
import dev.q4niel.item.tall_flower_seeds.*
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
    public val azureBluetSeeds_: Item = regFlowerSeeds("azure_bluet_seeds", ::AzureBluetSeedsItem, Settings());
    public val blueOrchidSeeds_: Item = regFlowerSeeds("blue_orchid_seeds", ::BlueOrchidSeedsItem, Settings());
    public val cornflowerSeeds_: Item = regFlowerSeeds("cornflower_seeds", ::CornflowerSeedsItem, Settings());
    public val dandelionSeeds_: Item = regFlowerSeeds("dandelion_seeds", ::DandelionSeedsItem, Settings());
    public val lilyOfTheValleySeeds_: Item = regFlowerSeeds("lily_of_the_valley_seeds", ::LilyOfTheValleySeedsItem, Settings());
    public val orangeTulipSeeds_: Item = regFlowerSeeds("orange_tulip_seeds", ::OrangeTulipSeedsItem, Settings());
    public val oxeyeDaisySeeds_: Item = regFlowerSeeds("oxeye_daisy_seeds", ::OxeyeDaisySeedsItem, Settings());
    public val pinkTulipSeeds_: Item = regFlowerSeeds("pink_tulip_seeds", ::PinkTulipSeedsItem, Settings());
    public val poppySeeds_: Item = regFlowerSeeds("poppy_seeds", ::PoppySeedsItem, Settings());
    public val redTulipSeeds_: Item = regFlowerSeeds("red_tulip_seeds", ::RedTulipSeedsItem, Settings());
    public val whiteTulipSeeds_: Item = regFlowerSeeds("white_tulip_seeds", ::WhiteTulipSeedsItem, Settings());
    public val witherRoseSeeds_: Item = regFlowerSeeds("wither_rose_seeds", ::WitherRoseSeedsItem, Settings());

    // Tall Flowers
    public val lilacSeeds_: Item = regFlowerSeeds("lilac_seeds", ::LilacSeedsItem, Settings());
    public val peonySeeds_: Item = regFlowerSeeds("peony_seeds", ::PeonySeedsItem, Settings());
    public val roseBushSeeds_: Item = regFlowerSeeds("rose_bush_seeds", ::RoseBushSeedsItem, Settings());
    public val sunflowerSeeds_: Item = regFlowerSeeds("sunflower_seeds", ::SunflowerSeedsItem, Settings());

    public fun init(): Unit {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register {
            entries: FabricItemGroupEntries ->

            // Short Flowers
            entries.add(alliumSeeds_);
            entries.add(azureBluetSeeds_);
            entries.add(blueOrchidSeeds_);
            entries.add(cornflowerSeeds_);
            entries.add(dandelionSeeds_);
            entries.add(lilyOfTheValleySeeds_);
            entries.add(orangeTulipSeeds_);
            entries.add(oxeyeDaisySeeds_);
            entries.add(pinkTulipSeeds_);
            entries.add(poppySeeds_);
            entries.add(redTulipSeeds_);
            entries.add(whiteTulipSeeds_);
            entries.add(witherRoseSeeds_);

            // Tall Flowers
            entries.add(lilacSeeds_);
            entries.add(peonySeeds_);
            entries.add(roseBushSeeds_);
            entries.add(sunflowerSeeds_);
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