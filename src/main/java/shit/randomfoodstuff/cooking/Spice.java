package shit.randomfoodstuff.cooking;

import net.minecraft.item.ItemStack;
import shit.randomfoodstuff.util.ItemHelper;

/**
 * @author Arthur
 * Wrapper Class for Spices
 */
public class Spice implements ISpice {

    protected ItemStack spice;
    protected String effect;

    public Spice(ItemStack spice, String effect) {
        this.spice = spice;
        this.effect = effect;
    }

    public boolean represents(ItemStack stack) {
        return ItemHelper.compareStacks(spice, stack);
    }

    @Override
    public String getEffectName(ItemStack stack) {
        return effect;
    }

}
