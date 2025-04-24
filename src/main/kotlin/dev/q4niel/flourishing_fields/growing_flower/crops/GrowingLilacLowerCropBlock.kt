package dev.q4niel.flourishing_fields.growing_flower.crops

import dev.q4niel.flourishing_fields.growing_flower.GrowingTallFlowerLowerCropBlock
import dev.q4niel.flourishing_fields.growing_flower.seeds.GrowingFlowerSeeds
import net.minecraft.block.Block
import net.minecraft.item.ItemConvertible

class GrowingLilacLowerCropBlock(settings: Settings?) : GrowingTallFlowerLowerCropBlock(settings) {
    override fun getUpperBlock(): Block = GrowingFlowerCrops.LILAC.UPPER;

    override fun getSeedsItem(): ItemConvertible = GrowingFlowerSeeds.LILAC;
}