package dev.q4niel.flourishing_fields

import dev.q4niel.flourishing_fields.growing_flower.seeds.GrowingFlowerSeeds
import net.fabricmc.fabric.api.loot.v3.LootTableEvents
import net.fabricmc.fabric.api.loot.v3.LootTableSource
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.block.enums.DoubleBlockHalf
import net.minecraft.entity.EntityType
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.condition.BlockStatePropertyLootCondition
import net.minecraft.loot.condition.EntityPropertiesLootCondition
import net.minecraft.loot.condition.LootCondition
import net.minecraft.loot.condition.MatchToolLootCondition
import net.minecraft.loot.condition.RandomChanceLootCondition
import net.minecraft.loot.context.LootContext
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.function.SetCountLootFunction
import net.minecraft.loot.provider.number.UniformLootNumberProvider
import net.minecraft.predicate.StatePredicate
import net.minecraft.predicate.entity.EntityPredicate
import net.minecraft.predicate.entity.EntityTypePredicate
import net.minecraft.predicate.item.ItemPredicate
import net.minecraft.registry.*
import net.minecraft.state.property.Properties
import net.minecraft.util.Identifier
import net.minecraft.world.World

object ModLootTableModifiers {
    fun init(): Unit = LootTableEvents.REPLACE.register {
        key: RegistryKey<LootTable>,
        original: LootTable,
        source: LootTableSource,
        registries: RegistryWrapper.WrapperLookup
        ->

        when (key.value) {
            Identifier.ofVanilla("blocks/dandelion") -> _replaceFlower(Items.DANDELION, Blocks.DANDELION, GrowingFlowerSeeds.DANDELION, false);
            Identifier.ofVanilla("blocks/poppy") -> _replaceFlower(Items.POPPY, Blocks.POPPY, GrowingFlowerSeeds.POPPY, false);
            Identifier.ofVanilla("blocks/blue_orchid") -> _replaceFlower(Items.BLUE_ORCHID, Blocks.BLUE_ORCHID, GrowingFlowerSeeds.BLUE_ORCHID, false);
            Identifier.ofVanilla("blocks/allium") -> _replaceFlower(Items.ALLIUM, Blocks.ALLIUM, GrowingFlowerSeeds.ALLIUM, false);
            Identifier.ofVanilla("blocks/azure_bluet") -> _replaceFlower(Items.AZURE_BLUET, Blocks.AZURE_BLUET, GrowingFlowerSeeds.AZURE_BLUET, false);
            Identifier.ofVanilla("blocks/red_tulip") -> _replaceFlower(Items.RED_TULIP, Blocks.RED_TULIP, GrowingFlowerSeeds.RED_TULIP, false);
            Identifier.ofVanilla("blocks/orange_tulip") -> _replaceFlower(Items.ORANGE_TULIP, Blocks.ORANGE_TULIP, GrowingFlowerSeeds.ORANGE_TULIP, false);
            Identifier.ofVanilla("blocks/white_tulip") -> _replaceFlower(Items.WHITE_TULIP, Blocks.WHITE_TULIP, GrowingFlowerSeeds.WHITE_TULIP, false);
            Identifier.ofVanilla("blocks/pink_tulip") -> _replaceFlower(Items.PINK_TULIP, Blocks.PINK_TULIP, GrowingFlowerSeeds.PINK_TULIP, false);
            Identifier.ofVanilla("blocks/oxeye_daisy") -> _replaceFlower(Items.OXEYE_DAISY, Blocks.OXEYE_DAISY, GrowingFlowerSeeds.OXEYE_DAISY, false);
            Identifier.ofVanilla("blocks/cornflower") -> _replaceFlower(Items.CORNFLOWER, Blocks.CORNFLOWER, GrowingFlowerSeeds.CORNFLOWER, false);
            Identifier.ofVanilla("blocks/lily_of_the_valley") -> _replaceFlower(Items.LILY_OF_THE_VALLEY, Blocks.LILY_OF_THE_VALLEY, GrowingFlowerSeeds.LILY_OF_THE_VALLEY, false);
            Identifier.ofVanilla("blocks/wither_rose") -> _replaceFlower(Items.WITHER_ROSE, Blocks.WITHER_ROSE, GrowingFlowerSeeds.WITHER_ROSE, false);
            Identifier.ofVanilla("blocks/sunflower") -> _replaceFlower(Items.SUNFLOWER, Blocks.SUNFLOWER, GrowingFlowerSeeds.SUNFLOWER, true);
            Identifier.ofVanilla("blocks/lilac") -> _replaceFlower(Items.LILAC, Blocks.LILAC, GrowingFlowerSeeds.LILAC, true);
            Identifier.ofVanilla("blocks/rose_bush") -> _replaceFlower(Items.ROSE_BUSH, Blocks.ROSE_BUSH, GrowingFlowerSeeds.ROSE_BUSH, true);
            Identifier.ofVanilla("blocks/peony") -> _replaceFlower(Items.PEONY, Blocks.PEONY, GrowingFlowerSeeds.PEONY, true);
            else -> original;
        }
    };

    private fun _replaceFlower(flowerItem: Item, flowerBlock: Block, seedsItem: Item, isTall: Boolean): LootTable =
        if (isTall) LootTable.builder()
            .pool(_seedsPool(seedsItem).conditionally(_bottomHalf(flowerBlock)).conditionally(_byPlayer()).build())
            .pool(_seedsPool(seedsItem).conditionally(_topHalf(flowerBlock)).conditionally(_byPlayer()).build())
            .pool(_itemPool(flowerItem).conditionally(_bottomHalf(flowerBlock)).build())
            .pool(_itemPool(flowerItem).conditionally(_topHalf(flowerBlock)).build())
            .build()
        else LootTable.builder()
            .pool(_seedsPool(seedsItem).build())
            .pool(_itemPool(seedsItem).build())
            .build()
    ;
    private fun _chance(chance: Float): LootCondition.Builder = RandomChanceLootCondition.builder(chance);

    private fun _withShears(value: Boolean): LootCondition.Builder {
        val condition: LootCondition.Builder = MatchToolLootCondition.builder (
            ItemPredicate
                .Builder
                .create()
                .items(Registries.ITEM, {Items.SHEARS})
        );

        return if (value) condition else condition.invert();
    }

    private fun _byPlayer(): LootCondition.Builder = EntityPropertiesLootCondition.builder (
        LootContext.EntityTarget.THIS,
        EntityPredicate.Builder.create().type (
            EntityTypePredicate.create (
                FlourishingFields.getServer()?.getWorld(World.OVERWORLD)?.registryManager?.getEntryOrThrow(RegistryKeys.ENTITY_TYPE)?.value(),
                EntityType.PLAYER
            )
        ).build()
    );

    private fun _seedsPool(seedsItem: Item): LootPool.Builder = LootPool.builder()
        .conditionally(_withShears(false))
        .conditionally(_chance(0.125F))
        .with(ItemEntry.builder(seedsItem))
        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1F, 2F)).build())
    ;

    private fun _itemPool(flowerItem: Item): LootPool.Builder = LootPool.builder()
        .conditionally(_withShears(true))
        .with(ItemEntry.builder(flowerItem))
    ;

    private fun _bottomHalf(flower: Block): LootCondition.Builder = BlockStatePropertyLootCondition
        .builder(flower)
        .properties(StatePredicate.Builder.create().exactMatch(Properties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER))
    ;

    private fun _topHalf(flower: Block): LootCondition.Builder = BlockStatePropertyLootCondition
        .builder(flower)
        .properties(StatePredicate.Builder.create().exactMatch(Properties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER))
    ;
}