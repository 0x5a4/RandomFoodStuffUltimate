package shit.randomfoodstuff.cooking;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public abstract class TemplateSoupEffect extends SoupEffect {
	
	public static class Potion extends TemplateSoupEffect {
		
		protected PotionEffect potionEffect;
		protected PotionEffect selfReactionEffect;
		protected String displayName;
		protected String selfReactionDisplayName;
		
		public Potion(String displayName, PotionEffect potionEffect) {
			this.potionEffect = potionEffect;
			this.displayName = displayName;
		}
		
		public Potion(String displayName, int potionId, int duration, int amp) {
			this(displayName, new PotionEffect(potionId, duration, amp));
		}
		
		public Potion(String displayName, int potionId, int duration) {
			this(displayName, new PotionEffect(potionId, duration, 0));
		}
		
		public Potion setSelfReactionEffect(PotionEffect effect) {
			this.selfReactionEffect = effect;
			return this;
		}
		
		public Potion setSelfReactionEffect(int potionId, int duration, int amp) {
			return setSelfReactionEffect(new PotionEffect(potionId, duration, amp));
		}
		
		public Potion setSelfReactionEffect(int potionId, int duration) {
			return setSelfReactionEffect(new PotionEffect(potionId, duration, 0));
		}
		
		public Potion setSelfReactionDisplayName(String displayName) {
			this.selfReactionDisplayName = displayName;
			return this;
		}
		
		@Override
		public void perform(World world, EntityPlayer player) {
			System.out.println("Performed Effect");
			player.addPotionEffect(this.potionEffect);
		}
		
		@Override
		public void reactWithSelf(World world, EntityPlayer player) {
			if (this.selfReactionEffect != null) {
				player.addPotionEffect(this.selfReactionEffect);
			} else {
				player.addPotionEffect(this.potionEffect);
			}
		}

		@Override
		public String getDisplayName() {
			return displayName;
		}
		
		@Override
		public String getSelfReactionDisplayName() {
			return this.selfReactionDisplayName;
		}
		
	}
	
	public static class Child extends TemplateSoupEffect {

		protected SoupEffect parent;
		
		public Child(SoupEffect parent) {
			this.parent = parent;
		}
		
		public Child(String parentName) {
			this.parent = SoupRegistry.getEffect(parentName);
		}
		
		@Override
		public void perform(World world, EntityPlayer player) {
			parent.perform(world, player);
		}
		
		@Override
		public void reactWithSelf(World world, EntityPlayer player) {
			parent.reactWithSelf(world, player);
		}
		
		@Override
		public boolean onReactedWith(String spice, String reagent, World world, EntityPlayer player) {
			return parent.onReactedWith(spice, reagent, world, player);
		}
		
		@Override
		public String getSelfReactionDisplayName() {
			return parent.getSelfReactionDisplayName();
		}

		@Override
		public String getDisplayName() {
			return parent.getDisplayName();
		}
		
	}
}
