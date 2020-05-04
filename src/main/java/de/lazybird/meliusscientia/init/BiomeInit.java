package de.lazybird.meliusscientia.init;

import de.lazybird.meliusscientia.MeliusScientia;
import de.lazybird.meliusscientia.worldgen.NukedBiome;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BiomeInit {

    public static final DeferredRegister<Biome> BIOMES = new DeferredRegister<>(ForgeRegistries.BIOMES, MeliusScientia.MODID);

    public static final RegistryObject<Biome> nuked_biome = BIOMES.register("nuked_biome", () -> new NukedBiome());

    private static void registerBiome(Biome biome, int weight, BiomeManager.BiomeType type, BiomeDictionary.Type... types){
        BiomeDictionary.addTypes(biome, types);
        BiomeManager.addSpawnBiome(biome);
        BiomeManager.addBiome(type, new BiomeManager.BiomeEntry(biome, weight));

    }

    public static void registerBiomes(){
        registerBiome(nuked_biome.get(), 10, BiomeManager.BiomeType.ICY, BiomeDictionary.Type.OVERWORLD);
    }

}
