package dev.q4niel.flourishing_fields.growing_flower

import dev.q4niel.flourishing_fields.FlourishingFields
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class GrowingTallFlowerTopCropBlock(settings: Settings?) : Block(settings) {
    override fun onStateReplaced(
        state: BlockState?,
        world: World?,
        pos: BlockPos?,
        newState: BlockState?,
        moved: Boolean
    ) {
        super.onStateReplaced(state, world, pos, newState, moved)

        FlourishingFields.serverExec {
            world?.breakBlock(pos?.down(), false);
        };
    }
}