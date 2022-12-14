package com.permissionx.goodnews

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.permissionx.goodnews.db.AppDatabase
import dagger.hilt.android.HiltAndroidApp
import kotlin.coroutines.CoroutineContext
//获取全局的context
@HiltAndroidApp
class App:Application() {

    companion object{
        //忽略全局变量有可能造成的内存泄漏问题
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        lateinit var db: AppDatabase

        @SuppressLint("StaticFieldLeak")
        lateinit var instance:App
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        db = AppDatabase.getInstance(context)
        instance = this
    }
}