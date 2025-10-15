package dev.q4niel.block

import dev.q4niel.EndpointHelper
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos

class TallFlowerTopCropBlock(settings: Settings) : Block(settings) {
    override fun onStateReplaced(state: BlockState?, world: ServerWorld?, pos: BlockPos?, moved: Boolean) {
        super.onStateReplaced(state, world, pos, moved)

        EndpointHelper.serverExec {
            world?.breakBlock(pos?.down(), false);
        };
    }
}