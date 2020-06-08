package com.creativemd.ingameconfigmanager.api.client.gui;

import com.creativemd.creativecore.client.rendering.RenderHelper2D;
import com.creativemd.creativecore.common.gui.SubGui;
import com.creativemd.creativecore.common.gui.controls.GuiButton;
import com.creativemd.creativecore.common.gui.controls.GuiComboBox;
import com.creativemd.creativecore.common.gui.controls.GuiInvSelector;
import com.creativemd.creativecore.common.gui.controls.GuiLabel;
import com.creativemd.creativecore.common.gui.controls.GuiStackSelector;
import com.creativemd.creativecore.common.gui.controls.GuiStateButton;
import com.creativemd.creativecore.common.gui.controls.GuiTextfield;
import com.creativemd.creativecore.common.gui.event.ControlChangedEvent;
import com.creativemd.creativecore.common.gui.event.ControlClickEvent;
import com.creativemd.creativecore.common.gui.event.GuiToolTipEvent;
import com.creativemd.creativecore.common.utils.stack.StackInfo;
import com.creativemd.creativecore.common.utils.stack.StackInfoBlock;
import com.creativemd.creativecore.common.utils.stack.StackInfoFuel;
import com.creativemd.creativecore.common.utils.stack.StackInfoItem;
import com.creativemd.creativecore.common.utils.stack.StackInfoItemStack;
import com.creativemd.creativecore.common.utils.stack.StackInfoMaterial;
import com.creativemd.creativecore.common.utils.stack.StackInfoOre;
import com.n247s.api.eventapi.eventsystem.CustomEventSubscribe;
import java.util.ArrayList;
import java.util.Arrays;
import javax.vecmath.Vector4d;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

public class SubGuiFullItemDialog extends SubGui {

   public static ArrayList latest = new ArrayList();
   public StackInfo info;
   public boolean supportStackSize;


   public SubGuiFullItemDialog(boolean supportStackSize) {
      super(150, 175);
      this.supportStackSize = supportStackSize;
   }

   public void createControls() {
      String selected = "Default";
      if(this.controls.size() > 0) {
         selected = ((GuiComboBox)this.controls.get(0)).caption;
      } else if(this.info != null) {
         if(this.info instanceof StackInfoBlock || this.info instanceof StackInfoItem || this.info instanceof StackInfoItemStack) {
            selected = "Default";
         }

         if(this.info instanceof StackInfoOre) {
            selected = "Ore";
         }

         if(this.info instanceof StackInfoMaterial) {
            selected = "Material";
         }

         if(this.info instanceof StackInfoFuel) {
            selected = "Fuel";
         }
      }

      this.controls.clear();
      ArrayList lines = new ArrayList();
      lines.add("Default");
      lines.add("Ore");
      lines.add("Material");
      lines.add("Fuel");
      lines.add("Latest");
      GuiComboBox box = new GuiComboBox("type", 5, 5, 140, lines);
      int index = lines.indexOf(selected);
      box.caption = selected;
      box.index = index;
      this.controls.add(box);
      GuiInvSelector var11;
      switch(index) {
      case 0:
         var11 = new GuiInvSelector("inv", 5, 30, 140, this.container.player, false);
         this.controls.add(var11);
         this.controls.add(new GuiTextfield("search", "", 5, 55, 140, 20));
         this.controls.add(new GuiLabel("guilabel1", 5, 80));
         this.controls.add(new GuiLabel("guilabel2", 5, 90));
         GuiStateButton damage = new GuiStateButton("damage", 0, 5, 102, 70, 20, new String[]{"Damage: Off", "Damage: On"});
         this.controls.add(damage);
         GuiStateButton nbt = new GuiStateButton("nbt", 0, 85, 102, 60, 20, new String[]{"NBT: Off", "NBT: On"});
         this.controls.add(nbt);
         if(this.info instanceof StackInfoBlock || this.info instanceof StackInfoItem || this.info instanceof StackInfoItemStack) {
            var11.addAndSelectStack(this.info.getItemStack().copy());
            if(this.info instanceof StackInfoItemStack) {
               damage.nextState();
               if(((StackInfoItemStack)this.info).needNBT) {
                  nbt.nextState();
               }
            }
         }
         break;
      case 1:
         ArrayList ores = new ArrayList(Arrays.asList(OreDictionary.getOreNames()));
         GuiComboBox ore = new GuiComboBox("ore", 5, 30, 140, ores);
         this.controls.add(ore);
         this.controls.add(new GuiTextfield("search", "", 5, 55, 140, 20));
         if(this.info instanceof StackInfoOre) {
            ore.caption = ((StackInfoOre)this.info).ore;
         }
         break;
      case 2:
         var11 = new GuiInvSelector("inv", 5, 30, 140, this.container.player, true);
         this.controls.add(var11);
         if(this.info instanceof StackInfoMaterial) {
            var11.addAndSelectStack(this.info.getItemStack());
         }
         break;
      case 3:
         this.controls.add(new GuiLabel("Nothing to select", 5, 30));
         break;
      case 4:
         GuiStackSelector field = new GuiStackSelector("stack", 5, 30, 140, this.container.player, false);
         field.stacks.clear();
         field.lines.clear();

         for(int i = 0; i < latest.size(); ++i) {
            field.addAndSelectStack(((StackInfo)latest.get(i)).getItemStack());
         }

         if(field.lines.size() > 0) {
            field.caption = (String)field.lines.get(0);
         } else {
            field.caption = "";
         }

         this.controls.add(field);
      }

      if(this.supportStackSize) {
         this.controls.add(new GuiLabel("StackSize:", 5, 132));
         GuiTextfield var12 = (new GuiTextfield("stacksize", "1", 110, 127, 30, 20)).setNumbersOnly();
         if(this.info != null) {
            var12.text = "" + this.info.stackSize;
         }

         this.controls.add(var12);
      }

      this.controls.add(new GuiButton("Cancel", 5, 150, 45, 20));
      this.controls.add(new GuiButton("Remove", 52, 150, 45, 20));
      this.controls.add(new GuiButton("Save", 100, 150, 45, 20));
   }

