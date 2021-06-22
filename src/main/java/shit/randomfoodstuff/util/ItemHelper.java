package shit.randomfoodstuff.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import shit.randomfoodstuff.cooking.ISpecialFood;

public class ItemHelper {

    public static boolean compareStacks(ItemStack stack1, ItemStack stack2) {
        return stack1 != null && stack2 != null && stack1.getItem() == stack2.getItem() && stack1.getItemDamage() == stack2.getItemDamage();
    }

    public static void addFoodStatFromItem(EntityPlayer player, ItemFood food) {
        player.getFoodStats().func_151686_a(food, new ItemStack(food, 1));
    }

    public static void addFoodStatsFromItem(EntityPlayer player, ItemFood food, int itemDamage) {
        player.getFoodStats().func_151686_a(food, new ItemStack(food, 1, itemDamage));
    }

    public static void addFoodStats(EntityPlayer player, int foodLevel, float saturation) {
        player.getFoodStats().addStats(foodLevel, saturation);
    }

    public static int getFoodLevelForItem(ItemStack stack) {
        if (stack.getItem() instanceof ISpecialFood) {
            return ((ISpecialFood) stack.getItem()).getFoodLevelForStack(stack);
        } else if (stack.getItem() instanceof ItemFood) {
            return ((ItemFood) stack.getItem()).func_150905_g(stack);
        }
        return 0;
    }

    public static float getSaturationAmplifierForItem(ItemStack stack) {
        if (stack.getItem() instanceof ISpecialFood) {
            return ((ISpecialFood) stack.getItem()).getSaturationForStack(stack);
        } else if (stack.getItem() instanceof ItemFood) {
            return ((ItemFood) stack.getItem()).func_150906_h(stack);
        }
        return 0;
    }

    public static float getSaturationAmplifier(int foodLevel, float saturation) {
        return (float) saturation / 2F / (float) foodLevel;
    }

    public static int getSaturationFromAmplifier(int foodLevel, float saturationAmplifier) {
        return (int) (foodLevel * 2 * saturationAmplifier);
    }

}
