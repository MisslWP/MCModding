package com.creativemd.ingameconfigmanager.api.client.gui;

import com.creativemd.creativecore.client.rendering.RenderHelper2D;
import com.creativemd.creativecore.common.gui.SubGui;
import com.creativemd.creativecore.common.gui.controls.GuiButton;
import com.creativemd.creativecore.common.gui.controls.GuiInvSelector;
import com.creativemd.creativecore.common.gui.controls.GuiLabel;
import com.creativemd.creativecore.common.gui.controls.GuiTextfield;
import com.creativemd.creativecore.common.gui.event.ControlChangedEvent;
import com.creativemd.creativecore.common.gui.event.ControlClickEvent;
import com.n247s.api.eventapi.eventsystem.CustomEventSubscribe;
import javax.vecmath.Vector4d;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class SubGuiItemDialog extends SubGui {

   public ItemStack stack;


   public SubGuiItemDialog() {
      super(150, 105);
   }

   public void createControls() {
      GuiInvSelector selector = new GuiInvSelector("inv", 5, 5, 140, this.container.player, false);
      this.controls.add(selector);
      if(this.stack != null) {
         selector.addAndSelectStack(this.stack);
      }

      this.controls.add(new GuiTextfield("search", "", 5, 30, 140, 20));
      this.controls.add(new GuiLabel("StackSize:", 5, 55));
      GuiTextfield field = (new GuiTextfield("stacksize", "1", 110, 55, 30, 20)).setNumbersOnly();
      if(this.stack != null) {
         field.text = "" + this.stack.stackSize;
      }

      this.controls.add(field);
      this.controls.add(new GuiButton("Cancel", 5, 80, 60, 20));
      this.controls.add(new GuiButton("Save", 85, 80, 60, 20));
   }

   public void drawOverlay(FontRenderer fontRenderer) {}

   @CustomEventSubscribe
   public void onChanged(ControlChangedEvent event) {
      if(event.source.is("search")) {
         GuiInvSelector inv = (GuiInvSelector)this.getControl("inv");
         inv.search = ((GuiTextfield)event.source).text.toLowerCase();
         inv.updateItems(this.container.player);
         inv.closeBox();
      }

   }

   @CustomEventSubscribe
   public void onClicked(ControlClickEvent event) {
      if(event.source.is("Save")) {
         this.stack = ((GuiInvSelector)this.getControl("inv")).getStack();
         boolean nbt = true;

         int nbt1;
         try {
            nbt1 = Integer.parseInt(((GuiTextfield)this.getControl("stacksize")).text);
         } catch (Exception var4) {
            nbt1 = 1;
         }

         if(this.stack != null) {
            this.stack = this.stack.copy();
            this.stack.stackSize = nbt1;
         }

         this.closeLayer(new NBTTagCompound());
      }

      if(event.source.is("Cancel")) {
         NBTTagCompound nbt2 = new NBTTagCompound();
         nbt2.setBoolean("canceled", true);
         this.closeLayer(nbt2);
      }

   }

   public void drawBackground() {
      int k = (this.width - this.width) / 2;
      int l = (this.height - this.height) / 2;
      Vector4d color = new Vector4d(0.0D, 0.0D, 0.0D, 255.0D);
      RenderHelper2D.drawGradientRect(k, l, k + this.width, l + this.height, color, color);
      color = new Vector4d(120.0D, 120.0D, 120.0D, 255.0D);
      RenderHelper2D.drawGradientRect(k + 2, l + 2, k + this.width - 2, l + this.height - 2, color, color);
   }
}
