package cobyx.gnezdo.hnews.data.news

import cobyx.gnezdo.hnews.data.api.news.NewsApi
import cobyx.gnezdo.hnews.data.news.dto.NewsItemDto

class RetrofitNewsDataSource(
    private val api: NewsApi
): RemoteNewsDataSource {
    override suspend fun loadTopNewsIds(): List<Int> {
        return api.loadTopNewsIds()
    }

    override suspend fun loadNewsItem(id: Int): NewsItemDto {
        return api.loadNewsItem(id)
    }
}