package com.example.e_commerce

import android.app.Application
import com.example.e_commerce.di.components.AppComponent
import com.example.e_commerce.di.components.DaggerAppComponent
import com.yariksoffice.lingver.Lingver

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
        Lingver.init(this, "az")
    }


    fun getComponent(): AppComponent? {
        return appComponent
    }
}