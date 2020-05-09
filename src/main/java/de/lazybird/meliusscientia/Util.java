package de.lazybird.meliusscientia;

import de.lazybird.meliusscientia.init.BiomeInit;
import de.lazybird.meliusscientia.init.ModItem;
import de.lazybird.meliusscientia.worldgen.tree.BeechTree;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

    public static boolean isWearingRPS(PlayerEntity player){
        Iterable<ItemStack> armor = player.getArmorInventoryList();
        for(ItemStack slot : armor){
            if(slot.isEmpty() || !slot.getItem().getRegistryName().toString().contains("rps"))return false;
        }
        return true;
    }
}
