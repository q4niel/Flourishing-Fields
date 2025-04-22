package dev.q4niel.flourishing_fields.datagen

import dev.q4niel.flourishing_fields.growing_flower.GrowingFlowerCropBlock
import dev.q4niel.flourishing_fields.growing_flower.crops.GrowingFlowerCrops
import dev.q4niel.flourishing_fields.growing_flower.seeds.GrowingFlowerSeeds
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.minecraft.block.Block
import net.minecraft.client.data.BlockStateModelGenerator
import net.minecraft.client.data.ItemModelGenerator
import net.minecraft.client.data.Models

class ModModelProvider(output: FabricDataOutput?) : FabricModelProvider(output) {
    override fun generateBlockStateModels(generator: BlockStateModelGenerator) {
        genFlowerCropBlockState(generator, GrowingFlowerCrops.POPPY);
    }

    override fun generateItemModels(generator: ItemModelGenerator) {
        generator.register(GrowingFlowerSeeds.POPPY, Models.GENERATED);
    }

    private fun genFlowerCropBlockState(generator: BlockStateModelGenerator, cropBlock: Block) {
        generator.registerCrop (
            (cropBlock as GrowingFlowerCropBlock),
            GrowingFlowerCropBlock.AGE,
            0, 1
        );
    }
}