package cc.mcyx.fast

import cc.mcyx.fast.action.ActionConfig
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
        ActionConfig.getActionListReload()
        //当配置文件被修改与更新，读入配置文件并且重新解析所有行为
        bindConfig.configReload {
            ActionConfig.getActionListReload()
        }
    }

    override fun onEnabled() {
        logger.info("物品绑定命令已启用")
        logger.info("已加载 ${ActionConfig.actionList.size} 个交互行为")
        FastCommand(this).register()
    }
}