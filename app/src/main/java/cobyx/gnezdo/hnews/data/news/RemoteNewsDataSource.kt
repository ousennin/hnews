package cobyx.gnezdo.hnews.data.news

import cobyx.gnezdo.hnews.data.news.dto.NewsItemDto

interface RemoteNewsDataSource {
    suspend fun loadTopNewsIds(): List<Int>
    suspend fun loadNewsItem(id: String): NewsItemDto
}