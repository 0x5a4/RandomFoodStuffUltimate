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
import shit.randomfoodstuff.util.ItemHelper;

import java.util.List;

public class ItemSchnitzel extends ItemFood implements ISchnitzelBackpackable, ISpecialFood {

    private IIcon iconRaw, iconCooked, iconSpicy;

    public ItemSchnitzel(int healAmount, float saturation) {
        super(healAmount, saturation, false);

        setCreativeTab(RFMain.cTab);
        setHasSubtypes(true);
    }

    @Override
    public void registerIcons(IIconRegister registry) {
        iconRaw = registry.registerIcon(Reference.TextureName + "itemSchnitzelRaw");
        iconCooked = registry.registerIcon(Reference.TextureName + "itemCookedSchnitzel");
        iconSpicy = registry.registerIcon(Reference.TextureName + "itemSchnitzelSpicy");
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
                return "itemSchnitzelCooked";
            case 2:
                return "itemSchnitzelSpicy";
            default:
                return "itemSchnitzelRaw";
        }
    }

    @Override
    public IIcon getIconFromDamage(int damage) {
        switch (damage) {
            case 1:
                return iconCooked;
            case 2:
                return iconSpicy;
            default:
                return iconRaw;
        }
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
        switch (stack.getItemDamage()) {
            case 1:
                ItemHelper.addFoodStats(player, 4, ItemHelper.getSaturationAmplifier(4, 2));
                break;
            case 2:
                ItemHelper.addFoodStats(player, 5, ItemHelper.getSaturationAmplifier(5, 4));
                break;
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (stack.getItemDamage() != 0) {
            return super.onItemRightClick(stack, world, player);
        }
        return stack;
    }

    @Override
    public boolean canEatItem(ItemStack stack) {
        if (stack.getItemDamage() != 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getFoodLevelForStack(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case 1:
                return 4;
            case 2:
                return 5;
        }
        return 0;
    }

    @Override
    public float getSaturationForStack(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case 1:
                return 2;
            case 2:
                return 4;
        }
        return 0;
    }

    @Override
    public boolean alwaysEatable(ItemStack stack) {
        return false;
    }

}
