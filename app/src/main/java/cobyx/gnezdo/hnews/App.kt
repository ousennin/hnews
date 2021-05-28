package cobyx.gnezdo.hnews

import android.app.Application
import cobyx.gnezdo.hnews.di.networkModule
import cobyx.gnezdo.hnews.di.newsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(
                networkModule,
                newsModule,
            )
        }
    }
}