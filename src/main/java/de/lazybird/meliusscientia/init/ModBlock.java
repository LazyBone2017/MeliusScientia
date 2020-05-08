package de.lazybird.meliusscientia.init;

import de.lazybird.meliusscientia.MeliusScientia;
import de.lazybird.meliusscientia.block.BeechSapling;
import de.lazybird.meliusscientia.block.CombustionGenerator;
import de.lazybird.meliusscientia.block.RadioactiveDirtBlock;
import de.lazybird.meliusscientia.worldgen.tree.BeechTree;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlock {

    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, MeliusScientia.MODID);

    public static final RegistryObject<Block> uranium_ore = BLOCKS.register("uranium_ore", () -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 3.0F).harvestLevel(2).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> test_block = BLOCKS.register("test_block", () -> new Block(Block.Properties.create(Material.BAMBOO)));
    public static RegistryObject<Block> radioactive_dirt = BLOCKS.register("radioactive_dirt", () -> new RadioactiveDirtBlock(Block.Properties.from(Blocks.DIRT)));
    public static RegistryObject<Block> sludge = BLOCKS.register("sludge", () -> new Block(Block.Properties.create(Material.EARTH).hardnessAndResistance(0.1F).sound(SoundType.SAND)));
    public static RegistryObject<Block> beech_planks = BLOCKS.register("beech_planks", () -> new Block(Block.Properties.from(Blocks.OAK_PLANKS)));
    public static RegistryObject<Block> beech_leaves = BLOCKS.register("beech_leaves", () -> new LeavesBlock(Block.Properties.from(Blocks.OAK_LEAVES)));
    public static RegistryObject<Block> beech_log = BLOCKS.register("beech_log", () -> new LogBlock(MaterialColor.WOOD, Block.Properties.from(Blocks.OAK_LOG)));
    public static RegistryObject<Block> beech_sapling = BLOCKS.register("beech_sapling", () -> new BeechSapling(BeechTree::new, Block.Properties.from(Blocks.OAK_SAPLING)));
    public static RegistryObject<Block> combustion_generator = BLOCKS.register("combustion_generator", CombustionGenerator::new);

}
