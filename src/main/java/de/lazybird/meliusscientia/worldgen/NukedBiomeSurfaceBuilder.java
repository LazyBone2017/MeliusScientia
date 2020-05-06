package de.lazybird.meliusscientia.worldgen;

import com.mojang.datafixers.Dynamic;
import de.lazybird.meliusscientia.init.ModBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;
import java.util.function.Function;

public class NukedBiomeSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {


    public NukedBiomeSurfaceBuilder(Function<Dynamic<?>, ? extends SurfaceBuilderConfig> dynamicFunction) {
        super(dynamicFunction);
    }

    @Override
    public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
        Random rd = new Random();
        int i = rd.nextInt(8);
        if(i == 0 || i == 1) {
            SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed,
                    new SurfaceBuilderConfig(Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState(), ModBlock.sludge.get().getDefaultState()));

        }
        else if(i == 2){
            SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed,
                    new SurfaceBuilderConfig(ModBlock.sludge.get().getDefaultState(), Blocks.STONE.getDefaultState(), ModBlock.sludge.get().getDefaultState()));
        }
        else if(i == 3 || i == 4){
            SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed,
                    new SurfaceBuilderConfig(Blocks.COBBLESTONE.getDefaultState(), Blocks.STONE.getDefaultState(), ModBlock.sludge.get().getDefaultState()));
        }
        else{
            SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed,
                    new SurfaceBuilderConfig(ModBlock.radioactive_dirt.get().getDefaultState(), Blocks.STONE.getDefaultState(), ModBlock.sludge.get().getDefaultState()));
        }


    }
}
