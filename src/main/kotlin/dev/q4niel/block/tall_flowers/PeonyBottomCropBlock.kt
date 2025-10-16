package dev.q4niel.block.tall_flowers

import dev.q4niel.block.ModBlocks
import dev.q4niel.block.TallFlowerBottomCropBlock
import dev.q4niel.item.ModItems
import net.minecraft.block.Block
import net.minecraft.item.ItemConvertible
import net.minecraft.util.shape.VoxelShape

class PeonyBottomCropBlock(settings: Settings) : TallFlowerBottomCropBlock(settings) {
    override fun getMidShape(): VoxelShape = createColumnShape(10.0, 0.0, 16.0);
    override fun getUpperBlock(): Block = ModBlocks.peonyCrop_.top_;
    override fun getSeedsItem(): ItemConvertible = ModItems.peonySeeds_;
}