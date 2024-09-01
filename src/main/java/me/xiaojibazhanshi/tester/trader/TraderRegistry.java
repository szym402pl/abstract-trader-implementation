package me.xiaojibazhanshi.tester.trader;

import me.xiaojibazhanshi.tester.Tester;
import me.xiaojibazhanshi.tester.trader.traders.AmbrosiaTrader;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TraderRegistry {

    private final Map<String, AbstractTrader> traders = new HashMap<>();
    private final NamespacedKey customTraderKey;

    public TraderRegistry(Tester plugin) {
        customTraderKey = new NamespacedKey(plugin, "custom-trader");

        registerTraderClass("ambrosia", new AmbrosiaTrader(this));
    }

    public <T extends AbstractTrader> void registerTraderClass(String name, T traderClass) {
        traders.put(name, traderClass);
    }

    public AbstractTrader getTraderByName(String name) {
        return traders.get(ChatColor.stripColor(name.toLowerCase()));
    }

    public void registerTrader(Villager trader) {
        trader.getPersistentDataContainer().set(customTraderKey, PersistentDataType.BOOLEAN, true);
    }

    public Set<String> getTraderNames() {
        return traders.isEmpty() ? Collections.emptySet() : traders.keySet();
    }

    public boolean isNotRegistered(Entity entity) {
        return !entity.getPersistentDataContainer().has(customTraderKey);
    }

}
