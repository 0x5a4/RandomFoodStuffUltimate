package shit.randomfoodstuff;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public class RandomAchievements {

	public static HashMap<String, Achievement> achievements = new HashMap<String, Achievement>();
	public static ArrayList<Achievement> completionRewardExceptions = new ArrayList<Achievement>();
	
	public static final String completionAchievement = "completionist";
	
	public static void init() {
		
		addAchievement("guide", 0, 0, RandomItems.itemCactusGuide, "");
		addAchievement("completionist", 2, 2, RandomItems.itemArthur, "");
		
		//Lauch-Tree
		addAchievement("lauch", 0, -1, RandomItems.itemLauch, "");
		addAchievement("awfull", -2, -2, RandomBlocks.blockAwfull, "lauch");
		addAchievement("pepperoni", 2, -1, RandomItems.itemPepperoni, "lauch");
		addAchievement("spicy", 3, -3, new ItemStack(RandomItems.itemSchnitzel, 1 ,2), "pepperoni");
		addAchievement("warping", 2, -5, Items.ender_pearl, "spicy");
		addAchievement("flying", 4, -5, RandomItems.itemFlyingSchnitzel, "spicy");
		addAchievement("backpack", 0, -5, RandomItems.itemSchnitzelBackpack, "warping");
		addAchievement("warping2", 1, -4, Items.ender_eye, "warping");
		addAchievement("flyHigh", 4, -6, Items.feather, "flying");
		
		//Fat-Tree
		addAchievement("fat", 0, 1, RandomItems.itemFat, "");
		addAchievement("fatInfuser", -2, 1, RandomBlocks.blockFatInfuser, "fat");
		addAchievement("cookingPot", -4, 1, RandomBlocks.blockCookingPot, "fatInfuser");
		addAchievement("soup", -5, 0, RandomItems.itemSoup, "cookingPot");
		addAchievement("benni", -2, 3, new ItemStack(RandomItems.itemBenni, 1, 2), "fatInfuser");
		
		//Special
		getAchievementByName("guide").setSpecial();
		getAchievementByName("completionist").setSpecial();
		
		addAchievementException(getAchievementByName("completionist"));
		
	}
	
	public static void register() {
		AchievementPage.registerAchievementPage(new AchievementPage("RF Ultimate", toArray()));
	}
	
	public static void addAchievement(String name, int posX, int posY, ItemStack icon, String parent) {
		Achievement parentAchievement = null;
		if (achievements.containsKey(parent)) {
			parentAchievement = achievements.get(parent);
		}
		Achievement achievement = new Achievement("randomFoodStuff.achievement." + name, name, posX, posY, icon, parentAchievement).registerStat();
		achievements.put(name, achievement);
	}
	
	public static void addAchievement(Achievement achievement, String name) {
		achievement.registerStat();
		achievements.put(name, achievement);
	}
	
	public static void addAchievement(String name, int posX, int posY, Block icon, String parent) {
		addAchievement(name, posX, posY, new ItemStack(icon), parent);
	}
	
	public static void addAchievement(String name, int posX, int posY, Item icon, String parent) {
		addAchievement(name, posX, posY, new ItemStack(icon), parent);
	}
	
	public static void addAchievementException(Achievement achievement) {
		completionRewardExceptions.add(achievement);
	}
	
	public static Achievement[] toArray() {
		Achievement[] result = new Achievement[achievements.size()];
		Iterator iterator = achievements.values().iterator();
		for (int i = 0; iterator.hasNext(); i++) {
			result[i] = (Achievement) iterator.next();
		}
		return result;
	}
	
	public static Achievement getAchievementByName(String name) {
		return achievements.getOrDefault(name, null);
	}
	
	public static boolean isRandomAchievement(Achievement achievement) {
		return achievements.containsValue(achievement);
	}
	
	public static Collection<Achievement> getAchievementList() {
		return achievements.values();
	}

	public static boolean isCompletionException(Achievement achievement) {
		return completionRewardExceptions.contains(achievement);
	}

	
}
