package dev.q4niel.item

import net.minecraft.item.ItemUsageContext

abstract class TallFlowerSeedsItem(settings: Settings) : FlowerSeedsItem(settings) {
    override fun _guardClause(context: ItemUsageContext): Boolean {
        return !context.world.getBlockState(context.blockPos.up()).isAir;
    }
}