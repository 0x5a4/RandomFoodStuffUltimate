package shit.randomfoodstuff.guide;

import java.util.ArrayList;
import java.util.HashMap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import shit.randomfoodstuff.guide.recipes.IRecipeDiscoverer;

@SideOnly(Side.CLIENT)
public class GuideRegistry {
	
	private static HashMap<String, GuideArticle> articleList = new HashMap<String, GuideArticle>();
	private static HashMap<String, GuideMenu> menuList = new HashMap<String, GuideMenu>();
	
	private static ArrayList<IRecipeDiscoverer> recipeDiscovererList = new ArrayList<IRecipeDiscoverer>();
	
	private static String defaultMenu = "mainMenu";
	
	public static void addGuideArticle(String identifier, GuideArticle entry) {
		entry.setIdentifier(identifier);
		articleList.put(identifier, entry);
	}
	
	public static void addMenu(String identifier, GuideMenu menu) {
		menu.setIdentifier(identifier);
		menuList.put(identifier, menu);
	}
	
	/**
	 * Make sure you have all registered before the Articles load. 
	 * Doing afterwards will result in the discoverer not being taken into account
	 */
	public static void registerRecipeDiscoverer(IRecipeDiscoverer discoverer) {
		recipeDiscovererList.add(discoverer);
	}
	
	public static GuideArticle getArticleByName(String identifier) {
		return articleList.getOrDefault(identifier, null);
	}
	
	public static GuideMenu getMenuByName(String identifier) {
		return menuList.getOrDefault(identifier, null);
	}
	
	public static ArrayList<IRecipeDiscoverer> getRecipeDiscovererList() {
		return recipeDiscovererList;
	}
	
	public static boolean doesArticleExist(String identifier) {
		return articleList.containsKey(identifier);
	}
	
	public static boolean doesMenuExist(String identifier) {
		return menuList.containsKey(identifier);
	}
	
	public static void setDefaultMenu(String defaultMenu) {
		GuideRegistry.defaultMenu = defaultMenu;
	}
	
	public static String getDefaultMenu() {
		return defaultMenu;
	}
	
	
}
