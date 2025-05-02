package dev.q4niel.flourishing_fields.growing_flower

import dev.q4niel.flourishing_fields.FlourishingFields
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemUsageContext
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand

abstract class GrowingFlowerSeedsItem(settings: Settings?) : Item(settings) {
    abstract val crop: Block;

    protected open fun guardClause(context: ItemUsageContext): Boolean = false;

    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        FlourishingFields.serverExec Runnable@ {
            if (guardClause(context)) return@Runnable;
            val block: Block = context.world.getBlockState(context.blockPos).block;
            if (!GROWING_FLOWER_PLANTABLES.contains(block)) return@Runnable;

            context.world.setBlockState (
                context.blockPos.up(),
                crop.defaultState,
                3
            );

            context.world.playSound (
                null,
                context.blockPos,
                SoundEvents.ITEM_CROP_PLANT,
                SoundCategory.BLOCKS
            );

            val hand: Hand? = context.player?.activeHand;
            context.player?.getStackInHand(hand)?.decrement(1);
            context.player?.swingHand(hand);
        };
        return super.useOnBlock(context);
    }
}