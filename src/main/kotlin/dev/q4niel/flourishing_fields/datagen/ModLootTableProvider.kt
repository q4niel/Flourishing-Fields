package dev.q4niel.flourishing_fields.datagen

import dev.q4niel.flourishing_fields.growing_flower.GrowingFlowerCropBlock
import dev.q4niel.flourishing_fields.growing_flower.GrowingTallFlowerBottomCropBlock
import dev.q4niel.flourishing_fields.growing_flower.crops.GrowingFlowerCrops
import dev.q4niel.flourishing_fields.growing_flower.seeds.GrowingFlowerSeeds
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
    dataOutput: FabricDataOutput?,
    registryLookup: CompletableFuture<RegistryWrapper.WrapperLookup>?
): FabricBlockLootTableProvider (
    dataOutput,
    registryLookup
) {
    override fun generate() {
        genFlowerCrop(GrowingFlowerCrops.DANDELION, GrowingFlowerSeeds.DANDELION, Items.DANDELION);
        genFlowerCrop(GrowingFlowerCrops.POPPY, GrowingFlowerSeeds.POPPY, Items.POPPY);
        genFlowerCrop(GrowingFlowerCrops.BLUE_ORCHID, GrowingFlowerSeeds.BLUE_ORCHID, Items.BLUE_ORCHID);
        genFlowerCrop(GrowingFlowerCrops.ALLIUM, GrowingFlowerSeeds.ALLIUM, Items.ALLIUM);
        genFlowerCrop(GrowingFlowerCrops.AZURE_BLUET, GrowingFlowerSeeds.AZURE_BLUET, Items.AZURE_BLUET);
        genFlowerCrop(GrowingFlowerCrops.RED_TULIP, GrowingFlowerSeeds.RED_TULIP, Items.RED_TULIP);
        genFlowerCrop(GrowingFlowerCrops.ORANGE_TULIP, GrowingFlowerSeeds.ORANGE_TULIP, Items.ORANGE_TULIP);
        genFlowerCrop(GrowingFlowerCrops.WHITE_TULIP, GrowingFlowerSeeds.WHITE_TULIP, Items.WHITE_TULIP);
        genFlowerCrop(GrowingFlowerCrops.PINK_TULIP, GrowingFlowerSeeds.PINK_TULIP, Items.PINK_TULIP);
        genFlowerCrop(GrowingFlowerCrops.OXEYE_DAISY, GrowingFlowerSeeds.OXEYE_DAISY, Items.OXEYE_DAISY);
        genFlowerCrop(GrowingFlowerCrops.CORNFLOWER, GrowingFlowerSeeds.CORNFLOWER, Items.CORNFLOWER);
        genFlowerCrop(GrowingFlowerCrops.LILY_OF_THE_VALLEY, GrowingFlowerSeeds.LILY_OF_THE_VALLEY, Items.LILY_OF_THE_VALLEY);
        genFlowerCrop(GrowingFlowerCrops.WITHER_ROSE, GrowingFlowerSeeds.WITHER_ROSE, Items.WITHER_ROSE);

        genTallFlowerCrop(GrowingFlowerCrops.SUNFLOWER, GrowingFlowerSeeds.SUNFLOWER, Items.SUNFLOWER);
        genTallFlowerCrop(GrowingFlowerCrops.LILAC, GrowingFlowerSeeds.LILAC, Items.LILAC);
        genTallFlowerCrop(GrowingFlowerCrops.ROSE_BUSH, GrowingFlowerSeeds.ROSE_BUSH, Items.ROSE_BUSH);
        genTallFlowerCrop(GrowingFlowerCrops.PEONY, GrowingFlowerSeeds.PEONY, Items.PEONY);
    }

    private fun genTallFlowerCrop (
        tallCrop: GrowingFlowerCrops.TallCrop,
        seeds: Item,
        flower: Item
    ): Unit {
        genFlowerCrop (
            tallCrop.BOTTOM,
            seeds,
            flower,
            GrowingTallFlowerBottomCropBlock.AGE,
            GrowingTallFlowerBottomCropBlock.MAX_AGE
        )

        addDrop (
            tallCrop.TOP,
            flowerCropDrops (
                tallCrop.TOP,
                flower,
                seeds,
                BlockStatePropertyLootCondition.builder(tallCrop.TOP)
            )
        );
    }

    private fun genFlowerCrop (
        crop: Block,
        seeds: Item,
        flower: Item,
        age: IntProperty = GrowingFlowerCropBlock.AGE,
        maxAge: Int = GrowingFlowerCropBlock.MAX_AGE
    ): Unit {
        val builder: BlockStatePropertyLootCondition.Builder = BlockStatePropertyLootCondition
            .builder(crop)
            .properties(StatePredicate.Builder.create().exactMatch(age, maxAge))
        ;
        addDrop (
            crop,
            flowerCropDrops (
                crop,
                flower,
                seeds,
                builder
            )
        );
    }

    private fun flowerCropDrops (
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
                    .with(ItemEntry.builder(seeds).apply(ApplyBonusLootFunction.binomialWithBonusCount(impl.getOrThrow(Enchantments.FORTUNE), 0.5714286F, 3)))
                )
                .pool(LootPool.builder()
                    .conditionally(condition)
                    .conditionally(createWithShearsCondition())
                    .with(ItemEntry.builder(product))
                )
        ) as LootTable.Builder;
    }
}