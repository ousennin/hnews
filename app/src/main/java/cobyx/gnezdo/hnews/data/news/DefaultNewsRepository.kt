package cobyx.gnezdo.hnews.data.news

import cobyx.gnezdo.hnews.data.news.dto.toDomain
import cobyx.gnezdo.hnews.domain.news.NewsRepository
import cobyx.gnezdo.hnews.domain.news.model.NewsItem
import io.reactivex.Single
import kotlin.random.Random

class DefaultNewsRepository(
    private val remoteNewsDataSource: RemoteNewsDataSource,

) : NewsRepository {
    override fun getRandomTopNewsItem(): Single<NewsItem> {
        return remoteNewsDataSource.loadTopNewsIds()
            .filter { it.isNotEmpty() }
            .cache()
            .flatMapSingle {
                loadRandomTopNewsItem(it)
            }
    }

    private fun loadRandomTopNewsItem(list: List<Int>): Single<NewsItem> {
        return remoteNewsDataSource
            .loadNewsItem(list[Random.nextInt(list.size)])
            .map { it.toDomain() }
    }
}