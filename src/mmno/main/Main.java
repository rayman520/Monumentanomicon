package mmno.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
 
public class Main extends JavaPlugin {
 
    @Override
    public void onEnable() {
    		getCommand("monumentanomicon").setExecutor(new Monumentanomicon());
    		getCommand("mmnogetitemstring").setExecutor(new Mmnogetitemstring());
    		PluginManager pm = Bukkit.getPluginManager(); 
    		pm.registerEvents(new EvtHandler(), this);
    		Bukkit.getConsoleSender().sendMessage("[MonumentaNomicon] Plugin enabled!");
    }
   
    @Override
    public void onDisable() {
    }
   
}
