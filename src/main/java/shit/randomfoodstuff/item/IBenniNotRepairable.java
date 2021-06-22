package shit.randomfoodstuff.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IBenniNotRepairable {

    public boolean canRepair(ItemStack stack, EntityPlayer player);
}
