package shit.randomfoodstuff.item;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import shit.randomfoodstuff.RFMain;
import shit.randomfoodstuff.Reference;
import shit.randomfoodstuff.cooking.ISpice;
import shit.randomfoodstuff.entity.EntityItemPepperoni;
import shit.randomfoodstuff.potion.RFPotion;
import shit.randomfoodstuff.util.ColorHelper;
import shit.randomfoodstuff.util.DurationHelper;
import shit.randomfoodstuff.util.RandomFoodSeed;

import java.util.List;

public class ItemPepperoni extends RandomFoodSeed implements ISpice {

    public ItemPepperoni(int parHealAmount, float parSaturationModifier, Block parBlockPlant, Block parSoilBlock) {
        super(parHealAmount, parSaturationModifier, parBlockPlant, parSoilBlock);

        setUnlocalizedName("itemPepperoni");
        setTextureName(Reference.TextureName + "itemPepperoni");
        setCreativeTab(RFMain.cTab);

    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par1) {
        list.add(ColorHelper.getColorByName("RED") + "Spicy as Fuck");
        super.addInformation(stack, player, list, par1);
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
        player.addPotionEffect(new PotionEffect(RFPotion.potionPepperoni.id, DurationHelper.minutesToTicks(2), 0));
        super.onFoodEaten(stack, world, player);
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Override
    public Entity createEntity(World world, Entity location, ItemStack itemstack) {
        return new EntityItemPepperoni(world, location, itemstack);
    }

    @Override
    public String getEffectName(ItemStack stack) {
        return "pepperoni";
    }

}
