package com.permissionx.goodnews.repository

import com.permissionx.goodnews.network.NetworkRequest
import com.permissionx.goodnews.utils.Constant
import kotlinx.coroutines.Dispatchers


//仓库层提供接口给到ViewModel
object EpidemicNewsRepository:BaseRepository() {

    fun getEpidemicNews() = fire(Dispatchers.IO){
        val epidemicNews = NetworkRequest.getEpidemicNews()
        if (epidemicNews.code == Constant.CODE){
            Result.success(epidemicNews)
        }else{
            Result.failure(java.lang.RuntimeException("getNews response code is ${epidemicNews.code}" +
                    "msg is ${epidemicNews.msg}"))
        }
    }
}