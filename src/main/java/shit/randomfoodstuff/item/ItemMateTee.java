package shit.randomfoodstuff.item;


import net.minecraft.block.Block;
import shit.randomfoodstuff.RFMain;
import shit.randomfoodstuff.Reference;
import shit.randomfoodstuff.util.RandomItemSeed;

public class ItemMateTee extends RandomItemSeed {
	
	public ItemMateTee(Block plant, Block soil) {
		super(plant, soil);
		setUnlocalizedName("itemMateTee");
		setTextureName(Reference.TextureName + "itemMateTee");
		setCreativeTab(RFMain.tab);
	}

}
