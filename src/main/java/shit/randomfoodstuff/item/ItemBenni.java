package shit.randomfoodstuff.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import shit.randomfoodstuff.RFMain;
import shit.randomfoodstuff.Reference;
import shit.randomfoodstuff.cooking.ISpecialFood;
import shit.randomfoodstuff.potion.RFPotion;
import shit.randomfoodstuff.util.ItemHelper;

import java.util.List;

public class ItemBenni extends ItemFood implements ISpecialFood {

    private IIcon benniRaw, benniMedium, benniRoasted;

    public ItemBenni(int healAmount, float saturation) {
        super(healAmount, saturation, false);

        setCreativeTab(RFMain.cTab);
        setHasSubtypes(true);
    }

    @Override
    public void registerIcons(IIconRegister registry) {
        benniRaw = registry.registerIcon(Reference.TextureName + "itemRawBenni");
        benniMedium = registry.registerIcon(Reference.TextureName + "itemMediumBenni");
        benniRoasted = registry.registerIcon(Reference.TextureName + "itemRoastedBenni");
        super.registerIcons(registry);
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        list.add(new ItemStack(item, 1, 1));
        list.add(new ItemStack(item, 1, 2));
        super.getSubItems(item, tab, list);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case 1:
                return "itemMediumBenni";
            case 2:
                return "itemRoastedBenni";
            default:
                return "itemRawBenni";
        }
    }

    @Override
    public IIcon getIconFromDamage(int damage) {
        switch (damage) {
            case 1:
                return benniMedium;
            case 2:
                return benniRoasted;
            default:
                return benniRaw;
        }
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
        switch (stack.getItemDamage()) {
            case 0:
                ItemHelper.addFoodStats(player, 1, ItemHelper.getSaturationAmplifier(1, 1));
                break;
            case 1:
                ItemHelper.addFoodStats(player, 5, ItemHelper.getSaturationAmplifier(5, 3));
                player.heal(4F);
                break;
            case 2:
                ItemHelper.addFoodStats(player, 8, ItemHelper.getSaturationAmplifier(8, 6));
                player.removePotionEffect(RFPotion.potionLauch.id);
                break;
        }
        super.onFoodEaten(stack, world, player);
    }

    @Override
    public int getFoodLevelForStack(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case 0:
                return 1;
            case 1:
                return 5;
            case 2:
                return 8;
        }
        return 0;
    }

    @Override
    public float getSaturationForStack(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case 0:
                return 1;
            case 1:
                return 3;
            case 2:
                return 6;
        }
        return 0;
    }
}
