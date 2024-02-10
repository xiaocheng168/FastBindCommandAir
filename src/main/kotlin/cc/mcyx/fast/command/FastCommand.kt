package cc.mcyx.fast.command

import cc.mcyx.paimon.common.command.PaimonCommand
import cc.mcyx.paimon.common.plugin.Paimon

class FastCommand(paimon: Paimon) : PaimonCommand(paimon, "bindItemCommand") {
    init {
        addSubCommand(BindCommand(paimon))
    }
}