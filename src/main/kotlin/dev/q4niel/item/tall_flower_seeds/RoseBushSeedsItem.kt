package dev.q4niel.item.tall_flower_seeds

import dev.q4niel.block.ModBlocks
import dev.q4niel.item.TallFlowerSeedsItem
import net.minecraft.block.Block

class RoseBushSeedsItem (
    settings: Settings,
    override val crop_: Block = ModBlocks.roseBushCrop_.bottom_
) : TallFlowerSeedsItem(settings);