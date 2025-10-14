package dev.q4niel

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object FlourishingFields : ModInitializer {
    public const val modID_: String = "flourishing_fields";

    private val _logger = LoggerFactory.getLogger(modID_);
    public fun print(string: String): Unit = _logger.info(string);

	override fun onInitialize(): Unit {
        EndpointHelper.init();
    }
}