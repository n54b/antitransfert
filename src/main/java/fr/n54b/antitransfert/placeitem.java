package fr.n54b.antitransfert;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import world.bentobox.bentobox.database.objects.Island;
import java.util.Optional;

public class placeitem implements Listener
{
    @EventHandler
    public void onplaceitem(final BlockPlaceEvent e) {
        final Location loc = e.getBlock().getLocation();
        final ItemStack b = e.getItemInHand();
        try {
            if (b.getItemMeta().hasLore() && b.getItemMeta().getLore().get(1).contains("Localisation")) {
                final String lore = b.getItemMeta().getLore().get(1);
                final String[] splitlore = lore.split(" ");
                final float x = Float.parseFloat(splitlore[1]);
                final float z = Float.parseFloat(splitlore[3]);

                Optional<Island> islandAt = BentoBox.getInstance().getIslandsManager().getIslandAt(loc);

                if (islandAt.isPresent())
                {
                    Island island = islandAt.get();

                    final Location locile = island.getCenter();
                    if (locile.getX() == x && locile.getZ() == z) {
                        e.getBlockPlaced();
                    }
                    else {
                        e.getPlayer().sendMessage("§4[ATTENTION] : §6Vous ne pouvez pas poser cet item sur cette \u00eele !!!");
                        e.setCancelled(true);
                    }
                }
            }
        }
        catch (NullPointerException npe) {}
    }
}
