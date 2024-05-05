package com.example.e_commerce

import android.app.Application
import com.example.e_commerce.di.components.AppComponent
import com.example.e_commerce.di.components.DaggerAppComponent

class MainApplication: Application() {

    private var appComponent: AppComponent? = null

    companion object {
        private var app: MainApplication? = null

        fun getApp(): MainApplication? {
            return app
        }
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        appComponent = DaggerAppComponent.factory().create(this)
    }


    fun getComponent(): AppComponent? {
        return appComponent
    }
}