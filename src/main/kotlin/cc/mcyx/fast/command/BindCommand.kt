package cc.mcyx.fast.command

import cc.mcyx.fast.action.ActionCallMgr
import cc.mcyx.fast.action.ActionConfig
import cc.mcyx.fast.exception.MessageException
import cc.mcyx.paimon.common.command.PaimonCommand
import cc.mcyx.paimon.common.minecraft.network.PaimonSender
import cc.mcyx.paimon.common.plugin.Paimon
import com.sun.xml.internal.ws.api.server.AbstractInstanceResolver
import org.bukkit.Material
import org.bukkit.entity.Player

class BindCommand(paimon: Paimon) : PaimonCommand(paimon, "open") {
    init {
        paimonExec { sender, _, args ->
            try {
                if (sender is Player) {
                    val bindItem = sender.inventory.itemInMainHand
                    if (bindItem == null || bindItem.type == Material.AIR) throw MessageException("§c主手上必须拿着物品")
                    if (args.size >= 2) {
                        val actionId = args[args.size - 1]
                        ActionCallMgr.bindItem(sender, bindItem, actionId)
                    } else throw MessageException("§c缺少 §factionId §c参数")
                } else throw MessageException("§c后台不可以执行这个命令哦~")
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
        paimonTab { _, _, _ ->
            return@paimonTab mutableListOf<String>().also { rt ->
                ActionConfig.actionList.forEach {
                    rt.add(it.name)
                }
            }
        }
    }
}