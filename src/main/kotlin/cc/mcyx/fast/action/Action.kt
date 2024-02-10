package cc.mcyx.fast.action

import cc.mcyx.fast.util.PapiUtil
import cc.mcyx.paimon.common.minecraft.network.PaimonSender
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent
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
    val isCancelEvent: Boolean
) {
    /**
     * 执行此交互行为
     */
    fun runAction(player: Player, itemStack: ItemStack, event: PlayerInteractEvent) {
        //是否取消事件
        event.isCancelled = isCancelEvent
        if (!player.hasPermission(permission)) {
            noPermissionMessage.takeIf { it != "" }.also {
                PaimonSender.sendMessage(player, noPermissionMessage)
            }
        } else {
            if (isDie) {
                if (--itemStack.amount <= 0) {
                    itemStack.type = Material.AIR
                }
            }
            commands.forEach { command ->
                val sender = command.split(" ")[0]
                var rc: String = command.replace("$sender ", "").replace("%player_name%", player.name)
                Bukkit.getPluginManager().getPlugin("PlaceholderAPI")?.also {
                    rc = PapiUtil.papiFormat(rc, player)
                }
                when (sender) {
                    //玩家以OP权限执行
                    "op" -> {
                        val isOp = player.isOp
                        try {
                            player.performCommand(rc)
                        } catch (e: Throwable) {
                            PaimonSender.sendMessage(player, "§c执行出错!,请联系服务器管理员!")
                            e.printStackTrace()
                        } finally {
                            //如何对方不是op，那么取消他的op!
                            player.isOp = isOp
                        }
                    }
                    //玩家普通执行
                    "player" -> player.performCommand(rc)
                    //控制台执行
                    "console" -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), rc)
                }
            }
        }
    }
}