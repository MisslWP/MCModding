package com.creativemd.ingameconfigmanager.api.common.container.controls;

import com.creativemd.creativecore.common.container.slot.SlotControlNoSync;
import com.creativemd.creativecore.common.container.slot.SlotPreview;
import com.creativemd.creativecore.common.gui.controls.GuiComboBox;
import com.creativemd.creativecore.common.gui.controls.GuiComboBoxExtension;
import com.creativemd.creativecore.common.gui.controls.GuiLabel;
import com.creativemd.creativecore.common.gui.controls.container.GuiSlotControl;
import com.creativemd.creativecore.common.gui.event.ControlClickEvent;
import com.creativemd.ingameconfigmanager.api.common.container.controls.GuiInvSelector;
import com.n247s.api.eventapi.eventsystem.CustomEventSubscribe;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GuiItemStackSelector extends GuiComboBoxExtension {

   public ArrayList stacks;
   public ArrayList inv;
   public String search;
   public boolean onlyBlocks;


   public GuiItemStackSelector(String name, EntityPlayer player, int x, int y, int width, int height, GuiComboBox comboBox, boolean onlyBlocks, String search) {
      super(name, player, comboBox, x, y, width, height, new ArrayList());
      this.search = search;
      this.onlyBlocks = onlyBlocks;
      this.stacks = new ArrayList();
      this.inv = new ArrayList();

      for(int iterator = 0; iterator < player.inventory.mainInventory.length; ++iterator) {
         if(player.inventory.mainInventory[iterator] != null) {
            this.inv.add(player.inventory.mainInventory[iterator]);
         }
      }

      Iterator var12 = Item.itemRegistry.iterator();

      while(var12.hasNext()) {
         Item item = (Item)var12.next();
         if(item != null && item.getCreativeTab() != null) {
            item.getSubItems(item, (CreativeTabs)null, this.stacks);
         }
      }

      this.refreshControls();
   }

   public static String getItemName(ItemStack stack) {
      String itemName = "";

      try {
         itemName = stack.getDisplayName();
      } catch (Exception var3) {
         if(!(Block.getBlockFromItem(stack.getItem()) instanceof BlockAir)) {
            itemName = Block.blockRegistry.getNameForObject(Block.getBlockFromItem(stack.getItem()));
         } else {
            itemName = Item.itemRegistry.getNameForObject(stack.getItem());
         }
      }

      return itemName;
   }

   public static boolean shouldShowItem(boolean onlyBlocks, String search, ItemStack stack) {
      return onlyBlocks && Block.getBlockFromItem(stack.getItem()) instanceof BlockAir?false:(search.equals("")?true:getItemName(stack).toLowerCase().contains(search));
   }

   public void refreshControls() {
      if(this.stacks != null) {
         this.gui.controls.clear();
         byte height = 0;
         GuiLabel label = new GuiLabel("Inventory", 3, height);
         label.width = this.width - 20;
         label.height = 14;
         this.addControl(label);
         int var8 = height + label.height;
         int SlotsPerRow = this.width / 18;
         int count = 0;

         int i;
         InventoryBasic basic;
         int row;
         for(i = 0; i < this.inv.size(); ++i) {
            if(shouldShowItem(this.onlyBlocks, this.search, (ItemStack)this.inv.get(i))) {
               basic = new InventoryBasic("", false, 1);
               basic.setInventorySlotContents(0, (ItemStack)this.inv.get(i));
               row = count / SlotsPerRow;
               this.addControl(new SlotControlNoSync(new SlotPreview(basic, 0, (count - row * SlotsPerRow) * 18, var8 + row * 18)));
               ++count;
            }
         }

         var8 = (int)((double)var8 + Math.floor((double)(count / SlotsPerRow + 1)) * 18.0D);
         label = new GuiLabel("Items", 3, var8);
         label.width = this.width - 20;
         label.height = 14;
         this.addControl(label);
         var8 += label.height;
         count = 0;

         for(i = 0; i < this.stacks.size(); ++i) {
            if(shouldShowItem(this.onlyBlocks, this.search, (ItemStack)this.stacks.get(i))) {
               basic = new InventoryBasic("", false, 1);
               basic.setInventorySlotContents(0, (ItemStack)this.stacks.get(i));
               row = count / SlotsPerRow;
               this.addControl(new SlotControlNoSync(new SlotPreview(basic, 0, (count - row * SlotsPerRow) * 18, var8 + row * 18)));
               ++count;
            }
         }
      }

   }

   @CustomEventSubscribe
   public void onLabelClicked(ControlClickEvent event) {
      if(event.source instanceof GuiSlotControl) {
         ((GuiInvSelector)this.comboBox).addAndSelectStack(((GuiSlotControl)event.source).slot.slot.getStack().copy());
         this.comboBox.closeBox();
      }

   }

   public boolean mouseScrolled(int posX, int posY, int scrolled) {
      this.scrolled -= scrolled * 30;
      this.onScrolled();
      return true;
   }
}
