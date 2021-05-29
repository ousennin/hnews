package cobyx.gnezdo.hnews.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object HttpClientFactory {

    fun createHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor()
                .apply { level = HttpLoggingInterceptor.Level.BODY })
            .build()
    }
}