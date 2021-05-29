package cobyx.gnezdo.hnews.domain.news

import cobyx.gnezdo.hnews.domain.news.model.NewsItem
import io.reactivex.Single

interface NewsRepository {
    fun getRandomTopNewsItem(): Single<NewsItem>
}