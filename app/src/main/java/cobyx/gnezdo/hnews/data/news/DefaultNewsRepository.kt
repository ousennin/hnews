package cobyx.gnezdo.hnews.data.news

import cobyx.gnezdo.hnews.data.news.dto.toDomain
import cobyx.gnezdo.hnews.domain.news.NewsRepository
import cobyx.gnezdo.hnews.domain.news.model.NewsItem
import io.reactivex.Single
import kotlin.random.Random

class DefaultNewsRepository(
    private val remoteNewsDataSource: RemoteNewsDataSource,
    private val localNewsDataSource: LocalNewsDataSource,

) : NewsRepository {
    override fun getRandomTopNewsItem(): Single<NewsItem> {
        if (localNewsDataSource.isNotEmpty())
            return remoteNewsDataSource.loadNewsItem(localNewsDataSource.getRandomNewsId())
                .map { it.toDomain() }

        return remoteNewsDataSource.loadTopNewsIds()
            .filter { it.isNotEmpty() }
            .doOnSuccess {
                it?.let(localNewsDataSource::saveTopNewsIds)
            }
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