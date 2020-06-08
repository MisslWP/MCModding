package com.creativemd.ingameconfigmanager.api.common.container.controls;

import com.creativemd.creativecore.client.avatar.AvatarItemStack;
import com.creativemd.creativecore.client.rendering.RenderHelper2D;
import com.creativemd.creativecore.common.gui.controls.GuiComboBox;
import com.creativemd.ingameconfigmanager.api.common.container.controls.GuiItemStackSelector;
import java.util.ArrayList;
import java.util.Iterator;
import javax.vecmath.Vector4d;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class GuiInvSelector extends GuiComboBox {

   public ArrayList stacks;
   public boolean onlyBlocks;
   public String search;


   public GuiInvSelector(String name, int x, int y, int width, EntityPlayer player, boolean onlyBlocks, String search) {
      super(name, x, y, width, new ArrayList());
      this.stacks = new ArrayList();
      this.search = search;
      this.onlyBlocks = onlyBlocks;
      this.updateItems(player);
   }

   public GuiInvSelector(String name, int x, int y, int width, EntityPlayer player, boolean onlyBlocks) {
      this(name, x, y, width, player, onlyBlocks, "");
   }

   public void updateItems(EntityPlayer player) {
      boolean shouldSearch = this.search.equals("");
      ArrayList newStacks = new ArrayList();

      for(int iterator = 0; iterator < player.inventory.mainInventory.length; ++iterator) {
         if(player.inventory.mainInventory[iterator] != null) {
            newStacks.add(player.inventory.mainInventory[iterator].copy());
         }
      }

      Iterator var6 = Item.itemRegistry.iterator();

      while(var6.hasNext()) {
         Item i = (Item)var6.next();
         if(i != null && i.getCreativeTab() != null) {
            i.getSubItems(i, (CreativeTabs)null, newStacks);
         }
      }

      this.stacks.clear();
      this.lines.clear();

      for(int var7 = 0; var7 < newStacks.size(); ++var7) {
         if(GuiItemStackSelector.shouldShowItem(this.onlyBlocks, this.search, (ItemStack)newStacks.get(var7))) {
            this.stacks.add(newStacks.get(var7));
            this.lines.add(GuiItemStackSelector.getItemName((ItemStack)newStacks.get(var7)));
         }
      }

      if(this.lines.size() > 0) {
         this.index = 0;
         this.caption = (String)this.lines.get(0);
      } else {
         this.caption = "";
         this.index = -1;
      }

   }

   public void addAndSelectStack(ItemStack stack) {
      try {
         this.lines.add(stack.getDisplayName());
      } catch (Exception var3) {
         this.lines.add(Item.itemRegistry.getNameForObject(stack.getItem()));
      }

      this.stacks.add(stack.copy());
      this.caption = (String)this.lines.get(this.lines.size() - 1);
      this.index = this.lines.size() - 1;
   }

   public void drawControl(FontRenderer renderer) {
      Vector4d black = new Vector4d(0.0D, 0.0D, 0.0D, 255.0D);
      RenderHelper2D.drawGradientRect(0, 0, this.width, this.height, black, black);
      Vector4d color = new Vector4d(60.0D, 60.0D, 60.0D, 255.0D);
      RenderHelper2D.drawGradientRect(1, 1, this.width - 1, this.height - 1, color, color);
      if(this.index != -1 && this.stacks.size() > this.index && this.stacks.get(this.index) != null) {
         AvatarItemStack avatar = new AvatarItemStack((ItemStack)this.stacks.get(this.index));
         GL11.glTranslated(1.0D, 1.0D, 0.0D);
         avatar.handleRendering(mc, renderer, 18, 18);
      }

      renderer.drawString(this.caption, 24, this.height / 2 - renderer.FONT_HEIGHT / 2, 14737632);
   }

   public void openBox() {
      this.extension = new GuiItemStackSelector(this.name + "extension", this.parent.container.player, this.posX, this.posY + this.height, this.width, 200, this, this.onlyBlocks, this.search);
      this.parent.controls.add(this.extension);
      this.extension.parent = this.parent;
      this.extension.moveControlToTop();
      this.extension.init();
      this.parent.refreshControls();
      this.extension.rotation = this.rotation;
   }

   public ItemStack getStack() {
      return this.index != -1?(ItemStack)this.stacks.get(this.index):null;
   }
}
