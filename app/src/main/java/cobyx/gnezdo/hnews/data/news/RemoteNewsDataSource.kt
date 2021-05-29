package cobyx.gnezdo.hnews.data.news

import cobyx.gnezdo.hnews.data.news.dto.NewsItemDto
import io.reactivex.Single

interface RemoteNewsDataSource {
    fun loadTopNewsIds(): Single<List<Int>>
    fun loadNewsItem(id: Int): Single<NewsItemDto>
}