package q4niel.flourishing_fields.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import q4niel.flourishing_fields.FlourishingFields;

@Mixin(Block.class)
public class BlockMixin {
    @Inject (
            method = "(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;)V",
            at = @At("HEAD")
    )
    void onPlaced (
            World world,
            BlockPos pos,
            BlockState state,
            @Nullable LivingEntity placer,
            ItemStack itemStack,
            CallbackInfo ci
    ) {
        if (FlourishingFields.SERVER == null) return;

        FlourishingFields.SERVER.execute(() -> {
            if (state.getBlock() instanceof FlowerBlock) {
                world.setBlockState(pos, Blocks.STONE.getDefaultState(), 3);
            }
        });
    }
}