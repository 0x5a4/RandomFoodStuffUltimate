package shit.randomfoodstuff.item;

import java.util.List;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import shit.randomfoodstuff.RFMain;
import shit.randomfoodstuff.Reference;
import shit.randomfoodstuff.util.DurationHelper;

public class ItemMagicBaguette extends Item{
	
	public ItemMagicBaguette() {
		setUnlocalizedName("itemMagicBaguette");
		setTextureName(Reference.TextureName + "itemMagicBaguette");
		setMaxStackSize(1);
		setMaxDamage(2);
		setCreativeTab(RFMain.tab);
	}
	
	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.epic;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par1) {
		list.add("Feel the Magic");
		super.addInformation(stack, player, list, par1);
	}
	
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 72000;
	}
	
	public EnumAction getItemUseAction(ItemStack p_77661_1_) {
		return EnumAction.bow;
	}
	 
	 @Override
	 public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int itemInUseCount) {
		 int playerX = (int) player.posX;
		 int playerY = (int) player.posY;
		 int playerZ = (int) player.posZ;
		 int[] lightningCoords1 = new int[3];
		 int[] lightningCoords2 = new int[3];
		 int[] lightningCoords3 = new int[3];
		 int[] lightningCoords4 = new int[3];
		 int[] lightningCoords5 = new int[3];
		 int[] lightningCoords6 = new int[3];
		 int[] lightningCoords7 = new int[3];
		 int[] lightningCoords8 = new int[3];
		 boolean canPlayerSeeSky = world.canBlockSeeTheSky(playerX, playerY, playerZ);
		 if (canPlayerSeeSky) {
			 lightningCoords1[0] = playerX + 5;
			 lightningCoords1[2] = playerZ;
			 lightningCoords2[0] = playerX - 5;
			 lightningCoords2[2] = playerZ;
			 lightningCoords3[0] = playerX;
			 lightningCoords3[2] = playerZ + 5;;
			 lightningCoords4[0] = playerX;
			 lightningCoords4[2] = playerZ - 5;
			 lightningCoords5[0] = playerX + 3;
			 lightningCoords5[2] = playerZ + 3;
			 lightningCoords6[0] = playerX + 3;
			 lightningCoords6[2] = playerZ - 3;
			 lightningCoords7[0] = playerX - 3;
			 lightningCoords7[2] = playerZ + 3;
			 lightningCoords8[0] = playerX - 3;
			 lightningCoords8[2] = playerZ - 3;
			 
			 lightningCoords1[1] = world.getHeightValue(lightningCoords1[0], lightningCoords1[2]);
			 lightningCoords2[1] = world.getHeightValue(lightningCoords2[0], lightningCoords2[2]);
			 lightningCoords3[1] = world.getHeightValue(lightningCoords3[0], lightningCoords3[2]);
			 lightningCoords4[1] = world.getHeightValue(lightningCoords4[0], lightningCoords4[2]);
			 lightningCoords5[1] = world.getHeightValue(lightningCoords5[0], lightningCoords5[2]);
			 lightningCoords6[1] = world.getHeightValue(lightningCoords6[0], lightningCoords6[2]);
			 lightningCoords7[1] = world.getHeightValue(lightningCoords7[0], lightningCoords7[2]);
			 lightningCoords8[1] = world.getHeightValue(lightningCoords8[0], lightningCoords8[2]);
	    		
			 player.addPotionEffect(new PotionEffect(Potion.resistance.id, DurationHelper.secondsToTicks(1), 5));
	    		
			 if (!world.isRemote) {
				 world.addWeatherEffect(new EntityLightningBolt(world, lightningCoords1[0], lightningCoords1[1], lightningCoords1[2]));
				 world.addWeatherEffect(new EntityLightningBolt(world, lightningCoords2[0], lightningCoords2[1], lightningCoords2[2]));
				 world.addWeatherEffect(new EntityLightningBolt(world, lightningCoords3[0], lightningCoords3[1], lightningCoords3[2]));
				 world.addWeatherEffect(new EntityLightningBolt(world, lightningCoords4[0], lightningCoords4[1], lightningCoords4[2]));
				 world.addWeatherEffect(new EntityLightningBolt(world, lightningCoords5[0], lightningCoords5[1], lightningCoords5[2]));
				 world.addWeatherEffect(new EntityLightningBolt(world, lightningCoords6[0], lightningCoords6[1], lightningCoords6[2]));
				 world.addWeatherEffect(new EntityLightningBolt(world, lightningCoords7[0], lightningCoords7[1], lightningCoords7[2]));
				 world.addWeatherEffect(new EntityLightningBolt(world, lightningCoords8[0], lightningCoords8[1], lightningCoords8[2]));
				 if (!player.capabilities.isCreativeMode) {
					 stack.damageItem(1, player);
					 System.err.println(stack.getItemDamage());
					 if (stack.getItemDamageForDisplay() == 0) {
						 player.destroyCurrentEquippedItem();
					 }
				 }
			 }
		 }
		 super.onPlayerStoppedUsing(stack, world, player, itemInUseCount);
	 }
	    
	 @Override
	 public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		 int dimensionID = world.provider.dimensionId;
		 if (dimensionID != 0) {
			 if (world.isRemote) {
				 player.addChatComponentMessage(new ChatComponentText("The baguette Magic only works on the Overworld"));
			 }
			 return super.onItemRightClick(stack, world, player);
		 }
		 if (!player.isSneaking()) {
			 player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		 } else {
			 if (!world.getWorldInfo().isRaining()) {
				 world.getWorldInfo().setThundering(true);
				 world.getWorldInfo().setRaining(true);
				 if (world.isRemote) {
					 player.addChatComponentMessage(new ChatComponentText("Now things get Stormy..."));
				 }
			 } else {
				 world.getWorldInfo().setRaining(false);
				 world.getWorldInfo().setThundering(false);
				 if (world.isRemote) {
					 player.addChatComponentMessage(new ChatComponentText("Get those fucking Clouds out of da WAE"));
				 }
			 }
		 }
		 return super.onItemRightClick(stack, world, player);
	 }

}
