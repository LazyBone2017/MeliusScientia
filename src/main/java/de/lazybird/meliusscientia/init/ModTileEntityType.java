package de.lazybird.meliusscientia.init;

import de.lazybird.meliusscientia.MeliusScientia;
import de.lazybird.meliusscientia.tileentity.CombustionGeneratorTileEntity;
import de.lazybird.meliusscientia.tileentity.CrusherTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntityType {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, MeliusScientia.MODID);

    public static final RegistryObject<TileEntityType<CombustionGeneratorTileEntity>> combustion_generator = TILE_ENTITY_TYPES.register("combustion_generator", () -> TileEntityType.Builder.create(CombustionGeneratorTileEntity::new, ModBlock.combustion_generator.get()).build(null));
    public static final RegistryObject<TileEntityType<CrusherTileEntity>> crusher = TILE_ENTITY_TYPES.register("crusher", () -> TileEntityType.Builder.create(CrusherTileEntity::new, ModBlock.crusher.get()).build(null));
}
