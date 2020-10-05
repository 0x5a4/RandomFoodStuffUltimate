package shit.randomfoodstuff.event.handler;

import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import shit.randomfoodstuff.RandomBlocks;
import shit.randomfoodstuff.block.BlockAwfull;
import shit.randomfoodstuff.cooking.SoupRegistry;
import shit.randomfoodstuff.entity.EntityItemWarpingSchnitzel;
import shit.randomfoodstuff.event.AwfullLookingBlockImmitationEvent;
import shit.randomfoodstuff.item.ItemBlockAwfull;
import shit.randomfoodstuff.item.ItemWarpingSchnitzel;
import shit.randomfoodstuff.potion.RFPotion;
import shit.randomfoodstuff.util.DurationHelper;

public class RFEventHandler {

	public static Map<String, Boolean> playersWithFlight = new HashMap();

	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event) {
		EntityLivingBase entity = event.entityLiving;
		// Potions

		if (entity.isPotionActive(RFPotion.potionPepperoni)) {
			if (entity.getActivePotionEffect(RFPotion.potionPepperoni).getDuration() == 0 || entity.isInWater() || entity.isPotionActive(Potion.fireResistance.id)) {
				entity.removePotionEffect(RFPotion.potionPepperoni.id);
				entity.extinguish();
			} else {
				entity.setFire(1);
				entity.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 20, 24));
				entity.moveEntityWithHeading(entity.moveStrafing, 10);
			}
		}

		if (entity.isPotionActive(RFPotion.potionMoldy) && !event.entityLiving.worldObj.isRemote) {
			if (entity.getActivePotionEffect(RFPotion.potionMoldy).getDuration() == 0) {
				entity.removePotionEffect(RFPotion.potionMoldy.id);
				entity.removePotionEffect(Potion.nightVision.id);
				entity.removePotionEffect(Potion.blindness.id);
				entity.removePotionEffect(Potion.weakness.id);
				entity.removePotionEffect(Potion.resistance.id);
				entity.removePotionEffect(Potion.damageBoost.id);
			} else {
				if (!entity.worldObj.isRemote) {
					if (entity.worldObj.getBlockLightValue((int) entity.posX, (int) entity.posY, (int) entity.posZ) <= 8) {
						entity.addPotionEffect(new PotionEffect(Potion.nightVision.id, DurationHelper.secondsToTicks(15)));
						entity.addPotionEffect(new PotionEffect(Potion.resistance.id, 21, 1));
						entity.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 21, 0));
						entity.removePotionEffect(Potion.blindness.id);
					} else {
						entity.addPotionEffect(new PotionEffect(Potion.blindness.id, 21, 5));
						entity.addPotionEffect(new PotionEffect(Potion.weakness.id, 21, 1));
						entity.removePotionEffect(Potion.nightVision.id);
					}
				}
			}
		}

		if (entity.isPotionActive(RFPotion.potionCaffeine)) {
			if (entity.getActivePotionEffect(RFPotion.potionCaffeine).getDuration() == 0) {
				entity.removePotionEffect(Potion.digSpeed.id);
				entity.removePotionEffect(Potion.moveSpeed.id);
			} else {
				entity.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 21, 1));
				entity.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 21, 2));
			}
		}

		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			if (player.isPotionActive(RFPotion.potionFlight)) {
				playersWithFlight.put(player.getGameProfile().getName(), true);
				player.capabilities.allowFlying = true;
				player.sendPlayerAbilities();
			} else {
				if (!playersWithFlight.containsKey(player.getGameProfile().getName())) {
					playersWithFlight.put(player.getGameProfile().getName(), false);
				}

				if (playersWithFlight.get(player.getGameProfile().getName())) {
					playersWithFlight.put(player.getGameProfile().getName(), false);
					if (!player.capabilities.isCreativeMode) {
						player.capabilities.allowFlying = false;
						player.capabilities.isFlying = false;
						player.sendPlayerAbilities();
					}
				}
			}
		}
		
		if (entity.isPotionActive(RFPotion.potionLauch)) {
			if (entity.getActivePotionEffect(RFPotion.potionLauch).getDuration() == 0) {
				entity.removePotionEffect(RFPotion.potionLauch.id);
			} else {
				if (entity.onGround) {
					entity.moveEntityWithHeading(entity.moveStrafing, entity.moveForward * 100);
					float f = 2F;
					if (entity.getHealth() > f)
					entity.setHealth(f);
				}
			}
		}
		
		if (entity.isPotionActive(RFPotion.potionFloating)) {
			if (entity.getActivePotionEffect(RFPotion.potionFloating).getDuration() == 0) {
				entity.removePotionEffect(RFPotion.potionFloating.id);
			} else {
				entity.motionY = 0;
			}
		}
	}
	
	

	@SubscribeEvent
	public void onEntityStruckByLighting(EntityStruckByLightningEvent event) {
		if (event.entity instanceof EntityItemWarpingSchnitzel) {
			if (event.isCancelable()) {
				event.setCanceled(true);
			}
			ItemStack stack = ((EntityItem) event.entity).getEntityItem().copy();
			ItemWarpingSchnitzel.setPowered(stack, true);
			((EntityItem) event.entity).setEntityItemStack(stack);
		}
	}

	@SubscribeEvent
	public void onEntityJump(LivingJumpEvent event) {
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			if (player.isPotionActive(RFPotion.potionLauch)) {
				player.motionY += 0.4F;
			}
		}
	}

	@SubscribeEvent
	public void onRenderItemTooltip(ItemTooltipEvent event) {
		if (SoupRegistry.isItemSpice(event.itemStack)) {
			event.toolTip.add("Soup Effect:");
			event.toolTip.add(SoupRegistry.getEffectDisplayName(event.itemStack));
		}
	}

	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event) {
		World world = event.world;
		EntityPlayer player = event.entityPlayer;
		int x = event.x;
		int y = event.y;
		int z = event.z;

		if (event.action == Action.RIGHT_CLICK_BLOCK) {
			if (world.getBlock(x, y, z) == Blocks.bed) {
				if (player.isPotionActive(RFPotion.potionCaffeine.id)) {
					if (event.isCancelable()) {
						event.setCanceled(true);
					}
				}
			}

			if (player.isSneaking()) {
				if (player.getCurrentEquippedItem() != null) {
					if (player.getCurrentEquippedItem().getItem() == Item.getItemFromBlock(RandomBlocks.blockAwfull)) {
						if (BlockAwfull.canImmitate(world, x, y, z)) {
							int fakeBlockID = Block.getIdFromBlock(world.getBlock(x, y, z));
							int fakeBlockMeta = world.getBlockMetadata(x, y, z);
							AwfullLookingBlockImmitationEvent immitationEvent = new AwfullLookingBlockImmitationEvent(world, x, y, z, fakeBlockID, fakeBlockMeta);
							MinecraftForge.EVENT_BUS.post(immitationEvent);
							if (!immitationEvent.isCanceled()) {
								ItemBlockAwfull.setFakeBlockID(player.getCurrentEquippedItem(), immitationEvent.blockID);
								ItemBlockAwfull.setFakeBlockMeta(player.getCurrentEquippedItem(), immitationEvent.blockMeta);
							}
						}
					}
				}
			}
		}
	}

}
