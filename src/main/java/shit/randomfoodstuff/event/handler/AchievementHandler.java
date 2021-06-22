package shit.randomfoodstuff.event.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.StatFileWriter;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.AchievementEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import shit.randomfoodstuff.RandomAchievements;
import shit.randomfoodstuff.RandomBlocks;
import shit.randomfoodstuff.RandomItems;
import shit.randomfoodstuff.event.AwfullLookingBlockImmitationEvent;
import shit.randomfoodstuff.event.FatInfusingEvent;
import shit.randomfoodstuff.event.SoupCreationEvent;

import java.util.Iterator;

import static shit.randomfoodstuff.RandomAchievements.getAchievementByName;

public class AchievementHandler {

    @SubscribeEvent
    public void onItemPickup(EntityItemPickupEvent event) {
        if (event.item.getEntityItem().getItem() == RandomItems.itemLauch) {
            event.entityPlayer.addStat(getAchievementByName("lauch"), 1);
        } else if (event.item.getEntityItem().getItem() == RandomItems.itemPepperoni) {
            event.entityPlayer.addStat(getAchievementByName("pepperoni"), 1);
        }
    }

    @SubscribeEvent
    public void onPlayerUpdate(LivingUpdateEvent event) {
        if (event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;
            if (player.posY >= 256) {
                player.addStat(getAchievementByName("flyHigh"), 1);
            }
        }
    }

    @SubscribeEvent
    public void onFatInfuse(FatInfusingEvent.ItemInfusedEvent event) {
        if (event.output.getItem() == RandomItems.itemBenni) {
            event.player.addStat(getAchievementByName("benni"), 1);
        }
    }

    @SubscribeEvent
    public void onItemCooked(SoupCreationEvent.CreateSoupItemEvent.Post event) {
        event.player.addStat(getAchievementByName("soup"), 1);
    }

    @SubscribeEvent
    public void onAwfullLookingBlockImmitate(AwfullLookingBlockImmitationEvent event) {

    }

    @SubscribeEvent
    public void onItemCrafted(ItemCraftedEvent event) {
        if (event.crafting.getItem() == RandomItems.itemWarpingSchnitzel) {
            event.player.addStat(getAchievementByName("warping"), 1);
        } else if (event.crafting.getItem() == RandomItems.itemFlyingSchnitzel) {
            event.player.addStat(getAchievementByName("flying"), 1);
        } else if (event.crafting.getItem() == RandomItems.itemSchnitzelBackpack) {
            event.player.addStat(getAchievementByName("backpack"), 1);
        } else if (event.crafting.getItem() == Item.getItemFromBlock(RandomBlocks.blockAwfull)) {
            event.player.addStat(getAchievementByName("awfull"), 1);
        } else if (event.crafting.getItem() == RandomItems.itemSchnitzel) {
            if (event.crafting.getItemDamage() == 2) {
                event.player.addStat(getAchievementByName("spicy"), 1);
            }
        } else if (event.crafting.getItem() == RandomItems.itemFat) {
            event.player.addStat(getAchievementByName("fat"), 1);
        } else if (event.crafting.getItem() == Item.getItemFromBlock(RandomBlocks.blockFatInfuser)) {
            event.player.addStat(getAchievementByName("fatInfuser"), 1);
        } else if (event.crafting.getItem() == Item.getItemFromBlock(RandomBlocks.blockCookingPot)) {
            event.player.addStat(getAchievementByName("cookingPot"), 1);
        }
    }

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        event.player.addStat(RandomAchievements.getAchievementByName("joinWorld"), 1);
    }

    @SubscribeEvent
    public void onAchievement(AchievementEvent event) {
        if (RandomAchievements.isRandomAchievement(event.achievement)) {
            if (event.entityPlayer instanceof EntityPlayerMP) {
                EntityPlayerMP player = (EntityPlayerMP) event.entityPlayer;
                StatFileWriter stats = player.func_147099_x();
                Iterator<Achievement> iterator = RandomAchievements.getAchievementList().iterator();
                boolean flag = true;

                if (event.achievement == RandomAchievements.getAchievementByName("joinWorld")) {
                    if (!stats.hasAchievementUnlocked(event.achievement)) {
                        event.entityPlayer.inventory.addItemStackToInventory(new ItemStack(RandomItems.itemCactusGuide));
                    }
                }
				
				/*
				while (iterator.hasNext()) {
					Achievement achievement = iterator.next();
					if (!stats.hasAchievementUnlocked(achievement) && !RandomAchievements.isCompletionException(achievement)) {
						flag = false;
					}
				}

				if (flag && RandomAchievements.getAchievementByName(RandomAchievements.completionAchievement) != event.achievement) {
					ItemStack stack = new ItemStack(RandomBlocks.blockBenni);
					if (!event.entityPlayer.inventory.addItemStackToInventory(stack)) {
						event.entityPlayer.dropPlayerItemWithRandomChoice(stack, true);
					}
					player.addStat(getAchievementByName(RandomAchievements.completionAchievement), 1);
				}
				*/
            }
        }
    }

}
