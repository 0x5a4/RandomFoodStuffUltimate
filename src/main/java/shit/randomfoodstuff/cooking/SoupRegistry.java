package shit.randomfoodstuff.cooking;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Arthur
 * Managment Class for the Cooking System
 * Dictionary:
 * -Effect -> Performed when a Soup is eaten
 * -Reaction ->Process of 1 Effect getting influenced by another to perform something dictated by the first effect
 * -Spice -> The First item in the Reaction. It determines the first effect and dictates the process
 * -Reagent -> The Second item in the Recation. It is used by the Spice to determine the new effect but cant controll the result(Has a "Veto", but this sould be avoided because it leads to inconsistency)
 */
public class SoupRegistry {

    /**
     * Map holding every known effect
     */
    private static HashMap<String, SoupEffect> effectList = new HashMap<String, SoupEffect>();
    /**
     * List holding every known Reaction
     */
    private static ArrayList<Reaction> reactionList = new ArrayList<Reaction>();
    /**
     * List of Spices. Normally you would just implement {@link ISpice} but this isnt possible for items you didnt make yourself(e.g. TNT)
     */
    private static ArrayList<Spice> spiceList = new ArrayList<Spice>();

    public static boolean isItemSpice(ItemStack stack) {
        if (stack == null) {
            return false;
        }

        if (stack.getItem() instanceof ISpice) {
            return getEffectFromStack(stack) != null;
        }

        for (Spice spice : spiceList) {
            if (spice.represents(stack)) {
                return true;
            }
        }

        return false;
    }

    public static String getEffectID(SoupEffect effect) {
        for (String s : effectList.keySet()) {
            if (effectList.get(s) == effect) {
                return s;
            }
        }

        return null;
    }

    public static String getEffectDisplayName(String id) {
        if (effectList.containsKey(id)) {
            return effectList.get(id).getDisplayName();
        }
        return null;
    }

    public static String getEffectDisplayName(ItemStack stack) {
        return getEffectDisplayName(getEffectFromStack(stack));
    }

    public static SoupEffect getEffect(String name) {
        return effectList.getOrDefault(name, null);
    }

    public static String getEffectFromStack(ItemStack stack) {
        if (stack == null) {
            return null;
        }

        if (stack.getItem() instanceof ISpice) {
            return ((ISpice) stack.getItem()).getEffectName(stack);
        }

        for (Spice spice : spiceList) {
            if (spice.represents(stack)) {
                return spice.getEffectName(stack);
            }
        }

        return null;
    }

    public static boolean canReact(String spice, String reagent) {
        for (Reaction reaction : reactionList) {
            if (reaction.canTrigger(spice, reagent)) {
                return true;
            }
        }

        return false;
    }

    public static Reaction getReaction(String spice, String reagent) {
        for (Reaction reaction : reactionList) {
            if (reaction.canTrigger(spice, reagent)) {
                return reaction;
            }
        }

        return null;
    }

    public static boolean canReact(ItemStack spice, ItemStack reagent) {
        return canReact(getEffectFromStack(spice), getEffectFromStack(reagent));
    }

    public static Reaction getReaction(ItemStack spice, ItemStack reagent) {
        return getReaction(getEffectFromStack(spice), getEffectFromStack(reagent));
    }

    /**
     * @param spice   The Spice
     * @param reagent The Reagent. Pass null if there is none Performs the Soup Effect based on spice and reagent
     */
    public static void performEffect(String spice, String reagent, EntityPlayer player, World world) {
        System.out.println("Test");
        SoupEffect spiceEffect = getEffect(spice);

        if (reagent != null) {
            if (spice.equals(reagent)) {
                spiceEffect.reactWithSelf(world, player);
                return;
            }

            SoupEffect reagentEffect = getEffect(reagent);

            Reaction reaction = getReaction(spice, reagent);
            if (reaction != null) {
                SoupRegistry.getEffect(reaction.getResultEffect()).perform(world, player);
                return;
            }

            spiceEffect.perform(world, player);
        } else {
            spiceEffect.perform(world, player);
        }

    }

    public static void registerReaction(Reaction... reactions) {
        for (Reaction r : reactions) {
            if (r != null) {
                reactionList.add(r);
            }
        }
    }

    public static void registerSoupEffect(String id, SoupEffect effect) {
        if (effect != null) {
            if (!effectList.containsKey(id)) {
                effectList.put(id, effect);
            } else {
                System.err.println("SoupEffect ID already taken: " + id);
            }
        }
    }

    public static void registerSpice(ItemStack stack, String effect) {
        registerSpice(new Spice(stack, effect));
    }

    public static void registerSpice(Spice spice) {
        if (spice != null) {
            spiceList.add(spice);
        }
    }

}
