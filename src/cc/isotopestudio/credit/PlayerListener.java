package cc.isotopestudio.credit;
/*
 * Created by david on 10/22/2017.
 * Copyright ISOTOPE Studio
 */

import cc.isotopestudio.credit.sql.SqlManager;
import cc.isotopestudio.credit.util.S;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        String item = CreditDataManager.getItemString(event.getBlock().getTypeId(), event.getBlock().getData());
        OfflinePlayer player = SqlManager.getRecord(event.getBlock().getLocation(), item);
        if (player == null) return;
        CreditDataManager.addPlayerCredit(player, event.getBlock().getTypeId(), event.getBlock().getData(), 1);
        if (player.equals(event.getPlayer())) {
            event.getPlayer().sendMessage(S.toYellow("You have " +
                    CreditDataManager.getPlayerCredit(player, event.getBlock().getTypeId(), event.getBlock().getData())
                    + " credits remaining."));
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (player.isOp()) return;
        int id = event.getBlock().getTypeId();
        byte data = event.getBlock().getData();
        String item = CreditDataManager.getItemString(id, data);
        int credit = CreditDataManager.getPlayerCredit(player, id, data);
        if (credit < 0) {
            if (ConfigUpdateTask.defaultCredit.containsKey(item)) {
                CreditDataManager.addPlayerCredit(player, id, data, ConfigUpdateTask.defaultCredit.get(item));
                credit = CreditDataManager.getPlayerCredit(player, id, data);
            }
        } else if (credit == 0) {
            event.setCancelled(true);
            player.sendMessage(S.toRed("You cannot place the block since you don't have enough credit"));
        }
        if (!event.isCancelled()) {
            CreditDataManager.minusPlayerCredit(player, id, data);
            SqlManager.addRecord(player, event.getBlock().getLocation(), item);
            player.sendMessage(S.toYellow("You have " + --credit + " credits remaining."));
        }
    }
}
