package me.xiaojibazhanshi.tester.trader.traders;

import me.xiaojibazhanshi.tester.trader.AbstractTrader;
import me.xiaojibazhanshi.tester.trader.TraderRegistry;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import java.util.List;

public class AmbrosiaTrader extends AbstractTrader {

    public AmbrosiaTrader(TraderRegistry registry) {
        super(registry, ChatColor.GREEN + "Ambrosia", null, null);
    }

    private MerchantRecipe appleForDiamond() {
        MerchantRecipe recipe = new MerchantRecipe(new ItemStack(Material.APPLE), 999);
        recipe.addIngredient(new ItemStack(Material.DIAMOND));

        return recipe;
    }

    @Override
    public List<MerchantRecipe> getRecipes() {
        return List.of(
                appleForDiamond()
        );
    }

}
