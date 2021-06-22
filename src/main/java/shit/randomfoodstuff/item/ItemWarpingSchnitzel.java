package shit.randomfoodstuff.item;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import shit.randomfoodstuff.RFMain;
import shit.randomfoodstuff.RandomAchievements;
import shit.randomfoodstuff.Reference;
import shit.randomfoodstuff.entity.EntityItemWarpingSchnitzel;
import shit.randomfoodstuff.util.ColorHelper;

import java.util.List;

public class ItemWarpingSchnitzel extends Item implements ISchnitzelBackpackable {

    public ItemWarpingSchnitzel() {
        super();

        setUnlocalizedName("itemWarpingSchnitzel");
        setTextureName(Reference.TextureName + "itemWarpingSchnitzel");
        setMaxStackSize(1);
        setCreativeTab(RFMain.cTab);
        setHasSubtypes(true);
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        ItemStack poweredStack = new ItemStack(item, 1);
        this.setPowered(poweredStack, true);
        list.add(poweredStack);
        super.getSubItems(item, tab, list);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par1) {
        if (this.getUses(stack) != 0) {
            if (!isPowered(stack)) {
                list.add("Uses: " + this.getUses(stack));
            } else {
                list.add("Uses: " + ColorHelper.getColorByName("DARK_Purple") + "INFINITE");
            }
            if (GuiScreen.isShiftKeyDown()) {
                if (this.getX(stack) != 0 || this.getY(stack) != 0 || this.getZ(stack) != 0) {
                    list.add("X: " + this.getX(stack));
                    list.add("Y: " + this.getY(stack));
                    list.add("Z: " + this.getZ(stack));
                    list.add("Dimension: " + this.getDimension(stack));
                } else {
                    list.add("No Destination :(");
                    list.add("Shift Click to set a Destination");
                }
            } else {
                list.add(EnumChatFormatting.GRAY + "Hold Shift for more");
            }
        }
        super.addInformation(stack, player, list, par1);
    }

