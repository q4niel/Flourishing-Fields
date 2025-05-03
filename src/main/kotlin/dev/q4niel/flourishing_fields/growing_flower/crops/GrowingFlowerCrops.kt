package dev.q4niel.flourishing_fields.growing_flower.crops

import dev.q4niel.flourishing_fields.FlourishingFields
import dev.q4niel.flourishing_fields.growing_flower.GrowingTallFlowerTopCropBlock
import dev.q4niel.flourishing_fields.growing_flower.crops.peony.GrowingPeonyBottomCropBlock
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.MapColor
import net.minecraft.block.piston.PistonBehavior
import net.minecraft.client.render.RenderLayer
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.Item.Settings
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.Identifier
import java.util.function.Function

object GrowingFlowerCrops {
    data class TallCrop (
        val TOP: Block,
        val BOTTOM: Block
    );

    val DANDELION: Block = regCrop("dandelion", GrowingDandelionCropBlock(cropSettings()));
    val POPPY: Block = regCrop("poppy", GrowingPoppyCropBlock(cropSettings()));
    val BLUE_ORCHID: Block = regCrop("blue_orchid", GrowingBlueOrchidCropBlock(cropSettings()));
    val ALLIUM: Block = regCrop("allium", GrowingAlliumCropBlock(cropSettings()));
    val AZURE_BLUET: Block = regCrop("azure_bluet", GrowingAzureBluetCropBlock(cropSettings()));
    val RED_TULIP: Block = regCrop("red_tulip", GrowingRedTulipCropBlock(cropSettings()));
    val ORANGE_TULIP: Block = regCrop("orange_tulip", GrowingOrangeTulipCropBlock(cropSettings()));
    val WHITE_TULIP: Block = regCrop("white_tulip", GrowingWhiteTulipCropBlock(cropSettings()));
    val PINK_TULIP: Block = regCrop("pink_tulip", GrowingPinkTulipCropBlock(cropSettings()));
    val OXEYE_DAISY: Block = regCrop("oxeye_daisy", GrowingOxeyeDaisyCropBlock(cropSettings()));
    val CORNFLOWER: Block = regCrop("cornflower", GrowingCornflowerCropBlock(cropSettings()));
    val LILY_OF_THE_VALLEY: Block = regCrop("lily_of_the_valley", GrowingLilyOfTheValleyCropBlock(cropSettings()));
    val WITHER_ROSE: Block = regCrop("wither_rose", GrowingWitherRoseCropBlock(cropSettings()));

    val SUNFLOWER: TallCrop = regTallCrop (
        "sunflower_top", GrowingTallFlowerTopCropBlock(cropSettings()),
        "sunflower_bottom", GrowingSunflowerBottomCropBlock(cropSettings())
    );
    val LILAC: TallCrop = regTallCrop (
        "lilac_top", GrowingTallFlowerTopCropBlock(cropSettings()),
        "lilac_bottom", GrowingLilacBottomCropBlock(cropSettings())
    );
    val ROSE_BUSH: TallCrop = regTallCrop (
        "rose_bush_top", GrowingTallFlowerTopCropBlock(cropSettings()),
        "rose_bush_bottom", GrowingRoseBushBottomCropBlock(cropSettings())
    );
    val PEONY: TallCrop = regTallCrop (
        "peony_top", GrowingTallFlowerTopCropBlock(cropSettings()),
        "peony_bottom", GrowingPeonyBottomCropBlock(cropSettings())
    );

    fun init(): Unit {
        for (c: Block in arrayOf (
            DANDELION,
            POPPY,
            BLUE_ORCHID,
            ALLIUM,
            AZURE_BLUET,
            RED_TULIP,
            ORANGE_TULIP,
            WHITE_TULIP,
            PINK_TULIP,
            OXEYE_DAISY,
            CORNFLOWER,
            LILY_OF_THE_VALLEY,
            WITHER_ROSE
        )) {
            fixLayerMap(c);
        }

        for (c: TallCrop in arrayOf (
            SUNFLOWER,
            LILAC,
            ROSE_BUSH,
            PEONY
        )) {
            fixLayerMap(c.BOTTOM);
            fixLayerMap(c.TOP);
        }
    }

    private fun fixLayerMap(crop: Block): Unit = BlockRenderLayerMap.INSTANCE.putBlock(crop, RenderLayer.getCutout());

    private fun regCrop (
        name: String,
        block: Block
    ): Block = reg (
        name,
        true,
        block
    );

    private fun regTallCrop (
        upperName: String,
        upperBlock: Block,
        lowerName: String,
        lowerBlock: Block
    ): TallCrop = TallCrop (
        regCrop(upperName, upperBlock),
        regCrop(lowerName, lowerBlock)
    );

    private fun cropSettings(): AbstractBlock.Settings = AbstractBlock.Settings
        .create()
        .noCollision()
        .ticksRandomly()
        .breakInstantly()
        .sounds(BlockSoundGroup.CROP)
        .pistonBehavior(PistonBehavior.DESTROY)
        .mapColor(MapColor.DARK_GREEN)
    ;

    private fun reg (
        name: String,
        withItem: Boolean,
        block: Block
    ): Block {
        if (withItem) regBlockItem(name, block);

        return Registry.register (
            Registries.BLOCK,
            blockKey(name),
            block
        );
    }

    private fun regBlockItem(name: String, block: Block): BlockItem = Registry.register (
        Registries.ITEM,
        itemKey(name),
        BlockItem(block, Settings())
    );

    private fun blockKey(name: String): RegistryKey<Block> = RegistryKey.of (
        RegistryKeys.BLOCK,
        Identifier.of(FlourishingFields.MOD_ID, name)
    );

    private fun itemKey(name: String): RegistryKey<Item> = RegistryKey.of (
        RegistryKeys.ITEM,
        Identifier.of(FlourishingFields.MOD_ID, name)
    );
}