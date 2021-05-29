package cobyx.gnezdo.hnews.data.news

import cobyx.gnezdo.hnews.data.news.dto.toDomain
import cobyx.gnezdo.hnews.domain.news.NewsRepository
import cobyx.gnezdo.hnews.domain.news.model.NewsItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultNewsRepository(
    private val remoteNewsDataSource: RemoteNewsDataSource,
    private val localNewsDataSource: LocalNewsDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : NewsRepository {
    override suspend fun getRandomTopNewsItem(): NewsItem = withContext(dispatcher) {
        if (localNewsDataSource.noLocalNews())
            remoteNewsDataSource.loadTopNewsIds()
                .let(localNewsDataSource::saveTopNewsIds)

        val id = localNewsDataSource.getRandomNewsId()
        remoteNewsDataSource.loadNewsItem(id).toDomain()
    }

}