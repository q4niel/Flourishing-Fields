package dev.q4niel.flourishing_fields.growing_flower

import dev.q4niel.flourishing_fields.FlourishingFields
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.WorldAccess

abstract class GrowingTallFlowerUpperCropBlock(settings: Settings?) : Block(settings) {
    override fun onBroken(world: WorldAccess?, pos: BlockPos?, state: BlockState?) {
        super.onBroken(world, pos, state)

        FlourishingFields.serverExec {
            world?.breakBlock(pos?.down(), false);
        };
    }
}