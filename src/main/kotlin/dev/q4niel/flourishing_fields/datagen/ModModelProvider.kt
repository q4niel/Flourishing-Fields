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
        genShortFlowerCropBlockState(generator, GrowingFlowerCrops.DANDELION);
        genShortFlowerCropBlockState(generator, GrowingFlowerCrops.POPPY);
        genShortFlowerCropBlockState(generator, GrowingFlowerCrops.BLUE_ORCHID);
        genShortFlowerCropBlockState(generator, GrowingFlowerCrops.ALLIUM);
        genShortFlowerCropBlockState(generator, GrowingFlowerCrops.AZURE_BLUET);
        genShortFlowerCropBlockState(generator, GrowingFlowerCrops.RED_TULIP);
        genShortFlowerCropBlockState(generator, GrowingFlowerCrops.ORANGE_TULIP);
        genShortFlowerCropBlockState(generator, GrowingFlowerCrops.WHITE_TULIP);
        genShortFlowerCropBlockState(generator, GrowingFlowerCrops.PINK_TULIP);
        genShortFlowerCropBlockState(generator, GrowingFlowerCrops.OXEYE_DAISY);
        genShortFlowerCropBlockState(generator, GrowingFlowerCrops.CORNFLOWER);
        genShortFlowerCropBlockState(generator, GrowingFlowerCrops.LILY_OF_THE_VALLEY);
        genShortFlowerCropBlockState(generator, GrowingFlowerCrops.WITHER_ROSE);

        genTallFlowerLowerCropBlockState(generator, GrowingFlowerCrops.SUNFLOWER.LOWER);
        generator.registerSimpleCubeAll(GrowingFlowerCrops.SUNFLOWER.UPPER);
        genTallFlowerLowerCropBlockState(generator, GrowingFlowerCrops.LILAC.LOWER);
        generator.registerSimpleCubeAll(GrowingFlowerCrops.LILAC.UPPER);
        genTallFlowerLowerCropBlockState(generator, GrowingFlowerCrops.ROSE_BUSH.LOWER);
        generator.registerSimpleCubeAll(GrowingFlowerCrops.ROSE_BUSH.UPPER);
        genTallFlowerLowerCropBlockState(generator, GrowingFlowerCrops.PEONY.LOWER);
        generator.registerSimpleCubeAll(GrowingFlowerCrops.PEONY.UPPER);
    }

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