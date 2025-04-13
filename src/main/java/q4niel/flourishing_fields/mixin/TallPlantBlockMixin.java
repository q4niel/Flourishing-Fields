package q4niel.flourishing_fields.mixin;

import net.minecraft.block.*;
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

@Mixin(TallPlantBlock.class)
public class TallPlantBlockMixin {
    @Inject (
            method = "(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;)V",
            at = @At("TAIL")
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
            if (state.getBlock() instanceof TallFlowerBlock) {
                world.getWorldChunk(pos.up()).setBlockState(pos.up(), Blocks.AIR.getDefaultState(), false);
                world.setBlockState(pos, Blocks.STONE.getDefaultState(), 3);
            }
        });
    }
}