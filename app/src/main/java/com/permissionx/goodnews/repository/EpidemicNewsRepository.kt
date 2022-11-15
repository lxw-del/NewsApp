package com.permissionx.goodnews.repository

import com.permissionx.goodnews.network.NetworkRequest
import com.permissionx.goodnews.utils.Constant
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


//仓库层提供接口给到ViewModel
//进行依赖注入不能使用单例类,变为class就可以了
@ViewModelScoped
class EpidemicNewsRepository @Inject constructor():BaseRepository() {

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