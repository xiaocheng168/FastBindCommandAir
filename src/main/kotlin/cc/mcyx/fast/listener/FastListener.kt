package cc.mcyx.fast.listener

import cc.mcyx.paimon.common.listener.PaimonAutoListener
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerInteractEvent

class FastListener : PaimonAutoListener {
    @EventHandler(
        ignoreCancelled = true
    )
    fun onEvent(player: PlayerInteractEvent) {
        val item = player.item
        //交互的不是原物品
        if (item.hasItemMeta()) {

        }
    }
}