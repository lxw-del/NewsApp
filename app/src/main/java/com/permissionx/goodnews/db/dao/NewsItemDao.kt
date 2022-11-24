package com.permissionx.goodnews.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.permissionx.goodnews.db.bean.EpidemicNews
import com.permissionx.goodnews.db.bean.ListItem

@Dao
interface NewsItemDao {

    /*@Query("SELECT * FROM ListItem")
    suspend fun getAll():List<ListItem>*/

    /*@Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list:List<ListItem>?)*/

   /* @Query("DELETE FROM ListItem")
    suspend fun deleteAll()*/



    @Query("SELECT * FROM `epidemicnews` WHERE id LIKE :id LIMIT 1")
    suspend fun getNews(id: Int = 1): EpidemicNews

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(desc: EpidemicNews?)
}