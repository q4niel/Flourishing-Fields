package dev.q4niel.flourishing_fields.growing_flower

import dev.q4niel.flourishing_fields.FlourishingFields
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
import net.minecraft.world.World

abstract class GrowingTallFlowerBottomCropBlock(settings: Settings?) : GrowingFlowerCropBlock(settings) {
    companion object {
        const val MAX_AGE: Int = 2;
        val AGE: IntProperty = IntProperty.of("age", 0, 2);
    }

    override val FULL_SHAPE: VoxelShape = createCubeShape(16.0);

    abstract fun getMidShape(): VoxelShape;

    override fun getOutlineShape (
        state: BlockState,
        world: BlockView,
        pos: BlockPos,
        context: ShapeContext
    ): VoxelShape? = when (getAge(state)) {
        0 -> SPROUT_SHAPE
        1 -> getMidShape()
        2 -> FULL_SHAPE
        else -> null
    }

    abstract fun getUpperBlock(): Block;

    override fun getAgeProperty(): IntProperty = AGE;
    override fun getMaxAge(): Int = MAX_AGE;

    private fun tryGrowUpperBlock(world: ServerWorld?, lowerPos: BlockPos?): Unit? = FlourishingFields.serverExec Runnable@ {
        if (!isMature(world?.getBlockState(lowerPos))) return@Runnable;

        world?.setBlockState (
            lowerPos?.up(),
            getUpperBlock().defaultState,
            3
        );
    };

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?): Unit {
        builder?.add(AGE);
    }


    override fun randomTick(state: BlockState?, world: ServerWorld?, pos: BlockPos?, random: Random?) {
        FlourishingFields.serverExec Runnable@ {
            val age: Int = getAge(state);
            if (world?.getBaseLightLevel(pos, 0)!! < 9 || age >= getMaxAge()) return@Runnable;

            val moisture: Float = getAvailableMoisture(this, world, pos);
            if (random?.nextInt(((25F / moisture) + 1).toInt()) != 0) return@Runnable;

            if (true == world.getBlockState(pos?.up())?.isAir) {
                world.setBlockState(pos, this.withAge(age + 1), 2);
                tryGrowUpperBlock(world, pos);
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

        FlourishingFields.serverExec Runnable@ {
            if (false == world?.getBlockState(pos?.up())?.isAir) {
                world.breakBlock(pos, true);
                return@Runnable;
            }
            tryGrowUpperBlock(world, pos);
        };
    }

    override fun onStateReplaced(
        state: BlockState?,
        world: World?,
        pos: BlockPos?,
        newState: BlockState?,
        moved: Boolean
    ) {
        super.onStateReplaced(state, world, pos, newState, moved)

        FlourishingFields.serverExec Runnable@ {
            if (world?.getBlockState(pos?.up())?.block !is GrowingTallFlowerTopCropBlock) return@Runnable;
            world.breakBlock(pos?.up(), false);
        };
    }
}