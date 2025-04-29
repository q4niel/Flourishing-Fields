package dev.q4niel.flourishing_fields.growing_flower.crops

import dev.q4niel.flourishing_fields.growing_flower.GrowingTallFlowerBottomCropBlock
import dev.q4niel.flourishing_fields.growing_flower.seeds.GrowingFlowerSeeds
import net.minecraft.block.Block
import net.minecraft.item.ItemConvertible

class GrowingSunflowerBottomCropBlock(settings: Settings?) : GrowingTallFlowerBottomCropBlock(settings) {
    override fun getUpperBlock(): Block = GrowingFlowerCrops.SUNFLOWER.TOP;

    override fun getSeedsItem(): ItemConvertible = GrowingFlowerSeeds.SUNFLOWER;
}