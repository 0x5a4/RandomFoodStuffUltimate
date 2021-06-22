package shit.randomfoodstuff.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import shit.randomfoodstuff.block.BlockAwful;
import shit.randomfoodstuff.util.ColorHelper;

import java.util.List;

public class ItemBlockAwfull extends ItemBlock {

    public ItemBlockAwfull(Block block) {
        super(block);
    }

    public static int getFakeBlockID(ItemStack stack) {
        if (stack.hasTagCompound()) {
            return stack.getTagCompound().getInteger("fakeBlockID");
        } else {
            return -1;
        }
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        if (getFakeBlockID(stack) != -1) {
            ItemStack fakeStack = new ItemStack(Block.getBlockById(getFakeBlockID(stack)), 1, getFakeBlockMeta(stack));
            list.add("Immitating: " + fakeStack.getDisplayName());
        }
        super.addInformation(stack, player, list, bool);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
        if (player.isSneaking()) {
            if (!BlockAwful.canImitate(world, x, y, z)) {
                if (!world.isRemote) {
                    player.addChatComponentMessage(new ChatComponentText(ColorHelper.getColorByName("Dark_Purple") + "The Awfull Looking Block refuses to take its Shape..."));
                }
            }
            return false;
        } else {
            return super.onItemUse(stack, player, world, x, y, z, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_);
        }
    }

    public static int getFakeBlockMeta(ItemStack stack) {
        if (stack.hasTagCompound()) {
            return stack.getTagCompound().getInteger("fakeBlockMeta");
        } else {
            return -1;
        }
    }

    public static void setFakeBlockID(ItemStack stack, int fakeBlockID) {
        NBTTagCompound tag = stack.getTagCompound();
        if (tag == null) {
            tag = new NBTTagCompound();
            stack.setTagCompound(tag);
        }

        tag.setInteger("fakeBlockID", fakeBlockID);
    }

    public static void setFakeBlockMeta(ItemStack stack, int fakeBlockMeta) {
        NBTTagCompound tag = stack.getTagCompound();
        if (tag == null) {
            tag = new NBTTagCompound();
            stack.setTagCompound(tag);
        }

        tag.setInteger("fakeBlockMeta", fakeBlockMeta);
    }

}
