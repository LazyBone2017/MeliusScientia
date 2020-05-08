package de.lazybird.meliusscientia;

import de.lazybird.meliusscientia.init.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod(MeliusScientia.MODID)
public final class MeliusScientia {

    public static final String MODID = "meliusscientia";

    public MeliusScientia() {

        final ModLoadingContext modLoadingContext = ModLoadingContext.get();
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlock.BLOCKS.register(modEventBus);
        ModItem.ITEMS.register(modEventBus);
        PotionInit.EFFECTS.register(modEventBus);
        ModTileEntityType.TILE_ENTITY_TYPES.register(modEventBus);
        ModContainerTypes.CONTAINER_TYPES.register(modEventBus);
        BiomeInit.BIOMES.register(modEventBus);

    }
}
