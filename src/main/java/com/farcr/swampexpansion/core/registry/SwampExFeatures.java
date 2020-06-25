package com.farcr.swampexpansion.core.registry;

import com.farcr.swampexpansion.common.world.biome.SwampExBiomeFeatures;
import com.farcr.swampexpansion.common.world.gen.feature.CattailsFeature;
import com.farcr.swampexpansion.common.world.gen.feature.DenseCattailsFeature;
import com.farcr.swampexpansion.common.world.gen.feature.RiceFeature;
import com.farcr.swampexpansion.common.world.gen.feature.WillowTreeFeature;
import com.farcr.swampexpansion.core.SwampExpansion;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = SwampExpansion.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SwampExFeatures {

	public static final Feature<NoFeatureConfig> CATTAILS = new CattailsFeature(NoFeatureConfig::deserialize);
	public static final Feature<NoFeatureConfig> DENSE_CATTAILS = new DenseCattailsFeature(NoFeatureConfig::deserialize);
	public static final Feature<NoFeatureConfig> RICE = new RiceFeature(NoFeatureConfig::deserialize);
	public static final Feature<TreeFeatureConfig> WILLOW_TREE = new WillowTreeFeature(TreeFeatureConfig::func_227338_a_);

	@SubscribeEvent
	public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
		event.getRegistry().registerAll(
				CATTAILS.setRegistryName("cattails"), 
				DENSE_CATTAILS.setRegistryName("dense_cattails"), 
				RICE.setRegistryName("rice"), 
				WILLOW_TREE.setRegistryName("willow_tree"));
	}

	public static void generateFeatures() {
		ForgeRegistries.BIOMES.getValues().forEach(SwampExFeatures::generate);
	}

	public static void generate(Biome biome) {
		if (biome == Biomes.SWAMP || biome == Biomes.SWAMP_HILLS) {
			SwampExBiomeFeatures.overrideFeatures(biome);
			SwampExBiomeFeatures.addMushrooms(biome);
			SwampExBiomeFeatures.addWillowTrees(biome);
			SwampExBiomeFeatures.addSwampOaks(biome);
			SwampExBiomeFeatures.addCattails(biome);
			SwampExBiomeFeatures.addDuckweed(biome, 0.15F);
		}
		
		if (biome.getTempCategory() != Biome.TempCategory.COLD && (biome.getCategory() == Biome.Category.SWAMP || biome.getCategory() == Biome.Category.RIVER)) {
			SwampExBiomeFeatures.addCattails(biome);
		}
		
		if (biome.getCategory() == Biome.Category.SAVANNA || biome.getRegistryName().toString().contains("rosewood")) {
			SwampExBiomeFeatures.addGiantTallGrass(biome, 3);
		}
		
		if (biome.getCategory() == Biome.Category.JUNGLE) {
			SwampExBiomeFeatures.addGiantTallGrass(biome, 5);
		}
		
		if (biome.getCategory() == Biome.Category.PLAINS) {
			SwampExBiomeFeatures.addGiantTallGrass(biome, 1);
		}
	}
}