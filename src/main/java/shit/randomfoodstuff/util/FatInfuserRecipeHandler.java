package shit.randomfoodstuff.util;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import net.minecraft.item.ItemStack;
import shit.randomfoodstuff.client.gui.GuiGuide;
import shit.randomfoodstuff.guide.recipes.IGuideRecipe;
import shit.randomfoodstuff.guide.recipes.IRecipeDiscoverer;

public class FatInfuserRecipeHandler implements IRecipeDiscoverer{
	
	public static ArrayList<FatInfuserRecipe> recipes = new ArrayList<FatInfuserRecipe>();
	public static ArrayList<FatInfuserFuel> fuels = new ArrayList<FatInfuserFuel>();
	
	public static void addFuel(ItemStack input, int burnTime) {
		input.stackSize = 1;
		fuels.add(new FatInfuserFuel(input, burnTime));
	}
	
	public static void addRecipe(ItemStack input, ItemStack output) {
		input.stackSize = 1;
		recipes.add(new FatInfuserRecipe(input, output));
	}
	
	public static ItemStack getResultFor(ItemStack input) {
		for (FatInfuserRecipe recipe : recipes) {
			if (recipe.canCraft(input)) {
				return recipe.getOutput();
			}
		}
		return null;
	}
	
	public static int getBurnTime(ItemStack stack) {
		for (FatInfuserFuel fuel : fuels) {
			if (fuel.isItemFuel(stack)) {
				return fuel.getBurnTime();
			}
		}
		return 0;
	}
	
	public static boolean isItemFuel(ItemStack stack) {
		return getBurnTime(stack) > 0;
	}
	
	public static boolean canCraft(ItemStack input) {
		return getResultFor(input) != null;
	}
	
	@Override
	public ArrayList<IGuideRecipe> findRecipes(ItemStack stack) {
		ArrayList<IGuideRecipe> result = new ArrayList<IGuideRecipe>();
		for (FatInfuserRecipe recipe : recipes) {
			if (ItemHelper.compareStacks(recipe.getOutput(), stack)) {
				result.add(recipe);
			}
		}
		System.out.println("Found " + result.size() + " Fat Infuser Recipes for " + stack.getUnlocalizedName());
		return result;
	}
	
	public static class FatInfuserRecipe implements IGuideRecipe{
		
		protected ItemStack input;
		protected ItemStack output;
		
		public FatInfuserRecipe(ItemStack input, ItemStack output) {
			this.input = input.copy();
			this.output = output.copy();
		}
		
		public boolean canCraft(ItemStack stack) {
			if (stack.getItem() == input.getItem()) {
				if (stack.getItemDamage() == input.getItemDamage()) {
					return true;
				}
			}
			
			return false;
		}
		
		public ItemStack getInput() {
			return input.copy();
		}
		
		@Override
		public ItemStack getOutput() {
			return output.copy();
		}

		@Override
		public void loadRecipe() {}

		@Override
		public void drawToScreen(GuiGuide screen, int x, int y) {
			//Draw Background
			GL11.glColor4f(1, 1, 1, 1);
			screen.mc.getTextureManager().bindTexture(IGuideRecipe.recipeTextures);
			screen.drawTexturedModalRect(x, y, 0, 130, getWidth(), getHeight());
			
			//Draw Recipe
			GuiHelper.drawItemStack(screen, getInput(), x + 24, y + 24);
			GuiHelper.drawItemStack(screen, getOutput(), x + 100, y + 24);
		}

		@Override
		public int getWidth() {
			return 126;
		}

		@Override
		public int getHeight() {
			return maxHeight;
		}

		@Override
		public String getHeading() {
			return "Fat Infuser";
		}
	}
	
	public static class FatInfuserFuel {
		protected ItemStack fuel;
		protected int burnTime;
		
		public FatInfuserFuel(ItemStack fuel, int burnTime) {
			this.fuel = fuel;
			this.burnTime = burnTime;
		}
		
		public boolean isItemFuel(ItemStack stack) {
			if (stack.getItem() == fuel.getItem()) {
				if (stack.getItemDamage() == fuel.getItemDamage()) {
					return true;
				}
			}
			
			return false;
		}
		
		public ItemStack getFuel() {
			return fuel.copy();
		}
		
		public int getBurnTime() {
			return burnTime;
		}
		
		
	}
	
}
