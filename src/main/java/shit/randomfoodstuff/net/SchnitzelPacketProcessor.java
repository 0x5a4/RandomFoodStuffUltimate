package shit.randomfoodstuff.net;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import shit.randomfoodstuff.item.ItemSchnitzelBackpack;

public class SchnitzelPacketProcessor implements IMessage, IMessageHandler<SchnitzelPacketProcessor, IMessage> {

    private int slot;
    private int mode;
    private int playerID;

    public SchnitzelPacketProcessor() {
    }

    public SchnitzelPacketProcessor(int entityID, int slot, int mode) {
        this.slot = slot;
        this.mode = mode;
        this.playerID = entityID;
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        buffer.writeInt(slot);
        buffer.writeInt(mode);
        buffer.writeInt(playerID);
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        slot = buffer.readInt();
        mode = buffer.readInt();
        playerID = buffer.readInt();
    }

    @Override
    public IMessage onMessage(SchnitzelPacketProcessor message, MessageContext ctx) {
        ItemStack itemStack = null;

        if (message.slot > -1 && message.slot < 9) {
            itemStack = ctx.getServerHandler().playerEntity.inventory.getStackInSlot(message.slot);
        }

        if (itemStack != null) {
            ItemSchnitzelBackpack.cycleSchnitzel(true, message.playerID, itemStack, message.mode);
        }

        return null;
    }

}
