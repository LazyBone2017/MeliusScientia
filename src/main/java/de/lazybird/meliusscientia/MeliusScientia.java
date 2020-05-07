package de.lazybird.meliusscientia;

import de.lazybird.meliusscientia.init.*;
import de.lazybird.meliusscientia.worldgen.OreGen;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;

import static de.lazybird.meliusscientia.init.ModBlock.uranium_ore;


@Mod(MeliusScientia.MODID)
public final class MeliusScientia {

    public static final String MODID = "meliusscientia";

    public MeliusScientia() {

        final ModLoadingContext modLoadingContext = ModLoadingContext.get();
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlock.BLOCKS.register(modEventBus);
        ModItem.ITEMS.register(modEventBus);
        PotionInit.EFFECTS.register(modEventBus);
        //PotionInit.POTIONS.register(modEventBus);

        BiomeInit.BIOMES.register(modEventBus);

    }
}
