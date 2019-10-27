package com.traderx.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.traderx.api.RequestService

class UserViewModelFactory(private val networkSource: RequestService): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(networkSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}