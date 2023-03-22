package us.danny.tntbow;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

//this project is based on the ExplodingBows plugin.
public class Main extends JavaPlugin {
	
	private static final String lore = "Death from above!";
	private static final String metadata = "TNT arrow";
    
    public Main() {
    	//do nothing
    }
    
    @Override
    public void onEnable() {        
        PluginManager pluginManager = getServer().getPluginManager();
        
        BowShootListener bowListener = new BowShootListener(this, lore, metadata);
        pluginManager.registerEvents(bowListener, this);
        
        ProjectileHitListener projectileListener = new ProjectileHitListener(metadata);
        pluginManager.registerEvents(projectileListener, this);
        
        System.out.println("TNT Bow enabled");
    }
    
    @Override
    public void onDisable() {
    	System.out.println("TNT Bow disabled");
    }
}
