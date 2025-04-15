package dev.q4niel.flourishing_fields

data class ServerConfigFile (
    var beeSpreadChance: Int = 5,
    var flowerSpreadBlacklist: Array<String> = emptyArray()
)