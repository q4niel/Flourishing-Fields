package dev.q4niel.flourishing_fields

import dev.q4niel.flourishing_fields.growing_flower.crops.GrowingFlowerCrops
import dev.q4niel.flourishing_fields.growing_flower.seeds.GrowingFlowerSeeds
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.minecraft.client.MinecraftClient
import net.minecraft.server.MinecraftServer
import org.slf4j.LoggerFactory

object FlourishingFields: ModInitializer {
  const val MOD_ID: String = "flourishing_fields";
	fun print(value: String): Unit = _logger.info(value);

	fun getServer(): MinecraftServer? = _server;
	fun isServer(): Boolean = if (_server == null) false else true
	fun serverExec(runnable: Runnable): Unit? = _server?.execute(runnable);
	val serverConfig: ServerConfigFile = configInterpreter<ServerConfigFile>("config/${MOD_ID}.json") ?: ServerConfigFile();

	fun getClient(): MinecraftClient? = _client;
	fun isClient(): Boolean = if (_client == null) false else true
	fun clientExec(runnable: Runnable): Unit? = _client?.execute(runnable);

	private val _logger: org.slf4j.Logger = LoggerFactory.getLogger(MOD_ID);
	private var _server: MinecraftServer? = null;
	private var _client: MinecraftClient? = null;

	override fun onInitialize(): Unit {
		GrowingFlowerCrops.init();
		GrowingFlowerSeeds.init();
		ModLootTableModifiers.init();

		ServerLifecycleEvents.SERVER_STARTED.register { server ->
			_server = server;
		};

		ClientLifecycleEvents.CLIENT_STARTED.register { client ->
			_client = client;
		};
	}
}