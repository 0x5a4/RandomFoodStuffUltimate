package shit.randomfoodstuff;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;
import shit.randomfoodstuff.item.ItemArthur;
import shit.randomfoodstuff.item.ItemBenni;
import shit.randomfoodstuff.item.ItemBobTheBenni;
import shit.randomfoodstuff.item.ItemCactusGuide;
import shit.randomfoodstuff.item.ItemClubMate;
import shit.randomfoodstuff.item.ItemDrink;
import shit.randomfoodstuff.item.ItemFat;
import shit.randomfoodstuff.item.ItemFloatingSauce;
import shit.randomfoodstuff.item.ItemSuperFatBenni;
import shit.randomfoodstuff.item.ItemFlyingSchnitzel;
import shit.randomfoodstuff.item.ItemIceCube;
import shit.randomfoodstuff.item.ItemLauch;
import shit.randomfoodstuff.item.ItemMagicBaguette;
import shit.randomfoodstuff.item.ItemMateTee;
import shit.randomfoodstuff.item.ItemMutantLauch;
import shit.randomfoodstuff.item.ItemPepperoni;
import shit.randomfoodstuff.item.ItemSchnitzel;
import shit.randomfoodstuff.item.ItemSchnitzelBackpack;
import shit.randomfoodstuff.item.ItemSchokoriegel;
import shit.randomfoodstuff.item.ItemSoup;
import shit.randomfoodstuff.item.ItemSoupLadle;
import shit.randomfoodstuff.item.ItemSuperMushroom;
import shit.randomfoodstuff.item.ItemWarpingSchnitzel;
import shit.randomfoodstuff.item.tool.ItemAwfullBreaker;
import shit.randomfoodstuff.item.tool.ItemScythe;
import shit.randomfoodstuff.util.ItemHelper;

public class RandomItems {
	
	public static ToolMaterial awfullBreakingMaterial = EnumHelper.addToolMaterial("awfullBreakingMaterial", 1, 3, 5, 0, 0);
	
	public static Item itemFat;
	public static Item itemLauch;
	public static Item itemPepperoni;
	public static Item itemWarpingSchnitzel;
	public static Item itemDrink;
	public static Item itemSchokoriegel;
	public static Item itemFlyingSchnitzel;
	public static Item itemBenni;
	public static Item itemArthur;
	public static Item itemMagicBaguette;
	public static Item itemMutantLauch;
	public static Item itemAwfullBreaker;
	public static Item itemIceCube;
	public static Item itemClubMate;
	public static Item itemSchnitzelBackpack;
	public static Item itemSchnitzel;
	public static Item itemSoup;
	public static Item itemSoupLadle;
	public static Item itemSuperMushroom;
	public static Item itemScythe;
	public static Item itemMateTee;
	public static Item itemCactusGuide;
	public static Item itemBobTheBenni;
	public static Item itemSuperFatBenni;
	public static Item itemFloatingSauce;
	
	public static void init() {
		itemFat = new ItemFat();
		itemLauch = new ItemLauch(1, ItemHelper.getSaturationAmplifier(1, 1), RandomBlocks.blockLauch, Blocks.farmland);
		itemPepperoni = new ItemPepperoni(3, 0, RandomBlocks.blockPepperoni, Blocks.farmland);
		itemWarpingSchnitzel = new ItemWarpingSchnitzel();
		itemDrink = new ItemDrink(0, 0);
		itemSchokoriegel = new ItemSchokoriegel(5, ItemHelper.getSaturationAmplifier(5, 3));
		itemFlyingSchnitzel = new ItemFlyingSchnitzel(3, ItemHelper.getSaturationAmplifier(3, 1));
		itemBenni = new ItemBenni(0, 0);
		itemArthur = new ItemArthur(0, 0);
		itemMagicBaguette = new ItemMagicBaguette();
		itemMutantLauch = new ItemMutantLauch(2, ItemHelper.getSaturationAmplifier(2, 1));
		itemAwfullBreaker = new ItemAwfullBreaker(awfullBreakingMaterial);
		itemIceCube = new ItemIceCube(0, 0);
		itemClubMate = new ItemClubMate(7, ItemHelper.getSaturationAmplifier(7, 5));
		itemSchnitzelBackpack = new ItemSchnitzelBackpack();
		itemSchnitzel = new ItemSchnitzel(0, 0);
		itemSoup = new ItemSoup();
		itemSoupLadle = new ItemSoupLadle();
		itemSuperMushroom = new ItemSuperMushroom(1, ItemHelper.getSaturationAmplifier(1, 1));
		itemScythe = new ItemScythe(ToolMaterial.IRON);
		itemMateTee = new ItemMateTee(RandomBlocks.blockMateTee, Blocks.farmland);
		itemCactusGuide = new ItemCactusGuide();
		itemBobTheBenni = new ItemBobTheBenni();
		itemSuperFatBenni = new ItemSuperFatBenni();
		itemFloatingSauce = new ItemFloatingSauce();
	}
	
	public static void register() {
		GameRegistry.registerItem(itemFat, "itemBaseResource");
		GameRegistry.registerItem(itemLauch, "itemLauch");
		GameRegistry.registerItem(itemPepperoni, "itemPepperoni");
		GameRegistry.registerItem(itemWarpingSchnitzel, "itemWarpingSchnitzel");
		GameRegistry.registerItem(itemDrink, "itemDrink");
		GameRegistry.registerItem(itemSchokoriegel, "itemSchokoriegel");
		GameRegistry.registerItem(itemFlyingSchnitzel, "itemFlyingSchnitzel");
		GameRegistry.registerItem(itemBenni, "itemBenni");
		GameRegistry.registerItem(itemArthur, "itemArthur");
		GameRegistry.registerItem(itemMagicBaguette, "itemMagicBaguette");
		GameRegistry.registerItem(itemMutantLauch, "itemMutantLauch");
		GameRegistry.registerItem(itemAwfullBreaker, "itemAwfullBreaker");
		GameRegistry.registerItem(itemIceCube, "itemIceCube");
		GameRegistry.registerItem(itemClubMate, "itemClubMate");
		GameRegistry.registerItem(itemSchnitzelBackpack, "itemSchnitzelBackpack");
		GameRegistry.registerItem(itemSchnitzel, "itemSchnitzel");
		GameRegistry.registerItem(itemSoup, "itemSoup");
		GameRegistry.registerItem(itemSoupLadle, "itemSoupLadle");
		GameRegistry.registerItem(itemSuperMushroom, "itemSuperMushroom");
		GameRegistry.registerItem(itemScythe, "itemScythe");
		GameRegistry.registerItem(itemMateTee, "itemMateTee");
		GameRegistry.registerItem(itemCactusGuide, "itemCactusGuide");
		GameRegistry.registerItem(itemBobTheBenni, "itemBobTheBenni");
		GameRegistry.registerItem(itemSuperFatBenni, "itemFatModifiedBenni");
		GameRegistry.registerItem(itemFloatingSauce, "itemFloatingSauce");
	}
	
}
