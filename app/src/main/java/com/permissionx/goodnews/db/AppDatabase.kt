package com.permissionx.goodnews.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.permissionx.goodnews.db.bean.ListItem
import com.permissionx.goodnews.db.dao.NewsItemDao


@Database(entities = [ListItem::class], version = 1, exportSchema = false)
abstract class AppDatabase:RoomDatabase() {
    abstract fun listItemDao():NewsItemDao

    companion object{

        @Volatile
        //任何一个线程对它进行修改，都会让所有其他CPU高速缓存中的值过期，这样其他线程就必须去内存中重新获取最新的值，也就解决了可见性的问题。
        private var instance:AppDatabase? = null

        private const val DATABASE_NAME = "good_news.db"


        fun getInstance(context: Context):AppDatabase{
            //instance 初始化，在同步代码块中执行 also是不可改变it的，只能将值赋给别人或者打印。
            return instance ?: synchronized(this){
                instance ?: Room.databaseBuilder(context,AppDatabase::class.java, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build().also { instance = it }
            }
        }
    }
}