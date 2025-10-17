package dev.q4niel

import com.moandjiezana.toml.Toml
import java.io.File

data class ModConfigFile (
    val beeSpreadChance: Long = 5
)

object ModConfig {
    private var _config: ModConfigFile = ModConfigFile();
    private val _cfgFile: File = File("config/${FlourishingFields.modID_}.toml");

    public fun get(): ModConfigFile? = _config;

    public fun init() {
        if (!_cfgFile.exists()) return;

        val toml = Toml().read(_cfgFile);
        _config = ModConfigFile (
            toml.getLong("beeSpreadChance") ?: _config.beeSpreadChance
        )
    }
}