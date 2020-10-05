package shit.randomfoodstuff;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import shit.randomfoodstuff.client.gui.GuiHandler;
import shit.randomfoodstuff.event.handler.AchievementHandler;
import shit.randomfoodstuff.event.handler.RFEventHandler;
import shit.randomfoodstuff.event.handler.ScytheHandler;
import shit.randomfoodstuff.net.SchnitzelPacketHandler;
import shit.randomfoodstuff.potion.RFPotion;

@Mod(modid = Reference.ModID, name = Reference.Name, version = Reference.Version, guiFactory = Reference.GuiFactory_Path)
public class RFMain {

	@SidedProxy(clientSide = Reference.ClientProxy_Path, serverSide = Reference.CommonProxy_Path)
	public static IProxy proxy;
	
	@Mod.Instance(value = Reference.ModID)
	public static RFMain modinstance;

	public static ToolMaterial awfullBreakingMaterial = EnumHelper.addToolMaterial("awfullBreakingMaterial", 1, 3, 200, 0, 0);

	public static CreativeTabs cTab = new CreativeTabs("tabrandomfoodstuffultimate") {

		@Override
		public Item getTabIconItem() {
			return RandomItems.itemCactusGuide;
		}
		
	};

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ConfigHandler.init(event.getSuggestedConfigurationFile());
		RandomBlocks.init();
		RandomBlocks.registerTileEntities();
		RandomBlocks.register();
		RandomItems.init();
		RandomItems.register();
		SchnitzelPacketHandler.init();
		MinecraftForge.EVENT_BUS.register(new RFEventHandler());
		MinecraftForge.EVENT_BUS.register(new AchievementHandler());
		MinecraftForge.EVENT_BUS.register(new ScytheHandler());
		FMLCommonHandler.instance().bus().register(new RFEventHandler());
		FMLCommonHandler.instance().bus().register(new AchievementHandler());
		MinecraftForge.addGrassSeed(new ItemStack(RandomItems.itemLauch), 10);
		MinecraftForge.addGrassSeed(new ItemStack(RandomItems.itemMateTee), 5);
		//Waila Registration
		FMLInterModComms.sendMessage(Reference.WailaModID, "register", Reference.WailaConfigPath);
		
		// Potion-Array Extension
		if (!(Potion.potionTypes.length == 256)) {
			Potion[] potionTypes;

			for (Field f : Potion.class.getDeclaredFields()) {
				f.setAccessible(true);

				try {
					if (f.getName().equals("potionTypes") || f.getName().equals("field_76425_a")) {
						Field modfield = Field.class.getDeclaredField("modifiers");
						modfield.setAccessible(true);
						modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);
						potionTypes = (Potion[]) f.get(null);
						final Potion[] newPotionTypes = new Potion[256];
						System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
						f.set(null, newPotionTypes);
					}
				} catch (Exception e) {
					System.err.println(e);
					e.printStackTrace();
				}
			}
		}
		RFPotion.initPotions();
	}

	@EventHandler
	public void Init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(modinstance, new GuiHandler());
		try {
			proxy.registerModelCollections();
			proxy.registerRenderers();
		} catch (Exception e) {
			System.out.println(e);
		}
		proxy.registerEvents();
		RandomCraftingHandler.init();
		RandomAchievements.init();
		RandomSoupEffects.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		RandomAchievements.register();
		proxy.registerGuide();
	}
	
	public static boolean isClientSide() {
		return FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT;
	}

}
