package shit.randomfoodstuff;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import shit.randomfoodstuff.tileentity.TileEntityFatInfuser;
import shit.randomfoodstuff.util.FatInfuserRecipeHandler;

import static shit.randomfoodstuff.RandomBlocks.*;
import static shit.randomfoodstuff.RandomItems.*;

public class RandomCraftingHandler {

    public static void init() {
        smelt();
        craft();
        fatInfuse();
    }

    private static void smelt() {
        GameRegistry.addSmelting(new ItemStack(itemSchnitzel, 1, 0), new ItemStack(itemSchnitzel, 1, 1), 0.3F);
        GameRegistry.addSmelting(new ItemStack(itemArthur, 1, 0), new ItemStack(itemArthur, 1, 1), 0.3F);
        GameRegistry.addSmelting(new ItemStack(itemBenni, 1, 0), new ItemStack(itemBenni, 1, 1), 0.3F);
        GameRegistry.addSmelting(new ItemStack(itemBenni, 1, 1), new ItemStack(itemBenni, 1, 2), 0.3F);
    }

    private static void craft() {
        GameRegistry.addShapelessRecipe(new ItemStack(itemSchnitzel, 3, 0), new ItemStack(Items.porkchop), new ItemStack(Items.porkchop), new ItemStack(Items.porkchop), new ItemStack(Items.egg), new ItemStack(Items.milk_bucket), new ItemStack(Items.bread));
        GameRegistry.addShapelessRecipe(new ItemStack(itemSchnitzel, 1, 2), new ItemStack(itemSchnitzel, 1, 1), new ItemStack(itemPepperoni), new ItemStack(itemPepperoni));
        GameRegistry.addShapelessRecipe(new ItemStack(itemFat), new ItemStack(Items.chicken));
        GameRegistry.addShapelessRecipe(new ItemStack(itemFat), new ItemStack(Items.beef));
        GameRegistry.addShapelessRecipe(new ItemStack(itemFat), new ItemStack(Items.porkchop));
        GameRegistry.addShapelessRecipe(new ItemStack(itemDrink, 1, 0), new ItemStack(Items.iron_ingot), new ItemStack(Items.dye, 1, 14));
        GameRegistry.addShapelessRecipe(new ItemStack(itemDrink, 1, 2), new ItemStack(itemDrink, 1, 1), new ItemStack(Items.carrot), new ItemStack(Items.carrot), new ItemStack(Items.carrot));
        GameRegistry.addShapelessRecipe(new ItemStack(itemDrink, 1, 3), new ItemStack(itemDrink, 1, 1), new ItemStack(itemBenni, 1, 2));
        GameRegistry.addShapelessRecipe(new ItemStack(itemDrink, 1, 4), new ItemStack(itemDrink, 1, 1), new ItemStack(Items.sugar), new ItemStack(Items.sugar), new ItemStack(Items.sugar));
        GameRegistry.addShapelessRecipe(new ItemStack(itemSchokoriegel, 3), new ItemStack(Items.dye, 1, 3), new ItemStack(Items.milk_bucket), new ItemStack(Items.dye, 1, 3));
        GameRegistry.addShapelessRecipe(new ItemStack(itemArthur, 1, 0), new ItemStack(itemFat), new ItemStack(itemMagicBaguette), new ItemStack(itemLauch), new ItemStack(itemSchnitzel));
        GameRegistry.addShapedRecipe(new ItemStack(itemIceCube), " S ", "SIS", " S ", Character.valueOf('S'), Items.snowball, Character.valueOf('I'), Blocks.ice);
        GameRegistry.addShapedRecipe(new ItemStack(blockAwfull, 4), "LDL", "LDL", "DDD", Character.valueOf('L'), itemLauch, Character.valueOf('D'), Blocks.dirt);
        GameRegistry.addShapedRecipe(new ItemStack(blockFatInfuser), "FAF", "HLH", "PPP", Character.valueOf('F'), itemFat, Character.valueOf('A'), Blocks.furnace, Character.valueOf('H'), Item.getItemFromBlock(Blocks.hopper), Character.valueOf('L'), Items.lava_bucket, Character.valueOf('P'), Blocks.wooden_pressure_plate);
        GameRegistry.addShapedRecipe(new ItemStack(blockCookingPot), " F ", "ICI", "IDI", Character.valueOf('I'), Items.iron_ingot, Character.valueOf('C'), Item.getItemById(380), Character.valueOf('D'), Blocks.gold_block, Character.valueOf('F'), blockFatInfuser);
        GameRegistry.addShapelessRecipe(new ItemStack(itemClubMate), Items.glass_bottle, itemMateTee, itemMateTee);
        GameRegistry.addShapedRecipe(new ItemStack(itemMagicBaguette), "P  ", " BA", " AB", Character.valueOf('P'), new ItemStack(Items.dye, 1, 5), Character.valueOf('B'), Items.bread, Character.valueOf('A'), Items.blaze_powder);
        GameRegistry.addShapedRecipe(new ItemStack(itemSchnitzelBackpack), "LDL", "WCW", "LGL", Character.valueOf('L'), Items.leather, Character.valueOf('D'), Items.diamond, Character.valueOf('W'), Blocks.wool, Character.valueOf('G'), Items.gold_nugget, Character.valueOf('C'), Blocks.chest);
        GameRegistry.addShapedRecipe(new ItemStack(itemSoupLadle), " SI", " I ", "II ", Character.valueOf('S'), Items.stick, Character.valueOf('I'), Items.iron_ingot);
        GameRegistry.addShapedRecipe(new ItemStack(itemWarpingSchnitzel), "AEB", "ESE", "BEA", Character.valueOf('A'), new ItemStack(Items.dye, 1, 10), Character.valueOf('E'), Items.ender_eye, Character.valueOf('S'), new ItemStack(itemSchnitzel, 1, 2), Character.valueOf('B'), new ItemStack(Items.dye, 1, 5));
        GameRegistry.addShapedRecipe(new ItemStack(itemWarpingSchnitzel), "AEB", "ESE", "BEA", Character.valueOf('A'), new ItemStack(Items.dye, 1, 5), Character.valueOf('E'), Items.ender_eye, Character.valueOf('S'), new ItemStack(itemSchnitzel, 1, 2), Character.valueOf('B'), new ItemStack(Items.dye, 1, 10));
        GameRegistry.addShapedRecipe(new ItemStack(itemFlyingSchnitzel), "IEI", " G ", "FSF", Character.valueOf('I'), Items.gold_nugget, Character.valueOf('E'), Items.ender_pearl, Character.valueOf('G'), RandomItems.itemFloatingSauce, Character.valueOf('F'), Items.ghast_tear, Character.valueOf('S'), new ItemStack(itemSchnitzel, 1, 2));
        GameRegistry.addShapedRecipe(new ItemStack(itemAwfullBreaker), "AAA", " I ", " A ", Character.valueOf('A'), blockAwfull, Character.valueOf('I'), Items.iron_ingot);
        GameRegistry.addShapedRecipe(new ItemStack(itemCactusGuide), " B ", " P ", " M ", Character.valueOf('B'), Items.book, Character.valueOf('P'), Items.flower_pot, Character.valueOf('M'), new ItemStack(Items.dye, 1, 15));
        GameRegistry.addShapedRecipe(new ItemStack(itemBobTheBenni), " D ", "SBM", "   ", Character.valueOf('D'), Items.diamond_pickaxe, Character.valueOf('S'), Items.cooked_beef, Character.valueOf('B'), new ItemStack(itemBenni, 1, 1), Character.valueOf('M'), itemClubMate);
        GameRegistry.addShapedRecipe(new ItemStack(itemFloatingSauce), "F F", "GBG", " T ", Character.valueOf('F'), Items.feather, Character.valueOf('G'), Items.glowstone_dust, Character.valueOf('B'), Items.glass_bottle, Character.valueOf('T'), Items.ghast_tear);
        GameRegistry.addShapelessRecipe(new ItemStack(itemArthur, 2), new ItemStack(itemArthur), new ItemStack(itemSchnitzel, 1, 2));
    }

    private static void fatInfuse() {
        FatInfuserRecipeHandler.addFuel(new ItemStack(itemFat), TileEntityFatInfuser.getSpeed());
        FatInfuserRecipeHandler.addRecipe(new ItemStack(itemBenni, 1, 2), new ItemStack(itemSuperFatBenni));
        FatInfuserRecipeHandler.addRecipe(new ItemStack(itemLauch), new ItemStack(itemMutantLauch));
        FatInfuserRecipeHandler.addRecipe(new ItemStack(itemArthur, 1, 1), new ItemStack(itemBenni, 1, 0));
        FatInfuserRecipeHandler.addRecipe(new ItemStack(Blocks.brown_mushroom, 1), new ItemStack(itemSuperMushroom));
        FatInfuserRecipeHandler.addRecipe(new ItemStack(Blocks.red_mushroom, 1), new ItemStack(itemSuperMushroom));
        FatInfuserRecipeHandler.addRecipe(new ItemStack(Items.golden_hoe), new ItemStack(itemScythe));
    }

}
