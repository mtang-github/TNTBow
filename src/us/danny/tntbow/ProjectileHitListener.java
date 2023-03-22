package us.danny.tntbow;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ProjectileHitListener implements Listener
{
    private final String metadataString;
    
    public ProjectileHitListener(String metadataString) {
    	this.metadataString = metadataString;
    }
    
    @EventHandler
    public void onProjectileHit(final ProjectileHitEvent event) {
    	Projectile projectile = event.getEntity();
    	Entity passenger = projectile.getPassenger();
    	
    	boolean isArrow = projectile instanceof Arrow;
    	boolean hasMetadata = projectile.hasMetadata(metadataString);
    	boolean hasPassenger = passenger != null && passenger instanceof Item;
    	
        if (isArrow && hasMetadata && hasPassenger) {
        	spawnTNT(projectile);
            passenger.remove();
            projectile.remove();
        }
    }
    
    private void spawnTNT(Projectile projectile) {
    	Location location = projectile.getLocation();
    	World world = projectile.getWorld();
        TNTPrimed tnt = (TNTPrimed)world.spawnEntity(location, EntityType.PRIMED_TNT);
        tnt.setFuseTicks(40);
    }
}
