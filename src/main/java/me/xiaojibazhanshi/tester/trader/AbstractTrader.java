package me.xiaojibazhanshi.tester.trader;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.entity.Villager.Type;
import org.bukkit.event.Listener;
import org.bukkit.inventory.MerchantRecipe;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public abstract class AbstractTrader implements Listener {

    private final String customName;
    private final Type villagerType;
    private final Profession profession;
    private final TraderRegistry registry;

    public AbstractTrader(TraderRegistry registry, @NotNull String customName, @Nullable Profession profession, @Nullable Type villagerType) {
        this.registry = registry;
        this.customName = customName;
        this.villagerType = villagerType;
        this.profession = profession;
    }

    public abstract List<MerchantRecipe> getRecipes();

    public final void spawn(@NotNull Location location) {
        World world = location.getWorld();
        assert world != null;

        Villager villager = world.spawn(location, Villager.class);

        villager.setCustomName(customName);
        villager.setCustomNameVisible(true);

        villager.setAI(false);
        villager.setInvulnerable(true);

        villager.setProfession(profession == null ? Profession.LIBRARIAN : profession);

        if (villagerType != null)
            villager.setVillagerType(villagerType);

        registry.registerTrader(villager);
    }

}
