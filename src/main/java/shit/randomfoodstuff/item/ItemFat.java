package shit.randomfoodstuff.item;

import net.minecraft.item.Item;
import shit.randomfoodstuff.RFMain;
import shit.randomfoodstuff.Reference;

public class ItemFat extends Item {

    public ItemFat() {
        setUnlocalizedName("itemFat");
        setTextureName(Reference.TextureName + "itemFat");
        setCreativeTab(RFMain.cTab);
    }
}
