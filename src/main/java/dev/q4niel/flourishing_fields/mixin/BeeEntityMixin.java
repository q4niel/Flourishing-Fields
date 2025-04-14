package dev.q4niel.flourishing_fields.mixin;

import dev.q4niel.flourishing_fields.FlourishingFields;
import dev.q4niel.flourishing_fields.StateMachine;
import net.minecraft.entity.passive.BeeEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;

@Mixin(BeeEntity.class)
public class BeeEntityMixin {
    private BeeEntity self() {
        return (BeeEntity)(Object)this;
    }

    StateMachine<String> machine = new StateMachine<>("toFlower", new HashMap<String, Runnable>() {{
        put("toFlower", () -> {
            FlourishingFields.INSTANCE.print("flying to flower");

            if (self().hasNectar()) {
                machine.changeAction("returnHome");
            }
        });

        put("returnHome", () -> {
            FlourishingFields.INSTANCE.print("returning to beehive");

            if (!self().hasNectar()) {
                machine.changeAction("toFlower");
            }
        });
    }});

    @Inject(method = "tick()V", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        FlourishingFields.INSTANCE.serverExec(() -> machine.run());
    }
}