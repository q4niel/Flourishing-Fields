package dev.q4niel.flourishing_fields.growing_flower

import net.minecraft.item.ItemUsageContext

abstract class GrowingTallFlowerSeedsItem(settings: Settings?) : GrowingFlowerSeedsItem(settings) {
    override fun guardClause(context: ItemUsageContext): Boolean =
        !context.world.getBlockState(context.blockPos.up().up()).isAir
}