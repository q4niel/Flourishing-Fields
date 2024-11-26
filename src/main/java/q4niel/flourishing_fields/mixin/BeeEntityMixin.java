package q4niel.flourishing_fields.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import q4niel.flourishing_fields.FlourishingFields;

@Mixin(BeeEntity.class)
public class BeeEntityMixin {
    boolean spread = false;

    @Inject (
            method = "tick()V",
            at = @At("HEAD")
    )
    public void tick(CallbackInfo info) {
        FlourishingFields.SERVER.execute(() -> {
            BeeEntity self = (BeeEntity)(Object)this;
            if (!self.hasNectar()) {
                spread = false;
                return;
            }

            if (spread || self.getFlowerPos() == null) return;
            spread = true;
            World world = FlourishingFields.SERVER.getWorld(World.OVERWORLD);

            BlockPos srcFlowerPos = self.getFlowerPos();
            BlockPos dstFlowerPos = switch ((int)(Math.random() * 4)) {
                case 0 -> srcFlowerPos.north();
                case 1 -> srcFlowerPos.south();
                case 2 -> srcFlowerPos.west();
                case 3 -> srcFlowerPos.east();
                default -> null;
            };
            if (!world.getBlockState(dstFlowerPos).isAir()) return;

            BlockState srcSoilState = world.getBlockState(srcFlowerPos.down());
            BlockState dstSoilState = world.getBlockState(dstFlowerPos.down());
            if (srcSoilState.equals(dstSoilState)) {
                world.setBlockState (
                        dstFlowerPos,
                        world.getBlockState(srcFlowerPos),
                        3
                );
            }
        });
    }
}