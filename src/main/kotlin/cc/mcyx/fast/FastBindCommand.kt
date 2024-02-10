package cc.mcyx.fast

import cc.mcyx.fast.command.FastCommand
import cc.mcyx.paimon.common.PaimonPlugin
import cc.mcyx.paimon.common.config.PaimonConfig

class FastBindCommand : PaimonPlugin() {

    companion object {
        @JvmStatic
        lateinit var bindConfig: PaimonConfig
    }

    override fun onLoaded() {
        bindConfig = PaimonConfig(this, "config.yml", saveResource = true)
    }

    override fun onEnabled() {
        logger.info("物品绑定命令已启用")
        FastCommand(this, "bindItemCommand").register()
    }
}