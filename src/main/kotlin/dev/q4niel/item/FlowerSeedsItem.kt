package dev.q4niel.item

import dev.q4niel.EndpointHelper
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemUsageContext
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand

abstract class FlowerSeedsItem(settings: Item.Settings) : Item(settings) {
    abstract val crop_: Block;

    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        EndpointHelper.serverExec {
            context.world.setBlockState (
                context.blockPos.up(),
                crop_.defaultState,
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
        }

        return super.useOnBlock(context);
    }
}