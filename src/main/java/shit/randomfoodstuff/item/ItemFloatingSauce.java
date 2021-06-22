package shit.randomfoodstuff.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import shit.randomfoodstuff.RFMain;
import shit.randomfoodstuff.Reference;
import shit.randomfoodstuff.cooking.ISpice;

public class ItemFloatingSauce extends Item implements ISpice {

    public ItemFloatingSauce() {
        setUnlocalizedName("itemFloatingSauce");
        setTextureName(Reference.TextureName + "itemFloatingSauce");
        setCreativeTab(RFMain.cTab);
    }

    @Override
    public String getEffectName(ItemStack stack) {
        return "floating";
    }

}
