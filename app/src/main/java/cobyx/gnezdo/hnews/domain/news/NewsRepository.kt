package cobyx.gnezdo.hnews.domain.news

import cobyx.gnezdo.hnews.domain.news.model.NewsItem
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getRandomTopNewsItem(): Flow<NewsItem>
}