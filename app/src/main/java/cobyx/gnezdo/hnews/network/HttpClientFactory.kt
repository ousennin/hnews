package cobyx.gnezdo.hnews.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig

object HttpClientFactory {

    fun createHttpClient(
        vararg interceptors: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptors(listOfNotNull(getLogger()) + interceptors)
            .build()
    }

    private fun getLogger(): HttpLoggingInterceptor? {
        return HttpLoggingInterceptor()
            .takeIf { BuildConfig.DEBUG }
            ?.apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    private fun OkHttpClient.Builder.addInterceptors(interceptors: Iterable<Interceptor>) : OkHttpClient.Builder {
        interceptors.forEach { addInterceptor(it) }
        return this
    }
}