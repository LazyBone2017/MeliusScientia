package de.lazybird.meliusscientia;

import de.lazybird.meliusscientia.init.BiomeInit;
import de.lazybird.meliusscientia.init.ModBlock;
import de.lazybird.meliusscientia.init.ModItem;
import de.lazybird.meliusscientia.init.ModItemGroups;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;


@Mod(MeliusScientia.MODID)
public final class MeliusScientia {

    public static final String MODID = "meliusscientia";

    public MeliusScientia() {

        final ModLoadingContext modLoadingContext = ModLoadingContext.get();
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlock.BLOCKS.register(modEventBus);
        ModItem.ITEMS.register(modEventBus);

        BiomeInit.BIOMES.register(modEventBus);

    }
}
