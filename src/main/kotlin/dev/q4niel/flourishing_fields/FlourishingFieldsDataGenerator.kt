package dev.q4niel.flourishing_fields

import dev.q4niel.flourishing_fields.datagen.ModLootTableProvider
import dev.q4niel.flourishing_fields.datagen.ModModelProvider
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

object FlourishingFieldsDataGenerator: DataGeneratorEntrypoint {
	override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
		val pack: FabricDataGenerator.Pack = fabricDataGenerator.createPack();
		pack.addProvider(::ModLootTableProvider);
		pack.addProvider(::ModModelProvider);
	}
}