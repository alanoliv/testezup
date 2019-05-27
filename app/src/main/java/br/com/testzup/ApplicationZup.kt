package br.com.testzup

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex

open class ApplicationZup : Application() {
    companion object {
        lateinit var instance: ApplicationZup
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}