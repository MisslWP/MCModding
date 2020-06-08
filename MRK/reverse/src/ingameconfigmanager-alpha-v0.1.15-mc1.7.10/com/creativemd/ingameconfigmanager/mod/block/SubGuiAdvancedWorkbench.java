package com.creativemd.ingameconfigmanager.mod.block;

import com.creativemd.creativecore.common.gui.SubGui;
import com.creativemd.creativecore.common.gui.controls.GuiButton;
import com.creativemd.creativecore.common.gui.controls.GuiControl;
import com.creativemd.creativecore.common.gui.controls.GuiProgressBar;
import com.creativemd.creativecore.common.gui.controls.container.GuiSlotControl;
import com.creativemd.creativecore.common.gui.event.ControlClickEvent;
import com.creativemd.ingameconfigmanager.mod.block.AdvancedGridRecipe;
import com.creativemd.ingameconfigmanager.mod.block.BlockAdvancedWorkbench;
import com.creativemd.ingameconfigmanager.mod.block.SubContainerAdvancedWorkbench;
import com.n247s.api.eventapi.eventsystem.CustomEventSubscribe;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.nbt.NBTTagCompound;

public class SubGuiAdvancedWorkbench extends SubGui {

   public boolean crafting = false;
   public static long lastTick;


   public SubGuiAdvancedWorkbench() {
      super(176, 200);
   }

   public void createControls() {
      this.controls.add(new GuiButton("Craft!", 130, 85, 40, 20));
      this.controls.add(new GuiProgressBar("progress", 132, 30, 36, 10));
   }

   public void onTick() {
      if(this.crafting) {
         if(lastTick == 0L) {
            lastTick = System.currentTimeMillis();
         }

         GuiProgressBar bar = (GuiProgressBar)this.getControl("progress");
         double timeLeft = (double)(System.currentTimeMillis() - lastTick);
         bar.pos += timeLeft;
         if(bar.pos >= bar.max) {
            this.sendPacketToServer(0, new NBTTagCompound());
            bar.pos = bar.max;
            this.crafting = false;
            this.getControl("Craft!").enabled = true;

            for(int i = 0; i < this.controls.size(); ++i) {
               if(this.controls.get(i) instanceof GuiSlotControl) {
                  ((GuiControl)this.controls.get(i)).enabled = true;
               }
            }
         }
      }

      lastTick = System.currentTimeMillis();
   }

   @CustomEventSubscribe
   public void onClicked(ControlClickEvent event) {
      if(event.source.is("Craft!")) {
         AdvancedGridRecipe recipe = null;

         int bar;
         for(bar = 0; bar < BlockAdvancedWorkbench.recipes.size(); ++bar) {
            if(((AdvancedGridRecipe)BlockAdvancedWorkbench.recipes.get(bar)).isValidRecipe(((SubContainerAdvancedWorkbench)this.container).crafting, 6, 6)) {
               recipe = (AdvancedGridRecipe)BlockAdvancedWorkbench.recipes.get(bar);
               break;
            }
         }

         if(recipe != null) {
            for(bar = 0; bar < this.controls.size(); ++bar) {
               if(this.controls.get(bar) instanceof GuiSlotControl) {
                  ((GuiControl)this.controls.get(bar)).enabled = false;
               }
            }

            GuiProgressBar var4 = (GuiProgressBar)this.getControl("progress");
            var4.pos = 0.0D;
            var4.max = (double)recipe.duration;
            event.source.enabled = false;
            this.crafting = true;
         }
      }

   }

   public void drawOverlay(FontRenderer fontRenderer) {}
}
