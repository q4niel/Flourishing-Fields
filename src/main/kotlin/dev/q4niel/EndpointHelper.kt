package dev.q4niel

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.minecraft.client.MinecraftClient
import net.minecraft.server.MinecraftServer

object EndpointHelper {
    private var _server: MinecraftServer? = null;
    private var _client: MinecraftClient? = null;

    public fun isServer(): Boolean {
        if (_server == null) return false;
        return true;
    }
    public fun isClient(): Boolean = !isServer();

    public fun getServer(): MinecraftServer? = _server;
    public fun getClient(): MinecraftClient? = _client;

    public fun serverExec(runnable: Runnable): Unit? = _server?.execute(runnable);
    public fun clientExec(runnable: Runnable): Unit? = _client?.execute(runnable);

    public fun init(): Unit {
        ServerLifecycleEvents.SERVER_STARTED.register { server ->
            _server = server;
        }

        ClientLifecycleEvents.CLIENT_STARTED.register { client ->
            _client = client;
        }
    }
}