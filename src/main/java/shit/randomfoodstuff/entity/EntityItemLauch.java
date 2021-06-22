package shit.randomfoodstuff.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import shit.randomfoodstuff.RandomItems;

public class EntityItemLauch extends EntityItem {

    public EntityItemLauch(World world, double par1, double par2, double par3) {
        super(world, par1, par2, par3);

        this.lifespan = 72000;
        this.delayBeforeCanPickup = 30;

    }

    public EntityItemLauch(World world, Entity original, ItemStack stack) {
        this(world, original.posX, original.posY, original.posZ);

        this.motionX = original.motionX;
        this.motionY = original.motionY;
        this.motionZ = original.motionZ;
        this.setEntityItemStack(stack);
    }

    @Override
    public void onUpdate() {
        if (this.isBurning()) {
            this.setEntityItemStack(new ItemStack(RandomItems.itemPepperoni, this.getEntityItem().stackSize));
        }
        super.onUpdate();
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float damage) {
        if (source != DamageSource.inFire && source != DamageSource.onFire) {
            return true;
        } else {
            return false;
        }
    }

}
