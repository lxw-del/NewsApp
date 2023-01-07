package com.permissionx.goodnews.viewModel

import androidx.lifecycle.ViewModel
import com.permissionx.goodnews.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(repository: HomeRepository):ViewModel() {

    val result = repository.getSocialNews()
}