package com.permissionx.goodnews.db.bean

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.permissionx.goodnews.db.converter.ResultConverter

@TypeConverters(ResultConverter::class)
@Entity
data class EpidemicNews(
                       @PrimaryKey var id:Int = 0,
                       val msg: String = "",
                       val result: Result,
                       val code: Int = 0)

data class Result(val news: List<NewsItem>?,
                  val desc: Desc,
                  val riskarea: String = "")

data class NewsItem(val summary: String = "",
                    val sourceUrl: String = "",
                    val id: Int = 0,
                    val title: String = "",
                    val pubDate: Long = 0,
                    val pubDateStr: String = "",
                    val infoSource: String = "")

@Entity
data class Desc(val curedCount: Int = 0,
                val seriousCount: Int = 0,
                val currentConfirmedIncr: Int = 0,
                val midDangerCount: Int = 0,
                val suspectedIncr: Int = 0,
                val seriousIncr: Int = 0,
                val confirmedIncr: Int = 0,
                @Embedded val globalStatistics: GlobalStatistics,
                val deadIncr: Int = 0,
                val suspectedCount: Int = 0,
                val currentConfirmedCount: Int = 0,
                val confirmedCount: Int = 0,
                val modifyTime: Long = 0,
                val createTime: Long = 0,
                val curedIncr: Int = 0,
                val yesterdaySuspectedCountIncr: Int = 0,
                @Embedded val foreignStatistics: ForeignStatistics,
                val highDangerCount: Int = 0,
                @PrimaryKey var id: Int = 0,
                val deadCount: Int = 0,
                val yesterdayConfirmedCountIncr: Int = 0)

data class ForeignStatistics(
    @ColumnInfo(name = "foreign_currentConfirmedCount")
    val currentConfirmedCount: Int = 0,
    @ColumnInfo(name = "foreign_confirmedCount")
    val confirmedCount: Int = 0,
    @ColumnInfo(name = "foreign_curedCount")
    val curedCount: Int = 0,
    @ColumnInfo(name = "foreign_currentConfirmedIncr")
    val currentConfirmedIncr: Int = 0,
    @ColumnInfo(name = "foreign_suspectedIncr")
    val suspectedIncr: Int = 0,
    @ColumnInfo(name = "foreign_confirmedIncr")
    val confirmedIncr: Int = 0,
    @ColumnInfo(name = "foreign_curedIncr")
    val curedIncr: Int = 0,
    @ColumnInfo(name = "foreign_deadCount")
    val deadCount: Int = 0,
    @ColumnInfo(name = "foreign_deadIncr")
    val deadIncr: Int = 0,
    @ColumnInfo(name = "foreign_suspectedCount")
    val suspectedCount: Int = 0)

data class GlobalStatistics(
    @ColumnInfo(name = "global_currentConfirmedCount")
    val currentConfirmedCount: Int = 0,
    @ColumnInfo(name = "global_confirmedCount")
    val confirmedCount: Int = 0,
    @ColumnInfo(name = "global_curedCount")
    val curedCount: Int = 0,
    @ColumnInfo(name = "global_currentConfirmedIncr")
    val currentConfirmedIncr: Int = 0,
    @ColumnInfo(name = "global_confirmedIncr")
    val confirmedIncr: Int = 0,
    @ColumnInfo(name = "global_curedIncr")
    val curedIncr: Int = 0,
    @ColumnInfo(name = "global_deadCount")
    val deadCount: Int = 0,
    @ColumnInfo(name = "global_deadIncr")
    val deadIncr: Int = 0,
    @ColumnInfo(name = "global_yesterdayConfirmedCountIncr")
    val yesterdayConfirmedCountIncr: Int = 0)

