package shit.randomfoodstuff.client.gui.nei;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import shit.randomfoodstuff.Reference;
import shit.randomfoodstuff.client.gui.GuiFatInfuser;
import shit.randomfoodstuff.tileentity.TileEntityFatInfuser;
import shit.randomfoodstuff.util.FatInfuserRecipeHandler;
import shit.randomfoodstuff.util.FatInfuserRecipeHandler.FatInfuserFuel;
import shit.randomfoodstuff.util.FatInfuserRecipeHandler.FatInfuserRecipe;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NEIFatInfuserRecipeHandler extends TemplateRecipeHandler {

    public static ArrayList<Fuel> fuels;

    @Override
    public String getRecipeName() {
        return "Fat Infuser";
    }

    @Override
    public String getGuiTexture() {
        return new ResourceLocation(Reference.TextureName + "textures/gui/GuiFatInfuser.png").toString();
    }

    @Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(48, 8, 73, 36), "recipes"));
        //transferRects.add(new RecipeTransferRect(new Rectangle(96, 50, 13, 13), "fuels"));
    }

    @Override
    public TemplateRecipeHandler newInstance() {
        if (this.fuels == null) {
            this.initFuels();
        }
        return super.newInstance();
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals("recipes") && getClass() == NEIFatInfuserRecipeHandler.class) {
            for (FatInfuserRecipe recipe : FatInfuserRecipeHandler.recipes) {
                arecipes.add(new InfusingRecipe(recipe.getInput(), recipe.getOutput()));
            }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        for (FatInfuserRecipe recipe : FatInfuserRecipeHandler.recipes) {
            if (NEIServerUtils.areStacksSameTypeCrafting(result, recipe.getOutput())) {
                arecipes.add(new InfusingRecipe(recipe.getInput(), recipe.getOutput()));
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        for (FatInfuserRecipe recipe : FatInfuserRecipeHandler.recipes) {
            if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getInput(), ingredient)) {
                if (recipe != null && recipe.getOutput() != null) {
                    arecipes.add(new InfusingRecipe(recipe.getInput(), recipe.getOutput()));
                }
            }
        }
    }

    public static void initFuels() {
        fuels = new ArrayList<Fuel>();
        for (FatInfuserFuel fuel : FatInfuserRecipeHandler.fuels) {
            fuels.add(new Fuel(fuel.getFuel(), fuel.getBurnTime()));
        }
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        return GuiFatInfuser.class;
    }

    @Override
    public void drawExtras(int recipe) {
        drawProgressBar(48, 8, 176, 0, 73, 36, TileEntityFatInfuser.getSpeed(), 0);
        drawProgressBar(96, 50, 179, 37, 13, 13, TileEntityFatInfuser.getSpeed() / 2, 2);
    }

    @Override
    public String getOverlayIdentifier() {
        return "fatInfuser";
    }

    public class InfusingRecipe extends CachedRecipe {
        public PositionedStack input;
        public PositionedStack output;

        public InfusingRecipe(ItemStack input, ItemStack output) {
            input.stackSize = 1;
            this.input = new PositionedStack(input, 29, 8);
            this.output = new PositionedStack(output, 131, 8);
        }

        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 48, Arrays.asList(input));
        }

        public PositionedStack getOtherStack() {
            return fuels.get((cycleticks / 48) % fuels.size()).fuel;
        }

        @Override
        public PositionedStack getResult() {
            return output;
        }

    }

    public static class Fuel {
        PositionedStack fuel;
        int burnTime;

        public Fuel(ItemStack stack, int burnTime) {
            this.burnTime = burnTime;
            this.fuel = new PositionedStack(stack, 76, 48);
        }
    }

}
