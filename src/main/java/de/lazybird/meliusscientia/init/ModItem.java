package de.lazybird.meliusscientia.init;

import de.lazybird.meliusscientia.MeliusScientia;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

public class ModItem {

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, MeliusScientia.MODID);


    public static final RegistryObject<Item> bottle_u03 = ITEMS.register("bottle_u03", () -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
    public static final RegistryObject<Item> bottle_uo2 = ITEMS.register("bottle_u02", () -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
}
