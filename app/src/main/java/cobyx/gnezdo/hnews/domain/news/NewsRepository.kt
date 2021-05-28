package cobyx.gnezdo.hnews.domain.news

import cobyx.gnezdo.hnews.domain.news.model.NewsItem

interface NewsRepository {
    suspend fun getRandomTopNewsItem(): NewsItem
}