package de.lazybird.meliusscientia.init;

import de.lazybird.meliusscientia.MeliusScientia;
import de.lazybird.meliusscientia.item.GeigerCounterItem;
import de.lazybird.meliusscientia.util.enums.ModArmorMaterials;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItem {

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, MeliusScientia.MODID);


    public static final RegistryObject<Item> bottle_u03 = ITEMS.register("bottle_u03", () -> new Item(new Item.Properties().group(ModItemGroups.MELIUSSCIENTIA_ITEM_GROUP_MAIN)));
    public static final RegistryObject<Item> bottle_uo2 = ITEMS.register("bottle_u02", () -> new Item(new Item.Properties().group(ModItemGroups.MELIUSSCIENTIA_ITEM_GROUP_MAIN)));

    public static final RegistryObject<Item> geiger_counter = ITEMS.register("geiger_counter", GeigerCounterItem::new);

    public static final RegistryObject<Item> rps_boots = ITEMS.register("rps_boots", () -> new ArmorItem(ModArmorMaterials.RPS, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.MELIUSSCIENTIA_ITEM_GROUP_MAIN)));
    public static final RegistryObject<Item> rps_leggings = ITEMS.register("rps_leggings", () -> new ArmorItem(ModArmorMaterials.RPS, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.MELIUSSCIENTIA_ITEM_GROUP_MAIN)));
    public static final RegistryObject<Item> rps_chestplate = ITEMS.register("rps_chestplate", () -> new ArmorItem(ModArmorMaterials.RPS, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.MELIUSSCIENTIA_ITEM_GROUP_MAIN)));
    public static final RegistryObject<Item> rps_helmet = ITEMS.register("rps_helmet", () -> new ArmorItem(ModArmorMaterials.RPS, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.MELIUSSCIENTIA_ITEM_GROUP_MAIN)));
}
