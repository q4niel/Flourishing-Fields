package dev.q4niel.block

import dev.q4niel.FlourishingFields
import dev.q4niel.block.short_flowers.AlliumCropBlock
import dev.q4niel.block.tall_flowers.peony.PeonyBottomCropBlock
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
    public data class TallCrop (
        val top_: Block,
        val bottom_: Block
    );

    public val alliumCrop_: Block = _regCrop("allium", ::AlliumCropBlock);

    public val peony_: TallCrop = _regTallCrop("peony_top", "peony_bottom", ::PeonyBottomCropBlock);

    public fun init(): Unit {
        for (crop: Block in arrayOf (
            alliumCrop_,
            peony_.top_,
            peony_.bottom_
        )) {
            fixLayerMap(crop);
        }
    }

    private fun fixLayerMap(crop: Block): Unit {
        BlockRenderLayerMap.putBlock(crop, BlockRenderLayer.CUTOUT);
    }

    private fun _regCrop (
        name: String,
        factory: Function<AbstractBlock.Settings, Block>
    ): Block = _reg (
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

    private fun _regTallCrop (
        upperName: String,
        lowerName: String,
        lowerFactory: Function<AbstractBlock.Settings, Block>
    ): TallCrop = TallCrop (
        _regCrop(upperName, ::TallFlowerTopCropBlock),
        _regCrop(lowerName, lowerFactory)
    );

    private fun _reg (
        name: String,
        withItem: Boolean,
        factory: Function<AbstractBlock.Settings, Block>,
        settings: AbstractBlock.Settings
    ): Block {
        val blockKey_: RegistryKey<Block> = _blockKey(name);
        val block_: Block = factory.apply(settings.registryKey(blockKey_));

        if (withItem) _regBlockItem(name, block_);

        return Registry.register (
            Registries.BLOCK,
            _blockKey(name),
            block_
        );
    }

    private fun _regBlockItem(name: String, block: Block): BlockItem = Registry.register (
        Registries.ITEM,
        _itemKey(name),
        BlockItem (
            block,
            Settings().registryKey(_itemKey(name))
        )
    );

    private fun _blockKey(name: String): RegistryKey<Block> = RegistryKey.of (
        RegistryKeys.BLOCK,
        Identifier.of(FlourishingFields.modID_, name)
    );

    private fun _itemKey(name: String): RegistryKey<Item> = RegistryKey.of (
        RegistryKeys.ITEM,
        Identifier.of(FlourishingFields.modID_, name)
    );
}