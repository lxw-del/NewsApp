package com.permissionx.goodnews.viewModel

import androidx.lifecycle.ViewModel
import com.permissionx.goodnews.repository.EpidemicNewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

//ViewModel层 需要添加注解@HiltViewModel ，并且添加依赖参数，这里依赖仓库曾接口。
@HiltViewModel
class MainViewModel @Inject constructor(repository:EpidemicNewsRepository):ViewModel(){

    val result = repository.getEpidemicNews()
}