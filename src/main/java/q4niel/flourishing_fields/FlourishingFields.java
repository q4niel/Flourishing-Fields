package q4niel.flourishing_fields;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlourishingFields implements ModInitializer {
	public static final String MOD_ID = "flourishing_fields";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static MinecraftServer SERVER;

	@Override
	public void onInitialize() {
		ServerLifecycleEvents.SERVER_STARTED.register (
				server -> { SERVER = server; }
		);
	}
}