package dev.q4niel.block

import dev.q4niel.FlourishingFields
import dev.q4niel.block.short_flowers.*
import dev.q4niel.block.tall_flowers.*
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

    // Short Flowers
    public val alliumCrop_: Block = _regCrop("allium", ::AlliumCropBlock);
    public val azureBluetCrop_: Block = _regCrop("azure_bluet", ::AzureBluetCropBlock);
    public val blueOrchidCrop_: Block = _regCrop("blue_orchid", ::BlueOrchidCropBlock);
    public val cornflowerCrop_: Block = _regCrop("cornflower", ::CornflowerCropBlock);
    public val dandelionCrop_: Block = _regCrop("dandelion", ::DandelionCropBlock);
    public val lilyOfTheValleyCrop_: Block = _regCrop("lily_of_the_valley", ::LilyOfTheValleyCropBlock);
    public val orangeTulipCrop_: Block = _regCrop("orange_tulip", ::OrangeTulipCropBlock);
    public val oxeyeDaisyCrop_: Block = _regCrop("oxeye_daisy", ::OxeyeDaisyCropBlock);
    public val pinkTulipCrop_: Block = _regCrop("pink_tulip", ::PinkTulipCropBlock);
    public val poppyCrop_: Block = _regCrop("poppy", ::PoppyCropBlock);
    public val redTulipCrop_: Block = _regCrop("red_tulip", ::RedTulipCropBlock);
    public val whiteTulipCrop_: Block = _regCrop("white_tulip", ::WhiteTulipCropBlock);
    public val witherRoseCrop_: Block = _regCrop("wither_rose", ::WitherRoseCropBlock);

    // Tall Flowers
    public val lilacCrop_: TallCrop = _regTallCrop("lilac_top", "lilac_bottom", ::LilacBottomCropBlock);
    public val peonyCrop_: TallCrop = _regTallCrop("peony_top", "peony_bottom", ::PeonyBottomCropBlock);
    public val roseBushCrop_: TallCrop = _regTallCrop("rose_bush_top", "rose_bush_bottom", ::RoseBottomBushCropBlock);
    public val sunflowerCrop_: TallCrop = _regTallCrop("sunflower_top", "sunflower_bottom", ::SunflowerBottomCropBlock);

    public fun init(): Unit {
        for (crop: Block in arrayOf (
            alliumCrop_,
            azureBluetCrop_,
            blueOrchidCrop_,
            cornflowerCrop_,
            dandelionCrop_,
            lilyOfTheValleyCrop_,
            orangeTulipCrop_,
            oxeyeDaisyCrop_,
            pinkTulipCrop_,
            poppyCrop_,
            redTulipCrop_,
            whiteTulipCrop_,
            witherRoseCrop_,

            lilacCrop_.top_,
            lilacCrop_.bottom_,
            peonyCrop_.top_,
            peonyCrop_.bottom_,
            roseBushCrop_.top_,
            roseBushCrop_.bottom_,
            sunflowerCrop_.top_,
            sunflowerCrop_.bottom_
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