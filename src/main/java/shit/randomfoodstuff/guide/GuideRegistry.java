package shit.randomfoodstuff.guide;

import java.util.ArrayList;
import java.util.HashMap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.TextureMap;
import shit.randomfoodstuff.guide.recipes.IRecipeDiscoverer;

@SideOnly(Side.CLIENT)
public class GuideRegistry {
	
	private static HashMap<String, GuideTab> tabList = new HashMap<String, GuideTab>();
	
	//The Default Guide Tab that is opened when the GuideGui opens and when the Home Button is pressed
	public static String defaultGuideTab = "HOME";
	
	public static void registerTab(String name, GuideTab tab) {
		if (doesTabExist(name)) {
			System.err.println("Tab already exists: " + name);
			return;
		}

		if (tab.getName() == null) {
			tab.setName(name);
		}
		
		if (!tab.getName().equals(name)) {
			System.err.printf("Bruteforcing name override for tab %s due to Normalization and keeping Things consistent Reasons", tab.getClass().getName());
			System.err.println("Insead using: " + name);
			tab.setName(name);
		}
		
		tabList.put(name, tab);
	}
	
	public static void registerPageToTab(String tabName, String pageName, Class<? extends GuidePage> pageClass) {
		if (!doesTabExist(tabName)) {
			System.out.println("No such Tab: " + tabName);
			return;
		}
		
		getTab(tabName).registerPage(pageName, pageClass);
	}
	
	public static GuideTab getTab(String name) {
		return tabList.get(name);
	}
	
	public static boolean doesTabExist(String name) {
		return tabList.containsKey(name);
	}
	
	
}
