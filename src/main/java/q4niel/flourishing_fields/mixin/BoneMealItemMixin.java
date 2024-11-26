package q4niel.flourishing_fields.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import static net.minecraft.block.Block.dropStack;

@Mixin(BoneMealItem.class)
public class BoneMealItemMixin {
    @Inject (
            method = "useOnFertilizable(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Z",
            at = @At (
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;",
                    ordinal = 0
            ),
            cancellable = true
    )
    private static void afterGetBlockState(ItemStack stack, World world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockState = world.getBlockState(pos);
        if (blockState.getBlock() instanceof FlowerBlock && world instanceof ServerWorld) {
            PlayerEntity player = MinecraftClient.getInstance().player;
            player.swingHand(player.getActiveHand());
            dropStack(world, pos, new ItemStack(blockState.getBlock()));
            stack.decrement(1);
            cir.setReturnValue(true);
        }
    }
}