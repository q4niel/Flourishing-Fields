package dev.q4niel.block

import dev.q4niel.FlourishingFields
import dev.q4niel.block.short_flower.AlliumCropBlock
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.MapColor
import net.minecraft.block.piston.PistonBehavior
import net.minecraft.client.render.BlockRenderLayer
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import java.util.function.Function
import net.minecraft.item.Item.Settings
import net.minecraft.registry.RegistryKeys
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.Identifier

object ModBlocks {
    val alliumCrop_: Block = regCrop("allium", ::AlliumCropBlock);

    public fun init(): Unit {
        for (crop: Block in arrayOf (
            alliumCrop_
        )) {
            fixLayerMap(crop);
        }
    }

    private fun fixLayerMap(crop: Block): Unit {
        BlockRenderLayerMap.putBlock(crop, BlockRenderLayer.CUTOUT);
    }

    private fun regCrop (
        name: String,
        factory: Function<AbstractBlock.Settings, Block>
    ): Block = reg (
        name,
        true,
        factory,
        AbstractBlock.Settings
            .create()
            .noCollision()
            .ticksRandomly()
            .breakInstantly()
            .sounds(BlockSoundGroup.CROP)
            .pistonBehavior(PistonBehavior.DESTROY)
            .mapColor(MapColor.DARK_GREEN)
    );

    private fun reg (
        name: String,
        withItem: Boolean,
        factory: Function<AbstractBlock.Settings, Block>,
        settings: AbstractBlock.Settings
    ): Block {
        val blockKey_: RegistryKey<Block> = blockKey(name);
        val block_: Block = factory.apply(settings.registryKey(blockKey_));

        if (withItem) regBlockItem(name, block_);

        return Registry.register (
            Registries.BLOCK,
            blockKey(name),
            block_
        );
    }

    private fun regBlockItem(name: String, block: Block): BlockItem = Registry.register (
        Registries.ITEM,
        itemKey(name),
        BlockItem (
            block,
            Settings().registryKey(itemKey(name))
        )
    );

    private fun blockKey(name: String): RegistryKey<Block> = RegistryKey.of (
        RegistryKeys.BLOCK,
        Identifier.of(FlourishingFields.modID_, name)
    );

    private fun itemKey(name: String): RegistryKey<Item> = RegistryKey.of (
        RegistryKeys.ITEM,
        Identifier.of(FlourishingFields.modID_, name)
    );
}