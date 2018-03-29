package mmno.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ProxiedCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class BaseFuncts {
	
	public static boolean ft_param_check(String[] args, CommandSender sender) //checks if the parameter count is right
	{
		if (args.length < 1)
		{
			sender.sendMessage(ChatColor.RED + "you must target a player.");
			return (false);
		}
		else if (args.length > 1)
		{
			sender.sendMessage(ChatColor.RED + "Too many parameters given for this function!");
			return (false);
		}
		return (true);
	}
	
	public static Player ft_get_proxied_player(CommandSender sender)
	{
		Player target = null;
		
		if (sender instanceof Player)
			target = (Player)sender;
		else if (sender instanceof ProxiedCommandSender)
		{
			CommandSender callee = ((ProxiedCommandSender)sender).getCallee();
			if (callee instanceof Player)
				target = (Player)callee;
		}
		return (target);
	}
	
	public static void ft_menu_base_hud(Inventory inv)
	{
		ItemStack item = new ItemStack(Material.STONE, 1);
		ItemMeta meta = item.getItemMeta();
		
		//exit button
		item.setType(Material.REDSTONE);
		meta.setDisplayName(ChatColor.BLACK + "-=- " + ChatColor.RED + "Exit" + ChatColor.BLACK + " -=-");
		item.setItemMeta(meta);
		inv.	setItem(0, item);
		
		//back button
		item.setType(Material.GLOWSTONE_DUST);
		meta.setDisplayName(ChatColor.GOLD + "-=- " + ChatColor.YELLOW + "undo" + ChatColor.GOLD + " -=-");
		item.setItemMeta(meta);
		inv.setItem(5, item);
		
		//page prev
		item.setType(Material.ARROW);
		meta.setDisplayName(ChatColor.GRAY + " Previous Page ");
		item.setItemMeta(meta);
		inv.	setItem(7, item);
		
		//next page
		meta.setDisplayName(ChatColor.GRAY + "   Next Page   ");
		item.setItemMeta(meta);
		inv.	setItem(8, item);
		
		//frame
		item.setType(Material.STAINED_GLASS_PANE);
		item.setDurability((short) 15);
		meta.setDisplayName("  ");
		item.setItemMeta(meta);
		inv.setItem(9, item);
		inv.setItem(10, item);
		inv.setItem(11, item);
		inv.setItem(12, item);
		inv.setItem(13, item);
		inv.setItem(14, item);
		inv.setItem(15, item);
		inv.setItem(16, item);
		inv.setItem(17, item);
		inv.setItem(18, item);
		inv.setItem(26, item);
		inv.setItem(27, item);
		inv.setItem(35, item);
		inv.setItem(36, item);
		inv.setItem(44, item);
		inv.setItem(45, item);
		inv.setItem(46, item);
		inv.setItem(47, item);
		inv.setItem(48, item);
		inv.setItem(49, item);
		inv.setItem(50, item);
		inv.setItem(51, item);
		inv.setItem(52, item);
		inv.setItem(53, item);
	}
	
    public static String ft_get_file_extension(String str)
    {
        if(str.lastIndexOf(".") != -1 && str.lastIndexOf(".") != 0)
        		return str.substring(str.lastIndexOf(".")+1);
        else
        		return "";
    }
	
    public static String ft_convert_to_invisible_string(String s) {
        String hidden = "";
        for (char c : s.toCharArray()) hidden += ChatColor.COLOR_CHAR+""+c;
        return hidden;
    }
    
	public static Inventory ft_get_item_list(String file)
	{
		Inventory inv = Bukkit.getServer().createInventory(null, 900, "Monumentanomicon");
		Charset charset = Charset.forName("ISO-8859-1");
		ItemStack item = new ItemStack(Material.STONE, 1);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		
		int i = 1;
		
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(file), charset))
		{
		    String line = null;
		    if (ft_get_file_extension(file).equalsIgnoreCase("mmnopage"))
		    {
		    		while ((line = reader.readLine()) != null)
		    		{
		    			if (line.equalsIgnoreCase("backlink"))
		    			{
		    				item.setType(Material.ARROW);
		    				meta = item.getItemMeta();
		    				lore = meta.getLore();
		    				if (lore == null)
		    					lore = new ArrayList<String>();
		    				lore.add(ft_convert_to_invisible_string("Link:" + (line = reader.readLine())));
		    				meta.setLore(lore);
		    				meta.setDisplayName(ChatColor.DARK_GREEN + "-=- " + ChatColor.GREEN + "back" + ChatColor.DARK_GREEN + " -=-");
		    				item.setItemMeta(meta);
		    				inv.setItem(0, item);
		    				item = new ItemStack(Material.STONE, 1);
		    			}
		    			if (line.equalsIgnoreCase("next"))
		    			{
		    				inv.setItem(i, item);
		    				i++;
		    				item = new ItemStack(Material.STONE, 1);
		    				line = reader.readLine();
		    			}
		    			else if (line.equalsIgnoreCase("link"))
		    			{
		    				meta = item.getItemMeta();
		    				lore = meta.getLore();
		    				if (lore == null)
		    					lore = new ArrayList<String>();
		    				lore.add(ft_convert_to_invisible_string("Link:" + reader.readLine()));
		    				meta.setLore(lore);
		    				item.setItemMeta(meta);
		    			}
		    			else if (line.equalsIgnoreCase("name"))
		    			{
		    				meta = item.getItemMeta();
		    				meta.setDisplayName(reader.readLine());
		    				item.setItemMeta(meta);
		    			}
		    		}
		    }
		    else if (ft_get_file_extension(file).equalsIgnoreCase("mmnomob"))
		    {
		    		while ((line = reader.readLine()) != null)
		    		{
		    			if (line.equalsIgnoreCase("backlink"))
		    			{
		    				item = new ItemStack(Material.BARRIER, 1);
		    				meta = item.getItemMeta();
		    				lore = meta.getLore();
		    				meta.setDisplayName(ChatColor.DARK_RED + "Leave Mobpage");
		    				if (lore == null)
		    					lore = new ArrayList<String>();
		    				lore.add(ft_convert_to_invisible_string("Link:" + reader.readLine()));
		    				meta.setLore(lore);
		    				item.setItemMeta(meta);
		    				inv.setItem(0, item);
		    				item = new ItemStack(Material.STONE, 1);
		    				reader.readLine();
		    				System.out.println("hey");
		    			}
		    			else if (line.equalsIgnoreCase("helm"))
		    			{
		    				String str = reader.readLine().replaceAll("¶", "\n").replaceAll("Â", "");
		    				if (str.equalsIgnoreCase("null"))
		    					item = ft_get_null_item();
		    				else
		    					item = stringBlobToItem(str);
		    				item = ft_add_prefix_to_name(item, "§fHelmet: ");
		    				inv.setItem(1, item);
		    			}
		    			else if (line.equalsIgnoreCase("chest"))
		    			{
		    				String str = reader.readLine().replaceAll("¶", "\n").replaceAll("Â", "");
		    				if (str.equalsIgnoreCase("null"))
		    					item = ft_get_null_item();
		    				else
		    					item = stringBlobToItem(str);
		    				item = ft_add_prefix_to_name(item, "§fChestplate: ");
		    				inv.setItem(2, item);
		    			}
		    			else if (line.equalsIgnoreCase("legs"))
		    			{
		    				String str = reader.readLine().replaceAll("¶", "\n").replaceAll("Â", "");
		    				System.out.println(str);
		    				if (str.equalsIgnoreCase("null"))
		    					item = ft_get_null_item();
		    				else
		    					item = stringBlobToItem(str);
		    				item = ft_add_prefix_to_name(item, "§fLeggings: ");
		    				inv.setItem(3, item);
		    			}
		    			else if (line.equalsIgnoreCase("boots"))
		    			{
		    				String str = reader.readLine().replaceAll("¶", "\n").replaceAll("Â", "");
		    				if (str.equalsIgnoreCase("null"))
		    					item = ft_get_null_item();
		    				else
		    					item = stringBlobToItem(str);
		    				item = ft_add_prefix_to_name(item, "§fBoots: ");
		    				inv.setItem(4, item);
		    			}
		    			else if (line.equalsIgnoreCase("name"))
		    			{
		    				String str = reader.readLine();
		    				item = new ItemStack(Material.NAME_TAG, 1);
		    				meta = item.getItemMeta();
		    				meta.setDisplayName("Name: " + str);
		    				item.setItemMeta(meta);
		    				inv.setItem(5, item);
		    			}
		    			else if (line.equalsIgnoreCase("ai"))
		    			{
		    				String str = reader.readLine();
		    				String str2 = ft_monster_to_ai_string(str).replaceAll("¶", "\n").replaceAll("Â", "");
		    				item = stringBlobToItem(str2);
		    				item = ft_add_prefix_to_name(item, "§fMonster AI: ");
		    				inv.setItem(6, item);
		    			}
		    			else if (line.equalsIgnoreCase("family"))
		    			{
		    				String str = reader.readLine().replaceAll("¶", "\n").replaceAll("Â", "");
		    				if (str.equalsIgnoreCase("null"))
		    					item = ft_get_null_item();
		    				else
		    					item = stringBlobToItem(str);
		    				item = ft_add_prefix_to_name(item, "§fFamily: ");
		    				inv.setItem(7, item);
		    			}
		    			else if (line.equalsIgnoreCase("rarity"))
		    			{
		    				String str = ft_get_rarity_string(reader.readLine()).replaceAll("¶", "\n").replaceAll("Â", "");
		    				item = stringBlobToItem(str);
		    				item = ft_add_prefix_to_name(item, "§fMonster Rarity: ");
		    				inv.setItem(8, item);
		    			}
		    			else if (line.equalsIgnoreCase("lore"))
		    			{
		    				String str = reader.readLine().replaceAll("¶", "\n").replaceAll("Â", "");
		    				item = stringBlobToItem(str);
		    				item = ft_add_prefix_to_name(item, "§fLore: ");
		    				inv.setItem(9, item);
		    			}
		    			else if (line.equalsIgnoreCase("offhand"))
		    			{
		    				String str = reader.readLine().replaceAll("¶", "\n").replaceAll("Â", "");
		    				if (str.equalsIgnoreCase("null"))
		    					item = ft_get_null_item();
		    				else
		    					item = stringBlobToItem(str);
		    				item = ft_add_prefix_to_name(item, "§fOffhand: ");
		    				inv.setItem(10, item);
		    			}
		    			else if (line.equalsIgnoreCase("health"))
		    			{
		    				String input = reader.readLine();
		    				String str = ("i:¶  ==: org.bukkit.inventory.ItemStack¶  type: INK_SACK¶  damage: 1¶  meta:¶    ==: ItemMeta¶    meta-type: UNSPECIFIC¶    display-name: §c§lHealth Points¶    lore:¶    - §7this mob has §c§l" + input + " §7Health Points¶");
		    				str = str.replaceAll("¶", "\n").replaceAll("Â", "");
		    				item = stringBlobToItem(str);
		    				item.setAmount(Integer.parseInt(input));
		    				inv.setItem(11, item);
		    			}
		    			else if (line.equalsIgnoreCase("armor"))
		    			{
		    				String input = reader.readLine();
		    				String str = ("i:¶  ==: org.bukkit.inventory.ItemStack¶  type: IRON_INGOT¶  meta:¶    ==: ItemMeta¶    meta-type: UNSPECIFIC¶    display-name: §7§lArmor Points¶    lore:¶    - §7this mob has §7§l" + input + " §7Armor Points¶");
		    				str = str.replaceAll("¶", "\n").replaceAll("Â", "");
		    				item = stringBlobToItem(str);
		    				item.setAmount(Integer.parseInt(input));
		    				inv.setItem(12, item);
		    			}
		    			else if (line.equalsIgnoreCase("attack"))
		    			{
		    				String input = reader.readLine();
		    				String str = ("i:¶  ==: org.bukkit.inventory.ItemStack¶  type: STICK¶  meta:¶    ==: ItemMeta¶    meta-type: UNSPECIFIC¶    display-name: §6§lAttack Damage¶    lore:¶    - §7this mob has §6§l" + input + " §7Attack Damage¶");
		    				str = str.replaceAll("¶", "\n").replaceAll("Â", "");
		    				item = stringBlobToItem(str);
		    				item.setAmount(Integer.parseInt(input));
		    				inv.setItem(13, item);
		    			}
		    			else if (line.equalsIgnoreCase("mainhand"))
		    			{
		    				String str = reader.readLine().replaceAll("¶", "\n").replaceAll("Â", "");
		    				if (str.equalsIgnoreCase("null"))
		    					item = ft_get_null_item();
		    				else
		    					item = stringBlobToItem(str);
		    				item = ft_add_prefix_to_name(item, "§fMainhand: ");
		    				inv.setItem(14, item);
		    			}
		    		}
		    }
		}
		catch (IOException x)
		{
		    System.err.format("IOException: %s%n", x);
		}
		return (inv);
	}
	
	public static String ft_get_rarity_string(String str)
	{
		if (str.equalsIgnoreCase("common"))
			return ("i:¶  ==: org.bukkit.inventory.ItemStack¶  type: GRASS¶  meta:¶    ==: ItemMeta¶    meta-type: UNSPECIFIC¶    display-name: §aCommon¶    lore:¶    - §7This mob can spawn naturally¶    - §7and from spawners¶");
		else if (str.equalsIgnoreCase("uncommon"))
			return ("i:¶  ==: org.bukkit.inventory.ItemStack¶  type: IRON_BLOCK¶  meta:¶    ==: ItemMeta¶    meta-type: UNSPECIFIC¶    display-name: §7Uncommon¶    lore:¶    - §7This mob can only be¶    - §7found inside spawners¶");
		else if (str.equalsIgnoreCase("rare"))
			return ("i:¶  ==: org.bukkit.inventory.ItemStack¶  type: GOLD_BLOCK¶  meta:¶    ==: ItemMeta¶    meta-type: UNSPECIFIC¶    display-name: §eRare¶    lore:¶    - §7This mob can only be¶    - §7found inside spawners¶    - §7and wears the \"Elite\" tag¶");
		else if (str.equalsIgnoreCase("epic"))
			return ("i:¶  ==: org.bukkit.inventory.ItemStack¶  type: DIAMOND_BLOCK¶  meta:¶    ==: ItemMeta¶    meta-type: UNSPECIFIC¶    display-name: §bLegendary¶    lore:¶    - §7This mob is supposedly¶    - §7the only one of his kind¶");
		return ("i:¶  ==: org.bukkit.inventory.ItemStack¶  type: CONCRETE¶  meta:¶    ==: ItemMeta¶    meta-type: UNSPECIFIC¶    display-name: unknown¶    lore:¶    - §7something went wrong while¶    - §7reading the mob rarity.¶");
	}
	
	public static String ft_monster_to_ai_string(String str)
	{
		if (str.equalsIgnoreCase("zombie"))
			return ("i:¶  ==: org.bukkit.inventory.ItemStack¶  type: SKULL_ITEM¶  damage: 2¶  meta:¶    ==: ItemMeta¶    meta-type: SKULL¶    display-name: §2Zombie¶    lore:¶    - §7The AI of that mob¶    - §7is similar to a Zombie¶");
		else if (str.equalsIgnoreCase("creeper"))
			return ("i:¶  ==: org.bukkit.inventory.ItemStack¶  type: SKULL_ITEM¶  damage: 4¶  meta:¶    ==: ItemMeta¶    meta-type: SKULL¶    display-name: §aCreeper¶    lore:¶    - §7The AI of that mob¶    - §7is similar to a Creeper¶");
		else if (str.equalsIgnoreCase("skeleton"))
			return ("i:¶  ==: org.bukkit.inventory.ItemStack¶  type: SKULL_ITEM¶  meta:¶    ==: ItemMeta¶    meta-type: SKULL¶    display-name: §7Skeleton¶    lore:¶    - §7The AI of that mob¶    - §7is similar to a Skeleton¶");
		else if (str.equalsIgnoreCase("spider"))
			return ("i:¶  ==: org.bukkit.inventory.ItemStack¶  type: SKULL_ITEM¶  damage: 3¶  meta:¶    ==: ItemMeta¶    meta-type: SKULL¶    display-name: §8Spider¶    lore:¶    - §7The AI of that mob¶    - §7is similar to a Spider¶    internal: H4sIAAAAAAAAAE2PwW6CQBiE/zZpYkkfo1cSWBaBgwdTqy7RpSqguzeBVVkWahSq63P1AcuxhznMfDOHMQAMeNtUnVJfl+9DqcQAnkkB735WZJ5dWCb2EDaxnyEzcDA2hRdg38IIe4FvgNGPzuLSluL6CoNW3NvuIq4GADwN4CXdq07Ar9ChxXcnq9iFKtdk2Pt4Y6mIyLNHmlRnH2RI6p7Px8OFDv513Xa/dRVzwhNvVl1Wp9bCWSsxX9t5nfwwtLLp5LPXtGLbac1lcosmFepzTeNTyWdhxSdryeXxxh60ZPHxTmX+oHGuqRw70Sysebx0l49KU5TYFLE7aezgsBqN+gfwB8I+kI4cAQAA¶");
		return ("i:¶  ==: org.bukkit.inventory.ItemStack¶  type: SKULL_ITEM¶  damage: 3¶  meta:¶    ==: ItemMeta¶    meta-type: SKULL¶    display-name: §fUnknown¶    lore:¶    - §7The AI of that mob¶    - §7does not look like anything¶    internal: H4sIAAAAAAAAAE2OzU6DQBzE/5qYIPExvJIs7ELpwYOxabukC9JSPvbGxyLgUpsWKvBUPqJ48zCTTH4zyagAKjwdPnsp3y9fZS2FAve0gGdCsCGKVGgYk0wjtm1r2SLTNWKWJSIi1VPLVkGdR2dx6WpxfQSlE0PXX8RVBYA7BR7CVPYCfsToIB5XqIgdmY/UmnNwQNKjzXlBT+GYvVGLtjPfvlq7cfmva3ZpZMoEOxU/+X3WhmiH91Js93reHm+JsW7ZiplJGzYs4n+q3ek4eEHy7QVOzRou3YYNM8FJ8IH5hrcs8KfZB29FTXfKTW/jI9dgOo/WFa+dZRmjl/k9/AJOS8veGAEAAA==¶");
	}
	
	public static ItemStack ft_add_prefix_to_name(ItemStack item, String str)
	{
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(str + meta.getDisplayName());
		item.setItemMeta(meta);
		return (item);
	}
	
	public static ItemStack ft_get_null_item()
	{
		ItemStack item = new ItemStack(Material.GLASS, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GRAY + "None");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GRAY + "Nothing equiped in this slot.");
		meta.setLore(lore);
		item.setItemMeta(meta);
		return (item);
	}
	
	public static String itemToStringBlob(ItemStack itemStack) {
        YamlConfiguration config = new YamlConfiguration();
        config.set("i", itemStack);
        return config.saveToString();
    }
	
    public static ItemStack stringBlobToItem(String stringBlob) {
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.loadFromString(stringBlob);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return config.getItemStack("i", null);
    }
}
