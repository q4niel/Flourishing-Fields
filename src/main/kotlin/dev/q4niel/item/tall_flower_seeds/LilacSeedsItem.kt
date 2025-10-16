package dev.q4niel.item.tall_flower_seeds

import dev.q4niel.block.ModBlocks
import dev.q4niel.item.TallFlowerSeedsItem
import net.minecraft.block.Block

class LilacSeedsItem (
    settings: Settings,
    override val crop_: Block = ModBlocks.lilacCrop_.bottom_
) : TallFlowerSeedsItem(settings);