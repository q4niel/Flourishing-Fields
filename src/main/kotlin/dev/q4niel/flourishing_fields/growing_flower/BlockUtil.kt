package dev.q4niel.flourishing_fields.growing_flower

import net.minecraft.block.Block
import net.minecraft.util.shape.VoxelShape

fun createCubeShape(size: Double): VoxelShape = createCuboidShape(size, size, size);

fun createCuboidShape (
    sizeX: Double,
    sizeY: Double,
    sizeZ: Double
): VoxelShape {
    val d: Double = sizeY / 2.0;
    return createColumnShape(sizeX, sizeZ, 8.0 - d, 8.0 + d);
}

fun createColumnShape (
    sizeXz: Double,
    minY: Double,
    maxY: Double
): VoxelShape = createColumnShape (
    sizeXz,
    sizeXz,
    minY,
    maxY
);

fun createColumnShape (
    sizeX: Double,
    sizeZ: Double,
    minY: Double,
    maxY: Double
): VoxelShape {
    val d = sizeX / 2.0;
    val e: Double = sizeZ / 2.0;
    return Block.createCuboidShape (
        8.0 - d,
        minY,
        8.0 - e,
        8.0 + d,
        maxY,
        8.0 + e
    );
}