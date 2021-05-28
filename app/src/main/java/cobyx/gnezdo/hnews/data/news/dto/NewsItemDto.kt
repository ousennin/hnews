package cobyx.gnezdo.hnews.data.news.dto

import cobyx.gnezdo.hnews.domain.news.model.NewsItem

data class NewsItemDto(
    val title: String?,
    val url: String?,
)

fun NewsItemDto.toDomain() = NewsItem(
    title = title.orEmpty(),
    url = url.orEmpty(),
)