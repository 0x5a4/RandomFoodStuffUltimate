package shit.randomfoodstuff.event;

import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import shit.randomfoodstuff.tileentity.TileEntityCookingPot;

public class SoupCreationEvent extends Event {

    public World worldobj;
    public int x;
    public int y;
    public int z;

    public SoupCreationEvent(World world, int x, int y, int z) {
        this.worldobj = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Fired when the Soup is initialized aka when the player is able to get a Soup Item
     * (the {@link TileEntityCookingPot#soupRemaining} is set to {@link TileEntityCookingPot#soupVal} * 2)
     */
    public static class CreateSoupEvent extends SoupCreationEvent {

        public CreateSoupEvent(World world, int x, int y, int z) {
            super(world, x, y, z);
        }

    }

    public static class CreateSoupItemEvent extends SoupCreationEvent {

        public ItemStack soupItem;
        public EntityPlayer player;

        public CreateSoupItemEvent(World world, int x, int y, int z, ItemStack soupItem, EntityPlayer player) {
            super(world, x, y, z);

            this.player = player;
            this.soupItem = soupItem;
        }

        /**
         * Fired when the Soup Item has been created but has not been added to the inventory of the player
         * If canceled no soup will be created and no bowl will be consumed and the {@link TileEntityCookingPot#soupRemaining}
         * will not be negated
         */
        @Cancelable
        public static class Pre extends CreateSoupItemEvent {

            public Pre(World world, int x, int y, int z, ItemStack soupItem, EntityPlayer player) {
                super(world, x, y, z, soupItem, player);

            }


        }

        /**
         * Fired when the Soup Item has been created and has been added to the player inventory
         * Changing any of the Values wont have any Effect
         */
        public static class Post extends CreateSoupItemEvent {

            public Post(World world, int x, int y, int z, ItemStack soupItem, EntityPlayer player) {
                super(world, x, y, z, soupItem, player);

            }

        }
    }
}
