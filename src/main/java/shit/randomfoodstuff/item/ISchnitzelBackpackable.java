package shit.randomfoodstuff.item;

import net.minecraft.item.ItemStack;

public interface ISchnitzelBackpackable {

	public boolean canEatItem(ItemStack stack);
	public boolean alwaysEatable(ItemStack stack);
}
