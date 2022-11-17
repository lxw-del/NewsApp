package com.permissionx.goodnews.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.permissionx.goodnews.db.bean.ListItem

@Dao
interface NewsItemDao {

    @Query("SELECT * FROM ListItem")
    fun getAll():List<ListItem>

    @Insert
    fun insertAll(list:List<ListItem>?)

    @Query("DELETE FROM ListItem")
    fun deleteAll()

}