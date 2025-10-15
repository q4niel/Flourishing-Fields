package dev.q4niel.block

import dev.q4niel.flowerSeedsPlantables_
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.CropBlock
import net.minecraft.block.ShapeContext
import net.minecraft.item.ItemConvertible
import net.minecraft.state.StateManager
import net.minecraft.state.property.IntProperty
import net.minecraft.util.math.BlockPos
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView

abstract class FlowerCropBlock(settings: Settings) : CropBlock(settings) {
    abstract override fun getSeedsItem(): ItemConvertible;

    companion object {
        val maxAge_: Int = 1;
        val age_: IntProperty = IntProperty.of("age", 0, 1);
    }

    val sproutShape_: VoxelShape = createColumnShape(6.0, 0.0, 5.0);
    protected open val fullShape_: VoxelShape = createColumnShape(6.0, 0.0, 10.0);

    override fun getAgeProperty(): IntProperty = age_;
    override fun getMaxAge(): Int = maxAge_;

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?): Unit {
        builder?.add(age_);
    }

    override fun canPlantOnTop(floor: BlockState?, world: BlockView?, pos: BlockPos?): Boolean {
        return flowerSeedsPlantables_.contains(floor?.block);
    }

    override fun getOutlineShape (
        state: BlockState,
        world: BlockView,
        pos: BlockPos,
        context: ShapeContext
    ): VoxelShape? = when (getAge(state)) {
        0 -> sproutShape_;
        1 -> fullShape_;
        else -> null;
    }
}