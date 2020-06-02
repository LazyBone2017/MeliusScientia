package de.lazybird.meliusscientia.recipe;

import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Set;

public class CrusherRecipeHandler implements IRecipeHandler<Item, Item> {

    HashMap<Item, Item> recipes = new HashMap<>();

    @Override
    public Item getResult(Item input) {
        return recipes.get(input);
    }

    @Override
    public void addRecipe(Item input, Item output) {
        recipes.put(input, output);
    }

    @Override
    public Set<Item> inputs() {
        return recipes.keySet();
    }
}
