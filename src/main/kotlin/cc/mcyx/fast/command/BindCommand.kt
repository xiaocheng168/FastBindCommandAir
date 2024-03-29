package cc.mcyx.fast.command

import cc.mcyx.fast.action.ActionConfig
import cc.mcyx.fast.exception.MessageException
import cc.mcyx.fast.util.ActionItemBuild
import cc.mcyx.paimon.common.command.PaimonCommand
import cc.mcyx.paimon.common.minecraft.network.PaimonSender
import cc.mcyx.paimon.common.plugin.Paimon
import org.bukkit.entity.Player

class BindCommand(paimon: Paimon) : PaimonCommand(paimon, "set") {
    init {
        this.description = "&f[Action] &a绑定一个 Action 行为"
        paimonExec { sender, _, args ->
            try {
                if (sender is Player) {
                    val bindItem = sender.inventory.itemInMainHand ?: throw MessageException("§c主手上必须拿着物品")
                    if (bindItem.typeId == 0) throw MessageException("§c主手上必须拿着物品")
                    if (args.size >= 2) {
                        val actionId = args[args.size - 1]
                        ActionItemBuild.bindItem(sender, bindItem, actionId)
                        PaimonSender.sendMessage(sender, "&a已将 &f$actionId &a行为绑定在此物品上!")
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
                ActionConfig.actionList.keys.forEach {
                    rt.add(it)
                }
            }
        }
    }
}