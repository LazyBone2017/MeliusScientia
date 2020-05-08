package de.lazybird.meliusscientia.worldgen.tree;

import de.lazybird.meliusscientia.init.ModBlock;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.AcaciaFoliagePlacer;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nullable;
import java.util.Random;

public class BeechTree extends Tree {

    //going to be a radioactive tree
    //file names stay the same
    public static final TreeFeatureConfig BEECH_TREE_CONFIG = (new TreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(ModBlock.beech_log.get().getDefaultState()),
            new SimpleBlockStateProvider(ModBlock.beech_leaves.get().getDefaultState()),
            new AcaciaFoliagePlacer(2, 0))).baseHeight(3).heightRandA(2).heightRandB(2).trunkHeight(0).ignoreVines()
            .setSapling((IPlantable) ModBlock.beech_sapling.get()).build();

    @Nullable
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean b) {
        return Feature.ACACIA_TREE.withConfiguration(BEECH_TREE_CONFIG);
    }
}
