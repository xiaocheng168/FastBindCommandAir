package cc.mcyx.fast.command

import cc.mcyx.fast.exception.MessageException
import cc.mcyx.fast.util.ActionItemBuild
import cc.mcyx.paimon.common.command.PaimonCommand
import cc.mcyx.paimon.common.minecraft.network.PaimonSender
import cc.mcyx.paimon.common.plugin.Paimon
import org.bukkit.entity.Player

class InfoCommand(paimon: Paimon) : PaimonCommand(paimon, "info") {
    init {
        this.description = "查看手中的物品绑定的 Action"
        paimonExec { sender, _, _ ->
            try {
                if (sender is Player) {
                    sender.inventory.itemInMainHand.also {
                        ActionItemBuild.getItemBindAction(it).also { ac ->
                            if (ac == null) throw MessageException("&a手上的物品没有绑定任何 &fAction &a行为")
                            PaimonSender.sendMessage(sender, "&a当前绑定物品的行为Id：&f${ac}")
                        }
                    }
                }
            } catch (e: Throwable) {
                if (e is MessageException) {
                    PaimonSender.sendMessage(sender, e.msg)
                } else {
                    PaimonSender.sendMessage(sender, "§c系统错误，请联系服务器管理员后台日志，并且反馈问题!")
                    e.printStackTrace()
                }
            }
            return@paimonExec true
        }
    }
}