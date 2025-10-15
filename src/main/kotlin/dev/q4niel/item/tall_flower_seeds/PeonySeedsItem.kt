package dev.q4niel.item.tall_flower_seeds

import dev.q4niel.block.ModBlocks
import dev.q4niel.item.TallFlowerSeedsItem
import net.minecraft.block.Block

class PeonySeedsItem (
    settings: Settings,
    override val crop_: Block = ModBlocks.peony_.bottom_
) : TallFlowerSeedsItem(settings);