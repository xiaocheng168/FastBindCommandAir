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
            var replaceOver = string
            string.split("%").forEachIndexed { index, s ->
                if (index % 2 == 1) {
                    replaceOver = replaceOver.replace("%${s}%", PlaceholderAPI.setPlaceholders(player, "%${s}%"))
                }
            }
            return replaceOver
        }
    }
}