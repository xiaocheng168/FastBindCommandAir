package cc.mcyx.fast.command

import cc.mcyx.paimon.common.command.PaimonCommand
import cc.mcyx.paimon.common.plugin.Paimon

class FastCommand(paimon: Paimon, command: String) : PaimonCommand(paimon, command) {
    init {
        paimonExec { sender, command, args ->
            return@paimonExec true
        }
        paimonTab { sender, command, args ->
            return@paimonTab mutableListOf()
        }
    }
}