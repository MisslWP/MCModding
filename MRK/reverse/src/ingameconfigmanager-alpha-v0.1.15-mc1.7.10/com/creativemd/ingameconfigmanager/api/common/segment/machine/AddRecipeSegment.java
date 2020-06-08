package com.creativemd.ingameconfigmanager.api.common.segment.machine;

import com.creativemd.creativecore.common.gui.SubGui;
import com.creativemd.creativecore.common.utils.stack.StackInfo;
import com.creativemd.creativecore.common.utils.string.StringUtils;
import com.creativemd.ingameconfigmanager.api.common.container.controls.GuiButtonRemoveRecipe;
import com.creativemd.ingameconfigmanager.api.common.machine.RecipeMachine;
import com.creativemd.ingameconfigmanager.api.common.segment.ConfigSegment;
import com.creativemd.ingameconfigmanager.api.common.segment.machine.InfoGridSegment;
import com.creativemd.ingameconfigmanager.api.common.segment.machine.OutputSegment;
import com.creativemd.ingameconfigmanager.api.common.segment.machine.RecipeSegment;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class AddRecipeSegment extends RecipeSegment {

   public int width = 0;
   public int height = 0;


   public AddRecipeSegment(String id, RecipeMachine machine, Object recipe) {
      super(id, recipe, machine, recipe);
   }

   public void addSubSegments() {
      StackInfo[] items = null;
      if(super.value != null) {
         items = new StackInfo[super.machine.getWidth() * super.machine.getHeight()];
         super.machine.fillGridInfo(items, super.recipe);
      }

      this.addSubSegment(new InfoGridSegment("grid", items, this));
      this.addSubSegment((new OutputSegment("output", super.value != null?super.machine.getOutput(super.value):null, super.machine)).setOffset(114, super.machine.getHeight() * 18 / 2 - super.machine.getOutputCount() * 18 / 2));
   }

   @SideOnly(Side.CLIENT)
   public ArrayList createGuiControls(SubGui gui, int x, int y, int maxWidth) {
      ArrayList controls = super.createGuiControls(gui, x, y, maxWidth);
      controls.add(new GuiButtonRemoveRecipe("Remove", x + maxWidth - 80, y + 30, 70, 20, this));
      return controls;
   }

   public void onRemoveRecipe() {
      if(super.subSegments.size() > 0) {
         ((InfoGridSegment)super.subSegments.get(0)).empty();
         ((OutputSegment)super.subSegments.get(1)).empty();
      }

   }

   public StackInfo[] getInput(StackInfo[] input) {
      int startX = super.machine.getWidth();
      int endX = 0;
      int startY = super.machine.getHeight();
      int endY = 0;
      boolean found = false;

      int width;
      int height;
      for(width = 0; width < super.machine.getWidth(); ++width) {
         for(height = 0; height < super.machine.getHeight(); ++height) {
            if(input[width + height * super.machine.getWidth()] != null) {
               startX = Math.min(startX, width);
               endX = Math.max(endX, width);
               startY = Math.min(startY, height);
               endY = Math.max(endY, height);
               found = true;
            }
         }
      }

      width = endX - startX + 1;
      height = endY - startY + 1;
      StackInfo[] result = new StackInfo[width * height];

      for(int i = 0; i < result.length; ++i) {
         int rows = i / width;
         result[i] = input[startX + (startY + rows) * super.machine.getWidth() + (i - rows * width)];
      }

      return result;
   }

   public String createPacketInformation(boolean isServer) {
      if(!isServer && super.guiControls != null) {
         for(int info = 0; info < super.subSegments.size(); ++info) {
            ((ConfigSegment)super.subSegments.get(info)).createPacketInformation(isServer);
         }

         NBTTagCompound var15 = new NBTTagCompound();
         super.machine.parseExtraInfo(var15, this, super.guiControls, super.containerControls);
         StackInfo[] output = (StackInfo[])((StackInfo[])((InfoGridSegment)super.subSegments.get(0)).value).clone();
         int nbt = super.machine.getWidth();
         int endX = 0;
         int startY = super.machine.getHeight();
         int endY = 0;
         boolean found = false;

         int width;
         for(int hasOutput = 0; hasOutput < super.machine.getWidth(); ++hasOutput) {
            for(width = 0; width < super.machine.getHeight(); ++width) {
               if(output[hasOutput + width * super.machine.getWidth()] != null) {
                  nbt = Math.min(nbt, hasOutput);
                  endX = Math.max(endX, hasOutput);
                  startY = Math.min(startY, width);
                  endY = Math.max(endY, width);
                  found = true;
               }
            }
         }

         boolean var19 = false;
         if(((OutputSegment)super.subSegments.get(1)).value != null) {
            for(width = 0; width < ((ItemStack[])((OutputSegment)super.subSegments.get(1)).value).length; ++width) {
               if(((ItemStack[])((OutputSegment)super.subSegments.get(1)).value)[width] != null) {
                  var19 = true;
                  break;
               }
            }
         }

         if(found && var19) {
            width = endX - nbt + 1;
            int height = endY - startY + 1;
            StackInfo[] info1 = new StackInfo[width * height];

            for(int i = 0; i < info1.length; ++i) {
               int rows = i / width;
               info1[i] = output[nbt + (startY + rows) * super.machine.getWidth() + (i - rows * width)];
            }

            this.width = width;
            this.height = height;
            super.value = super.machine.parseRecipe(info1, (ItemStack[])((OutputSegment)super.subSegments.get(1)).value, var15, width, height);
         } else {
            super.value = null;
         }
      }

      if(super.value != null) {
         StackInfo[] var16 = new StackInfo[super.machine.getWidth() * super.machine.getHeight()];
         super.machine.fillGridInfo(var16, super.value);
         ItemStack[] var17 = super.machine.getOutput(super.value);
         NBTTagCompound var18 = new NBTTagCompound();
         super.machine.onBeforeSave(super.value, var18);
         return StringUtils.ObjectsToString(new Object[]{var16, var17, var18, Integer.valueOf(this.width), Integer.valueOf(this.height)});
      } else {
         return null;
      }
   }

   public void receivePacketInformation(String input) {
      Object[] objects = StringUtils.StringToObjects(input);
      if(objects.length > 2 && objects[0] instanceof StackInfo[] && objects[1] instanceof ItemStack[] && objects[2] instanceof NBTTagCompound && (objects.length == 3 || objects.length == 4 || objects.length == 5)) {
         StackInfo[] info = (StackInfo[])((StackInfo[])objects[0]);
         ItemStack[] output = (ItemStack[])((ItemStack[])objects[1]);
         NBTTagCompound nbt = (NBTTagCompound)objects[2];
         if(objects.length > 4) {
            this.width = ((Integer)objects[3]).intValue();
         } else {
            this.width = super.machine.getWidth();
         }

         if(objects.length == 5) {
            this.height = ((Integer)objects[4]).intValue();
         } else {
            this.height = super.machine.getHeight();
         }

         if(super.subSegments.size() > 0) {
            ((ConfigSegment)super.subSegments.get(0)).value = info;
            ((ConfigSegment)super.subSegments.get(1)).value = output;
         }

         if(info != null && output != null && nbt != null) {
            super.value = super.machine.parseRecipe(this.getInput(info), output, nbt, this.width, this.height);
         }
      }

   }

   public boolean contains(String search) {
      return this.createPacketInformation(false).toLowerCase().contains(search);
   }
}
