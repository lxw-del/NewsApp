package com.permissionx.goodnews.repository

import androidx.compose.ui.unit.Constraints
import com.permissionx.goodnews.network.NetworkRequest
import com.permissionx.goodnews.utils.Constant
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@ViewModelScoped
class HomeRepository @Inject constructor():BaseRepository(){

    /**
     * 获取社会新闻
     */

    fun getSocialNews() = fire(Dispatchers.IO){
        val news = NetworkRequest.getSocialNews()
        if (news.code == Constant.CODE) Result.success(news)
        else Result.failure(java.lang.RuntimeException("get news response code is ${news.code} msg is" +
                news.msg
        ))

    }
}