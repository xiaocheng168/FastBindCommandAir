package cc.mcyx.fast.command

import cc.mcyx.paimon.common.command.PaimonCommand
import cc.mcyx.paimon.common.minecraft.network.PaimonSender
import cc.mcyx.paimon.common.plugin.Paimon

class FastCommand(paimon: Paimon) : PaimonCommand(paimon, "bindItemCommand") {
    init {
        description = "物品行为帮助命令"
        addSubCommand(BindCommand(paimon))
        addSubCommand(InfoCommand(paimon))

        paimonExec { sender, _, _ ->
            PaimonSender.sendMessage(sender, "&a > Run Version &f${paimon.description.version}")
            PaimonSender.sendCommandHelp(sender, this)
            return@paimonExec true
        }
    }
}