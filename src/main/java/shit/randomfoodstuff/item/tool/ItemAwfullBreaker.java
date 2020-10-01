package shit.randomfoodstuff.item.tool;

import net.minecraft.item.ItemPickaxe;
import shit.randomfoodstuff.RFMain;
import shit.randomfoodstuff.Reference;

public class ItemAwfullBreaker extends ItemPickaxe implements IAwfullLookingBlockBreaker {

	public ItemAwfullBreaker(ToolMaterial material) {
		super(material);
		
		setMaxStackSize(1);
		setUnlocalizedName("itemAwfullBreaker");
		setTextureName(Reference.TextureName + "itemAwfullBreaker");
		setCreativeTab(RFMain.tab);
	}
	
	

}
