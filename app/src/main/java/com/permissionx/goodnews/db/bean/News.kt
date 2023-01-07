package com.permissionx.goodnews.db.bean

data class News(val msg: String = "",
                val result: NewsResult,
                val code: Int = 0)

data class NewsResult(val curpage: Int = 0,
                  val allnum: Int = 0,
                  val newslist: List<NewslistItem>?)

data class NewslistItem(val picUrl: String = "",
                        val ctime: String = "",
                        val description: String = "",
                        val id: String = "",
                        val source: String = "",
                        val title: String = "",
                        val url: String = "")