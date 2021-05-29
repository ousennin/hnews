package cobyx.gnezdo.hnews.data.api.news

import cobyx.gnezdo.hnews.data.news.dto.NewsItemDto
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsApi {
    @GET("topstories.json")
    suspend fun loadTopNewsIds(): List<Int>

    @GET("item/{id}.json")
    suspend fun loadNewsItem(
        @Path(value = "id", encoded = true) id: String
    ): NewsItemDto
}