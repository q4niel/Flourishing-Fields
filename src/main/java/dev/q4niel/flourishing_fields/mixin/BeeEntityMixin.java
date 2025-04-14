package dev.q4niel.flourishing_fields.mixin;

import dev.q4niel.flourishing_fields.FlourishingFields;
import dev.q4niel.flourishing_fields.StateMachine;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallFlowerBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Random;

@Mixin(BeeEntity.class)
public class BeeEntityMixin {
    private BeeEntity self = FlourishingFields.INSTANCE.isServer()
        ? (BeeEntity)(Object)this
        : null
    ;

    private World world = FlourishingFields.INSTANCE.isServer()
        ? FlourishingFields.INSTANCE.getServer().getWorld(World.OVERWORLD)
        : null
    ;

    private BlockState getFlowerBlockState() {
        return self.hasFlower() ? world.getBlockState(self.getFlowerPos()) : null;
    }

    private boolean isBottomClear() {
        return world.getBlockState(self.getBlockPos()).isAir()
        &      world.getBlockState(self.getBlockPos().down()).isOf(Blocks.GRASS_BLOCK)
        ;
    }

    private boolean isTopClear() {
        return world.getBlockState(self.getBlockPos().up()).isAir();
    }

    private boolean spreadSuccess() {
        return new Random().nextInt(100) < 100;
    }

//  enum Action {
        private final int _collectingAction = 0;
        private final int _returningAction = 1;
        private final int _shortFlowerAction = 2;
        private final int _tallFlowerAction = 3;
//  }
    StateMachine<Integer> machine = new StateMachine<Integer>(_collectingAction, new HashMap<Integer, Runnable>() {{
        put(_collectingAction, () -> {
            if (!self.hasNectar() || getFlowerBlockState() == null) return;

            if (getFlowerBlockState().getBlock() instanceof TallFlowerBlock) {
                machine.changeAction(_tallFlowerAction);
            }
            else {
                machine.changeAction(_shortFlowerAction);
            }
        });

        put(_returningAction, () -> {
            if (self.hasNectar()) return;
            machine.changeAction(_collectingAction);
        });

        put(_shortFlowerAction, () -> {
            if (!isBottomClear() || !spreadSuccess()) return;

            world.setBlockState (
                    self.getBlockPos(),
                    getFlowerBlockState(),
                    3
            );

            machine.changeAction(_returningAction);
        });

        put(_tallFlowerAction, () -> {
            if (!isBottomClear()
            ||  !isTopClear()
            ||  !spreadSuccess()
            ) return;

            world.setBlockState (
                    self.getBlockPos(),
                    getFlowerBlockState().getBlock().getDefaultState().with(TallFlowerBlock.HALF, DoubleBlockHalf.LOWER),
                    3
            );

            world.setBlockState (
                    self.getBlockPos().up(),
                    getFlowerBlockState().getBlock().getDefaultState().with(TallFlowerBlock.HALF, DoubleBlockHalf.UPPER),
                    3
            );

            machine.changeAction(_returningAction);
        });
    }});

    @Inject(method = "tick()V", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        FlourishingFields.INSTANCE.serverExec(() -> machine.run());
    }
}