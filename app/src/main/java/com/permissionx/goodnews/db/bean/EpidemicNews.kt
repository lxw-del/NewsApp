package com.permissionx.goodnews.db.bean

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.permissionx.goodnews.db.converter.ResultConverter

@TypeConverters(ResultConverter::class)
@Entity
data class EpidemicNews(
                        @PrimaryKey var id:Int = 0,
                        var msg: String = "",
                        var result: Result,
                        var code: Int = 0)

data class Result(var list: List<ListItem>?)


data class ListItem(val id:Int = 0,
                    val digest: String = "",
                    val source: String = "",
                    val mtime: String = "",
                    val title: String = "",
                    val imgsrc: String = "",
                    val url: String = "")