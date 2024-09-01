package me.xiaojibazhanshi.tester.trader;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.*;

import java.util.List;

public class MerchantOpenListener implements Listener {

    private final TraderRegistry traderRegistry;

    public MerchantOpenListener(TraderRegistry traderRegistry) {
        this.traderRegistry = traderRegistry;
    }

    @EventHandler
    public void onMerchantOpen(InventoryOpenEvent event) {
        Player player = (Player) event.getPlayer();

        Inventory inventory = event.getInventory();
        if (!(inventory instanceof MerchantInventory merchantInventory)) return;

        InventoryHolder holder = merchantInventory.getHolder();
        if (holder == null) return;

        Entity holderEntity = (Entity) holder;
        if (traderRegistry.isNotRegistered(holderEntity)) return;

        Villager villager = (Villager) holderEntity;
        String villagerName = villager.getCustomName();
        if (villagerName == null) return;

        AbstractTrader trader = traderRegistry.getTraderByName(villagerName);
        List<MerchantRecipe> recipes = trader.getRecipes();

        if (recipes == null || recipes.isEmpty()) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "This custom merchant has no set recipes!");
            return;
        }

        event.setCancelled(true);

        Merchant merchant = Bukkit.createMerchant(villagerName);
        merchant.setRecipes(recipes);

        player.openMerchant(merchant, true);
    }
}
