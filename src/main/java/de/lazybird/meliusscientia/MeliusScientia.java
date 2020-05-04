package de.lazybird.meliusscientia;

import de.lazybird.meliusscientia.init.ModBlock;
import de.lazybird.meliusscientia.init.ModItem;
import net.minecraft.block.BlockState;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod(MeliusScientia.MODID)
public final class MeliusScientia {

    public static final String MODID = "meliusscientia";

    public MeliusScientia() {

        final ModLoadingContext modLoadingContext = ModLoadingContext.get();
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register Deferred Registers (Does not need to be before Configs)
        ModBlock.BLOCKS.register(modEventBus);
        ModItem.ITEMS.register(modEventBus);
        // Register Configs (Does not need to be after Deferred Registers)

    }
}
