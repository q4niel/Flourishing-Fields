package dev.q4niel.mixin;

import dev.q4niel.EndpointHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(BeeEntity.class)
public class BeeEntityMixin {
    private BeeEntity _self = EndpointHelper.INSTANCE.isServer()
        ? (BeeEntity)(Object)this
        : null
    ;

    private boolean _hasSpread = false;
    private BlockPos _prevBlockPos = _self.getBlockPos();

    private World _getWorld() {
        return EndpointHelper.INSTANCE.getServer().getWorld(World.OVERWORLD);
    }

    private BlockState _getFlowerBlockState() {
        return _getWorld().getBlockState(_self.getFlowerPos());
    }

    private boolean _spreadRoll() {
        return new Random().nextInt(100) < 5;
    }

    @Inject(method = "tick()V", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        EndpointHelper.INSTANCE.serverExec(() -> {
            if (!_self.hasNectar() && _hasSpread) _hasSpread = false;

            if (_prevBlockPos.getX() == _self.getBlockX()
            &&  _prevBlockPos.getZ() == _self.getBlockZ()
            ) return;
            _prevBlockPos = _self.getBlockPos();

            // 'tick' runs twice for some reason, hence the double roll
            if (!_spreadRoll() && !_spreadRoll()) return;

            if (!_self.hasNectar()
            ||  !_self.hasHivePos()
            ||  _self.getBlockPos() == null
            ||  _hasSpread
            ) return;

            if (_getWorld().getBlockState(_self.getBlockPos()).isAir()
            &&  _getWorld().getBlockState(_self.getBlockPos().down()).isOf(Blocks.GRASS_BLOCK)
            ) {
                _getWorld().setBlockState (
                    _self.getBlockPos(),
                    _getFlowerBlockState(),
                    3
                );
                _hasSpread = true;
            }
            else if (_getWorld().getBlockState(_self.getBlockPos().down()).isAir()
            &&       _getWorld().getBlockState(_self.getBlockPos().down().down()).isOf(Blocks.GRASS_BLOCK)
            ) {
                _getWorld().setBlockState (
                    _self.getBlockPos().down(),
                    _getFlowerBlockState(),
                    3
                );
                _hasSpread = true;
            }
        });
    }
}