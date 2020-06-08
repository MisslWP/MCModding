package com.creativemd.ingameconfigmanager.api.client.gui;

import com.creativemd.creativecore.common.container.slot.ContainerControl;
import com.creativemd.creativecore.common.container.slot.SlotControlNoSync;
import com.creativemd.creativecore.common.gui.SubGui;
import com.creativemd.creativecore.common.gui.controls.GuiButton;
import com.creativemd.creativecore.common.gui.controls.GuiControl;
import com.creativemd.creativecore.common.gui.controls.GuiScrollBox;
import com.creativemd.creativecore.common.gui.controls.GuiTextfield;
import com.creativemd.creativecore.common.gui.controls.container.GuiSlotControl;
import com.creativemd.creativecore.common.gui.event.ControlChangedEvent;
import com.creativemd.creativecore.common.gui.event.ControlClickEvent;
import com.creativemd.creativecore.common.packet.PacketHandler;
import com.creativemd.ingameconfigmanager.api.client.gui.SubGuiFullItemDialog;
import com.creativemd.ingameconfigmanager.api.client.gui.SubGuiItemDialog;
import com.creativemd.ingameconfigmanager.api.common.branch.ConfigBranch;
import com.creativemd.ingameconfigmanager.api.common.container.controls.InfoSlotControl;
import com.creativemd.ingameconfigmanager.api.common.packets.RequestInformationPacket;
import com.creativemd.ingameconfigmanager.api.common.segment.ConfigSegment;
import com.creativemd.ingameconfigmanager.api.core.InGameConfigManager;
import com.n247s.api.eventapi.eventsystem.CustomEventSubscribe;
import java.util.ArrayList;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class SubGuiBranch extends SubGui {

   public ConfigBranch branch;
   public static final int loadPerTick = 20;
   public int index;
   public int height;
   public ArrayList segments;
   public String search = "";
   public GuiSlotControl openedSlot;


   public SubGuiBranch(ConfigBranch branch) {
      super(250, 250);
      this.branch = branch;
   }

   public void createSegmentControls() {
      GuiScrollBox box = (GuiScrollBox)this.getControl("scrollbox");
      ArrayList segments = this.branch.getConfigSegments();
      box.gui.controls.clear();
      box.container.controls.clear();
      box.maxScroll = 0;
      box.scrolled = 0;
      if(this.getControl("Save") != null) {
         this.getControl("Save").setEnabled(false);
      }

      this.segments = new ArrayList(segments);
      this.height = 5;
      this.index = 0;
      this.onTick();
   }

   public void onTick() {
      if(this.segments != null) {
         GuiScrollBox box = (GuiScrollBox)this.getControl("scrollbox");
         int count = 0;
         int countLoaded = 0;

         for(int i = this.index; i < this.segments.size() && countLoaded < 20; ++i) {
            boolean visible = true;
            if(!this.search.equals("")) {
               visible = ((ConfigSegment)this.segments.get(i)).contains(this.search);
            }

            if(visible) {
               ArrayList guiControls = ((ConfigSegment)this.segments.get(i)).createGuiControls(this, 0, this.height, 220);
               ArrayList containerControls = ((ConfigSegment)this.segments.get(i)).createContainerControls(box.container, 0, this.height, 220);
               ((ConfigSegment)this.segments.get(i)).guiControls = guiControls;
               ((ConfigSegment)this.segments.get(i)).containerControls = containerControls;
               ((ConfigSegment)this.segments.get(i)).onSegmentLoaded(0, this.height, 220);

               int j;
               for(j = 0; j < guiControls.size(); ++j) {
                  box.addControl((GuiControl)guiControls.get(j));
               }

               for(j = 0; j < containerControls.size(); ++j) {
                  box.addControl((ContainerControl)containerControls.get(j));
                  guiControls.add(box.gui.controls.get(box.gui.controls.size() - 1));
               }

               this.height = ((ConfigSegment)this.segments.get(i)).getHeight() + 5;
               ++countLoaded;
            }

            ++count;
         }

         this.index += count;
         if(this.index >= this.segments.size()) {
            box.maxScroll += 5;
            this.getControl("Save").setEnabled(true);
            this.segments = null;
            this.index = 0;
         }
      }

   }

   public void createControls() {
      GuiScrollBox box = new GuiScrollBox("scrollbox", this.container.player, 5, 5, 240, 220);
      this.controls.add(box);
      this.controls.add(new GuiButton("Cancel", 5, 226, 40, 20));
      this.controls.add((new GuiButton("Save", 205, 226, 40, 20)).setEnabled(false));
      if(this.branch.isSearchable()) {
         this.controls.add(new GuiTextfield("search", "", 49, 227, 152, 18));
      }

      this.createSegmentControls();
   }

   @CustomEventSubscribe
   public void onButtonClicked(ControlClickEvent event) {
      if(event.source instanceof GuiButton) {
         if(event.source.is("Cancel")) {
            PacketHandler.sendPacketToServer(new RequestInformationPacket(this.branch));
            if(this.branch.tab.branches.size() > 1) {
               InGameConfigManager.openModOverviewGui(this.container.player, this.branch.tab.getID());
            } else if(this.branch.tab.branches.size() == 1) {
               InGameConfigManager.openModsGui(this.container.player);
            }
         } else if(event.source.is("Save")) {
            InGameConfigManager.sendUpdatePacket(this.branch);
         }
      } else if(event.source instanceof GuiSlotControl) {
         NBTTagCompound nbt;
         if(((GuiSlotControl)event.source).slot instanceof InfoSlotControl) {
            this.openedSlot = (GuiSlotControl)event.source;
            nbt = new NBTTagCompound();
            nbt.setBoolean("ItemDialog", true);
            nbt.setBoolean("fullEdit", true);
            this.openNewLayer(nbt);
         } else if(((GuiSlotControl)event.source).slot instanceof SlotControlNoSync) {
            this.openedSlot = (GuiSlotControl)event.source;
            nbt = new NBTTagCompound();
            nbt.setBoolean("ItemDialog", true);
            nbt.setBoolean("fullEdit", false);
            this.openNewLayer(nbt);
         }
      }

   }

   public void onLayerClosed(SubGui gui, NBTTagCompound nbt) {
      if(gui instanceof SubGuiFullItemDialog && !nbt.getBoolean("canceled") && this.openedSlot != null) {
         ((InfoSlotControl)this.openedSlot.slot).info = ((SubGuiFullItemDialog)gui).info;
         if(((SubGuiFullItemDialog)gui).info != null) {
            this.openedSlot.slot.slot.putStack(((SubGuiFullItemDialog)gui).info.getItemStack());
         } else {
            this.openedSlot.slot.slot.putStack((ItemStack)null);
         }
      }

      if(gui instanceof SubGuiItemDialog && !nbt.getBoolean("canceled") && this.openedSlot != null) {
         this.openedSlot.slot.slot.putStack(((SubGuiItemDialog)gui).stack);
      }

      this.openedSlot = null;
   }

   public SubGui createLayerFromPacket(World world, EntityPlayer player, NBTTagCompound nbt) {
      SubGui gui = super.createLayerFromPacket(world, player, nbt);
      if(gui == null && nbt.getBoolean("ItemDialog")) {
         if(nbt.getBoolean("fullEdit")) {
            SubGuiFullItemDialog dialog1 = new SubGuiFullItemDialog(this.branch.doesInputSupportStackSize());
            dialog1.info = ((InfoSlotControl)this.openedSlot.slot).info;
            return dialog1;
         } else {
            SubGuiItemDialog dialog = new SubGuiItemDialog();
            dialog.stack = this.openedSlot.slot.slot.getStack();
            if(dialog.stack != null) {
               dialog.stack = dialog.stack.copy();
            }

            return dialog;
         }
      } else {
         return gui;
      }
   }

   @CustomEventSubscribe
   public void onTextfieldChanged(ControlChangedEvent event) {
      if(event.source instanceof GuiTextfield && event.source.is("search")) {
         this.search = ((GuiTextfield)event.source).text.toLowerCase();
         ArrayList segments = this.branch.getConfigSegments();

         for(int i = 0; i < segments.size(); ++i) {
            ((ConfigSegment)segments.get(i)).createPacketInformation(false);
         }

         this.createSegmentControls();
      }

   }

   public void drawOverlay(FontRenderer fontRenderer) {}
}
