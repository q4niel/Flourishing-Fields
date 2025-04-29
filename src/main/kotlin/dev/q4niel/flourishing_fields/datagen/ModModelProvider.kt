package dev.q4niel.flourishing_fields.datagen

import dev.q4niel.flourishing_fields.growing_flower.seeds.GrowingFlowerSeeds
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.minecraft.client.data.BlockStateModelGenerator
import net.minecraft.client.data.ItemModelGenerator
import net.minecraft.client.data.Models

class ModModelProvider(output: FabricDataOutput?) : FabricModelProvider(output) {
    override fun generateBlockStateModels(generator: BlockStateModelGenerator) {}

    override fun generateItemModels(generator: ItemModelGenerator) {
        generator.register(GrowingFlowerSeeds.DANDELION, Models.GENERATED);
        generator.register(GrowingFlowerSeeds.POPPY, Models.GENERATED);
        generator.register(GrowingFlowerSeeds.BLUE_ORCHID, Models.GENERATED);
        generator.register(GrowingFlowerSeeds.ALLIUM, Models.GENERATED);
        generator.register(GrowingFlowerSeeds.AZURE_BLUET, Models.GENERATED);
        generator.register(GrowingFlowerSeeds.RED_TULIP, Models.GENERATED);
        generator.register(GrowingFlowerSeeds.ORANGE_TULIP, Models.GENERATED);
        generator.register(GrowingFlowerSeeds.WHITE_TULIP, Models.GENERATED);
        generator.register(GrowingFlowerSeeds.PINK_TULIP, Models.GENERATED);
        generator.register(GrowingFlowerSeeds.OXEYE_DAISY, Models.GENERATED);
        generator.register(GrowingFlowerSeeds.CORNFLOWER, Models.GENERATED);
        generator.register(GrowingFlowerSeeds.LILY_OF_THE_VALLEY, Models.GENERATED);
        generator.register(GrowingFlowerSeeds.WITHER_ROSE, Models.GENERATED);

        generator.register(GrowingFlowerSeeds.SUNFLOWER, Models.GENERATED);
        generator.register(GrowingFlowerSeeds.LILAC, Models.GENERATED);
        generator.register(GrowingFlowerSeeds.ROSE_BUSH, Models.GENERATED);
        generator.register(GrowingFlowerSeeds.PEONY, Models.GENERATED);
    }
}