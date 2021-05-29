package cobyx.gnezdo.hnews.data.api.news

import cobyx.gnezdo.hnews.data.news.dto.NewsItemDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsApi {
    @GET("topstories.json")
    fun loadTopNewsIds(): Single<List<Int>>

    @GET("item/{id}.json")
    fun loadNewsItem(
        @Path(value = "id", encoded = true) id: Int
    ): Single<NewsItemDto>
}