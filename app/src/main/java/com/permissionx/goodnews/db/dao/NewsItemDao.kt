package com.permissionx.goodnews.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.permissionx.goodnews.db.bean.EpidemicNews

@Dao
interface NewsItemDao {

    /*@Query("SELECT * FROM ListItem")
    suspend fun getAll():List<ListItem>*/

    /*@Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list:List<ListItem>?)*/

   /* @Query("DELETE FROM ListItem")
    suspend fun deleteAll()*/

    // ``这个符号目前不知道为什么要这么用，但是不这样写会报错。，这里只取表中的第一个epidemicnews数据
    @Query("SELECT * FROM `epidemicnews` WHERE id LIKE :id LIMIT 1")
    suspend fun getNews(id: Int = 1): EpidemicNews

    //Insert 设置了onConflict 设置为REPLACE 会将冲突的地方删除或者替换掉。
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(desc: EpidemicNews?)
}


