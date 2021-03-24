package fr.n54b.antitransfert;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.meta.ItemMeta;
import world.bentobox.bentobox.database.objects.Island;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

public class dropitem implements Listener
{
    @EventHandler
    public void ondropitem(final ItemSpawnEvent e) {
        final Item Item = e.getEntity();
        if (Item.getItemStack().getType() != Material.DIRT && Item.getItemStack().getType() != Material.GRASS_BLOCK && Item.getItemStack().getType() != Material.MYCELIUM) {
            if (Item.getItemStack().getType() != Material.RED_SAND) {
                return;
            }
        }
        try {
            if (!Item.getItemStack().getItemMeta().hasLore()) {
                final Location locitem = Item.getLocation();
                Optional<Island> islandAt = BentoBox.getInstance().getIslandsManager().getIslandAt(locitem);
                
                if (islandAt.isPresent())
                {
                    Island island = islandAt.get();
                    final Location locile = island.getCenter();
                    final UUID playeruuid = island.getOwner();
                    final String uuid = playeruuid.toString();
                    final String player = Bukkit.getServer().getOfflinePlayer(UUID.fromString(uuid)).getName();
                    final ItemMeta ItemL = Item.getItemStack().getItemMeta();
                    final ArrayList<String> lore = new ArrayList<String>();
                    lore.add("§2Chef de l'\u00eele: " + player);
                    lore.add("§2Localisation: " + locile.getX() + " " + locile.getY() + " " + locile.getZ());
                    lore.add("§4Ce block peut \u00eatre pos\u00e9 que sur l'\u00eele");
                    lore.add("§4ou il a \u00e9t\u00e9 cass\u00e9 !! ");
                    lore.add("§4NE PEUT PAS ETRE VENDU OU DONNE !! ");
                    ItemL.setLore((List)lore);
                    Item.getItemStack().setItemMeta(ItemL);
                }
            }
        }
        catch (NullPointerException npe) {}
    }
}
