package cc.mcyx.fast.util

import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.entity.Player

abstract class PapiUtil {
    companion object {

        /**
         * 解析字符串里所有Papi变量
         * @param string 原字符
         * @return 返回解析完后的Papi文本
         */
        @JvmStatic
        fun papiFormat(string: String, player: Player): String {
            //获取字符串里所有%xxx%变量打包成list
            val list = string.split("%").filter { it.isNotEmpty() }.toMutableList()
            //遍历list
            for (i in list.indices) {
                //获取变量名
                val varName = list[i].substring(1, list[i].length - 1)
                //获取变量值
                val varValue = PlaceholderAPI.setPlaceholders(player, varName)
                //替换变量
                list[i] = varValue
            }
            return list.joinToString("")
        }
    }
}