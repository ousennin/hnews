package cobyx.gnezdo.hnews.data.news

import cobyx.gnezdo.hnews.data.news.dto.toDomain
import cobyx.gnezdo.hnews.domain.news.NewsRepository
import cobyx.gnezdo.hnews.domain.news.model.NewsItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DefaultNewsRepository(
    private val remoteNewsDataSource: RemoteNewsDataSource,
    private val localNewsDataSource: LocalNewsDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : NewsRepository {
    override fun getRandomTopNewsItem(): Flow<NewsItem> = flow {
        if (localNewsDataSource.noLocalNews())
            remoteNewsDataSource.loadTopNewsIds()
                .let(localNewsDataSource::saveTopNewsIds)

        val id = localNewsDataSource.getRandomNewsId()
        emit(remoteNewsDataSource.loadNewsItem(id).toDomain())
    }.flowOn(dispatcher)

}