package dev.q4niel.item.flower_seeds

import dev.q4niel.block.ModBlocks
import dev.q4niel.item.FlowerSeedsItem
import net.minecraft.block.Block

class AlliumSeedsItem (
    settings: Settings,
    override val crop_: Block = ModBlocks.alliumCrop_
): FlowerSeedsItem(settings);