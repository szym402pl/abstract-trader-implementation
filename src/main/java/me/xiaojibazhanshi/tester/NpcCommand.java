package me.xiaojibazhanshi.tester;

import me.xiaojibazhanshi.tester.trader.AbstractTrader;
import me.xiaojibazhanshi.tester.trader.TraderRegistry;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;

import java.util.Set;

public class NpcCommand implements CommandExecutor {

    private final TraderRegistry traderRegistry;

    public NpcCommand(TraderRegistry traderRegistry) {
        this.traderRegistry = traderRegistry;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "Usage: /npc <name> or /npc delete");
            return true;
        }

        Set<String> names = traderRegistry.getTraderNames();
        String arg = args[0];

        if (arg.equalsIgnoreCase("delete")) {
            handleTraderDeletion(player);
            return true;
        }

        if (!names.contains(arg)) {
            player.sendMessage(ChatColor.GREEN + "List of trader names: \n");
            names.forEach(name -> player.sendMessage(ChatColor.GREEN + name));
            return true;
        }

        AbstractTrader trader = traderRegistry.getTraderByName(arg);
        trader.spawn(player.getLocation());

        return true;
    }


    private void handleTraderDeletion(Player player) {
        Entity lookedAt = getEntityInFrontOf(player);

        if (lookedAt == null || traderRegistry.isNotRegistered(lookedAt)) {
            player.sendMessage(ChatColor.RED + "You're not standing in front of a valid trader!");
        } else {
            player.sendMessage(ChatColor.GREEN + "You've successfully deleted one of the traders!");
            lookedAt.remove();
        }
    }


    private Entity getEntityInFrontOf(Player player) {
        RayTraceResult result = player.getWorld().rayTraceEntities(
                player.getEyeLocation(),
                player.getEyeLocation().getDirection(),
                5.0,
                1.0,
                entity -> !entity.equals(player)
        );

        return result == null ? null : result.getHitEntity();
    }
}
