package dev.q4niel.flourishing_fields.growing_flower.seeds

import dev.q4niel.flourishing_fields.growing_flower.GrowingFlowerSeedsItem
import dev.q4niel.flourishing_fields.growing_flower.crops.GrowingFlowerCrops
import net.minecraft.block.Block

class GrowingSunflowerSeedsItem(settings: Settings?) : GrowingFlowerSeedsItem(settings) {
    override val crop: Block = GrowingFlowerCrops.SUNFLOWER.BOTTOM;
}