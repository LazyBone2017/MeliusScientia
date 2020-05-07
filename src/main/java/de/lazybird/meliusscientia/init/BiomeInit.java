package de.lazybird.meliusscientia.init;

import de.lazybird.meliusscientia.MeliusScientia;
import de.lazybird.meliusscientia.worldgen.NukedBiome;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
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
        registerBiome(nuked_biome.get(), 5, BiomeManager.BiomeType.WARM, BiomeDictionary.Type.OVERWORLD);
    }

    @SuppressWarnings("deprecation")
    public static <C extends ISurfaceBuilderConfig, F extends SurfaceBuilder<C>> F register(String key, F builderIn) {
        return (F) (Registry.<SurfaceBuilder<?>>register(Registry.SURFACE_BUILDER, key, builderIn));
    }
}
