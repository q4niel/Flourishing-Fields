package dev.q4niel.flourishing_fields.growing_flower.crops

import dev.q4niel.flourishing_fields.FlourishingFields
import dev.q4niel.flourishing_fields.growing_flower.GrowingTallFlowerUpperCropBlock
import dev.q4niel.flourishing_fields.growing_flower.crops.peony.GrowingPeonyLowerCropBlock
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
        val UPPER: Block,
        val LOWER: Block
    );

    val DANDELION: Block = regCrop("dandelion", ::GrowingDandelionCropBlock);
    val POPPY: Block = regCrop("poppy", ::GrowingPoppyCropBlock);
    val BLUE_ORCHID: Block = regCrop("blue_orchid", ::GrowingBlueOrchidCropBlock);
    val ALLIUM: Block = regCrop("allium", ::GrowingAlliumCropBlock);
    val AZURE_BLUET: Block = regCrop("azure_bluet", ::GrowingAzureBluetCropBlock);
    val RED_TULIP: Block = regCrop("red_tulip", ::GrowingRedTulipCropBlock);
    val ORANGE_TULIP: Block = regCrop("orange_tulip", ::GrowingOrangeTulipCropBlock);
    val WHITE_TULIP: Block = regCrop("white_tulip", ::GrowingWhiteTulipCropBlock);
    val PINK_TULIP: Block = regCrop("pink_tulip", ::GrowingPinkTulipCropBlock);
    val OXEYE_DAISY: Block = regCrop("oxeye_daisy", ::GrowingOxeyeDaisyCropBlock);
    val CORNFLOWER: Block = regCrop("cornflower", ::GrowingCornflowerCropBlock);
    val LILY_OF_THE_VALLEY: Block = regCrop("lily_of_the_valley", ::GrowingLilyOfTheValleyCropBlock);
    val WITHER_ROSE: Block = regCrop("wither_rose", ::GrowingWitherRoseCropBlock);

    val SUNFLOWER: TallCrop = regTallCrop (
        "sunflower_upper", ::GrowingTallFlowerUpperCropBlock,
        "sunflower_lower", ::GrowingSunflowerLowerCropBlock
    );
    val LILAC: TallCrop = regTallCrop (
        "lilac_upper", ::GrowingTallFlowerUpperCropBlock,
        "lilac_lower", ::GrowingLilacLowerCropBlock
    );
    val ROSE_BUSH: TallCrop = regTallCrop (
        "rose_bush_upper", ::GrowingTallFlowerUpperCropBlock,
        "rose_bush_lower", ::GrowingRoseBushLowerCropBlock
    );
    val PEONY: TallCrop = regTallCrop (
        "peony_upper", ::GrowingTallFlowerUpperCropBlock,
        "peony_lower", ::GrowingPeonyLowerCropBlock
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
            fixLayerMap(c.LOWER);
            fixLayerMap(c.UPPER);
        }
    }

    private fun fixLayerMap(crop: Block): Unit = BlockRenderLayerMap.INSTANCE.putBlock(crop, RenderLayer.getCutout());

    private fun regCrop (
        name: String,
        factory: Function<AbstractBlock.Settings, Block>
    ): Block = reg (
        name,
        true,
        factory,
        cropSettings()
    );

    private fun regTallCrop (
        upperName: String,
        upperFactory: Function<AbstractBlock.Settings, Block>,
        lowerName: String,
        lowerFactory: Function<AbstractBlock.Settings, Block>
    ): TallCrop = TallCrop (
        regCrop(upperName, upperFactory),
        regCrop(lowerName, lowerFactory)
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
        factory: Function<AbstractBlock.Settings, Block>,
        settings: AbstractBlock.Settings
    ): Block {
        val blockKey: RegistryKey<Block> = blockKey(name);
        val block: Block = factory.apply(settings.registryKey(blockKey));

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
        BlockItem (
            block,
            Settings().registryKey(itemKey(name))
        )
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