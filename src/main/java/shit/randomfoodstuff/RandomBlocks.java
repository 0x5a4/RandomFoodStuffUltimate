package shit.randomfoodstuff;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import shit.randomfoodstuff.block.BlockAwfull;
import shit.randomfoodstuff.block.BlockBenni;
import shit.randomfoodstuff.block.BlockCookingPot;
import shit.randomfoodstuff.block.BlockFatInfuser;
import shit.randomfoodstuff.block.BlockLauch;
import shit.randomfoodstuff.block.BlockMate;
import shit.randomfoodstuff.block.BlockPepperoni;
import shit.randomfoodstuff.item.ItemBlockAwfull;
import shit.randomfoodstuff.item.ItemBlockBenni;
import shit.randomfoodstuff.tileentity.TileEntityBlockAwfull;
import shit.randomfoodstuff.tileentity.TileEntityBlockBenni;
import shit.randomfoodstuff.tileentity.TileEntityCookingPot;
import shit.randomfoodstuff.tileentity.TileEntityFatInfuser;

public class RandomBlocks {

	public static Block blockLauch;
	public static Block blockPepperoni;
	public static Block blockAwfull;
	public static Block blockFatInfuser;
	public static Block blockBenni;
	public static Block blockCookingPot;
	public static Block blockMateTee;
	
	public static void init() {
		blockLauch = new BlockLauch();
		blockAwfull = new BlockAwfull();
		blockPepperoni = new BlockPepperoni();
		blockFatInfuser = new BlockFatInfuser();
		blockBenni = new BlockBenni();
		blockCookingPot = new BlockCookingPot();
		blockMateTee = new BlockMate();
	}
	
	public static void register() {
		GameRegistry.registerBlock(blockLauch, "blockLauch");
		GameRegistry.registerBlock(blockAwfull, ItemBlockAwfull.class, "blockAwfull");
		GameRegistry.registerBlock(blockPepperoni, "blockPepperoni");
		GameRegistry.registerBlock(blockFatInfuser, "blockFatInfuser");
		GameRegistry.registerBlock(blockBenni, ItemBlockBenni.class, "blockBenni");
		GameRegistry.registerBlock(blockCookingPot, "blockCookingPot");
		GameRegistry.registerBlock(blockMateTee, "blockMateTee");
	}
	
	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityFatInfuser.class, "TileEntityFatInfuser");
		GameRegistry.registerTileEntity(TileEntityBlockBenni.class, "TileEntityBlockBenni");
		GameRegistry.registerTileEntity(TileEntityCookingPot.class, "TileEntityCookingPot");
		GameRegistry.registerTileEntity(TileEntityBlockAwfull.class, "TileEntityBlockAwfull");
	}
}
