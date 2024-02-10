package cc.mcyx.fast.listener

import cc.mcyx.fast.action.ActionCallMgr
import cc.mcyx.fast.util.ActionItemBuild
import cc.mcyx.paimon.common.listener.PaimonAutoListener
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerInteractEvent

class FastListener : PaimonAutoListener {
    @EventHandler
    fun onEvent(e: PlayerInteractEvent) {
        e.player.also { player ->
            player.inventory.itemInMainHand.also { item ->
                if (item.hasItemMeta()) {
                    ActionItemBuild.getItemBindAction(item)?.also { ac ->
                        ActionCallMgr.callAction(player, ac, e.action, item, e)
                    }
                }
            }
        }
    }
}