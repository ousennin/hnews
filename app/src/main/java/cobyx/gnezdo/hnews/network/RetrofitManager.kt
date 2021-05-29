package cobyx.gnezdo.hnews.network

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager(baseUrl: String, httpClient: OkHttpClient, gson: Gson) {

    private val retrofit = retrofitBuilder(baseUrl, httpClient, gson).build()

    private fun retrofitBuilder(baseUrl: String, httpClient: OkHttpClient, gson: Gson) = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(httpClient)

    fun <T> getApi(apiClass: Class<T>): T {
        return retrofit.create(apiClass)
    }
}