package dev.q4niel.flourishing_fields.datagen

import dev.q4niel.flourishing_fields.growing_flower.GrowingFlowerCropBlock
import dev.q4niel.flourishing_fields.growing_flower.GrowingTallFlowerLowerCropBlock
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
        genShortFlowerCropBlockState(generator, GrowingFlowerCrops.POPPY);

        genTallFlowerLowerCropBlockState(generator, GrowingFlowerCrops.PEONY.LOWER);
        generator.registerSimpleCubeAll(GrowingFlowerCrops.PEONY.UPPER);
    }

    override fun generateItemModels(generator: ItemModelGenerator) {
        generator.register(GrowingFlowerSeeds.POPPY, Models.GENERATED);
        generator.register(GrowingFlowerSeeds.PEONY, Models.GENERATED);
    }

    private fun genShortFlowerCropBlockState(generator: BlockStateModelGenerator, cropBlock: Block) {
        generator.registerCrop (
            (cropBlock as GrowingFlowerCropBlock),
            GrowingFlowerCropBlock.AGE,
            0, 1
        );
    }

    private fun genTallFlowerLowerCropBlockState(generator: BlockStateModelGenerator, cropBlock: Block) {
        generator.registerCrop (
            (cropBlock as GrowingTallFlowerLowerCropBlock),
            GrowingTallFlowerLowerCropBlock.AGE,
            0, 1, 2
        );
    }
}