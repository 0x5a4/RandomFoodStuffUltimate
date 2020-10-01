package shit.randomfoodstuff;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import shit.randomfoodstuff.cooking.Reaction;
import shit.randomfoodstuff.cooking.SoupEffect;
import shit.randomfoodstuff.cooking.SoupRegistry;
import shit.randomfoodstuff.cooking.Spice;
import shit.randomfoodstuff.cooking.TemplateSoupEffect;
import shit.randomfoodstuff.potion.RFPotion;
import shit.randomfoodstuff.util.DurationHelper;

public class RandomSoupEffects {
	
	private static void registerSpices() {
		SoupRegistry.registerSpice(new Spice(new ItemStack(Blocks.tnt), "tnt"));
		SoupRegistry.registerSpice(new Spice(new ItemStack(Items.milk_bucket), "milk"));
	}
	
	private static void registerReactions() {
		SoupRegistry.registerReaction(new Reaction("pepperoni", "milk", "speed"),
									  new Reaction("tnt", "pepperoni", "firytnt")
									  );
	}
	
	private static void registerEffects() {
		SoupRegistry.registerSoupEffect("awake", new TemplateSoupEffect.Potion("Caffein Overdosed", RFPotion.potionCaffeine.id, DurationHelper.minutesToTicks(10)).setSelfReactionEffect(RFPotion.potionCaffeine.id, DurationHelper.minutesToTicks(15)));
		SoupRegistry.registerSoupEffect("pepperoni", new TemplateSoupEffect.Potion("SPICEY", RFPotion.potionPepperoni.id, DurationHelper.minutesToTicks(2)).setSelfReactionEffect(RFPotion.potionPepperoni.id, DurationHelper.minutesToTicks(10)).setSelfReactionDisplayName("ARE YOU CRAZY?!"));
		SoupRegistry.registerSoupEffect("saturation", new TemplateSoupEffect.Potion("*Burp*", 23, DurationHelper.secondsToTicks(30)).setSelfReactionEffect(23, DurationHelper.secondsToTicks(45)));
		SoupRegistry.registerSoupEffect("speed", new TemplateSoupEffect.Potion("Speedy Gonzales", Potion.moveSpeed.id, DurationHelper.minutesToTicks(5), 2));
		SoupRegistry.registerSoupEffect("lauch", new TemplateSoupEffect.Potion("Mathis?", RFPotion.potionLauch.id, DurationHelper.minutesToTicks(3)).setSelfReactionEffect(RFPotion.potionLauch.id, DurationHelper.minutesToTicks(5)));
		
		//Moldy
		SoupRegistry.registerSoupEffect("moldy", new SoupEffect() {

			@Override
			public void perform(World world, EntityPlayer player) {
				player.addPotionEffect(new PotionEffect(RFPotion.potionCaffeine.id, DurationHelper.minutesToTicks(3)));
			}

			@Override
			public String getDisplayName() {
				return "Mushrooms!!!";
			}
			
			@Override
			public boolean onReactedWith(String spice, String reagent, World world, EntityPlayer player) {
				player.addPotionEffect(new PotionEffect(Potion.confusion.id, DurationHelper.secondsToTicks(15)));
				return true;
			}
			
			@Override
			public void reactWithSelf(World world, EntityPlayer player) {
				player.addPotionEffect(new PotionEffect(RFPotion.potionCaffeine.id, DurationHelper.minutesToTicks(5)));
			}
			
		});
		
		//TNT
		SoupRegistry.registerSoupEffect("tnt", new SoupEffect() {

			@Override
			public void perform(World world, EntityPlayer player) {
				Explosion explosion = world.createExplosion(player, player.posX, player.posY, player.posZ, 5, true);
                player.attackEntityFrom(DamageSource.setExplosionSource(explosion), 10);
			}
			
			@Override
			public void reactWithSelf(World world, EntityPlayer player) {
				Explosion explosion = world.createExplosion(player, player.posX, player.posY, player.posZ, 10, true);
                player.attackEntityFrom(DamageSource.setExplosionSource(explosion), 15);
			}

			@Override
			public String getDisplayName() {
				return "Bumm Bumm";
			}
			
			@Override
			public String getSelfReactionDisplayName() {
				return "BOOOOOM!!!!!";
			}
			
		});
		
		//FiryTnt
		SoupRegistry.registerSoupEffect("firytnt", new TemplateSoupEffect.Child("tnt") {
			@Override
			public void perform(World world, EntityPlayer player) {
				Explosion explosion = world.newExplosion(player, player.posX, player.posY, player.posZ, 5, true, true);
                player.attackEntityFrom(DamageSource.setExplosionSource(explosion), 10);
			}
			
			@Override
			public void reactWithSelf(World world, EntityPlayer player) {
				Explosion explosion = world.newExplosion(player, player.posX, player.posY, player.posZ, 10, true, true);
                player.attackEntityFrom(DamageSource.setExplosionSource(explosion), 15);
			}
			
			@Override
			public String getDisplayName() {
				return "Thermonuclear";
			}
		});
		
		//Milk
		SoupRegistry.registerSoupEffect("milk", new SoupEffect() {

			@Override
			public void perform(World world, EntityPlayer player) {
				player.curePotionEffects(new ItemStack(Items.milk_bucket));
			}

			@Override
			public String getDisplayName() {
				return "Milk...";
			}
			
		});
	}
	
	public static void init() {
		registerEffects();
		registerSpices();
		registerReactions();
	}
	
	
	
}
