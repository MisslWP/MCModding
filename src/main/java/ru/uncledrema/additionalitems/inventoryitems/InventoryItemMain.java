package ru.uncledrema.additionalitems.inventoryitems;


import cpw.mods.fml.common.Mod;	
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = "inventoryitemmod", name = "Podsumok Mod", version = "2.1")
//1.6.4. only: @NetworkMod(clientSideRequired=true, serverSideRequired=false)
public final class InventoryItemMain
{
	@Instance("inventoryitemmod")
	public static InventoryItemMain instance;

	@SidedProxy(clientSide = "ru.uncledrema.additionalitems.inventoryitems.ClientProxy", serverSide = "ru.uncledrema.additionalitems.inventoryitems.CommonProxy")
	public static CommonProxy proxy;

	/** This is used to keep track of GUIs that we make*/
	private static int modGuiIndex = 0;

	/** Set our custom inventory Gui index to the next available Gui index */
	public static final int GUI_ITEM_INV_THREE = modGuiIndex++;
	public static final int GUI_ITEM_INV_TWO = modGuiIndex++;
	public static final int GUI_ITEM_INV_FIVE = modGuiIndex++;

	// ITEMS ETC.
	public static Item itemThreeSlots;
	public static Item itemTwoSlots;
	public static Item itemFiveSlots;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
	   
		itemThreeSlots = (new ItemStoreThree()).setUnlocalizedName("BagThreeSlots");
		itemTwoSlots = (new ItemStoreTwo()).setUnlocalizedName("BagTwoSlots");
		itemFiveSlots = (new ItemStoreFive()).setUnlocalizedName("BagFiveSlots");
	     
	    GameRegistry.registerItem(itemThreeSlots, itemThreeSlots.getUnlocalizedName());
	    GameRegistry.registerItem(itemTwoSlots, itemTwoSlots.getUnlocalizedName());
	    GameRegistry.registerItem(itemFiveSlots, itemFiveSlots.getUnlocalizedName());
	     
	    EventsForge.blocked_items.add(itemThreeSlots);
	    EventsForge.blocked_items.add(itemTwoSlots);
	    EventsForge.blocked_items.add(itemFiveSlots);
	}

	@EventHandler
	public void load(FMLInitializationEvent event)
	{
		// no renderers or entities to register, but whatever
		proxy.registerRenderers();
		// register CommonProxy as our GuiHandler
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new CommonProxy());
	}

	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new EventsForge());
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
	}
}
