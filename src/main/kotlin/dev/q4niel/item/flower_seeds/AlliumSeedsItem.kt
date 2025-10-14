package dev.q4niel.item.flower_seeds

import dev.q4niel.item.FlowerSeedsItem
import net.minecraft.block.Block
import net.minecraft.block.Blocks

class AlliumSeedsItem (
    settings: Settings,
    override val crop_: Block = Blocks.ALLIUM
): FlowerSeedsItem(settings);