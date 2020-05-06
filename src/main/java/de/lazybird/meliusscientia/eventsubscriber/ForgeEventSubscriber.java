package de.lazybird.meliusscientia.eventsubscriber;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = "meliusscientia", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventSubscriber {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){
        if(Objects.requireNonNull(event.player.world.getBiome(event.player.getPosition()).getRegistryName()).toString().equals("meliusscientia:nuked_biome")){
            //TODO add radiation poisoning
        }
    }
}
