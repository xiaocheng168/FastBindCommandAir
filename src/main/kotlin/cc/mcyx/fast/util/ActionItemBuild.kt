package cc.mcyx.fast.util

import cc.mcyx.paimon.common.minecraft.craftbukkit.CraftBukkitPacket
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

abstract class ActionItemBuild {
    companion object {
        private val nbtTagCompoundClass = CraftBukkitPacket.asNMSPacketClass("NBTTagCompound")

        @JvmStatic
        fun bindItem(player: Player, itemStack: ItemStack, actionId: String) {
            val nmsItem = CraftBukkitPacket.bukkitItemToNMSItem(itemStack)

            //获得物品的NBT
            setItemTag(nmsItem, getTag(nmsItem).also {
                setTagString(it, "fbc", actionId)
            })

            player.inventory.itemInMainHand = CraftBukkitPacket.nmsItemToItemStack(nmsItem)
        }

        @JvmStatic
        fun getItemBindAction(itemStack: ItemStack): String? {
            getTag(CraftBukkitPacket.bukkitItemToNMSItem(itemStack)).also { tag ->
                getNBTTag(tag, "fbc").also {
                    return it?.toString()?.replace("\"", "")
                }
            }
        }

        /**
         * 返回NBT数据
         * @param nmsItem NMS物品
         */
        @JvmStatic
        private fun getTag(nmsItem: Any): Any {
            //获得物品的NBT
            var nbtTag = nmsItem.javaClass
                .getDeclaredMethod(
                    if (CraftBukkitPacket.serverId >= 1170) "getTagClone" else "getTag"
                ).also {
                    it.isAccessible = true
                }.invoke(nmsItem)
            //如果nbt是空的，那么要实例化一个nbt
            if (nbtTag == null) nbtTag = nbtTagCompoundClass.newInstance()
            return nbtTag
        }

        /**
         * 设置 NBT 字符串数据
         * @param nbtTag NBTTagCompound
         * @param key NBTKey
         * @param value NBTValue
         */
        @JvmStatic
        private fun setTagString(nbtTag: Any, key: String, value: String): Any {
            //设置物品的NBT
            nbtTag.javaClass.getDeclaredMethod(
                if (CraftBukkitPacket.serverId >= 1170) "a" else "setString",
                String::class.java,
                String::class.java
            ).also {
                it.isAccessible = true
                it.invoke(nbtTag, key, value)
            }
            return nbtTag
        }

        /**
         * 设置NMS物品NBTTag
         * @param nmsItem NMS物品
         * @param nbtTag NBT数据
         */
        @JvmStatic
        private fun setItemTag(nmsItem: Any, nbtTag: Any): Any {
            //将NBT设置回Item
            nmsItem.javaClass
                .getDeclaredMethod("c", nbtTagCompoundClass).also {
                    it.isAccessible = true
                }.invoke(nmsItem, nbtTag)
            return nmsItem
        }

        /**
         * 返回NBT对应Key的数据
         * @param nbtTag NBT数据
         * @param key 键
         * @return 返回任何NBT可存储的数据
         */
        @JvmStatic
        private fun getNBTTag(nbtTag: Any, key: String): Any? {
            //将NBT设置回Item
            return nbtTag.javaClass
                .getDeclaredMethod(if (CraftBukkitPacket.serverId >= 1170) "c" else "get", String::class.java).also {
                    it.isAccessible = true
                }.invoke(nbtTag, key)
        }
    }
}