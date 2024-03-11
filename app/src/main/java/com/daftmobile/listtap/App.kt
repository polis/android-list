package com.daftmobile.listtap

import android.app.Application
import com.daftmobile.listtap.data.ElementRepository
import com.daftmobile.listtap.data.ElementRepositoryImpl

class App : Application() {
    companion object {
        // This is a workaround for DI
        // By the description to the task we can't change the plugin section in build.gradle to add ksp or kapt plugin that is needed for DI libs like Hilt or Koin
        lateinit var itemRepository: ElementRepository
    }

    override fun onCreate() {
        super.onCreate()
        itemRepository = ElementRepositoryImpl()
    }
}