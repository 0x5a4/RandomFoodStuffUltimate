package shit.randomfoodstuff;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {

	public static Configuration config;
	
	public static String CATEGORY_POTION_IDS = "Potion ID's";
	public static String CATEGORY_ITEMS = "Items";
	
	//Potion IDs
	public static int potionPepperoni = 26;
	public static int potionMoldy = 27;
	public static int potionCaffeine = 28;
	public static int potionFlight = 29;
	public static int potionLauch = 30;
	public static int potionFloating = 31;
	
	//Items Vars
	public static int bobTheBenniMaxUses = 100;
	public static float bobTheBenniConvertRate = 0.5F;


	
	public static void init(File configFile) {
		 
		 config = new Configuration(configFile);
		 
		 try {
			 config.load();
			 
			 //PotionIDs
			 potionPepperoni = config.get(CATEGORY_POTION_IDS, "potionPepperoniID", potionPepperoni).setRequiresMcRestart(true).getInt();
			 potionMoldy = config.get(CATEGORY_POTION_IDS, "potionMushroomID", potionMoldy).setRequiresMcRestart(true).getInt();
			 potionCaffeine = config.get(CATEGORY_POTION_IDS, "potionAwakeID", potionCaffeine).setRequiresMcRestart(true).getInt();
			 potionFlight = config.get(CATEGORY_POTION_IDS, "potionFlightID", potionFlight).setRequiresMcRestart(true).getInt();
			 potionLauch = config.get(CATEGORY_POTION_IDS, "potionLauchID", potionLauch).setRequiresMcRestart(true).getInt();
			 potionFloating = config.get(CATEGORY_POTION_IDS, "potionFloatingID", potionFloating).setRequiresMcRestart(true).getInt();
			 
			 //Item Vars
			 bobTheBenniMaxUses = config.getInt("bobTheBenniMaxUses", CATEGORY_ITEMS, bobTheBenniMaxUses, 0, 1000000, "Charge Bob the Benni can store at once");
			 bobTheBenniConvertRate = config.getFloat("bobTheBenniConvertRate", CATEGORY_ITEMS, bobTheBenniConvertRate, 0, 10000, "How much Charge Bob the Benni recovers per Hungerpoint absorbed");
			 
		 } catch (Exception e) {
			 
		 } finally {
			 config.save();
		 }
	 }
	
	 public static Configuration getConfig() {
		 return config;
	 }
	 
}