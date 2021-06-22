package shit.randomfoodstuff.item;

import WayofTime.alchemicalWizardry.api.items.interfaces.IBindable;
import WayofTime.alchemicalWizardry.common.items.EnergySword;
import cpw.mods.fml.common.Loader;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import shit.randomfoodstuff.ConfigHandler;
import shit.randomfoodstuff.RFMain;
import shit.randomfoodstuff.Reference;
import shit.randomfoodstuff.util.ItemHelper;

import java.util.List;

public class ItemBobTheBenni extends Item {

    public static final int STATE_INACTIVE = 0;
    public static final int STATE_ACTIVE = 1;
    public static final int STATE_RECHARGING = 2;

    private IIcon iconInactive, iconActive, iconRecharging;

    public ItemBobTheBenni() {
        setUnlocalizedName("itemBobTheBenni");
        setTextureName(Reference.TextureName + "itemBobTheBenniActive");
        setMaxStackSize(1);
        setCreativeTab(RFMain.cTab);
        setHasSubtypes(true);
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        ItemStack fullCharge = new ItemStack(item, 1);
        setCharge(fullCharge, ConfigHandler.bobTheBenniMaxUses);
        setState(fullCharge, STATE_INACTIVE);
        list.add(fullCharge);
        super.getSubItems(item, tab, list);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int parInt, boolean parBool) {
        if (!world.isRemote && entity instanceof EntityPlayer) {
            if (getTickCounter(stack) == 20) {
                EntityPlayer player = (EntityPlayer) entity;

                switch (getState(stack)) {
                    case STATE_ACTIVE:
                        if (getCharge(stack) >= 1) {
                            boolean flag1 = false;

                            //Main Inventory
                            for (int i = 0; i < player.inventory.mainInventory.length; i++) {
                                ItemStack item = player.inventory.mainInventory[i];
                                if (item != null) {
                                    flag1 = tryRepair(item, player);
                                    if (flag1)
                                        break;
                                }
                            }

                            //Armor
                            if (!flag1) {
                                for (int i = 0; i < player.inventory.armorInventory.length; i++) {
                                    ItemStack item = player.inventory.armorInventory[i];
                                    if (item != null) {
                                        flag1 = tryRepair(item, player);
                                        if (flag1)
                                            break;
                                    }
                                }
                            }

                            if (flag1) {
                                setCharge(stack, getCharge(stack) - 1);
                            }

                        }
                        break;
                    case STATE_RECHARGING:
                        if (getCharge(stack) < ConfigHandler.bobTheBenniMaxUses) {
                            for (int i = 0; i < player.inventory.mainInventory.length; i++) {
                                ItemStack item = player.inventory.mainInventory[i];
                                if (item != null) {
                                    if (item.getItem() instanceof ItemFood) {
                                        float foodLevel = ItemHelper.getFoodLevelForItem(item) * ConfigHandler.bobTheBenniConvertRate;
                                        if (foodLevel > 0) {
                                            if (setCharge(stack, getCharge(stack) + foodLevel)) {
                                                item.stackSize--;
                                                if (item.stackSize <= 0) {
                                                    player.inventory.mainInventory[i] = null;
                                                }

                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;
                }
                setTickCounter(stack, 0);
            } else {
                increaseTickCounter(stack);
            }
        }
    }


    protected boolean tryRepair(ItemStack stack, EntityPlayer player) {
        if (!stack.getItem().isDamageable()) {
            return false;
        }

        if (stack.getItem() instanceof IBenniNotRepairable) {
            if (((IBenniNotRepairable) stack.getItem()).canRepair(stack, player)) {
                return false;
            }
        }

        if (Loader.isModLoaded(Reference.TConstructModID)) {
            Class superClass = stack.getItem().getClass().getSuperclass();

            while (superClass != null) {
                if (superClass.getSimpleName().equals("ToolCore")) {
                    System.out.println("TC Tools cant be repaired");
                    return false;
                }
                superClass = superClass.getSuperclass();
            }
        }

        if (Loader.isModLoaded(Reference.BloodMagicModID)) {
            if (stack.getItem() instanceof IBindable || stack.getItem() instanceof EnergySword) {
                System.out.println("Bound Tools/Armor cant be repaired");
                return false;
            }
        }

        if (stack.isItemDamaged()) {
            stack.setItemDamage(stack.getItemDamage() - 1);
            return true;
        }


        return false;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        if (stack.hasTagCompound()) {
            return 1 - (getCharge(stack) / ConfigHandler.bobTheBenniMaxUses);
        }
        return 0;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        switch (getState(stack)) {
            case STATE_INACTIVE:
                list.add("Inactive");
                break;
            case STATE_ACTIVE:
                list.add("Active");
                break;
            case STATE_RECHARGING:
                list.add("Recharging!");
                break;
        }
        list.add("Charge: " + getCharge(stack));
        super.addInformation(stack, player, list, bool);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (player.isSneaking()) {
            if (getState(stack) == STATE_RECHARGING) {
                setState(stack, STATE_INACTIVE);
            } else {
                setState(stack, STATE_RECHARGING);
            }
        } else {
            if (getState(stack) == STATE_INACTIVE) {
                setState(stack, STATE_ACTIVE);
            } else if (getState(stack) == STATE_ACTIVE) {
                setState(stack, STATE_INACTIVE);
            } else if (getState(stack) == STATE_RECHARGING) {
                setState(stack, STATE_INACTIVE);
            }
        }
        return super.onItemRightClick(stack, world, player);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return getState(stack) == STATE_RECHARGING;
    }

    private static void increaseTickCounter(ItemStack stack) {
        setTickCounter(stack, getTickCounter(stack) + 1);
    }

    @Override
    public void registerIcons(IIconRegister iconRegistry) {
        this.iconInactive = iconRegistry.registerIcon(Reference.TextureName + "itemBobTheBenniInactive");
        this.iconActive = iconRegistry.registerIcon(Reference.TextureName + "itemBobTheBenniActive");
        this.iconRecharging = iconRegistry.registerIcon(Reference.TextureName + "itemBobTheBenniRecharging");
        super.registerIcons(iconRegistry);
    }

    @Override
    public IIcon getIconIndex(ItemStack stack) {
        return getIcon(stack, 0);
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        switch (getState(stack)) {
            case STATE_ACTIVE:
                return this.iconActive;
            case STATE_RECHARGING:
                return this.iconRecharging;
            default:
                return this.iconInactive;
        }
    }

    //Setter

    protected static void setState(ItemStack stack, int state) {
        NBTTagCompound itemTag = stack.getTagCompound();

        if (itemTag == null) {
            stack.setTagCompound(new NBTTagCompound());
            itemTag = stack.getTagCompound();
        }

        itemTag.setInteger("state", state);
    }

    protected static boolean setCharge(ItemStack stack, float charge) {
        if (charge <= ConfigHandler.bobTheBenniMaxUses) {
            NBTTagCompound itemTag = stack.getTagCompound();

            if (itemTag == null) {
                stack.setTagCompound(new NBTTagCompound());
                itemTag = stack.getTagCompound();
            }

            itemTag.setFloat("charge", charge);
            return true;
        } else {
            return false;
        }

    }

    protected static void setTickCounter(ItemStack stack, int tickCounter) {
        NBTTagCompound itemTag = stack.getTagCompound();

        if (itemTag == null) {
            stack.setTagCompound(new NBTTagCompound());
            itemTag = stack.getTagCompound();
        }

        itemTag.setInteger("tickCounter", tickCounter);
    }

    //Getter

    public static int getState(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }

        NBTTagCompound itemTag = stack.getTagCompound();

        return itemTag.getInteger("state");
    }

    public static float getCharge(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }

        NBTTagCompound itemTag = stack.getTagCompound();

        return itemTag.getFloat("charge");
    }

    public static int getTickCounter(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }

        NBTTagCompound itemTag = stack.getTagCompound();

        return itemTag.getInteger("tickCounter");
    }

}
