package shit.randomfoodstuff.item;

import java.util.List;

import WayofTime.alchemicalWizardry.api.items.interfaces.ArmourUpgrade;
import cpw.mods.fml.common.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import shit.randomfoodstuff.RFMain;
import shit.randomfoodstuff.Reference;
import shit.randomfoodstuff.cooking.ISpice;
import shit.randomfoodstuff.util.ItemHelper;

@Optional.Interface(modid=Reference.BloodMagicModID, iface="WayofTime.alchemicalWizardry.api.items.interfaces.ArmourUpgrade")
public class ItemSuperFatBenni extends Item implements ArmourUpgrade, ISpice {

	public ItemSuperFatBenni() {
		setCreativeTab(RFMain.cTab);
		setUnlocalizedName("itemSuperFatBenni");
		setTextureName(Reference.TextureName + "itemSuperFatBenni");
		setMaxStackSize(1);
	}
	
	@Override
	public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List list, boolean p_77624_4_) {
		list.add("Its so Fat that it doesnt fit in your mouth.");
		super.addInformation(p_77624_1_, p_77624_2_, list, p_77624_4_);
	}
	
	@Optional.Method(modid = Reference.BloodMagicModID)
	@Override
	public void onArmourUpdate(World world, EntityPlayer player, ItemStack thisItemStack) {
		ItemHelper.addFoodStats(player, 20, 20);
	}

	@Optional.Method(modid = Reference.BloodMagicModID)
	@Override
	public boolean isUpgrade() {
		return true;
	}

	@Optional.Method(modid = Reference.BloodMagicModID)
	@Override
	public int getEnergyForTenSeconds() {
		return 50;
	}

	@Override
	public String getEffectName(ItemStack stack) {
		return "saturation";
	}
}