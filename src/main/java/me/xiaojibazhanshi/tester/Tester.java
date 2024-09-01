package me.xiaojibazhanshi.tester;

import me.xiaojibazhanshi.tester.trader.MerchantOpenListener;
import me.xiaojibazhanshi.tester.trader.TraderRegistry;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Tester extends JavaPlugin {

    @Override
    public void onEnable() {
        TraderRegistry traderRegistry = new TraderRegistry(this);

        getCommand("trader").setExecutor(new NpcCommand(traderRegistry));
        Bukkit.getPluginManager().registerEvents(new MerchantOpenListener(traderRegistry), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
