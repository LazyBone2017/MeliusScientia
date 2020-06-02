package de.lazybird.meliusscientia.eventsubscriber;

import de.lazybird.meliusscientia.MeliusScientia;
import de.lazybird.meliusscientia.Util;
import de.lazybird.meliusscientia.init.BiomeInit;
import de.lazybird.meliusscientia.init.ModBlock;
import de.lazybird.meliusscientia.init.ModItemGroups;
import de.lazybird.meliusscientia.init.RecipeInit;
import de.lazybird.meliusscientia.worldgen.OreGen;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = MeliusScientia.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventSubscriber {

    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();

        ModBlock.BLOCKS.getEntries().stream()
                .map(RegistryObject::get)
                // You can do extra filtering here if you don't want some tags to have an BlockItem automatically registered for them
                // .filter(block -> needsItemBlock(block))
                // Register the BlockItem for the block
                .forEach(block -> {
                    // Make the properties, and make it so that the item will be on our ItemGroup (CreativeTab)
                    final Item.Properties properties = new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP);
                    // Create the new BlockItem with the block and it's properties
                    final BlockItem blockItem = new BlockItem(block, properties);
                    // Set the new BlockItem's registry name to the block's registry name
                    blockItem.setRegistryName(block.getRegistryName());
                    // Register the BlockItem
                    registry.register(blockItem);
                });
    }

    @SubscribeEvent
    public static void loadCompleteEvent(FMLLoadCompleteEvent event){
        RecipeInit.registerRecipes();
    }

    @SubscribeEvent
    public static void setup(FMLClientSetupEvent event){
        DeferredWorkQueue.runLater(OreGen::generateOres);
        DeferredWorkQueue.runLater(Util::generateFeatures);
    }

    @SubscribeEvent
    public static void onRegisterBiome(RegistryEvent<Biome> event){
        BiomeInit.registerBiomes();
    }

}
