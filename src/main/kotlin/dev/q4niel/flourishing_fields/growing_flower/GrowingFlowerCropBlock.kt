package dev.q4niel.flourishing_fields.growing_flower

import dev.q4niel.flourishing_fields.growing_flower.seeds.GrowingFlowerSeeds
import net.minecraft.block.*
import net.minecraft.item.ItemConvertible
import net.minecraft.state.StateManager
import net.minecraft.state.property.IntProperty
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView

abstract class GrowingFlowerCropBlock(settings: Settings?) : CropBlock(settings) {
    companion object {
        const val MAX_AGE: Int = 1;
        val AGE: IntProperty = IntProperty.of("age", 0, 1);
    }

    abstract override fun getSeedsItem(): ItemConvertible;

    override fun getAgeProperty(): IntProperty = AGE;
    override fun getMaxAge(): Int = MAX_AGE;
    override fun canPlantOnTop(floor: BlockState?, world: BlockView?, pos: BlockPos?): Boolean = GROWING_FLOWER_PLANTABLES.contains(floor?.block);

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?): Unit {
        builder?.add(AGE);
    }
}