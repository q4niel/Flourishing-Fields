package dev.q4niel.datagen

import dev.q4niel.item.Items
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.minecraft.client.data.BlockStateModelGenerator
import net.minecraft.client.data.ItemModelGenerator
import net.minecraft.client.data.Models

class ModelProvider(output: FabricDataOutput) : FabricModelProvider(output) {
    override fun generateBlockStateModels(generator: BlockStateModelGenerator) {}

    override fun generateItemModels(generator: ItemModelGenerator) {
        generator.register(Items.alliumSeeds_, Models.GENERATED);
    }
}