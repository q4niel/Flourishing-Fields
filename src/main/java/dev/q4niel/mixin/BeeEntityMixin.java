package dev.q4niel.mixin;

import dev.q4niel.FlourishingFields;
import dev.q4niel.FlowerSeedsPlantablesKt;
import dev.q4niel.ModConfig;
import dev.q4niel.block.VanillaFlowerToCropKt;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TallFlowerBlock;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.Random;

@Mixin(BeeEntity.class)
public class BeeEntityMixin {
    private BeeEntity _self = FlourishingFields.INSTANCE.isServer()
        ? (BeeEntity)(Object)this
        : null
    ;

    private boolean _hasSpread = false;
    private BlockPos _prevBlockPos = _self.getBlockPos();

    private World _getWorld() {
        return FlourishingFields.INSTANCE.getServer().getWorld(World.OVERWORLD);
    }

    private BlockState _getFlowerBlockState() {
        return _getWorld().getBlockState(_self.getFlowerPos());
    }

    private boolean _spreadRoll() {
        int percentage = (int)ModConfig.INSTANCE.get().getBeeSpreadChance();
        return new Random().nextInt(100) < percentage;
    }

    @Inject(method = "tick()V", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        FlourishingFields.INSTANCE.serverExec(() -> {
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
            ||  !_getWorld().getBlockState(_self.getBlockPos()).isAir()
            ) return;

            BlockPos plantPos = (_getWorld().getBlockState(_self.getBlockPos().down()).isAir())
                ?   _self.getBlockPos().down()
                :   _self.getBlockPos()
            ;

            if (_getFlowerBlockState().getBlock() instanceof TallFlowerBlock) {
                if (!_getWorld().getBlockState(plantPos.up()).isAir()) return;
            }

            for (Block plantable : FlowerSeedsPlantablesKt.getFlowerSeedsPlantables_()) {
                if (plantable != _getWorld().getBlockState(plantPos.down()).getBlock()) continue;

                for (Map.Entry<Block, Block> entry : VanillaFlowerToCropKt.getVanillaFlowerToCrop_().entrySet()) {
                    if (entry.getKey() == _getFlowerBlockState().getBlock()) {
                        _getWorld().setBlockState (
                                plantPos,
                                entry.getValue().getDefaultState(),
                                3
                        );
                        _hasSpread = true;
                        break;
                    }
                }

                break;
            }
        });
    }
}