package com.creativemd.ingameconfigmanager.api.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIClientConfig;
import codechicken.nei.NEIClientUtils;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.api.API;
import codechicken.nei.api.DefaultOverlayRenderer;
import codechicken.nei.api.IOverlayHandler;
import codechicken.nei.api.IRecipeOverlayRenderer;
import codechicken.nei.api.IStackPositioner;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.ICraftingHandler;
import codechicken.nei.recipe.IUsageHandler;
import codechicken.nei.recipe.RecipeInfo;
import codechicken.nei.recipe.TemplateRecipeHandler.RecipeTransferRect;
import codechicken.nei.recipe.TemplateRecipeHandler.RecipeTransferRectHandler;
import com.creativemd.creativecore.common.recipe.GridRecipe;
import com.creativemd.ingameconfigmanager.mod.block.AdvancedGridRecipe;
import com.creativemd.ingameconfigmanager.mod.block.BlockAdvancedWorkbench;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class NEIAdvancedRecipeHandler implements ICraftingHandler, IUsageHandler {

   public LinkedList transferRects = new LinkedList();
   public ArrayList arecipes = new ArrayList();
   public int cycleticks = Math.abs((int)System.currentTimeMillis());


   public static void load() {
      API.registerRecipeHandler(new NEIAdvancedRecipeHandler());
      API.registerUsageHandler(new NEIAdvancedRecipeHandler());
   }

   public String getRecipeName() {
      return "AdvancedRecipe";
   }

   public NEIAdvancedRecipeHandler() {
      this.loadTransferRects();
      RecipeTransferRectHandler.registerRectsToGuis(this.getRecipeTransferRectGuis(), this.transferRects);
   }

   public void onUpdate() {
      if(!NEIClientUtils.shiftKey()) {
         ++this.cycleticks;
      }

   }

   public void loadTransferRects() {
      this.transferRects.add(new RecipeTransferRect(new Rectangle(84, 23, 24, 18), "crafting", new Object[0]));
   }

   @SideOnly(Side.CLIENT)
   public Class getGuiClass() {
      return GuiCrafting.class;
   }

   public List getRecipeTransferRectGuis() {
      Class clazz = this.getGuiClass();
      if(clazz != null) {
         LinkedList list = new LinkedList();
         list.add(clazz);
         return list;
      } else {
         return null;
      }
   }

   public void loadCraftingRecipes(String outputId, Object ... results) {
      if(this.getClass() == NEIAdvancedRecipeHandler.class) {
         if(outputId.equals("crafting")) {
            Iterator var3 = BlockAdvancedWorkbench.recipes.iterator();

            while(var3.hasNext()) {
               AdvancedGridRecipe irecipe = (AdvancedGridRecipe)var3.next();
               NEIAdvancedRecipeHandler.CachedAdvRecipe recipe = new NEIAdvancedRecipeHandler.CachedAdvRecipe(irecipe);
               recipe.computeVisuals();
               this.arecipes.add(recipe);
            }
         }

         if(outputId.equals("item")) {
            this.loadCraftingRecipes((ItemStack)results[0]);
         }
      }

   }

   public void loadCraftingRecipes(ItemStack result) {
      Iterator var2 = BlockAdvancedWorkbench.recipes.iterator();

      while(var2.hasNext()) {
         AdvancedGridRecipe irecipe = (AdvancedGridRecipe)var2.next();

         for(int i = 0; i < irecipe.output.length; ++i) {
            if(NEIServerUtils.areStacksSameTypeCrafting(irecipe.output[i], result)) {
               NEIAdvancedRecipeHandler.CachedAdvRecipe recipe = new NEIAdvancedRecipeHandler.CachedAdvRecipe(irecipe);
               recipe.computeVisuals();
               this.arecipes.add(recipe);
               break;
            }
         }
      }

   }

   public void loadUsageRecipes(ItemStack ingredient) {
      Iterator var2 = BlockAdvancedWorkbench.recipes.iterator();

      while(var2.hasNext()) {
         AdvancedGridRecipe irecipe = (AdvancedGridRecipe)var2.next();
         NEIAdvancedRecipeHandler.CachedAdvRecipe recipe = new NEIAdvancedRecipeHandler.CachedAdvRecipe(irecipe);
         if(recipe != null && recipe.contains(recipe.ingredients, ingredient.getItem())) {
            recipe.computeVisuals();
            if(recipe.contains(recipe.ingredients, ingredient)) {
               recipe.setIngredientPermutation(recipe.ingredients, ingredient);
               this.arecipes.add(recipe);
            }
         }
      }

   }

   public IRecipeOverlayRenderer getOverlayRenderer(GuiContainer gui, int recipe) {
      IStackPositioner positioner = RecipeInfo.getStackPositioner(gui, "crafting2x2");
      return positioner == null?null:new DefaultOverlayRenderer(this.getIngredientStacks(recipe), positioner);
   }

   public boolean isRecipe2x2(int recipe) {
      Iterator var2 = this.getIngredientStacks(recipe).iterator();

      PositionedStack stack;
      do {
         if(!var2.hasNext()) {
            return true;
         }

         stack = (PositionedStack)var2.next();
      } while(stack.relx <= 43 && stack.rely <= 24);

      return false;
   }

   public int numRecipes() {
      return this.arecipes.size();
   }

   public void drawBackground(int paramInt) {}

   public void drawForeground(int paramInt) {}

   public List getIngredientStacks(int paramInt) {
      return ((NEIAdvancedRecipeHandler.CachedAdvRecipe)this.arecipes.get(paramInt)).getIngredients();
   }

   public List getOtherStacks(int paramInt) {
      return ((NEIAdvancedRecipeHandler.CachedAdvRecipe)this.arecipes.get(paramInt)).getOtherStacks();
   }

   public PositionedStack getResultStack(int paramInt) {
      return ((NEIAdvancedRecipeHandler.CachedAdvRecipe)this.arecipes.get(paramInt)).getResult();
   }

   public int recipiesPerPage() {
      return 1;
   }

   public List handleTooltip(GuiRecipe gui, List currenttip, int recipe) {
      return currenttip;
   }

   public List handleItemTooltip(GuiRecipe paramGuiRecipe, ItemStack paramItemStack, List currenttip, int paramInt) {
      return currenttip;
   }

   public boolean keyTyped(GuiRecipe gui, char keyChar, int keyCode, int recipe) {
      return keyCode == NEIClientConfig.getKeyBinding("gui.recipe")?this.transferRect(gui, recipe, false):(keyCode == NEIClientConfig.getKeyBinding("gui.usage")?this.transferRect(gui, recipe, true):false);
   }

   public boolean mouseClicked(GuiRecipe gui, int button, int recipe) {
      return button == 0?this.transferRect(gui, recipe, false):(button == 1?this.transferRect(gui, recipe, true):false);
   }

   public void loadUsageRecipes(String inputId, Object ... ingredients) {
      if(inputId.equals("item")) {
         this.loadUsageRecipes((ItemStack)ingredients[0]);
      }

   }

   private boolean transferRect(GuiRecipe gui, int recipe, boolean usage) {
      Point offset = gui.getRecipePosition(recipe);
      return transferRect(gui, this.transferRects, offset.x, offset.y, usage);
   }

   public ICraftingHandler getRecipeHandler(String outputId, Object ... results) {
      NEIAdvancedRecipeHandler handler = this.newInstance();
      handler.loadCraftingRecipes(outputId, results);
      return handler;
   }

   public IUsageHandler getUsageHandler(String inputId, Object ... ingredients) {
      NEIAdvancedRecipeHandler handler = this.newInstance();
      handler.loadUsageRecipes(inputId, ingredients);
      return handler;
   }

   public NEIAdvancedRecipeHandler newInstance() {
      try {
         return (NEIAdvancedRecipeHandler)this.getClass().newInstance();
      } catch (Exception var2) {
         throw new RuntimeException(var2);
      }
   }

   private static boolean transferRect(GuiContainer gui, Collection transferRects, int offsetx, int offsety, boolean usage) {
      Point pos = GuiDraw.getMousePosition();
      return false;
   }

   public boolean hasOverlay(GuiContainer paramGuiContainer, Container paramContainer, int paramInt) {
      return false;
   }

   public IOverlayHandler getOverlayHandler(GuiContainer paramGuiContainer, int paramInt) {
      return null;
   }

   public class CachedAdvRecipe {

      final long offset;
      public ArrayList ingredients;
      public ArrayList result;


      public CachedAdvRecipe(int width, int height, Object[] items, ItemStack[] out) {
         this.offset = System.currentTimeMillis();
         this.result = new ArrayList();

         for(int i = 0; i < out.length; ++i) {
            if(out[i] != null) {
               this.result.add(new PositionedStack(out[i], 129, 3 + i * 18));
            }
         }

         this.ingredients = new ArrayList();
         this.setIngredients(width, height, items);
      }

      public CachedAdvRecipe(GridRecipe recipe) {
         this(recipe.width, recipe.height, recipe.getInputStacks(), recipe.output);
      }

      public void setIngredients(int width, int height, Object[] items) {
         for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
               if(items[y * width + x] != null) {
                  PositionedStack stack = new PositionedStack(items[y * width + x], 10 + x * 18, 6 + y * 18, false);
                  stack.setMaxSize(1);
                  this.ingredients.add(stack);
               }
            }
         }

      }

      public List getOtherStacks() {
         ArrayList stacks = new ArrayList();

         for(int i = 1; i < this.result.size(); ++i) {
            stacks.add(this.result.get(i));
         }

         return stacks;
      }

      public List getIngredients() {
         return this.getCycledIngredients(NEIAdvancedRecipeHandler.this.cycleticks / 20, this.ingredients);
      }

      public PositionedStack getResult() {
         return this.result.size() > 0?(PositionedStack)this.result.get(0):null;
      }

      public void computeVisuals() {
         Iterator var1 = this.ingredients.iterator();

         while(var1.hasNext()) {
            PositionedStack p = (PositionedStack)var1.next();
            p.generatePermutations();
         }

      }

      public List getCycledIngredients(int cycle, List ingredients) {
         for(int itemIndex = 0; itemIndex < ingredients.size(); ++itemIndex) {
            this.randomRenderPermutation((PositionedStack)ingredients.get(itemIndex), (long)(cycle + itemIndex));
         }

         return ingredients;
      }

      public void randomRenderPermutation(PositionedStack stack, long cycle) {
         Random rand = new Random(cycle + this.offset);
         stack.setPermutationToRender(Math.abs(rand.nextInt()) % stack.items.length);
      }

      public void setIngredientPermutation(Collection ingredients, ItemStack ingredient) {
         Iterator var3 = ingredients.iterator();

         while(var3.hasNext()) {
            PositionedStack stack = (PositionedStack)var3.next();

            for(int i = 0; i < stack.items.length; ++i) {
               if(NEIServerUtils.areStacksSameTypeCrafting(ingredient, stack.items[i])) {
                  ItemStack item = stack.items[i];
                  item.setItemDamage(ingredient.getItemDamage());
                  stack.items = new ItemStack[]{item};
                  stack.setPermutationToRender(0);
                  break;
               }
            }
         }

      }

      public boolean contains(Collection ingredients, ItemStack ingredient) {
         Iterator var3 = ingredients.iterator();

         PositionedStack stack;
         do {
            if(!var3.hasNext()) {
               return false;
            }

            stack = (PositionedStack)var3.next();
         } while(!stack.contains(ingredient));

         return true;
      }

      public boolean contains(Collection ingredients, Item ingred) {
         Iterator var3 = ingredients.iterator();

         PositionedStack stack;
         do {
            if(!var3.hasNext()) {
               return false;
            }

            stack = (PositionedStack)var3.next();
         } while(!stack.contains(ingred));

         return true;
      }
   }
}
