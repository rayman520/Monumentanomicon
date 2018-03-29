package mmno.main;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public final class EvtHandler implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void PlayerInteractEvent(PlayerInteractEvent event) {
		Action action = event.getAction();
		Player player = event.getPlayer();
		ItemStack item = event.getItem();

		//	Right Click.
		if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
			if (item != null && item.getType() == Material.STRUCTURE_VOID) {
				if(item.hasItemMeta()){
				       String name = item.getItemMeta().getDisplayName();
				       	if (name.equalsIgnoreCase("monumentanomicon"))
				       	{
				    	   		event.setCancelled(true);
				    	   		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute " + player.getName() + " ~ ~ ~ monumentanomicon " + player.getName());
				       	}
				}
			}
		}
	}

	@EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        Monumentanomicon.clickEvt(e);
    }
}

