package com.permissionx.goodnews.db.dao

import androidx.room.*
import com.permissionx.goodnews.db.bean.Desc

@Dao
interface DescDao {

    @Query("SELECT * FROM `desc` WHERE id LIKE :id LIMIT 1")
    suspend fun getDesc(id:Int = 1):Desc

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(desc: Desc?)

    @Query("DELETE FROM `desc`")
    suspend fun deleteAll()
}