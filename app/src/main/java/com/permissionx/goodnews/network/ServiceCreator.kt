package com.permissionx.goodnews.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//
object ServiceCreator {

    private const val baseUrl = "https://apis.tianapi.com"

    private fun getRetrofit():Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    fun<T> create(serviceClass:Class<T>):T = getRetrofit().create(serviceClass)

    inline fun <reified T> create():T = create(T::class.java)

}