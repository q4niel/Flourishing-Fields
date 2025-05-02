package dev.q4niel.flourishing_fields.growing_flower

import net.minecraft.block.*
import net.minecraft.item.ItemConvertible
import net.minecraft.state.StateManager
import net.minecraft.state.property.IntProperty
import net.minecraft.util.math.BlockPos
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView

abstract class GrowingFlowerCropBlock(settings: Settings?) : CropBlock(settings) {
    companion object {
        const val MAX_AGE: Int = 1;
        val AGE: IntProperty = IntProperty.of("age", 0, 1);
    }

    protected val SPROUT_SHAPE: VoxelShape = Block.createColumnShape(6.0, 0.0, 5.0);
    protected open val FULL_SHAPE: VoxelShape = Block.createColumnShape(6.0, 0.0, 10.0);

    abstract override fun getSeedsItem(): ItemConvertible;

    override fun getOutlineShape (
        state: BlockState,
        world: BlockView,
        pos: BlockPos,
        context: ShapeContext
    ): VoxelShape? = when (getAge(state)) {
        0 -> SPROUT_SHAPE
        1 -> FULL_SHAPE
        else -> null
    }

    override fun getAgeProperty(): IntProperty = AGE;
    override fun getMaxAge(): Int = MAX_AGE;
    override fun canPlantOnTop(floor: BlockState?, world: BlockView?, pos: BlockPos?): Boolean = GROWING_FLOWER_PLANTABLES.contains(floor?.block);

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?): Unit {
        builder?.add(AGE);
    }
}