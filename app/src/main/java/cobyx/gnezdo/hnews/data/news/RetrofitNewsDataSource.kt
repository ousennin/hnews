package cobyx.gnezdo.hnews.data.news

import cobyx.gnezdo.hnews.data.api.news.NewsApi
import cobyx.gnezdo.hnews.data.news.dto.NewsItemDto
import io.reactivex.Single

class RetrofitNewsDataSource(
    private val api: NewsApi
): RemoteNewsDataSource {
    override fun loadTopNewsIds(): Single<List<Int>> {
        return api.loadTopNewsIds()
    }

    override  fun loadNewsItem(id: Int): Single<NewsItemDto> {
        return api.loadNewsItem(id)
    }
}