package com.permissionx.goodnews.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.permissionx.goodnews.db.bean.ListItem

/**
 * 类型转换器，room不能直接将集合数据写入数据库，需要添加类型转换器
 */

class ResultConverter {


    @TypeConverter
    fun stringToResult(value: String): com.permissionx.goodnews.db.bean.Result {
        val listType = object : TypeToken<com.permissionx.goodnews.db.bean.Result>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun resultToString(result: com.permissionx.goodnews.db.bean.Result): String = Gson().toJson(result)
}