package com.permissionx.goodnews

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import kotlin.coroutines.CoroutineContext
//获取全局的context
class App:Application() {

    companion object{
        //忽略全局变量有可能造成的内存泄漏问题
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}