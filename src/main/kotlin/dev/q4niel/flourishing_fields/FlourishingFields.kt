package dev.q4niel.flourishing_fields

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.minecraft.client.MinecraftClient
import net.minecraft.server.MinecraftServer
import org.slf4j.LoggerFactory

object FlourishingFields: ModInitializer {
	fun print(value: String): Unit = _logger.info(value);

	fun getServer(): MinecraftServer? = _server;
	fun isServer(): Boolean = if (_server == null) false else true
	fun serverExec(runnable: Runnable): Unit? = _server?.execute(runnable);
	val serverConfig: ServerConfigFile = configInterpreter<ServerConfigFile>("config/flourishing_fields.json") ?: ServerConfigFile();

	fun getClient(): MinecraftClient? = _client;
	fun isClient(): Boolean = if (_client == null) false else true
	fun clientExec(runnable: Runnable): Unit? = _client?.execute(runnable);

	private val _logger: org.slf4j.Logger = LoggerFactory.getLogger("flourishing_fields");
	private var _server: MinecraftServer? = null;
	private var _client: MinecraftClient? = null;

	override fun onInitialize(): Unit {
		ServerLifecycleEvents.SERVER_STARTED.register { server ->
			_server = server;
		};

		ClientLifecycleEvents.CLIENT_STARTED.register { client ->
			_client = client;
		};
	}
}