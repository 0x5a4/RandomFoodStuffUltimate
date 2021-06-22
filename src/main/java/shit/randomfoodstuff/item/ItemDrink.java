package shit.randomfoodstuff.item;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import shit.randomfoodstuff.RFMain;
import shit.randomfoodstuff.RandomItems;
import shit.randomfoodstuff.Reference;
import shit.randomfoodstuff.util.DurationHelper;

import java.util.List;

public class ItemDrink extends ItemFood {

    private IIcon emptyCan, waterCan, carotDrink, proteineDrink, energyDrink;

    public ItemDrink(int healAmount, float saturation) {
        super(healAmount, saturation, false);

        setUnlocalizedName("itemDrink");
        setMaxStackSize(16);
        setAlwaysEdible();
        setHasSubtypes(true);
        setCreativeTab(RFMain.cTab);
    }

    @Override
    public void registerIcons(IIconRegister registry) {
        this.emptyCan = registry.registerIcon(Reference.TextureName + "itemEmptyCan");
        this.waterCan = registry.registerIcon(Reference.TextureName + "itemWaterCan");
        this.carotDrink = registry.registerIcon(Reference.TextureName + "itemCarotDrink");
        this.proteineDrink = registry.registerIcon(Reference.TextureName + "itemProteineDrink");
        this.energyDrink = registry.registerIcon(Reference.TextureName + "itemEnergyDrink");
        super.registerIcons(registry);
    }

    @Override
    public IIcon getIconFromDamage(int damage) {
        switch (damage) {
            case 1:
                return this.waterCan;
            case 2:
                return this.carotDrink;
            case 3:
                return this.proteineDrink;
            case 4:
                return this.energyDrink;
            default:
                return this.emptyCan;
        }
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        list.add(new ItemStack(item, 1, 1));
        list.add(new ItemStack(item, 1, 2));
        list.add(new ItemStack(item, 1, 3));
        list.add(new ItemStack(item, 1, 4));
        super.getSubItems(item, tab, list);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case 1:
                return "itemWaterCan";
            case 2:
                return "itemCarotDrink";
            case 3:
                return "itemProteineDrink";
            case 4:
                return "itemEnergyDrink";
            default:
                return "itemEmptyCan";
        }
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
        switch (stack.getItemDamage()) {
            case 1:
                player.extinguish();
                break;
            case 2:
                player.addPotionEffect(new PotionEffect(Potion.nightVision.id, DurationHelper.minutesToTicks(15)));
                break;
            case 3:
                player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, DurationHelper.minutesToTicks(10), 2));
                break;
            case 4:
                player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, DurationHelper.minutesToTicks(10), 1));
                break;
        }
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.drink;
    }

    @Override
    public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_) {
        --p_77654_1_.stackSize;
        p_77654_3_.getFoodStats().func_151686_a(this, p_77654_1_);
        p_77654_2_.playSoundAtEntity(p_77654_3_, "random.drink", 0.5F, p_77654_2_.rand.nextFloat() * 0.1F + 0.9F);
        this.onFoodEaten(p_77654_1_, p_77654_2_, p_77654_3_);
        return p_77654_1_;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (stack.getItemDamage() == 0) {

            MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);

            if (movingobjectposition == null) {
                return stack;
            } else {
                if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                    int i = movingobjectposition.blockX;
                    int j = movingobjectposition.blockY;
                    int k = movingobjectposition.blockZ;

                    if (!world.canMineBlock(player, i, j, k)) {
                        return stack;
                    }

                    if (!player.canPlayerEdit(i, j, k, movingobjectposition.sideHit, stack)) {
                        return stack;
                    }

                    if (world.getBlock(i, j, k).getMaterial() == Material.water) {
                        --stack.stackSize;

                        if (stack.stackSize <= 0) {
                            return new ItemStack(RandomItems.itemDrink, 1, 1);
                        }

                        if (!player.inventory.addItemStackToInventory(new ItemStack(RandomItems.itemDrink, 1, 1))) {
                            player.dropPlayerItemWithRandomChoice(new ItemStack(RandomItems.itemDrink, 1, 1), false);
                        }
                    }
                }

                return stack;
            }

        } else {
            super.onItemRightClick(stack, world, player);
        }

        return stack;

    }


}
