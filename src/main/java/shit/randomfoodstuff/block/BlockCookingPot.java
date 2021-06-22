package shit.randomfoodstuff.block;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import shit.randomfoodstuff.RFMain;
import shit.randomfoodstuff.RandomItems;
import shit.randomfoodstuff.Reference;
import shit.randomfoodstuff.client.gui.GuiHandler;
import shit.randomfoodstuff.cooking.INotCookable;
import shit.randomfoodstuff.cooking.ISpecialFood;
import shit.randomfoodstuff.cooking.SoupRegistry;
import shit.randomfoodstuff.event.SoupCreationEvent;
import shit.randomfoodstuff.tileentity.TileEntityCookingPot;
import shit.randomfoodstuff.util.ColorHelper;
import shit.randomfoodstuff.util.ItemHelper;
import shit.randomfoodstuff.util.RandomSpecialRenderedBlock;

public class BlockCookingPot extends RandomSpecialRenderedBlock {

    public BlockCookingPot() {
        super(Material.iron);

        setBlockName("blockCookingPot");
        setBlockTextureName(Reference.TextureName + "BlockCookingPot");
        setBlockBounds(0, 0, 0, 1, 0.5F, 1);
        setHardness(3F);
        setResistance(5F);
        setStepSound(soundTypeMetal);
        setCreativeTab(RFMain.cTab);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par1, float par2, float par3, float par4) {
        TileEntityCookingPot entity = (TileEntityCookingPot) world.getTileEntity(x, y, z);
        boolean usageFlag = false;

        if (entity == null) {
            return false;
        }
        ItemStack playerItem = player.getCurrentEquippedItem();
        if (player.isSneaking()) {
            if (playerItem != null) {
                if (playerItem.getItem() == RandomItems.itemSoupLadle) {
                    if (!world.isRemote) {
                        player.addChatComponentMessage(new ChatComponentText(ColorHelper.getColorByName("DARK_PURPLE") + "Clearing Soup..."));
                    }
                    entity.reset();
                    usageFlag = true;
                }
            } else {
                FMLNetworkHandler.openGui(player, RFMain.modinstance, GuiHandler.GuiIDCookingPot, world, x, y, z);
                usageFlag = true;
            }
        } else {
            if (playerItem != null) {
                if (SoupRegistry.isItemSpice(playerItem)) {
                    if (entity.canAddSpice(playerItem)) {
                        if (!entity.isBlockOnFire()) {
                            if (!world.isRemote) {
                                player.addChatComponentMessage(new ChatComponentText(ColorHelper.getColorByName("DARK_PURPLE") + "In Order to Function the Pot must have a fire underneath"));
                            }
                        }
                        ItemStack spice = playerItem.copy();
                        if (playerItem.getItem().hasContainerItem()) {
                            if (!player.inventory.addItemStackToInventory(new ItemStack(playerItem.getItem().getContainerItem()))) {
                                player.dropPlayerItemWithRandomChoice(new ItemStack(playerItem.getItem().getContainerItem()), false);
                            }
                        }
                        playerItem.stackSize--;
                        spice.stackSize = 1;
                        entity.addSpice(spice);
                        usageFlag = true;
                    }
                } else if ((playerItem.getItem() instanceof ItemFood || playerItem.getItem() instanceof ISpecialFood) && !(playerItem.getItem() instanceof INotCookable)) {
                    if (entity.canAddIngredient()) {
                        if (!entity.isBlockOnFire()) {
                            if (!world.isRemote) {
                                player.addChatComponentMessage(new ChatComponentText(ColorHelper.getColorByName("DARK_PURPLE") + "In Order to Function the Pot must have a fire underneath"));
                            }
                        }
                        ItemStack ingred = playerItem.copy();
                        if (ItemHelper.getFoodLevelForItem(playerItem) > 0) {
                            if (playerItem.getItem().hasContainerItem()) {
                                if (!player.inventory.addItemStackToInventory(new ItemStack(playerItem.getItem().getContainerItem()))) {
                                    player.dropPlayerItemWithRandomChoice(new ItemStack(playerItem.getItem().getContainerItem()), false);
                                }
                            }
                            ingred.stackSize = 1;
                            playerItem.stackSize--;
                            usageFlag = true;
                            entity.addIngredient(ingred);
                        }
                    }
                } else if (playerItem.getItem() == Items.bowl) {
                    if (entity.canCreateSoupItem() && entity.isBlockOnFire()) {
                        ItemStack soup = entity.createSoupItem();
                        SoupCreationEvent.CreateSoupItemEvent.Pre eventPre = new SoupCreationEvent.CreateSoupItemEvent.Pre(world, x, y, z, soup, player);
                        MinecraftForge.EVENT_BUS.post(eventPre);
                        if (!eventPre.isCanceled()) {
                            soup = eventPre.soupItem;
                            entity.negateSoup();
                            if (soup != null) {
                                playerItem.stackSize--;
                                if (!player.inventory.addItemStackToInventory(soup)) {
                                    player.dropPlayerItemWithRandomChoice(soup, false);
                                }
                                usageFlag = true;
                            }
                        }
                        SoupCreationEvent.CreateSoupItemEvent.Post eventPost = new SoupCreationEvent.CreateSoupItemEvent.Post(world, x, y, z, soup, player);
                        MinecraftForge.EVENT_BUS.post(eventPost);
                    }
                } else if (playerItem.getItem() == RandomItems.itemSoupLadle) {
                    if (!world.isRemote) {
                        player.addChatComponentMessage(new ChatComponentText(ColorHelper.getColorByName("DARK_PURPLE") + "Sneak-Click to clear all contents"));
                    }
                }
            }
        }
        world.markBlockForUpdate(x, y, z);
        return usageFlag;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par1) {
        return new TileEntityCookingPot();
    }
}
