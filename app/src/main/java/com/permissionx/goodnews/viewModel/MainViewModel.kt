package com.permissionx.goodnews.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.permissionx.goodnews.repository.EpidemicNewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

//ViewModel层 需要添加注解@HiltViewModel ，并且添加依赖参数，这里依赖仓库曾接口。
@HiltViewModel
class MainViewModel @Inject constructor(repository:EpidemicNewsRepository):ViewModel(){

    private val isRefresh = MutableLiveData<Boolean>()

    val result = Transformations.switchMap(isRefresh){
        repository.getEpidemicNews(it)
    }

    fun getNews(refresh:Boolean){
        isRefresh.value = refresh
    }
}