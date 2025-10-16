package dev.q4niel.datagen

import dev.q4niel.item.ModItems
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.minecraft.client.data.BlockStateModelGenerator
import net.minecraft.client.data.ItemModelGenerator
import net.minecraft.client.data.Models

class ModModelProvider(output: FabricDataOutput) : FabricModelProvider(output) {
    override fun generateBlockStateModels(generator: BlockStateModelGenerator) {}

    override fun generateItemModels(generator: ItemModelGenerator) {
        // Short Flowers
        generator.register(ModItems.alliumSeeds_, Models.GENERATED);
        generator.register(ModItems.azureBluetSeeds_, Models.GENERATED);
        generator.register(ModItems.blueOrchidSeeds_, Models.GENERATED);
        generator.register(ModItems.cornflowerSeeds_, Models.GENERATED);
        generator.register(ModItems.dandelionSeeds_, Models.GENERATED);
        generator.register(ModItems.lilyOfTheValleySeeds_, Models.GENERATED);
        generator.register(ModItems.orangeTulipSeeds_, Models.GENERATED);
        generator.register(ModItems.oxeyeDaisySeeds_, Models.GENERATED);
        generator.register(ModItems.pinkTulipSeeds_, Models.GENERATED);
        generator.register(ModItems.poppySeeds_, Models.GENERATED);
        generator.register(ModItems.redTulipSeeds_, Models.GENERATED);
        generator.register(ModItems.whiteTulipSeeds_, Models.GENERATED);
        generator.register(ModItems.witherRoseSeeds_, Models.GENERATED);

        // Tall Flowers
        generator.register(ModItems.lilacSeeds_, Models.GENERATED);
        generator.register(ModItems.peonySeeds_, Models.GENERATED);
        generator.register(ModItems.roseBushSeeds_, Models.GENERATED);
        generator.register(ModItems.sunflowerSeeds_, Models.GENERATED);
    }
}