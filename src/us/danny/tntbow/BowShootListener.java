package us.danny.tntbow;

import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class BowShootListener implements Listener
{
	private final Plugin plugin;
    private final String lore;
    private final String metadataString;
    
    public BowShootListener(Plugin plugin, String lore, String metadataString) {
    	this.plugin = plugin;
        this.lore = lore;
        this.metadataString = metadataString;
    }
    
    @EventHandler
    public void onShoot(final EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player)event.getEntity();
            ItemStack bow = event.getBow();
            
            if (isTNTBow(bow) && checkAndRemoveMaterial(player, Material.TNT)) {
            	Projectile arrow = (Projectile)event.getProjectile();
                setMetadata(arrow, metadataString, "", plugin);
                
                World playerWorld = player.getWorld();
                Location playerLocation = player.getLocation();
                ItemStack passengerStack = new ItemStack(Material.TNT);
                Item passenger = playerWorld.dropItem(playerLocation, passengerStack);
                arrow.setPassenger(passenger);
            }
        }
    }
    
    private boolean isTNTBow(ItemStack toTest) {
    	if(toTest.hasItemMeta()) {
    		ItemMeta itemMeta = toTest.getItemMeta();
    		if(itemMeta.hasLore()) {
    			List<String> loreList = itemMeta.getLore();
    			return loreList.contains(lore);
    		}
    	}
    	return false;
    }
    
    private boolean checkAndRemoveMaterial(Player player, Material material) {
    	Inventory inventory = player.getInventory();
    	ItemStack toRemove = null;;
    	boolean toRet = false;
    	boolean isCreative = player.getGameMode() == GameMode.CREATIVE;
    	
        for(ItemStack item : inventory.getContents()) {
            if(item != null && item.getType().equals(material)) {
                toRet = true;
                if(!isCreative) {
                	int amount = item.getAmount();
                	--amount;
                	if(amount > 0) {
                		item.setAmount(amount);
                	}
               		else {
                		toRemove = item;
                	}
               	}
               	break;
            }
        }
        if(toRemove != null && !isCreative) {
        	inventory.remove(toRemove);
        }
        return toRet;
    }
    
    private void setMetadata(
    		final Projectile projectile, 
    		final String key, 
    		final Object value, 
    		final Plugin plugin
    ) {
    	MetadataValue metadata = new FixedMetadataValue(plugin, value);
        projectile.setMetadata(key, metadata);
    }
}