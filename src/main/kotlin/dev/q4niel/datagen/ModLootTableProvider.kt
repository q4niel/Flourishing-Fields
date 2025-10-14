package dev.q4niel.datagen

import dev.q4niel.block.FlowerCropBlock
import dev.q4niel.block.ModBlocks
import dev.q4niel.item.ModItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.block.Block
import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.Enchantments
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.condition.BlockStatePropertyLootCondition
import net.minecraft.loot.condition.LootCondition
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.function.ApplyBonusLootFunction
import net.minecraft.predicate.StatePredicate
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.RegistryWrapper
import net.minecraft.state.property.IntProperty
import java.util.concurrent.CompletableFuture

class ModLootTableProvider (
    dataOutput: FabricDataOutput,
    registryLookup: CompletableFuture<RegistryWrapper.WrapperLookup>
) : FabricBlockLootTableProvider (
    dataOutput,
    registryLookup
) {
    override fun generate() {
        _genFlowerCrop(ModBlocks.alliumCrop_, ModItems.alliumSeeds_, Items.ALLIUM);
    }

    private fun _genFlowerCrop (
        crop: Block,
        seeds: Item,
        flower: Item,
        age: IntProperty = FlowerCropBlock.age_,
        maxAge: Int = FlowerCropBlock.maxAge_
    ): Unit {
        val builder: BlockStatePropertyLootCondition.Builder = BlockStatePropertyLootCondition
            .builder(crop)
            .properties(StatePredicate.Builder.create().exactMatch(age, maxAge))
        ;
        addDrop (
            crop,
            _flowerCropDrops (
                crop,
                flower,
                seeds,
                builder
            )
        );
    }

    private fun _flowerCropDrops (
        crop: Block,
        product: Item,
        seeds: Item,
        condition: LootCondition.Builder
    ): LootTable.Builder {
        val impl: RegistryWrapper.Impl<Enchantment> = this.registries.getOrThrow(RegistryKeys.ENCHANTMENT);

        return this.applyExplosionDecay (
            crop,
            LootTable
                .builder()
                .pool(LootPool.builder().with(ItemEntry.builder(seeds)))
                .pool(LootPool.builder()
                    .conditionally(condition)
                    .with(ItemEntry
                        .builder(seeds)
                        .apply(ApplyBonusLootFunction.binomialWithBonusCount (
                            impl.getOrThrow(Enchantments.FORTUNE),
                            0.5714286F,
                            3
                        ))
                    )
                )
                .pool(LootPool.builder()
                    .conditionally(condition)
                    .conditionally(createWithShearsCondition())
                    .with(ItemEntry.builder(product))
                )
        ) as LootTable.Builder;
    }
}