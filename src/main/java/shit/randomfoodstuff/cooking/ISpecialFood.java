package shit.randomfoodstuff.cooking;

import net.minecraft.item.ItemStack;

public interface ISpecialFood {

    public int getFoodLevelForStack(ItemStack stack);

    public float getSaturationForStack(ItemStack stack);
}