    @Override
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
        if (this.getX(stack) != 0 || this.getY(stack) != 0 || this.getZ(stack) != 0) {
            if (world.provider.dimensionId == this.getDimension(stack)) {
                EnderTeleportEvent event = new EnderTeleportEvent(player, this.getX(stack), this.getY(stack), this.getZ(stack), 0F);
                MinecraftForge.EVENT_BUS.post(event);
                if (!event.isCanceled()) {
                    // Teleport
                    player.worldObj = world;
                    player.setPositionAndUpdate(this.getX(stack), this.getY(stack), this.getZ(stack));

                    boolean flag = !RFMain.isClientSide();
                    if (flag) {
                        player.getFoodStats().addExhaustion(10);
                        this.decreaseUses(stack);
                        world.playSoundAtEntity(player, "mob.endermen.portal", 1, 1);
                        world.playSoundEffect(player.posX, player.posY, player.posZ, "mob.endermen.portal", 1, 1);
                    }
                    player.addStat(RandomAchievements.getAchievementByName("warping2"), 1);
                    return this.destroyItem(stack);
                } else {
                    if (world.isRemote) {
                        player.addChatComponentMessage(new ChatComponentText(ColorHelper.getColorByName("DARK_PURPLE") + "Some Magic Forces denied the Teleportation!!"));
                        player.addChatComponentMessage(new ChatComponentText(ColorHelper.getColorByName("RED") + "RUN!!!!"));
                    } else {
                        world.playSoundAtEntity(player, "mob.endermen.scream", 1, 1);
                        world.playSoundEffect(player.posX, player.posY, player.posZ, "mob.endermen.scream", 1, 1);
                    }
                }
            } else if (world.isRemote) {
                player.addChatComponentMessage(new ChatComponentText("The Power of the Schnitzel isnt focused enough"));
                player.addChatComponentMessage(new ChatComponentText("to warp you between Dimensions"));
            }
        } else if (world.isRemote) {
            player.addChatComponentMessage(new ChatComponentText("No Warping Point set"));
        }
        return stack;
    }

    private ItemStack destroyItem(ItemStack stack) {
        if (this.getUses(stack) == 0 && !this.isPowered(stack)) {
            return null;
        } else {
            return stack;
        }
    }

    private ItemStack decreaseUses(ItemStack stack) {
        if (isPowered(stack)) {
            return stack;
        } else {
            this.setUses(stack, getUses(stack) - 1);
            return stack;
        }
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par1, boolean par2) {
        if (this.getX(stack) == 0 && this.getY(stack) == 0 && this.getZ(stack) == 0) {
            this.setUses(stack, 20);
        }
        super.onUpdate(stack, world, entity, par1, par2);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (player.isSneaking()) {
            this.setX(stack, player.posX);
            this.setY(stack, player.posY);
            this.setZ(stack, player.posZ);
            this.setDimension(stack, player.dimension);
            if (!world.isRemote) {
                player.addChatComponentMessage(new ChatComponentText("Warping Point set"));
            }
            return stack;
        } else {
            player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
            return super.onItemRightClick(stack, world, player);
        }
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 32;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.eat;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return !isPowered(stack) && getUses(stack) < 20 && getUses(stack) > 0;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        if (stack.hasTagCompound()) {
            return 1D - getUses(stack) / 20D;
        }
        return 0;
    }

    private static void setX(ItemStack stack, double x) {
        NBTTagCompound itemTag = stack.getTagCompound();

        if (itemTag == null) {
            stack.setTagCompound(new NBTTagCompound());
            itemTag = stack.getTagCompound();
        }

        itemTag.setDouble("x", x);
    }

    private static double getX(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }

        NBTTagCompound itemTag = stack.getTagCompound();

        return itemTag.getDouble("x");
    }

    private static void setY(ItemStack stack, double y) {
        NBTTagCompound itemTag = stack.getTagCompound();

        if (itemTag == null) {
            stack.setTagCompound(new NBTTagCompound());
            itemTag = stack.getTagCompound();
        }

        itemTag.setDouble("y", y);
    }

    private static double getY(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }

        NBTTagCompound itemTag = stack.getTagCompound();

        return itemTag.getDouble("y");
    }

    private static void setZ(ItemStack stack, double z) {
        NBTTagCompound itemTag = stack.getTagCompound();

        if (itemTag == null) {
            stack.setTagCompound(new NBTTagCompound());
            itemTag = stack.getTagCompound();
        }

        itemTag.setDouble("z", z);
    }

    private static double getZ(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }

        NBTTagCompound itemTag = stack.getTagCompound();

        return itemTag.getDouble("z");
    }

    private static void setDimension(ItemStack stack, int dimension) {
        NBTTagCompound itemTag = stack.getTagCompound();

        if (itemTag == null) {
            stack.setTagCompound(new NBTTagCompound());
            itemTag = stack.getTagCompound();
        }

        itemTag.setInteger("dimension", dimension);
    }

    private static int getDimension(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }

        NBTTagCompound itemTag = stack.getTagCompound();

        return itemTag.getInteger("dimension");
    }

    public static void setPowered(ItemStack stack, boolean powered) {
        NBTTagCompound itemTag = stack.getTagCompound();

        if (itemTag == null) {
            stack.setTagCompound(new NBTTagCompound());
            itemTag = stack.getTagCompound();
        }

        itemTag.setBoolean("powered", powered);
    }

    public static boolean isPowered(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }

        NBTTagCompound itemTag = stack.getTagCompound();

        return itemTag.getBoolean("powered");
    }

    private static void setUses(ItemStack stack, int uses) {

        NBTTagCompound tag = stack.getTagCompound();

        if (tag == null) {
            stack.setTagCompound(new NBTTagCompound());
            tag = stack.getTagCompound();
        }

        tag.setInteger("uses", uses);
    }

    private static int getUses(ItemStack stack) {

        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }

        NBTTagCompound tag = stack.getTagCompound();

        return tag.getInteger("uses");
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return this.isPowered(stack);
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Override
    public Entity createEntity(World world, Entity location, ItemStack itemstack) {
        return new EntityItemWarpingSchnitzel(world, location, itemstack);
    }

    @Override
    public boolean canEatItem(ItemStack stack) {
        return true;
    }

    @Override
    public boolean alwaysEatable(ItemStack stack) {
        return true;
    }

}
