package dev.q4niel.flourishing_fields.growing_flower

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.state.StateManager
import net.minecraft.state.property.IntProperty

abstract class GrowingTallFlowerCropBlock(settings: Settings?) : GrowingFlowerCropBlock(settings) {
    companion object {
        const val MAX_AGE: Int = 2;
        val AGE: IntProperty = IntProperty.of("age", 0, 2);
    }

    override fun getAgeProperty(): IntProperty = AGE;
    override fun getMaxAge(): Int = MAX_AGE;

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?): Unit {
        builder?.add(AGE);
    }
}