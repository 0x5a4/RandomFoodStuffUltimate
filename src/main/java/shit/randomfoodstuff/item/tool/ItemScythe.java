package shit.randomfoodstuff.item.tool;

import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import shit.randomfoodstuff.RFMain;
import shit.randomfoodstuff.Reference;
import shit.randomfoodstuff.item.IScythe;

public class ItemScythe extends ItemHoe implements IScythe {

	public ItemScythe(ToolMaterial material) {
		super(material);
		setUnlocalizedName("itemScythe");
		setTextureName(Reference.TextureName + "itemScythe");
		setMaxStackSize(1);
		setCreativeTab(RFMain.cTab);
	}

	@Override
	public int getDropModifier() {
		return 2;
	}
	
}
