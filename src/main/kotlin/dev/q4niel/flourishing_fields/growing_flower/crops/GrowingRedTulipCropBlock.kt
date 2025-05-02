package dev.q4niel.flourishing_fields.growing_flower.crops

import dev.q4niel.flourishing_fields.growing_flower.GrowingFlowerCropBlock
import dev.q4niel.flourishing_fields.growing_flower.seeds.GrowingFlowerSeeds
import net.minecraft.item.ItemConvertible

class GrowingRedTulipCropBlock(settings: Settings?) : GrowingFlowerCropBlock(settings) {
    override fun getSeedsItem(): ItemConvertible = GrowingFlowerSeeds.RED_TULIP;
}