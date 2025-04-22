package dev.q4niel.flourishing_fields.datagen

import dev.q4niel.flourishing_fields.growing_flower.GrowingFlowerCropBlock
import dev.q4niel.flourishing_fields.growing_flower.crops.GrowingFlowerCrops
import dev.q4niel.flourishing_fields.growing_flower.seeds.GrowingFlowerSeeds
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.loot.condition.BlockStatePropertyLootCondition
import net.minecraft.predicate.StatePredicate
import net.minecraft.registry.RegistryWrapper
import java.util.concurrent.CompletableFuture

class ModLootTableProvider (
    dataOutput: FabricDataOutput?,
    registryLookup: CompletableFuture<RegistryWrapper.WrapperLookup>?
): FabricBlockLootTableProvider (
    dataOutput,
    registryLookup
) {
    override fun generate() {
        genFlowerCrop(GrowingFlowerCrops.POPPY, GrowingFlowerSeeds.POPPY, Items.POPPY);
    }

    private fun genFlowerCrop(crop: Block, seeds: Item, flower: Item) {
        val builder: BlockStatePropertyLootCondition.Builder = BlockStatePropertyLootCondition
            .builder(crop)
            .properties(StatePredicate.Builder.create().exactMatch(GrowingFlowerCropBlock.AGE, GrowingFlowerCropBlock.MAX_AGE))
        ;
        addDrop (
            crop,
            cropDrops (
                crop,
                flower,
                seeds,
                builder
            )
        );
    }
}