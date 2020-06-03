package de.lazybird.meliusscientia.init;

import de.lazybird.meliusscientia.MeliusScientia;
import de.lazybird.meliusscientia.container.CombustionGeneratorContainer;
import de.lazybird.meliusscientia.container.CrusherContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainerTypes {

    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = new DeferredRegister<>(ForgeRegistries.CONTAINERS, MeliusScientia.MODID);

    public static final RegistryObject<ContainerType<CombustionGeneratorContainer>> combustion_generator = CONTAINER_TYPES.register("combustion_generator", () -> IForgeContainerType.create(CombustionGeneratorContainer::new));
    public static final RegistryObject<ContainerType<CrusherContainer>> crusher = CONTAINER_TYPES.register("crusher", () -> IForgeContainerType.create(CrusherContainer::new));

}
