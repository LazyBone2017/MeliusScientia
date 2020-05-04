package de.lazybird.meliusscientia.worldgen;

import de.lazybird.meliusscientia.init.ModBlock;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.common.BiomeDictionary;

public class NukedBiome extends Biome {


    public NukedBiome() {
        super(new Biome.Builder()
        .precipitation(RainType.SNOW)
        .scale(0.065F)
        .temperature(0.0F)
        .waterColor(0x96FFA9)
        .waterFogColor(0x96FFA9)
        .surfaceBuilder(SurfaceBuilder.DEFAULT, new SurfaceBuilderConfig(ModBlock.radioactive_dirt.get().getDefaultState(), Blocks.STONE.getDefaultState(), ModBlock.sludge.get().getDefaultState()))
        .category(Category.ICY)
        .downfall(0.3F)
        .depth(0.125F)
        .parent(null));
        addCarver(GenerationStage.Carving.AIR, Biome.createCarver(WorldCarver.CAVE, new ProbabilityConfig(0.2F)));
        addCarver(GenerationStage.Carving.AIR, Biome.createCarver(WorldCarver.CANYON, new ProbabilityConfig(0.03F)));
        DefaultBiomeFeatures.addOres(this);
        DefaultBiomeFeatures.addExtraGoldOre(this);
        DefaultBiomeFeatures.addExtraEmeraldOre(this);
    }


}