   @CustomEventSubscribe
   public void onClicked(ControlClickEvent event) {
      if(event.source.is("Save")) {
         int nbt = ((GuiComboBox)this.controls.get(0)).index;
         boolean stacksize = false;

         int stacksize1;
         try {
            if(this.supportStackSize) {
               stacksize1 = Integer.parseInt(((GuiTextfield)this.getControl("stacksize")).text);
            } else {
               stacksize1 = 1;
            }
         } catch (Exception var9) {
            stacksize1 = 1;
         }

         Object info = null;
         switch(nbt) {
         case 0:
            ItemStack stack = ((GuiInvSelector)this.getControl("inv")).getStack();
            if(stack != null) {
               boolean ore1 = ((GuiStateButton)this.getControl("damage")).getState() == 1;
               boolean blockStack1 = ((GuiStateButton)this.getControl("nbt")).getState() == 1;
               if(ore1) {
                  info = new StackInfoItemStack(stack, blockStack1, stacksize1);
               } else if(!(Block.getBlockFromItem(stack.getItem()) instanceof BlockAir)) {
                  info = new StackInfoBlock(Block.getBlockFromItem(stack.getItem()), stacksize1);
               } else {
                  info = new StackInfoItem(stack.getItem(), stacksize1);
               }
            }
            break;
         case 1:
            String ore = ((GuiComboBox)this.getControl("ore")).caption;
            if(!ore.equals("")) {
               info = new StackInfoOre(ore, stacksize1);
            }
            break;
         case 2:
            ItemStack blockStack = ((GuiInvSelector)this.getControl("inv")).getStack();
            if(blockStack != null) {
               Block stackIndex1 = Block.getBlockFromItem(blockStack.getItem());
               if(!(stackIndex1 instanceof BlockAir)) {
                  info = new StackInfoMaterial(stackIndex1.getMaterial(), stacksize1);
               }
            }
            break;
         case 3:
            info = new StackInfoFuel(stacksize1);
            break;
         case 4:
            int stackIndex = ((GuiInvSelector)this.getControl("stack")).index;
            if(stackIndex >= 0 && stackIndex < latest.size()) {
               info = ((StackInfo)latest.get(stackIndex)).copy();
            }
         }

         if(info != null) {
            this.info = (StackInfo)info;
            if(!latest.contains(info)) {
               latest.add(0, ((StackInfo)info).copy());
            }

            this.closeLayer(new NBTTagCompound());
         }
      }

      if(event.source.is("Remove")) {
         this.info = null;
         this.closeLayer(new NBTTagCompound());
      }

      if(event.source.is("Cancel")) {
         NBTTagCompound nbt1 = new NBTTagCompound();
         nbt1.setBoolean("canceled", true);
         this.closeLayer(nbt1);
      }

   }

   @CustomEventSubscribe
   public void onToolTip(GuiToolTipEvent event) {
      if(event.source.is("stacksize")) {
         event.tooltip.add("0: no consumption");
         event.tooltip.add("1: normal");
      }

   }

   @CustomEventSubscribe
   public void onChanged(ControlChangedEvent event) {
      if(event.source.is("type")) {
         this.createControls();
         this.refreshControls();
      }

      if(event.source.is("search")) {
         int index = ((GuiComboBox)this.controls.get(0)).index;
         if(index == 1) {
            String inv = ((GuiTextfield)event.source).text;
            String[] oreNames = OreDictionary.getOreNames();
            ArrayList ores = new ArrayList();

            for(int comboBox = 0; comboBox < oreNames.length; ++comboBox) {
               if(oreNames[comboBox].toLowerCase().contains(inv.toLowerCase())) {
                  ores.add(oreNames[comboBox]);
               }
            }

            GuiComboBox var8 = (GuiComboBox)this.getControl("ore");
            if(var8 != null) {
               var8.lines = ores;
               if(!ores.contains(var8.caption)) {
                  if(ores.size() > 0) {
                     var8.caption = (String)ores.get(0);
                  } else {
                     var8.caption = "";
                  }
               }
            }
         } else if(index == 0) {
            GuiInvSelector var7 = (GuiInvSelector)this.getControl("inv");
            var7.search = ((GuiTextfield)event.source).text.toLowerCase();
            var7.updateItems(this.container.player);
            var7.closeBox();
         }
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

   public void drawOverlay(FontRenderer fontRenderer) {
      int index = ((GuiComboBox)this.controls.get(0)).lines.indexOf(((GuiComboBox)this.controls.get(0)).caption);
      switch(index) {
      case 0:
         GuiInvSelector selector = (GuiInvSelector)this.getControl("inv");
         if(selector != null) {
            int indexStack = selector.index;
            if(indexStack != -1) {
               ItemStack stack = (ItemStack)selector.stacks.get(indexStack);
               ((GuiLabel)this.getControl("guilabel1")).title = "damage:" + stack.getItemDamage();
               ((GuiLabel)this.getControl("guilabel2")).title = "nbt:" + (stack.stackTagCompound != null?stack.stackTagCompound.toString():"null");
            } else {
               ((GuiLabel)this.getControl("guilabel1")).title = "";
               ((GuiLabel)this.getControl("guilabel2")).title = "";
            }
         }
      default:
      }
   }

}
