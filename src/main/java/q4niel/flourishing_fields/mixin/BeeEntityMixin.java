package q4niel.flourishing_fields.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.passive.BeeEntity;
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

            if (!self.hasNectar()) {
                hasSpread = false;
                return;
            }

            if (flowerState != null
            &&  !hasSpread
            &&  world.getBlockState(self.getBlockPos()).isAir()
            &&  world.getBlockState(self.getBlockPos().down()).isOf(Blocks.GRASS_BLOCK)
            &&  new Random().nextInt(100) < 5
            ) {
                hasSpread = true;
                world.setBlockState (
                        self.getBlockPos(),
                        flowerState,
                        3
                );
            }
        });
    }
}