package de.lazybird.meliusscientia.worldgen;

import de.lazybird.meliusscientia.init.ModBlock;
import net.minecraft.block.Block;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

public class OreGen {

    //Frequency: 20 -> very common > coal

    public static void generateOre(RegistryObject<Block> ore, int freq, int bottom, int top, int amountMax){
        for(Biome biome : ForgeRegistries.BIOMES){
            ConfiguredPlacement customCfg = Placement.COUNT_RANGE.configure(new CountRangeConfig(freq, bottom, 0, top));
            biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ore.get().getDefaultState(), amountMax)).withPlacement(customCfg));
        }
    }
}
