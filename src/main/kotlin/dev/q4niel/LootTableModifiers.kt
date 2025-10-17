package dev.q4niel

import dev.q4niel.item.ModItems
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
import net.minecraft.registry.Registries
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.RegistryWrapper
import net.minecraft.state.property.Properties
import net.minecraft.util.Identifier
import net.minecraft.world.World

object LootTableModifiers {
    public fun init(): Unit {
        net.fabricmc.fabric.api.loot.v3.LootTableEvents.REPLACE.register {
            key: RegistryKey<LootTable>,
            original: LootTable,
            source: LootTableSource,
            registries: RegistryWrapper.WrapperLookup
            ->

            when (key.value) {
                // Short Flowers
                Identifier.ofVanilla("blocks/allium") -> _replaceFlower(Items.ALLIUM, Blocks.ALLIUM, ModItems.alliumSeeds_, false);
                Identifier.ofVanilla("blocks/azure_bluet") -> _replaceFlower(Items.AZURE_BLUET, Blocks.AZURE_BLUET, ModItems.azureBluetSeeds_, false);
                Identifier.ofVanilla("blocks/blue_orchid") -> _replaceFlower(Items.BLUE_ORCHID, Blocks.BLUE_ORCHID, ModItems.blueOrchidSeeds_, false);
                Identifier.ofVanilla("blocks/cornflower") -> _replaceFlower(Items.CORNFLOWER, Blocks.CORNFLOWER, ModItems.cornflowerSeeds_, false);
                Identifier.ofVanilla("blocks/dandelion") -> _replaceFlower(Items.DANDELION, Blocks.DANDELION, ModItems.dandelionSeeds_, false);
                Identifier.ofVanilla("blocks/lily_of_the_valley") -> _replaceFlower(Items.LILY_OF_THE_VALLEY, Blocks.LILY_OF_THE_VALLEY, ModItems.lilyOfTheValleySeeds_, false);
                Identifier.ofVanilla("blocks/orange_tulip") -> _replaceFlower(Items.ORANGE_TULIP, Blocks.ORANGE_TULIP, ModItems.orangeTulipSeeds_, false);
                Identifier.ofVanilla("blocks/oxeye_daisy") -> _replaceFlower(Items.OXEYE_DAISY, Blocks.OXEYE_DAISY, ModItems.oxeyeDaisySeeds_, false);
                Identifier.ofVanilla("blocks/pink_tulip") -> _replaceFlower(Items.PINK_TULIP, Blocks.PINK_TULIP, ModItems.pinkTulipSeeds_, false);
                Identifier.ofVanilla("blocks/poppy") -> _replaceFlower(Items.POPPY, Blocks.POPPY, ModItems.poppySeeds_, false);
                Identifier.ofVanilla("blocks/red_tulip") -> _replaceFlower(Items.RED_TULIP, Blocks.RED_TULIP, ModItems.redTulipSeeds_, false);
                Identifier.ofVanilla("blocks/white_tulip") -> _replaceFlower(Items.WHITE_TULIP, Blocks.WHITE_TULIP, ModItems.whiteTulipSeeds_, false);
                Identifier.ofVanilla("blocks/wither_rose") -> _replaceFlower(Items.WITHER_ROSE, Blocks.WITHER_ROSE, ModItems.witherRoseSeeds_, false);

                // Tall Flowers
                Identifier.ofVanilla("blocks/lilac") -> _replaceFlower(Items.LILAC, Blocks.LILAC, ModItems.lilacSeeds_, true);
                Identifier.ofVanilla("blocks/peony") -> _replaceFlower(Items.PEONY, Blocks.PEONY, ModItems.peonySeeds_, true);
                Identifier.ofVanilla("blocks/rose_bush") -> _replaceFlower(Items.ROSE_BUSH, Blocks.ROSE_BUSH, ModItems.roseBushSeeds_, true);
                Identifier.ofVanilla("blocks/sunflower") -> _replaceFlower(Items.SUNFLOWER, Blocks.SUNFLOWER, ModItems.sunflowerSeeds_, true);

                else -> original;
            }
        }
    }

    private fun _replaceFlower(flowerItem: Item, flowerBlock: Block, seedsItem: Item, isTall: Boolean): LootTable =
        if (isTall) LootTable.builder()
            .pool(_seedsPool(seedsItem).conditionally(_bottomHalf(flowerBlock)).conditionally(_byPlayer()).build())
            .pool(_seedsPool(seedsItem).conditionally(_topHalf(flowerBlock)).conditionally(_byPlayer()).build())
            .pool(_itemPool(flowerItem).conditionally(_bottomHalf(flowerBlock)).build())
            .pool(_itemPool(flowerItem).conditionally(_topHalf(flowerBlock)).build())
            .build()
        else LootTable.builder()
            .pool(_seedsPool(seedsItem).build())
            .pool(_itemPool(flowerItem).build())
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
        LootContext.EntityReference.THIS,
        EntityPredicate.Builder.create().type (
            EntityTypePredicate.create (
                FlourishingFields.server
                    ?.getWorld(World.OVERWORLD)
                    ?.registryManager
                    ?.getEntryOrThrow(RegistryKeys.ENTITY_TYPE)?.value(),
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