package dev.q4niel

import dev.q4niel.item.ModItems
import net.fabricmc.fabric.api.loot.v3.LootTableSource
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.entity.EntityType
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.condition.EntityPropertiesLootCondition
import net.minecraft.loot.condition.LootCondition
import net.minecraft.loot.condition.MatchToolLootCondition
import net.minecraft.loot.condition.RandomChanceLootCondition
import net.minecraft.loot.context.LootContext
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.function.SetCountLootFunction
import net.minecraft.loot.provider.number.UniformLootNumberProvider
import net.minecraft.predicate.entity.EntityPredicate
import net.minecraft.predicate.entity.EntityTypePredicate
import net.minecraft.predicate.item.ItemPredicate
import net.minecraft.registry.Registries
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.RegistryWrapper
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
                Identifier.ofVanilla("blocks/allium") -> _replaceFlower(Items.ALLIUM, Blocks.ALLIUM, ModItems.alliumSeeds_, false);
                else -> original;
            }
        }
    }

    private fun _replaceFlower(flowerItem: Item, flowerBlock: Block, seedsItem: Item, isTall: Boolean): LootTable =
        if (isTall) LootTable.builder()
//            .pool(_seedsPool(seedsItem).conditionally(_bottomHalf(flowerBlock)).conditionally(_byPlayer()).build())
//            .pool(_seedsPool(seedsItem).conditionally(_topHalf(flowerBlock)).conditionally(_byPlayer()).build())
//            .pool(_itemPool(flowerItem).conditionally(_bottomHalf(flowerBlock)).build())
//            .pool(_itemPool(flowerItem).conditionally(_topHalf(flowerBlock)).build())
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
                EndpointHelper.getServer()
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
}