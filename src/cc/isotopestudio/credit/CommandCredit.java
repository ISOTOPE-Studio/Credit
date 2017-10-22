package cc.isotopestudio.credit;
/*
 * Created by david on 10/22/2017.
 * Copyright ISOTOPE Studio
 */

import cc.isotopestudio.credit.util.S;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandCredit implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("credit")) {
            if (!sender.isOp()) return false;
            if (!(sender instanceof Player)) {
                sender.sendMessage(S.toRed("玩家执行的命令"));
                return true;
            }
            Player player = (Player) sender;
            if (args.length < 1) {
                sender.sendMessage(S.toGreen("/" + label + " add -"));
                return true;
            }
            if (args[0].equalsIgnoreCase("add")) {

                return true;
            }
            return true;
        }
        return false;
    }
}
