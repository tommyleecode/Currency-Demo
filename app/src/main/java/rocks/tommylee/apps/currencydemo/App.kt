package rocks.tommylee.apps.currencydemo

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import rocks.tommylee.apps.currencydemo.di.allModules
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        setupKoin()
        plantTree()
    }

    private fun setupKoin() {
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@App)
            modules(allModules)
        }
    }

    private fun plantTree() {
        Timber.plant(Timber.DebugTree())
    }
}