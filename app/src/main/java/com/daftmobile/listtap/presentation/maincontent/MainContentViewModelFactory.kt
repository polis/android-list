package com.daftmobile.listtap.presentation.maincontent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.daftmobile.listtap.App

class MainContentViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        MainContentViewModel(
            App.itemRepository
        ) as T
}