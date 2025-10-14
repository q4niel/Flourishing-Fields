package dev.q4niel

import dev.q4niel.item.Items
import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object FlourishingFields : ModInitializer {
    public val modID_: String = "flourishing_fields";

    private val _logger_ = LoggerFactory.getLogger(modID_);
    public fun print(string: String): Unit = _logger_.info(string);

	override fun onInitialize(): Unit {
        EndpointHelper.init();
        Items.init();
    }
}