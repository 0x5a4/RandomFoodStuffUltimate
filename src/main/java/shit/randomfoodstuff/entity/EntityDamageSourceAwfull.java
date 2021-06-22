package shit.randomfoodstuff.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.IChatComponent;

public class EntityDamageSourceAwfull extends EntityDamageSource {

    public EntityDamageSourceAwfull(Entity entity) {
        super("awfull", entity);

        setDamageBypassesArmor();
    }

    @Override
    public IChatComponent func_151519_b(EntityLivingBase entityLiving) {
        return new ChatComponentText(entityLiving.getCommandSenderName() + " got trolled hard");
    }

}
