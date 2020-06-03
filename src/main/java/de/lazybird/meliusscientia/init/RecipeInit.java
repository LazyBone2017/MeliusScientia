package de.lazybird.meliusscientia.init;

import de.lazybird.meliusscientia.recipe.CrusherRecipeHandler;

public class RecipeInit {

    public static CrusherRecipeHandler crusherRecipeHandler = new CrusherRecipeHandler();

    public static void registerRecipes() {
        crusherRecipeHandler.addRecipe(ModBlock.uranium_ore.get().asItem(), ModItem.bottle_uo3.get());
    }
}
