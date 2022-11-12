package com.permissionx.goodnews.repository

import androidx.lifecycle.liveData
import kotlin.coroutines.CoroutineContext

open class BaseRepository {

    fun<T> fire(context:CoroutineContext,block:suspend ()->Result<T>)=
        //livedata要传入block的返回值类型的
        liveData<Result<T>>(context) {
            val result = try {
                block()
            }catch (e:Exception){
                //这里注意需要一个泛型T要于高阶函数block的返回值T的类型相同。
                Result.failure<T>(e)
            }
            //通知数据变化
            emit(result)
        }
}