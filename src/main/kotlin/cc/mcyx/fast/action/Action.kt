package cc.mcyx.fast.action

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * 交互处理
 */
data class Action(
    val name: String,
    val enable: Boolean,
    val isDie: Boolean,
    val permission: String,
    val noPermissionMessage: String,
    val commands: MutableList<String>,
    val mode: ActionMode,
) {
    /**
     * 执行此交互行为
     */
    fun run(player: Player, item: ItemStack) {

    }
}