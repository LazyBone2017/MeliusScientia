package de.lazybird.meliusscientia;

import de.lazybird.meliusscientia.init.BiomeInit;
import de.lazybird.meliusscientia.worldgen.tree.BeechTree;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class Util {

    public static void generateFeatures(){
        for(Biome biome : ForgeRegistries.BIOMES){
            if(biome == BiomeInit.nuked_biome.get()){
                biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.ACACIA_TREE.withConfiguration(BeechTree.BEECH_TREE_CONFIG).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(1, 0.1F, 1))));
            }
        }
    }
}
