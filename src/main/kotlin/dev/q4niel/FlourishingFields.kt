package dev.q4niel

import dev.q4niel.block.ModBlocks
import dev.q4niel.item.ModItems
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.minecraft.server.MinecraftServer
import org.slf4j.LoggerFactory
import java.util.concurrent.CompletableFuture

object FlourishingFields : ModInitializer {
    public val modID_: String = "flourishing_fields";

    private val _logger_ = LoggerFactory.getLogger(modID_);
    public fun print(string: String): Unit = _logger_.info(string);

    public var server: MinecraftServer? = null
        private set

    public fun isServer(): Boolean = (server != null);
    public fun serverExec(runnable: Runnable): CompletableFuture<Void>? = server?.submit(runnable);

	override fun onInitialize(): Unit {
        ServerLifecycleEvents.SERVER_STARTED.register { server ->
            this.server = server
            ModConfig.init();
        }

        ModItems.init();
        ModBlocks.init();
        LootTableModifiers.init();
    }
}