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
    val DANDELION: Item = reg("dandelion_seeds", ::GrowingDandelionSeedsItem, Settings());
    val POPPY: Item = reg("poppy_seeds", ::GrowingPoppySeedsItem, Settings());
    val BLUE_ORCHID: Item = reg("blue_orchid_seeds", ::GrowingBlueOrchidSeedsItem, Settings());
    val ALLIUM: Item = reg("allium_seeds", ::GrowingAlliumSeedsItem, Settings());
    val AZURE_BLUET: Item = reg("azure_bluet_seeds", ::GrowingAzureBluetSeedsItem, Settings());
    val RED_TULIP: Item = reg("red_tulip_seeds", ::GrowingRedTulipSeedsItem, Settings());
    val ORANGE_TULIP: Item = reg("orange_tulip_seeds", ::GrowingOrangeTulipSeedsItem, Settings());
    val WHITE_TULIP: Item = reg("white_tulip_seeds", ::GrowingWhiteTulipSeedsItem, Settings());
    val PINK_TULIP: Item = reg("pink_tulip_seeds", ::GrowingPinkTulipSeedsItem, Settings());
    val OXEYE_DAISY: Item = reg("oxeye_daisy_seeds", ::GrowingOxeyeDaisySeedsItem, Settings());
    val CORNFLOWER: Item = reg("cornflower_seeds", ::GrowingCornflowerSeedsItem, Settings());
    val LILY_OF_THE_VALLEY: Item = reg("lily_of_the_valley_seeds", ::GrowingLilyOfTheValleySeedsItem, Settings());
    val WITHER_ROSE: Item = reg("wither_rose_seeds", ::GrowingWitherRoseSeedsItem, Settings());

    val SUNFLOWER: Item = reg("sunflower_seeds", ::GrowingSunflowerSeedsItem, Settings());
    val LILAC: Item = reg("lilac_seeds", ::GrowingLilacSeedsItem, Settings());
    val ROSE_BUSH: Item = reg("rose_bush_seeds", ::GrowingRoseBushSeedsItem, Settings());
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