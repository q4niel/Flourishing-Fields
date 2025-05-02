package dev.q4niel.flourishing_fields.growing_flower.crops.peony

import dev.q4niel.flourishing_fields.growing_flower.GrowingTallFlowerBottomCropBlock
import dev.q4niel.flourishing_fields.growing_flower.crops.GrowingFlowerCrops
import dev.q4niel.flourishing_fields.growing_flower.seeds.GrowingFlowerSeeds
import net.minecraft.block.Block
import net.minecraft.item.ItemConvertible
import net.minecraft.util.shape.VoxelShape

class GrowingPeonyBottomCropBlock(settings: Settings?) : GrowingTallFlowerBottomCropBlock(settings) {
    override fun getSeedsItem(): ItemConvertible = GrowingFlowerSeeds.PEONY;
    override fun getUpperBlock(): Block = GrowingFlowerCrops.PEONY.TOP;
    override fun getMidShape(): VoxelShape = Block.createColumnShape(10.0, 0.0, 16.0);
}