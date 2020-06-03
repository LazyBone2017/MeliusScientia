package de.lazybird.meliusscientia.eventsubscriber;

import de.lazybird.meliusscientia.MeliusScientia;
import de.lazybird.meliusscientia.client.gui.CombustionGeneratorScreen;
import de.lazybird.meliusscientia.client.gui.CrusherScreen;
import de.lazybird.meliusscientia.init.ModBlock;
import de.lazybird.meliusscientia.init.ModContainerTypes;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = MeliusScientia.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventSubscriber {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        ScreenManager.registerFactory(ModContainerTypes.combustion_generator.get(), CombustionGeneratorScreen::new);
        ScreenManager.registerFactory(ModContainerTypes.crusher.get(), CrusherScreen::new);
        RenderTypeLookup.setRenderLayer(ModBlock.beech_sapling.get(), RenderType.getCutout());
    }


}
