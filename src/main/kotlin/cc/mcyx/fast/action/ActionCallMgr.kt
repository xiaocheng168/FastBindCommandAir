package cc.mcyx.fast.action

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * 解析物品的Action行为
 */
abstract class ActionCallMgr {
    companion object {
        @JvmStatic
        fun bindItem(player: Player, itemStack: ItemStack, actionId: String) {
        }
    }
}