package de.lazybird.meliusscientia.worldgen;

import de.lazybird.meliusscientia.init.BiomeInit;
import de.lazybird.meliusscientia.init.ModBlock;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.common.BiomeDictionary;
import sun.security.krb5.SCDynamicStoreConfig;

public class NukedBiome extends Biome {


    public NukedBiome(){
        super(new Biome.Builder()
        .precipitation(RainType.SNOW)
        .scale(0.045F)
        .temperature(0.0F)
        .waterColor(0x96FFA9)
        .waterFogColor(0x96FFA9)
        .surfaceBuilder(new ConfiguredSurfaceBuilder<SurfaceBuilderConfig>(
                BiomeInit.register("meliusscientia:nuked_surface",
                        new NukedBiomeSurfaceBuilder(SurfaceBuilderConfig::deserialize)),
                        new SurfaceBuilderConfig(Blocks.COARSE_DIRT.getDefaultState(),
                                Blocks.DIRT.getDefaultState(),
                                Blocks.DIRT.getDefaultState())))
                .category(Category.PLAINS)
        .downfall(0.3F)
        .depth(0.125F)
        .parent((String)null));
        addCarver(GenerationStage.Carving.AIR, Biome.createCarver(WorldCarver.CAVE, new ProbabilityConfig(0.2F)));
        addCarver(GenerationStage.Carving.AIR, Biome.createCarver(WorldCarver.CANYON, new ProbabilityConfig(0.09F)));
        DefaultBiomeFeatures.addOres(this);
        DefaultBiomeFeatures.addExtraGoldOre(this);
        DefaultBiomeFeatures.addExtraEmeraldOre(this);
        DefaultBiomeFeatures.addLakes(this);
        DefaultBiomeFeatures.addStoneVariants(this);
        DefaultBiomeFeatures.addDeadBushes(this);
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE, 19, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE_VILLAGER, 1, 1, 1));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.HUSK, 80, 4, 4));
    }


}
