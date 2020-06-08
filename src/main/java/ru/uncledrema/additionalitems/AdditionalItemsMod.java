package ru.uncledrema.additionalitems;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.MinecraftForge;
@Mod(modid = "funcraftitems", name = "FunCraft Items Mod", version = "1.3")
//1.6.4. only: @NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class AdditionalItemsMod {
	
		@Instance("funcraftitems")
		public static AdditionalItemsMod instance;

		public static CreativeTabs funTab = new NewItemsTab("FunCraft");
		// ITEMS ETC.
		//public static Item itemThreeSlots;

		public static Item foodRation_RU;
		public static Item foodRation_US;
		public static Item healPackBest;
		public static Item healPackMedium;
		public static Item healPackSmall;
		public static Item activatedCoal;
		public static Item starCream;
		public static Item bandage;
		public static Item ethanol;
		public static Item adrenaline;
		public static Item painkiller;
		public static Item buff;
		public static Item badBuff;
		
		@EventHandler
		public void preInit(FMLPreInitializationEvent event) {
			
			foodRation_RU = new IRP().setUnlocalizedName("foodrationRU").setTextureName("funcraftitems:foodration_RU");
			foodRation_US = new IRP().setUnlocalizedName("foodrationUS").setTextureName("funcraftitems:foodration_US");
			healPackBest = new AptechkaBest().setUnlocalizedName("b_healpack").setTextureName("funcraftitems:b_healpack");
			healPackMedium = new AptechkaMedium().setUnlocalizedName("m_healpack").setTextureName("funcraftitems:m_healpack");
			healPackSmall = new AptechkaSmall().setUnlocalizedName("s_healpack").setTextureName("funcraftitems:s_healpack");
			activatedCoal = new ActivatedCoal().setUnlocalizedName("activatedcoal").setTextureName("funcraftitems:activatedcoal");
			starCream = new StarCream().setUnlocalizedName("starcream").setTextureName("funcraftitems:starcream");
			bandage = new Bandage().setUnlocalizedName("bandage").setTextureName("funcraftitems:bandage");
			ethanol = new Ethanol().setUnlocalizedName("ethanol").setTextureName("funcraftitems:ethanol");
			adrenaline = new Adrenaline().setUnlocalizedName("adrenaline").setTextureName("funcraftitems:adrenaline");
			painkiller = new Painkiller().setUnlocalizedName("painkiller").setTextureName("funcraftitems:painkiller");
			buff = new BuffItem(99999,true).setUnlocalizedName("buff").setTextureName("funcraftitems:buff");
			badBuff = new BuffItem(5,false).setUnlocalizedName("buff_bad").setTextureName("funcraftitems:buff");
			
			GameRegistry.registerItem(foodRation_RU, "foodration_RU");
			GameRegistry.registerItem(foodRation_US, "foodration_US");
			GameRegistry.registerItem(healPackBest, "b_healpack");
			GameRegistry.registerItem(healPackMedium, "m_healpack");
			GameRegistry.registerItem(healPackSmall, "s_healpack");
			GameRegistry.registerItem(activatedCoal, "activatedcoal");
			GameRegistry.registerItem(starCream, "starcream");
			GameRegistry.registerItem(bandage, "bandage");
			GameRegistry.registerItem(ethanol, "ethanol");
			GameRegistry.registerItem(adrenaline, "adrenaline");
			GameRegistry.registerItem(painkiller, "painkiller");
			GameRegistry.registerItem(buff, "buff");
			GameRegistry.registerItem(badBuff, "buff_bad");
			
			EventsForge.curativeItems.add(new ItemStack(activatedCoal));
			EventsForge.curativeItems.add(new ItemStack(healPackBest));
			EventsForge.curativeItems.add(new ItemStack(healPackMedium));
			EventsForge.curativeItems.add(new ItemStack(healPackSmall));
			EventsForge.curativeItems.add(new ItemStack(buff));
			EventsForge.curativeItems.add(new ItemStack(badBuff));
		}

		@EventHandler
		public void load(FMLInitializationEvent event) {
			
		}

		
		@EventHandler
		public void init(FMLInitializationEvent event) 
		{
			MinecraftForge.EVENT_BUS.register(new EventsForge());
		}
		
		@EventHandler
		public void postInit(FMLPostInitializationEvent event)
		{
		}
}
