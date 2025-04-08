package q4niel.flourishing_fields.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallFlowerBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.registry.Registries;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import q4niel.flourishing_fields.FlourishingFields;

import java.util.Random;

@Mixin(BeeEntity.class)
public class BeeEntityMixin {
    BlockState flowerState = null;
    boolean hasSpread = false;

    @Inject (
            method = "tick()V",
            at = @At("HEAD")
    )
    public void tick(CallbackInfo info) {
        if (FlourishingFields.SERVER == null) return;

        FlourishingFields.SERVER.execute(() -> {
            BeeEntity self = (BeeEntity)(Object)this;
            World world = FlourishingFields.SERVER.getWorld(World.OVERWORLD);

            if (self.hasFlower()) {
                flowerState = world.getBlockState(self.getFlowerPos());
            }

            if (flowerState == null) return;

            if (!self.hasNectar() || isBlacklisted()) {
                hasSpread = false;
                return;
            }

            if (!hasSpread
            &&  world.getBlockState(self.getBlockPos()).isAir()
            &&  world.getBlockState(self.getBlockPos().down()).isOf(Blocks.GRASS_BLOCK)
            &&  new Random().nextInt(100) < FlourishingFields.SERVER_CONFIG.BEE_SPREAD_CHANCE
            ) {
                if (flowerState.getBlock() instanceof TallFlowerBlock) {
                    if (!world.getBlockState(self.getBlockPos().up()).isAir()) return;
                    hasSpread = true;

                    world.setBlockState (
                            self.getBlockPos(),
                            flowerState.getBlock().getDefaultState().with(TallFlowerBlock.HALF, DoubleBlockHalf.LOWER),
                            3
                    );

                    world.setBlockState (
                            self.getBlockPos().up(),
                            flowerState.getBlock().getDefaultState().with(TallFlowerBlock.HALF, DoubleBlockHalf.UPPER),
                            3
                    );

                    return;
                }

                hasSpread = true;
                world.setBlockState (
                        self.getBlockPos(),
                        flowerState,
                        3
                );
            }
        });
    }

    private boolean isBlacklisted() {
        String flowerId = String.valueOf(Registries.BLOCK.getId(flowerState.getBlock()));

        for (String blackId : FlourishingFields.SERVER_CONFIG.FLOWER_SPREAD_BLACKLIST) {
            if (blackId.equals(flowerId)) return true;
        }

        return false;
    }
}