package com.permissionx.goodnews.db.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

data class EpidemicNews(val msg: String = "",
                        val result: Result,
                        val code: Int = 0)

data class Result(val list: List<ListItem>?)

@Entity
data class ListItem(@PrimaryKey val id:Int = 0,
                    val digest: String = "",
                    val source: String = "",
                    val mtime: String = "",
                    val title: String = "",
                    val imgsrc: String = "",
                    val url: String = "")