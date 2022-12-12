package com.permissionx.goodnews.repository

import android.util.Log
import com.permissionx.goodnews.App
import com.permissionx.goodnews.db.bean.EpidemicNews
import com.permissionx.goodnews.network.NetworkRequest
import com.permissionx.goodnews.utils.Constant
import com.permissionx.goodnews.utils.EasyDataStore
import com.permissionx.goodnews.utils.EasyDate
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


//仓库层提供接口给到ViewModel
//进行依赖注入不能使用单例类,变为class就可以了
@ViewModelScoped
class EpidemicNewsRepository @Inject constructor():BaseRepository() {

    private val TAG = "EpidemicNewsRepository"

    companion object{
        lateinit var epidemicNews: EpidemicNews
    }

    fun getEpidemicNews(isRefresh:Boolean) = fire(Dispatchers.IO){
       //判断是不是刷新并且今天是否是第一次请求网络
        if(!isRefresh && EasyDate.timestamp <= EasyDataStore.getData(Constant.REQUEST_TIMESTAMP,1649049670500)){
           //当前时间未超过次日0点，从本地获取数据库
            Log.d(TAG, "getEpidemicNews: 从数据库中获取")
            epidemicNews = getLocalForNews()
            //Log.d(TAG, "Database data0 is ${App.db.listItemDao().getAll()[0].digest}")
       }else{
            Log.d(TAG, "getEpidemicNews: 从网络中获取")
            epidemicNews = NetworkRequest.getEpidemicNews()
            Log.d(TAG, "getEpidemicNews: ${epidemicNews.result.news?.get(0)?.summary}")
            //保存到本地数据库
            saveNews(epidemicNews)
        }
        Log.d(TAG, " code is  ${epidemicNews.code}")
        if (epidemicNews.code == Constant.CODE) Result.success(epidemicNews)
        else Result.failure(java.lang.RuntimeException("getNews response code is ${epidemicNews.code} msg is ${epidemicNews.msg}"))
    }

    //保存到本地数据库
    private suspend fun saveNews(epidemicNews: EpidemicNews){
        Log.d(TAG, "saveNews: 保存到本地数据库 ${epidemicNews.result.news?.get(0)?.summary}")
        EasyDataStore.putData(Constant.REQUEST_TIMESTAMP,EasyDate.getMillisNextEarlyMorning())
        //App.db.listItemDao().deleteAll()
        //App.db.listItemDao().insertAll(epidemicNews.result.list)
        App.db.listItemDao().insert(epidemicNews.apply { id = 1 })
    }

    //从本地数据库中加载
    private suspend fun getLocalForNews() = App.db.listItemDao().getNews()


}