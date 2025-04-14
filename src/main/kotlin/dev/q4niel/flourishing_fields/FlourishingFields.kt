package dev.q4niel.flourishing_fields

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.minecraft.client.MinecraftClient
import net.minecraft.server.MinecraftServer
import org.slf4j.LoggerFactory

object FlourishingFields: ModInitializer {
	public fun print(value: String): Unit = _logger.info(value);

	public fun getServer(): MinecraftServer? = _server;
	public fun isServer(): Boolean = if (_server == null) false else true
	public fun serverExec(runnable: Runnable): Unit? = _server?.execute(runnable);

	public fun getClient(): MinecraftClient? = _client;
	public fun isClient(): Boolean = if (_client == null) false else true
	public fun clientExec(runnable: Runnable): Unit? = _client?.execute(runnable);

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