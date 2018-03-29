package mmno.main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Monumentanomicon implements CommandExecutor {

	static String prev_page = null;
	
	public Inventory CreateMenu(Player player) {
		Inventory inv = Bukkit.getServer().createInventory(null, 54, "Monumentanomicon");
		BaseFuncts.ft_menu_base_hud(inv);
		player.openInventory(inv);
		SetPage(inv, player, "plugins/MonumentaNomicon_data/mob_pages/Menu.mmnopage");
		return (inv);
	}
	
	public static void SetPage(Inventory inv, Player player, String str)
	{
		inv.clear();
		BaseFuncts.ft_menu_base_hud(inv);
		if (prev_page != null)
		{
			ItemStack item = inv.getItem(5);
			ItemMeta meta = item.getItemMeta();
			List<String> lore = new ArrayList<String>();
			lore = meta.getLore();
			if (lore == null)
				lore = new ArrayList<String>();
			lore.add("Link:" + prev_page);
			meta.setLore(lore);
			item.setItemMeta(meta);
			inv.setItem(5, item);
		}
		else
			inv.setItem(5, new ItemStack(Material.AIR, 1));
		prev_page = str.replace("plugins/MonumentaNomicon_data/mob_pages/", "");
		ItemStack[] ItemList = BaseFuncts.ft_get_item_list(str).getContents();
		if (BaseFuncts.ft_get_file_extension(str).equalsIgnoreCase("mmnopage"))
		{
			int i = 0;
			while (i < 15 && ItemList[i] != null)
			{
				inv.setItem(19 + i, ItemList[i]);
				i++;
			}
		}
		else if (BaseFuncts.ft_get_file_extension(str).equalsIgnoreCase("mmnomob"))
		{
			inv.setItem(19, ItemList[0]);
			inv.setItem(22, ItemList[1]);
			inv.setItem(23, ItemList[2]);
			inv.setItem(24, ItemList[3]);
			inv.setItem(25, ItemList[4]);
			inv.setItem(28, ItemList[5]);
			inv.setItem(29, ItemList[6]);
			inv.setItem(30, ItemList[7]);
			inv.setItem(31, ItemList[8]);
			inv.setItem(32, ItemList[9]);
			inv.setItem(34, ItemList[10]);
			inv.setItem(37, ItemList[11]);
			inv.setItem(38, ItemList[12]);
			inv.setItem(39, ItemList[13]);
			inv.setItem(43, ItemList[14]);
			String killItemString = "i:¶  ==: org.bukkit.inventory.ItemStack¶  type: DIAMOND¶  meta:¶    ==: ItemMeta¶    meta-type: UNSPECIFIC¶    display-name: §bKill Count¶    lore:¶    - §7Feature comming soon...¶";
			killItemString = killItemString.replaceAll("¶", "\n").replaceAll("Â", "");
			ItemStack item = BaseFuncts.stringBlobToItem(killItemString);
			inv.setItem(41, item);
		}
		player.closeInventory();
		player.openInventory(inv);
	}

	@Override
	public boolean onCommand(CommandSender send, Command command, String label, String[] args)
	{
		
		if (!BaseFuncts.ft_param_check(args, send))
			return false;
		
		Player sender = BaseFuncts.ft_get_proxied_player(send);
		if (sender == null)
		{
			send.sendMessage(ChatColor.RED + "This command must be run by/on a player!");
			return false;
		}
		
		Player player = Bukkit.getServer().getPlayer(args[0]);
		if (player == null)
		{
			send.sendMessage("player '" + args[0] + "' was not found. Check for Typos mistakes or maybe the target is offline");
			return false;
		}
		
		sender.sendMessage(ChatColor.GREEN + "You're now viewing " + player.getName() + "'s Monumentanomicon");
		CreateMenu(sender);
		return true;
	}
	
    public static void clickEvt(InventoryClickEvent e){
    		if (e.getClickedInventory().getTitle().equalsIgnoreCase("monumentanomicon"))
    		{
    			ItemStack item = e.getCurrentItem();
    			Player player = (Player)e.getWhoClicked();
    			if (item != null && item.getType() != Material.AIR && item.getType() != Material.STAINED_GLASS_PANE)
    			{
    				if (item.getType() == Material.REDSTONE)
    					player.closeInventory();
    				else
    				{
    					ItemMeta meta = item.getItemMeta();
    					List<String> lore = new ArrayList<String>();
    					lore = meta.getLore();
    					if (lore != null)
    					{
    						int size = lore.size();
    						String str = null;
    						while (size != 0)
    						{
    							size--;
    							str = lore.get(size).replaceAll("§", "");
    							if (str.contains("Link:"))
    							{
    								SetPage(e.getClickedInventory(), player, "plugins/MonumentaNomicon_data/mob_pages/" + str.replace("Link:",""));
    							}
    						}
    					}
    				}
    			}
    			if (!(player.isOp() && player.getGameMode() == GameMode.CREATIVE))
    	   			e.setCancelled(true);
    		}
    }
}
