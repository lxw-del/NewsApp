package com.permissionx.goodnews.network

import com.permissionx.goodnews.db.bean.EpidemicNews
import com.permissionx.goodnews.utils.Constant
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    //获取新闻数据
    @GET("/bulletin/index?key=${Constant.API_KEY}")
    fun getEpidemicNews(): Call<EpidemicNews>

}