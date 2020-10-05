package shit.randomfoodstuff;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import shit.randomfoodstuff.client.gui.GuiFakeDisconnect;
import shit.randomfoodstuff.client.gui.GuiGuide;
import shit.randomfoodstuff.event.handler.ClientEventHandler;
import shit.randomfoodstuff.guide.GuideRegistry;
import shit.randomfoodstuff.guide.SimpleGuideArticle;
import shit.randomfoodstuff.guide.content.GuideMainMenu;
import shit.randomfoodstuff.guide.content.GuideMenuBlocks;
import shit.randomfoodstuff.guide.content.GuideMenuCooking;
import shit.randomfoodstuff.guide.content.GuideMenuItems;
import shit.randomfoodstuff.guide.content.GuideMenuSchnitzel;
import shit.randomfoodstuff.guide.recipes.CraftingRecipeDiscoverer;
import shit.randomfoodstuff.guide.recipes.FurnaceRecipeDiscoverer;
import shit.randomfoodstuff.render.RandomItemRenderer;
import shit.randomfoodstuff.render.RandomModel;
import shit.randomfoodstuff.render.RandomModelCollection;
import shit.randomfoodstuff.render.RandomModelRenderer;
import shit.randomfoodstuff.render.model.ModelBlockBenniEast;
import shit.randomfoodstuff.render.model.ModelBlockBenniNorth;
import shit.randomfoodstuff.render.model.ModelBlockBenniSouth;
import shit.randomfoodstuff.render.model.ModelBlockBenniWest;
import shit.randomfoodstuff.render.model.ModelCookingPot;
import shit.randomfoodstuff.render.model.ModelFatInfuser;
import shit.randomfoodstuff.tileentity.TileEntityBlockBenni;
import shit.randomfoodstuff.tileentity.TileEntityCookingPot;
import shit.randomfoodstuff.tileentity.TileEntityFatInfuser;
import shit.randomfoodstuff.util.FatInfuserRecipeHandler;

public class ClientProxy implements IProxy {

	RandomModelCollection modelBlockBenni = new RandomModelCollection() {
		@Override
		public RandomModel getModelBasedOn(TileEntity entity, int x, int y, int z) {
			if (entity.hasWorldObj()) {
				int meta = entity.getBlockMetadata();
				switch(meta) {
				case 1:
					return getModel("South");
				case 2:
					return getModel("West");
				case 3:
					return getModel("North");
				case 4:
					return getModel("East");
				}
			}
			return getModel("West");
		}
	};
	
	@Override
	public void registerModelCollections() throws IllegalArgumentException, IllegalAccessException {
		modelBlockBenni.registerModel("South", new RandomModel(new ModelBlockBenniSouth()));
		modelBlockBenni.registerModel("North", new RandomModel(new ModelBlockBenniNorth()));
		modelBlockBenni.registerModel("West", new RandomModel(new ModelBlockBenniWest()));
		modelBlockBenni.registerModel("East", new RandomModel(new ModelBlockBenniEast()));
	}
	
	@Override
	public void registerRenderers() throws IllegalArgumentException, IllegalAccessException {
		registerRenderer(new RandomModelRenderer(modelBlockBenni).setDefaultTextureMap("ModelFatBenni.png"), RandomBlocks.blockBenni, new TileEntityBlockBenni(), true);
		registerRenderer(new RandomModelRenderer(createOneModelCollection(new RandomModel(new ModelFatInfuser()))).setDefaultTextureMap("ModelFatInfuser.png"), RandomBlocks.blockFatInfuser, new TileEntityFatInfuser(), true);
		registerRenderer(new RandomModelRenderer(createOneModelCollection(new RandomModel(new ModelCookingPot()))).setDefaultTextureMap("ModelCookingPot.png"), RandomBlocks.blockCookingPot, new TileEntityCookingPot(), true);
	}
	
	private RandomModelCollection createOneModelCollection(RandomModel model) {
		RandomModelCollection result = new RandomModelCollection() {
			@Override
			public RandomModel getModelBasedOn(TileEntity entity, int x, int y, int z) {
				return getModel("default");
			}
		};
		result.registerModel("default", model);
		return result;
	}
	
