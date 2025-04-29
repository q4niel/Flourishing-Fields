package dev.q4niel.flourishing_fields.growing_flower.seeds

import dev.q4niel.flourishing_fields.growing_flower.GrowingTallFlowerSeedsItem
import dev.q4niel.flourishing_fields.growing_flower.crops.GrowingFlowerCrops
import net.minecraft.block.Block

class GrowingPeonySeedsItem(settings: Settings?) : GrowingTallFlowerSeedsItem(settings) {
    override val crop: Block = GrowingFlowerCrops.PEONY.BOTTOM;
}