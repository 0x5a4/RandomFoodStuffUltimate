package shit.randomfoodstuff.event.handler;

import java.util.Iterator;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.StatFileWriter;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.AchievementEvent;
import shit.randomfoodstuff.RandomAchievements;
import shit.randomfoodstuff.RandomBlocks;
import shit.randomfoodstuff.item.ItemSchnitzelBackpack;
import shit.randomfoodstuff.net.SchnitzelPacketHandler;
import shit.randomfoodstuff.net.SchnitzelPacketProcessor;

@SideOnly(Side.CLIENT)
public class ClientEventHandler {

	@SubscribeEvent
	public void onMouseEvent(MouseEvent event) {
		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;

		if (event.dwheel != 0 && player != null && player.isSneaking()) {
			ItemStack stack = player.getCurrentEquippedItem();

			if (stack != null) {
				Item item = stack.getItem();

				if (item instanceof ItemSchnitzelBackpack) {
					cycleSchnitzel(stack, player, event.dwheel);
					event.setCanceled(true);
				}
			}
		}
	}

	private void cycleSchnitzel(ItemStack stack, EntityPlayer player, int dWheel) {
		int mode = ItemSchnitzelBackpack.getCurrentItem(stack);
		mode = dWheel < 0 ? ItemSchnitzelBackpack.next(mode) : ItemSchnitzelBackpack.prev(mode);
		ItemSchnitzelBackpack.cycleSchnitzel(false, player.getEntityId(), stack, mode);
		SchnitzelPacketHandler.INSTANCE.sendToServer(new SchnitzelPacketProcessor(player.getEntityId(), player.inventory.currentItem, mode));
	}

}