	private void registerRenderer(TileEntitySpecialRenderer renderer, Block block, TileEntity tileentity, boolean registerItemRenderer) {
		ClientRegistry.bindTileEntitySpecialRenderer(tileentity.getClass(), renderer);
		if (registerItemRenderer) {
			MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(block), new RandomItemRenderer(renderer, tileentity));
		}
	}
	
	@Override
	public void registerEvents() {
		MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
	}
	
	@Override
	public void registerGuide() {
		//RecipeDiscoverer
		System.out.println("Registering Recipe Discoverers");
		GuideRegistry.registerRecipeDiscoverer(new CraftingRecipeDiscoverer());
		GuideRegistry.registerRecipeDiscoverer(new FurnaceRecipeDiscoverer());
		GuideRegistry.registerRecipeDiscoverer(new FatInfuserRecipeHandler());
		
		//Articles
		System.out.println("Adding Guide Articles");
		//Items
		GuideRegistry.addGuideArticle("items.benni", new SimpleGuideArticle().addRecipes(new ItemStack(RandomItems.itemBenni)).addRecipes(new ItemStack(RandomItems.itemBenni, 1, 1)).addRecipes(new ItemStack(RandomItems.itemBenni, 1, 2)).addRecipes(new ItemStack(RandomItems.itemBobTheBenni)));
		GuideRegistry.addGuideArticle("items.arthur", new SimpleGuideArticle().addRecipes(new ItemStack(RandomItems.itemArthur)).addRecipes(new ItemStack(RandomItems.itemArthur, 1, 1)));
		GuideRegistry.addGuideArticle("items.drinks", new SimpleGuideArticle().addRecipes(new ItemStack(RandomItems.itemDrink)).addRecipes(new ItemStack(RandomItems.itemDrink, 1, 1)).addRecipes(new ItemStack(RandomItems.itemDrink, 1, 2)).addRecipes(new ItemStack(RandomItems.itemDrink, 1, 3)).addRecipes(new ItemStack(RandomItems.itemDrink, 1, 4)));
		GuideRegistry.addGuideArticle("items.baguette", new SimpleGuideArticle().addRecipes(new ItemStack(RandomItems.itemMagicBaguette)));
		GuideRegistry.addGuideArticle("items.lauch", new SimpleGuideArticle());
		//Schnitzel
		GuideRegistry.addGuideArticle("items.schnitzel.schnitzel", new SimpleGuideArticle().addRecipes(new ItemStack(RandomItems.itemWarpingSchnitzel)).addRecipes(new ItemStack(RandomItems.itemFlyingSchnitzel)).addRecipes(new ItemStack(RandomItems.itemSchnitzel)).addRecipes(new ItemStack(RandomItems.itemSchnitzel, 1, 1)).addRecipes(new ItemStack(RandomItems.itemSchnitzel, 1, 2)));
		GuideRegistry.addGuideArticle("items.schnitzel.backpack", new SimpleGuideArticle().addRecipes(new ItemStack(RandomItems.itemSchnitzelBackpack)));
		
		//Blocks
		GuideRegistry.addGuideArticle("blocks.fatInfuser", new SimpleGuideArticle().addRecipes(new ItemStack(RandomBlocks.blockFatInfuser)).addRecipes(new ItemStack(RandomItems.itemFat)));
		GuideRegistry.addGuideArticle("blocks.awfull", new SimpleGuideArticle().addRecipes(new ItemStack(RandomBlocks.blockAwfull)).addRecipes(new ItemStack(RandomItems.itemAwfullBreaker)));
		
		//Cooking
		GuideRegistry.addGuideArticle("cooking.cookingPot", new SimpleGuideArticle().addRecipes(new ItemStack(RandomBlocks.blockCookingPot)));
		GuideRegistry.addGuideArticle("cooking.soupLadle", new SimpleGuideArticle().addRecipes(new ItemStack(RandomItems.itemSoupLadle)));
		
		//Menus
		GuideRegistry.addMenu("mainMenu", new GuideMainMenu());
		GuideRegistry.addMenu("items", new GuideMenuItems());
		GuideRegistry.addMenu("cooking", new GuideMenuCooking());
		GuideRegistry.addMenu("blocks", new GuideMenuBlocks());
		GuideRegistry.addMenu("items.schnitzel", new GuideMenuSchnitzel());
	}
	
	@Override
	public void openGuideGui() {
		Minecraft.getMinecraft().displayGuiScreen(new GuiGuide());
	}
	
	@Override
	public void fakeDisconnect(String msg, IChatComponent reason) {
		Minecraft.getMinecraft().displayGuiScreen(new GuiFakeDisconnect(msg, reason));
	}

	@Override
	public Side getSide() {
		return Side.CLIENT;
	}
	
	
}
