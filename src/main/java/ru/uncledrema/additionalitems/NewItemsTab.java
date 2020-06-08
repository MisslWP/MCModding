package ru.uncledrema.additionalitems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class NewItemsTab extends CreativeTabs {

	public NewItemsTab(String lable) 
	{
		super(lable);
	}
	
	@Override
	public Item getTabIconItem() {
		return AdditionalItemsMod.healPackBest;
	}
	
	@SideOnly(Side.CLIENT)
    public String getTranslatedTabLabel()
    {
        return "FunCraft Items";
    }

}
