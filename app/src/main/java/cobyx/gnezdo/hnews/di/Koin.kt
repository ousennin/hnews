package cobyx.gnezdo.hnews.di

import cobyx.gnezdo.hnews.data.api.news.NewsApi
import cobyx.gnezdo.hnews.data.news.DefaultNewsRepository
import cobyx.gnezdo.hnews.data.news.RemoteNewsDataSource
import cobyx.gnezdo.hnews.data.news.RetrofitNewsDataSource
import cobyx.gnezdo.hnews.domain.news.NewsRepository
import cobyx.gnezdo.hnews.network.HttpClientFactory
import cobyx.gnezdo.hnews.network.RetrofitManager
import com.google.gson.GsonBuilder
import org.koin.dsl.module

private const val BASE_URL = "https://hacker-news.firebaseio.com/v0/"

val networkModule = module {
    single { GsonBuilder().create() }
    single { RetrofitManager(BASE_URL, HttpClientFactory.createHttpClient(), get()) }
}

val newsModule = module {
    single { get<RetrofitManager>().getApi(NewsApi::class.java) }
    single<RemoteNewsDataSource> { RetrofitNewsDataSource(get()) }
    single<NewsRepository> { DefaultNewsRepository(get()) }
}

