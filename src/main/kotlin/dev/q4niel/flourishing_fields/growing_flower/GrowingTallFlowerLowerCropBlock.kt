package dev.q4niel.flourishing_fields.growing_flower

import dev.q4niel.flourishing_fields.FlourishingFields
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.IntProperty
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.random.Random
import net.minecraft.world.WorldAccess

abstract class GrowingTallFlowerLowerCropBlock(settings: Settings?) : GrowingFlowerCropBlock(settings) {
    companion object {
        const val MAX_AGE: Int = 2;
        val AGE: IntProperty = IntProperty.of("age", 0, 2);
    }

    abstract fun getUpperBlock(): Block;

    override fun getAgeProperty(): IntProperty = AGE;
    override fun getMaxAge(): Int = MAX_AGE;

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?): Unit {
        builder?.add(AGE);
    }

    override fun grow (
        world: ServerWorld?,
        random: Random?,
        pos: BlockPos?,
        state: BlockState?
    ) {
        super.grow(world, random, pos, state);

        FlourishingFields.serverExec Runnable@ {
            if (!isMature(world?.getBlockState(pos))) return@Runnable;

            world?.setBlockState (
                pos?.up(),
                getUpperBlock().defaultState,
                3
            );
        };
    }

    override fun onBroken(world: WorldAccess?, pos: BlockPos?, state: BlockState?) {
        super.onBroken(world, pos, state);

        FlourishingFields.serverExec {
            world?.breakBlock(pos?.up(), false);
        };
    }
}