package dev.q4niel.item.flower_seeds

import dev.q4niel.block.ModBlocks
import dev.q4niel.item.FlowerSeedsItem
import net.minecraft.block.Block

class WitherRoseSeedsItem (
    settings: Settings,
    override val crop_: Block = ModBlocks.witherRoseCrop_
): FlowerSeedsItem(settings);