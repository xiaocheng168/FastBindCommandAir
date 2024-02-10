package cc.mcyx.fast.action

import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

/**
 * 解析物品的Action行为
 */
abstract class ActionCallMgr {
    companion object {
        @JvmStatic
        fun callAction(
            player: Player,
            actionId: String,
            bukkitAction: Action,
            itemStack: ItemStack,
            event: PlayerInteractEvent
        ) {
            ActionConfig.actionList[actionId]?.also { ac ->
                if (
                    (ac.mode == ActionMode.LEFT && !player.isSneaking && (bukkitAction == Action.LEFT_CLICK_AIR || bukkitAction == Action.LEFT_CLICK_BLOCK)) ||
                    (ac.mode == ActionMode.SHIFT_LEFT && player.isSneaking && (bukkitAction == Action.LEFT_CLICK_AIR || bukkitAction == Action.LEFT_CLICK_BLOCK))
                ) {
                    ac.runAction(player, itemStack, event)
                }
                if (ac.mode == ActionMode.RIGHT && !player.isSneaking && (bukkitAction == Action.RIGHT_CLICK_AIR || bukkitAction == Action.RIGHT_CLICK_BLOCK) ||
                    ac.mode == ActionMode.SHIFT_RIGHT && player.isSneaking && (bukkitAction == Action.RIGHT_CLICK_AIR || bukkitAction == Action.RIGHT_CLICK_BLOCK)
                ) {
                    ac.runAction(player, itemStack, event)
                }
                return
            }
        }
    }
}