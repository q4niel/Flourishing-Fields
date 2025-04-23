package dev.q4niel.flourishing_fields.growing_flower.crops.peony

import dev.q4niel.flourishing_fields.growing_flower.GrowingTallFlowerLowerCropBlock
import dev.q4niel.flourishing_fields.growing_flower.seeds.GrowingFlowerSeeds
import net.minecraft.item.ItemConvertible

class GrowingPeonyLowerCropBlock(settings: Settings?) : GrowingTallFlowerLowerCropBlock(settings) {
    override fun getSeedsItem(): ItemConvertible = GrowingFlowerSeeds.PEONY;
}