package dev.q4niel.flourishing_fields.growing_flower.crops

import dev.q4niel.flourishing_fields.growing_flower.GrowingTallFlowerBottomCropBlock
import dev.q4niel.flourishing_fields.growing_flower.seeds.GrowingFlowerSeeds
import net.minecraft.block.Block
import net.minecraft.item.ItemConvertible
import net.minecraft.util.shape.VoxelShape

class GrowingRoseBushBottomCropBlock(settings: Settings?) : GrowingTallFlowerBottomCropBlock(settings) {
    override fun getUpperBlock(): Block = GrowingFlowerCrops.ROSE_BUSH.TOP;
    override fun getSeedsItem(): ItemConvertible = GrowingFlowerSeeds.ROSE_BUSH;
    override fun getMidShape(): VoxelShape = Block.createCubeShape(16.0);
}