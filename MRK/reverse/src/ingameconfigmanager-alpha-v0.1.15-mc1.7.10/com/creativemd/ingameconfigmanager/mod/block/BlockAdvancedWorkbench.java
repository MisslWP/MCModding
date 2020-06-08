package com.creativemd.ingameconfigmanager.mod.block;

import com.creativemd.creativecore.common.container.SubContainer;
import com.creativemd.creativecore.common.gui.IGuiCreator;
import com.creativemd.creativecore.common.gui.SubGui;
import com.creativemd.creativecore.core.CreativeCore;
import com.creativemd.ingameconfigmanager.mod.block.SubContainerAdvancedWorkbench;
import com.creativemd.ingameconfigmanager.mod.block.SubGuiAdvancedWorkbench;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockAdvancedWorkbench extends Block implements IGuiCreator {

   public static final int gridSize = 6;
   public static final int outputs = 4;
   public static ArrayList recipes = new ArrayList();


   public BlockAdvancedWorkbench() {
      super(Material.wood);
      this.setStepSound(Block.soundTypeWood);
   }

   public IIcon getIcon(int side, int meta) {
      return Blocks.crafting_table.getIcon(side, meta);
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister registry) {}

   public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
      if(!world.isRemote) {
         ((EntityPlayerMP)player).openGui(CreativeCore.instance, 0, world, x, y, z);
      }

      return true;
   }

   @SideOnly(Side.CLIENT)
   public SubGui getGui(EntityPlayer player, ItemStack stack, World world, int x, int y, int z) {
      return new SubGuiAdvancedWorkbench();
   }

   public SubContainer getContainer(EntityPlayer player, ItemStack stack, World world, int x, int y, int z) {
      return new SubContainerAdvancedWorkbench(player);
   }

}
