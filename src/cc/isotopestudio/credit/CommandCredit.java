package cc.isotopestudio.credit;
/*
 * Created by david on 10/22/2017.
 * Copyright ISOTOPE Studio
 */

import cc.isotopestudio.credit.util.S;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Map;

import static cc.isotopestudio.credit.Credit.config;

public class CommandCredit implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("credit")) {
            if (!sender.isOp()) return false;
            if (args.length < 1) {
                sender.sendMessage(S.toGreen("/" + label + " add <username> <itemID:type> <amountOfCredits>"));
                sender.sendMessage(S.toGreen("/" + label + " remove <username> <itemID:type> <amountOfCredits>"));
                sender.sendMessage(S.toGreen("/" + label + " <username>"));
                sender.sendMessage(S.toGreen("/" + label + " reload - reload config"));
                return true;
            }
            if (args[0].equalsIgnoreCase("add")) {
                if (args.length != 4) {
                    sender.sendMessage(S.toGreen("/" + label + " add <username> <itemID> <amountOfCredits>"));
                    return true;
                }
                OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
                if (player == null) {
                    sender.sendMessage(S.toRed("The player does not exist"));
                    return true;
                }

                String[] strings = args[2].split(":");
                int type;
                byte b = 0;
                try {
                    type = Integer.parseInt(strings[0]);
                    if (strings.length == 2) {
                        b = (byte) Integer.parseInt(strings[1]);
                    }
                } catch (NumberFormatException e) {
                    sender.sendMessage(S.toRed("The format of the number isn't correct"));
                    return true;
                }
                int credit;
                try {
                    credit = Integer.parseInt(args[3]);
                    if (credit < 0) throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    sender.sendMessage(S.toRed("The format of the number isn't correct"));
                    return true;
                }
                CreditDataManager.addPlayerCredit(player, type, b, credit);
                return true;
            }
            if (args[0].equalsIgnoreCase("remove")) {
                if (args.length != 4) {
                    sender.sendMessage(S.toGreen("/" + label + " remove <username> <itemID> <amountOfCredits>"));
                    return true;
                }
                OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
                if (player == null) {
                    sender.sendMessage(S.toRed("The player does not exist"));
                    return true;
                }
                String[] strings = args[2].split(":");
                int type;
                byte b = 0;
                try {
                    type = Integer.parseInt(strings[0]);
                    if (strings.length == 2) {
                        b = (byte) Integer.parseInt(strings[1]);
                    }
                } catch (NumberFormatException e) {
                    sender.sendMessage(S.toRed("The format of the number isn't correct"));
                    return true;
                }
                int remove;
                try {
                    remove = Integer.parseInt(args[3]);
                    if (remove < 0) throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    sender.sendMessage(S.toRed("The format of the number isn't correct"));
                    return true;
                }
                int credit = CreditDataManager.getPlayerCredit(player, type, b);
                if (credit > 0) {
                    if (credit - remove < 0) credit = 0;
                    else credit -= remove;
                    CreditDataManager.setPlayerCredit(player, type, b, credit);
                }

                return true;
            }
            if (args[0].equalsIgnoreCase("reload")) {
                config.reload();
                ConfigUpdateTask.run();
                sender.sendMessage(S.toGreen("Reload complete"));
            }
            OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
            if (player == null) {
                sender.sendMessage(S.toRed("The player does not exist"));
                return true;
            }
            sender.sendMessage(S.toGray("-- Credit Info for " + player.getName() + " --"));
            Map<String, Integer> creditInfo = CreditDataManager.getPlayerCredit(player);
            for (String material : creditInfo.keySet()) {
                sender.sendMessage(S.toGreen("  " + material.replaceAll(":0", ""))
                        + S.toGold(" " + creditInfo.get(material)));
            }
            return true;
        }
        return false;
    }
}
