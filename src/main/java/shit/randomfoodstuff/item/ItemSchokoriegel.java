package shit.randomfoodstuff.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import shit.randomfoodstuff.RFMain;
import shit.randomfoodstuff.Reference;

import java.util.List;

public class ItemSchokoriegel extends ItemFood {

    public ItemSchokoriegel(int healAmount, float saturation) {
        super(healAmount, saturation, false);

        setUnlocalizedName("itemSchokoriegel");
        setTextureName(Reference.TextureName + "itemSchokoriegel");
        setCreativeTab(RFMain.cTab);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par1) {
        list.add("Fuer Robert <3");
        super.addInformation(stack, player, list, par1);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 16;
    }


}
