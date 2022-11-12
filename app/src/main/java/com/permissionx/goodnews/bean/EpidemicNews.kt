package com.permissionx.goodnews.bean

data class EpidemicNews(val msg: String = "",
                        val result: Result,
                        val code: Int = 0)

data class Result(val list: List<ListItem>?)

data class ListItem(val digest: String = "",
                    val source: String = "",
                    val mtime: String = "",
                    val title: String = "",
                    val imgsrc: String = "",
                    val url: String = "")