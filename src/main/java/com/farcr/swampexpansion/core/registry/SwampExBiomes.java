package com.farcr.swampexpansion.core.registry;

import com.farcr.swampexpansion.common.world.biome.MarshBiome;
import com.farcr.swampexpansion.common.world.biome.MushroomMarshBiome;
import com.farcr.swampexpansion.core.SwampExpansion;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SwampExBiomes {
	public static final DeferredRegister<Biome> BIOMES = new DeferredRegister<>(ForgeRegistries.BIOMES, SwampExpansion.MODID);

	public static final RegistryObject<Biome> MARSH = BIOMES.register("marsh", () -> new MarshBiome());
	public static final RegistryObject<Biome> MUSHROOM_MARSH = BIOMES.register("mushroom_marsh", () -> new MushroomMarshBiome());

	public static void registerBiomesToDictionary() {
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(MARSH.get(), 6));
	}
	
	public static void addBiomeTypes() {
        BiomeDictionary.addTypes(MARSH.get(), BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.WET, BiomeDictionary.Type.SWAMP);
        BiomeDictionary.addTypes(MUSHROOM_MARSH.get(), BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.RARE, BiomeDictionary.Type.WET, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.SWAMP);
	}
}
