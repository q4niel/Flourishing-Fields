package dev.q4niel.flourishing_fields.growing_flower.crops

import dev.q4niel.flourishing_fields.growing_flower.GrowingTallFlowerBottomCropBlock
import dev.q4niel.flourishing_fields.growing_flower.seeds.GrowingFlowerSeeds
import net.minecraft.block.Block
import net.minecraft.item.ItemConvertible
import net.minecraft.util.shape.VoxelShape

class GrowingLilacBottomCropBlock(settings: Settings?) : GrowingTallFlowerBottomCropBlock(settings) {
    override fun getUpperBlock(): Block = GrowingFlowerCrops.LILAC.TOP;
    override fun getSeedsItem(): ItemConvertible = GrowingFlowerSeeds.LILAC;
    override fun getMidShape(): VoxelShape = Block.createColumnShape(8.0, 0.0, 16.0);
}