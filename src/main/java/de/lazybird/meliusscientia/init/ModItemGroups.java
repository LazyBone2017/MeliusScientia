package de.lazybird.meliusscientia.init;

import de.lazybird.meliusscientia.MeliusScientia;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class ModItemGroups {

    public static final ItemGroup MELIUSSCIENTIA_ITEM_GROUP_MAIN = new ModItemGroup(MeliusScientia.MODID, () -> new ItemStack(ModItem.bottle_u03.get()));

    public static final class ModItemGroup extends ItemGroup {

        @Nonnull
        private final Supplier<ItemStack> iconSupplier;

        public ModItemGroup(@Nonnull final String name, @Nonnull final Supplier<ItemStack> iconSupplier) {
            super(name);
            this.iconSupplier = iconSupplier;
        }

        @Override
        @Nonnull
        public ItemStack createIcon() {
            return iconSupplier.get();
        }

    }
}
