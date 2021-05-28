package cobyx.gnezdo.hnews.data.news

import cobyx.gnezdo.hnews.data.news.dto.toDomain
import cobyx.gnezdo.hnews.domain.news.NewsRepository
import cobyx.gnezdo.hnews.domain.news.model.NewsItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultNewsRepository(
    private val dataSource: RemoteNewsDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : NewsRepository {
    override suspend fun getRandomTopNewsItem(): NewsItem = withContext(dispatcher) {
        val id = dataSource.loadTopNewsIds().random().toString()
        dataSource.loadNewsItem(id).toDomain()
    }

}