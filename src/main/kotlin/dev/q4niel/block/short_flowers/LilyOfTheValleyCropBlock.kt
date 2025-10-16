package dev.q4niel.block.short_flowers

import dev.q4niel.block.FlowerCropBlock
import dev.q4niel.item.ModItems
import net.minecraft.item.ItemConvertible

class LilyOfTheValleyCropBlock(settings: Settings) : FlowerCropBlock(settings) {
    override fun getSeedsItem(): ItemConvertible = ModItems.lilyOfTheValleySeeds_;
}