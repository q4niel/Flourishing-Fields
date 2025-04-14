package dev.q4niel.flourishing_fields

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object FlourishingFields : ModInitializer {
    private val _logger:org.slf4j.Logger = LoggerFactory.getLogger("flourishing_fields");

	override fun onInitialize():Unit {}

	public fun print(value:String):Unit = _logger.info(value);
}