package shit.randomfoodstuff.tileentity.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import shit.randomfoodstuff.tileentity.TileEntityCookingPot;

public class ContainerCookingPot extends Container {

    private TileEntityCookingPot entity;

    private int lastTimeUntilReset;

    public ContainerCookingPot(TileEntityCookingPot entity) {
        this.entity = entity;

        //Ingredients
        this.addSlotToContainer(new SlotDisabled(entity, 0, 10, 19));
        this.addSlotToContainer(new SlotDisabled(entity, 1, 45, 19));
        this.addSlotToContainer(new SlotDisabled(entity, 2, 80, 19));
        //Spice
        this.addSlotToContainer(new SlotDisabled(entity, 3, 10, 53));
        this.addSlotToContainer(new SlotDisabled(entity, 4, 45, 53));
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); i++) {
            ICrafting crafting = (ICrafting) this.crafters.get(i);

            if (this.lastTimeUntilReset != this.entity.soupRemaining) {
                crafting.sendProgressBarUpdate(this, 0, this.entity.soupRemaining);
            }

            this.lastTimeUntilReset = this.entity.soupRemaining;
        }
    }

    @Override
    public void addCraftingToCrafters(ICrafting crafting) {
        super.addCraftingToCrafters(crafting);
        crafting.sendProgressBarUpdate(this, 0, this.entity.soupRemaining);
    }

    @Override
    public void updateProgressBar(int par1, int par2) {
        if (par1 == 0) {
            this.entity.soupRemaining = par2;
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.entity.isUseableByPlayer(player);
    }

}
