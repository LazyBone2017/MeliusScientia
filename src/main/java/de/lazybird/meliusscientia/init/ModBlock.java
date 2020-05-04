package de.lazybird.meliusscientia.init;

import de.lazybird.meliusscientia.MeliusScientia;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.extensions.IForgeBlockState;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

public class ModBlock {

    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, MeliusScientia.MODID);

    public static final RegistryObject<Block> uranium_ore = BLOCKS.register("uranium_ore", () -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 3.0F).harvestLevel(2).harvestTool(ToolType.PICKAXE)));
    public static RegistryObject<Block> test_block = BLOCKS.register("test_block", () -> new Block(Block.Properties.create(Material.BAMBOO)));
    public static RegistryObject<Block> radioactive_dirt = BLOCKS.register("radioactive_dirt", () -> new Block(Block.Properties.create(Material.EARTH).hardnessAndResistance(0.5F).sound(SoundType.GROUND)));
    public static RegistryObject<Block> sludge = BLOCKS.register("sludge", () -> new Block(Block.Properties.create(Material.EARTH).hardnessAndResistance(0.1F).sound(SoundType.SAND)));

}
