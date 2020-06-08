package ru.uncledrema.additionalitems;

import java.util.ArrayList;
import java.util.Collection;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import ru.uncledrema.additionalitems.inventoryitems.ItemStoreThree;

public class EventsForge 
{
	
	public static ArrayList<ItemStack> curativeItems = new ArrayList();
	
	
	@SubscribeEvent
	public void effectCure(LivingUpdateEvent event) 
	{
		if(event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entity;
			Collection<PotionEffect> potionEffects = player.getActivePotionEffects();
			for (PotionEffect effect : potionEffects)
			{
				effect.setCurativeItems(curativeItems);
			}
		}
	}
	
	@SubscribeEvent
	public void suicide(Clone event)
	{

		EntityPlayer newplayer = event.entityPlayer;
		
		newplayer.setHealth(6F);
	}

	
	
}
