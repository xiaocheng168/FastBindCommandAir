package cc.mcyx.fast.action

import cc.mcyx.fast.FastBindCommand

object ActionConfig {

    //行为列表
    val actionList = mutableListOf<Action>()
    /**
     * 读入配置文件里的所有行为
     */
    fun getActionListReload(): MutableList<Action> {
        return actionList.also { rt ->
            actionList.clear()
            val actionConfig = FastBindCommand.bindConfig.config.getConfigurationSection("actions")
            actionConfig?.also { ac ->
                ac.getKeys(false).forEach {
                    Action(
                        it,
                        actionConfig.getBoolean("${it}.enable", false),
                        actionConfig.getBoolean("${it}.isDie", false),
                        actionConfig.getString("${it}.permission", ""),
                        actionConfig.getString("${it}.no-permission-message", ""),
                        actionConfig.getStringList("${it}.commands"),
                        ActionMode.valueOf(actionConfig.getString("${it}.action", "RIGHT")),
                    ).apply {
                        rt.add(this)
                    }
                }
            }
        }
    }
}