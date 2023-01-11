package com.permissionx.goodnews.network

import com.permissionx.goodnews.db.bean.EpidemicNews
import com.permissionx.goodnews.db.bean.News
import com.permissionx.goodnews.utils.Constant
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    //获取疫情新闻数据
    @GET("/ncov/index?key=${Constant.API_KEY}&date=2022-01-18")
    fun getEpidemicNews(): Call<EpidemicNews>

    //获取社会新闻信息
    @GET("/social/index?key=${Constant.API_KEY}")
    fun getSocialNews(): Call<News>

    //获取军事新闻信息
    @GET("/military/index?key=${Constant.API_KEY}")
    fun getMilitaryNews(): Call<News>

    //获取科技新闻信息
    @GET("/keji/index?key=${Constant.API_KEY}")
    fun getTechnologyNews(): Call<News>

    //获取财经新闻
    @GET("/caijing/index?key=${Constant.API_KEY}")
    fun getFinanceNews(): Call<News>

    //获取娱乐新闻
    @GET("/huabian/index?key=${Constant.API_KEY}")
    fun getAmusementNews(): Call<News>
}