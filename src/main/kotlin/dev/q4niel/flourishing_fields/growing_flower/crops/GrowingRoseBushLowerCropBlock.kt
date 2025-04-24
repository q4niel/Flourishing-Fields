package dev.q4niel.flourishing_fields.growing_flower.crops

import dev.q4niel.flourishing_fields.growing_flower.GrowingTallFlowerLowerCropBlock
import dev.q4niel.flourishing_fields.growing_flower.seeds.GrowingFlowerSeeds
import net.minecraft.block.Block
import net.minecraft.item.ItemConvertible

class GrowingRoseBushLowerCropBlock(settings: Settings?) : GrowingTallFlowerLowerCropBlock(settings) {
    override fun getUpperBlock(): Block = GrowingFlowerCrops.ROSE_BUSH.UPPER;

    override fun getSeedsItem(): ItemConvertible = GrowingFlowerSeeds.ROSE_BUSH;
}