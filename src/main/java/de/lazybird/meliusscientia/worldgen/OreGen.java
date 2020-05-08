package de.lazybird.meliusscientia.worldgen;

import de.lazybird.meliusscientia.init.BiomeInit;
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

import java.util.ArrayList;

import static de.lazybird.meliusscientia.init.ModBlock.uranium_ore;

public class OreGen {

    //Frequency: 20 -> very common > coal
    private static ArrayList<Object[]> toGenerate = new ArrayList<>();


    public static void generateOres(){
        fillList();
        for(Biome biome : ForgeRegistries.BIOMES) {
            for (Object[] object : toGenerate) {
                RegistryObject<Block> ore = (RegistryObject<Block>) object[0];
                int freq = (int)object[1];
                int bottom = (int)object[2];
                int top = (int)object[3];
                int amountMax = (int)object[4];
                if (biome == BiomeInit.nuked_biome.get() || ore == ModBlock.uranium_ore) {
                    freq *= 3;
                    amountMax *= 2;
                }
                ConfiguredPlacement customCfg = Placement.COUNT_RANGE.configure(new CountRangeConfig(freq, bottom, 0, top));
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ore.get().getDefaultState(), amountMax)).withPlacement(customCfg));
            }
        }
    }

    public static void fillList(){
        Object[] o = {uranium_ore, 15, 0, 256, 5};
        toGenerate.add(o);
    }
}
