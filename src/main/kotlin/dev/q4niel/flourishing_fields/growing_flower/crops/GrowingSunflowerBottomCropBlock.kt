package dev.q4niel.flourishing_fields.growing_flower.crops

import dev.q4niel.flourishing_fields.growing_flower.GrowingTallFlowerBottomCropBlock
import dev.q4niel.flourishing_fields.growing_flower.createColumnShape
import dev.q4niel.flourishing_fields.growing_flower.seeds.GrowingFlowerSeeds
import net.minecraft.block.Block
import net.minecraft.item.ItemConvertible
import net.minecraft.util.shape.VoxelShape

class GrowingSunflowerBottomCropBlock(settings: Settings?) : GrowingTallFlowerBottomCropBlock(settings) {
    override fun getUpperBlock(): Block = GrowingFlowerCrops.SUNFLOWER.TOP;
    override fun getSeedsItem(): ItemConvertible = GrowingFlowerSeeds.SUNFLOWER;
    override fun getMidShape(): VoxelShape = createColumnShape(6.0, 0.0, 16.0);
}