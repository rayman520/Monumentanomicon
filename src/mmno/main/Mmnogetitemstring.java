package mmno.main;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Mmnogetitemstring implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender send, Command command, String label, String[] args)
	{
		Player sender = BaseFuncts.ft_get_proxied_player(send);
		if (sender == null)
		{
			send.sendMessage(ChatColor.RED + "This command must be run by/on a player!");
			return false;
		}
		
		ItemStack item = sender.getInventory().getItemInMainHand();
		if (item == null || item.getType() == Material.AIR)
		{
			send.sendMessage(ChatColor.RED + "No item found in main hand!");
			return (true);
		}
		String str = BaseFuncts.itemToStringBlob(item).replaceAll("\n", "¶");
		send.sendMessage(ChatColor.YELLOW + "\n\nItem string:\n\n" + ChatColor.WHITE + str);
		System.out.println(str);
		send.sendMessage(ChatColor.GRAY + "\n\n\nthis same string has also been sent to the server Console.");
		
		Block block = sender.getTargetBlock(null, 5);
		BlockState state = block.getState();
		if (block.getType() == Material.COMMAND || block.getType() == Material.COMMAND_CHAIN || block.getType() == Material.COMMAND_REPEATING)
		{
			((CommandBlock) state).setCommand(str);
			state.update();
			send.sendMessage(ChatColor.GREEN + "The item string has also been pasted in the command block you're pointing!");
		}
		return (true);
	}
}
