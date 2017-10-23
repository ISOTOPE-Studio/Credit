package cc.isotopestudio.credit;
/*
 * Created by david on 10/22/2017.
 * Copyright ISOTOPE Studio
 */

import cc.isotopestudio.credit.util.S;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        int credit = CreditDataManager.getPlayerCredit(player, event.getBlock().getTypeId(), event.getBlock().getData());
        if (credit < 0) return;
        else if (credit == 0) {
            event.setCancelled(true);
            player.sendMessage(S.toRed("You cannot place the block since you don't have enough credit"));
        }
        if (!event.isCancelled()) {
            CreditDataManager.minusPlayerCredit(player, event.getBlock().getTypeId(), event.getBlock().getData());
        }
    }
}
