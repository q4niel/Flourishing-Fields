package dev.q4niel.block

import dev.q4niel.EndpointHelper
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.ShapeContext
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.IntProperty
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.random.Random
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView

abstract class TallFlowerBottomCropBlock(settings: Settings) : FlowerCropBlock(settings) {
    companion object {
        val maxAge_: Int = 2;
        val age_: IntProperty = IntProperty.of("age", 0, 2);
    }

    abstract fun getMidShape(): VoxelShape;
    abstract fun getUpperBlock(): Block;

    override val fullShape_: VoxelShape = Block.createCubeShape(16.0);

    override fun getOutlineShape (
        state: BlockState,
        world: BlockView,
        pos: BlockPos,
        context: ShapeContext
    ): VoxelShape? = when (getAge(state)) {
        0 -> sproutShape_
        1 -> getMidShape()
        2 -> fullShape_
        else -> null
    }

    override fun getAgeProperty(): IntProperty = age_;
    override fun getMaxAge(): Int = maxAge_;
    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?) {
        builder?.add(age_);
    }

    private fun _tryGrowUpperBlock(world: ServerWorld?, lowerPos: BlockPos?): Unit? = EndpointHelper.serverExec Runnable@ {
        if (!isMature(world?.getBlockState(lowerPos))) return@Runnable;

        world?.setBlockState (
            lowerPos?.up(),
            getUpperBlock().defaultState,
            3
        );
    };


    override fun randomTick(state: BlockState?, world: ServerWorld?, pos: BlockPos?, random: Random?) {
        EndpointHelper.serverExec Runnable@ {
            val age: Int = getAge(state);
            if (world?.getBaseLightLevel(pos, 0)!! < 9 || age >= getMaxAge()) return@Runnable;

            val moisture: Float = getAvailableMoisture(this, world, pos);
            if (random?.nextInt(((25F / moisture) + 1).toInt()) != 0) return@Runnable;

            if (true == world.getBlockState(pos?.up())?.isAir) {
                world.setBlockState(pos, this.withAge(age + 1), 2);
                _tryGrowUpperBlock(world, pos);
            }
            else {
                world.breakBlock(pos, false);
            }
        };
    }

    override fun grow (
        world: ServerWorld?,
        random: Random?,
        pos: BlockPos?,
        state: BlockState?
    ) {
        super.grow(world, random, pos, state);

        EndpointHelper.serverExec Runnable@ {
            if (false == world?.getBlockState(pos?.up())?.isAir) {
                world.breakBlock(pos, true);
                return@Runnable;
            }
            _tryGrowUpperBlock(world, pos);
        };
    }

    override fun onStateReplaced(state: BlockState?, world: ServerWorld?, pos: BlockPos?, moved: Boolean) {
        super.onStateReplaced(state, world, pos, moved)

        EndpointHelper.serverExec Runnable@ {
            if (world?.getBlockState(pos?.up())?.block !is TallFlowerTopCropBlock) return@Runnable;
            world.breakBlock(pos?.up(), false);
        };
    }
}