package dev.q4niel.flourishing_fields.growing_flower

import dev.q4niel.flourishing_fields.FlourishingFields
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos

abstract class GrowingTallFlowerUpperCropBlock(settings: Settings?) : Block(settings) {
    override fun onStateReplaced(state: BlockState?, world: ServerWorld?, pos: BlockPos?, moved: Boolean) {
        super.onStateReplaced(state, world, pos, moved)

        FlourishingFields.serverExec {
            world?.breakBlock(pos?.down(), false);
        };
    }
}