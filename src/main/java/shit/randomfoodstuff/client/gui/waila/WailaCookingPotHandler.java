package shit.randomfoodstuff.client.gui.waila;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import shit.randomfoodstuff.cooking.SoupRegistry;
import shit.randomfoodstuff.tileentity.TileEntityCookingPot;

import java.util.ArrayList;
import java.util.List;

public class WailaCookingPotHandler implements IWailaDataProvider {

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if (accessor.getTileEntity() instanceof TileEntityCookingPot) {
            TileEntityCookingPot tileentity = (TileEntityCookingPot) accessor.getTileEntity();
            int soupPercent = (int) (((float) tileentity.soupRemaining / ((float) TileEntityCookingPot.soupVal * 2)) * 100F);

            currenttip.add("Soup Remaining: " + soupPercent + "%");
            if (tileentity.containsItems()) {
                ArrayList<String> ingredList = new ArrayList<String>();
                for (int i = 0; i < tileentity.getSizeInventory() - 2; i++) {
                    ItemStack ingred = tileentity.getStackInSlot(i);
                    if (ingred != null) {
                        String stackDisplayName = ingred.getDisplayName();
                        if (!ingredList.contains(stackDisplayName)) {
                            ingredList.add(stackDisplayName);
                        }
                    }
                }

                if (ingredList.isEmpty()) {
                    currenttip.add("Ingriedients: None");
                } else {
                    currenttip.add("Ingriedients: ");
                    for (String s : ingredList) {
                        currenttip.add("  " + s);
                    }
                }
            }

            if (tileentity.containsSpice()) {
                currenttip.add("Spices: ");
                if (tileentity.getReagent() != null) {
                    if (SoupRegistry.getEffectDisplayName(tileentity.getSpice()).equals(SoupRegistry.getEffectDisplayName(tileentity.getReagent()))) {
                        currenttip.add("  " + SoupRegistry.getEffectDisplayName(tileentity.getSpice()) + " x2");
                    } else {
                        currenttip.add("  " + SoupRegistry.getEffectDisplayName(tileentity.getReagent()));
                        if (SoupRegistry.canReact(tileentity.getSpice(), tileentity.getReagent())) {
                            currenttip.add("=" + SoupRegistry.getEffectDisplayName(SoupRegistry.getReaction(tileentity.getSpice(), tileentity.getReagent()).getResultEffect()));
                        }
                    }
                } else {
                    currenttip.add("  " + SoupRegistry.getEffectDisplayName(tileentity.getSpice()));
                }
            }
        }
        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z) {
        return tag;
    }

}
