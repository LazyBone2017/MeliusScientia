package de.lazybird.meliusscientia.recipe;

import net.minecraft.item.Item;

import java.util.Set;

public interface IRecipeHandler <T, S> {

    S getResult(T input);

    void addRecipe(T input, S output);

    Set<Item> inputs();
}
