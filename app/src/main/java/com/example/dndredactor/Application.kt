package com.example.dndredactor

import android.app.Application
import com.example.dndredactor.di.AppContainer

class DndApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}