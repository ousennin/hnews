package cobyx.gnezdo.hnews.data.news

interface LocalNewsDataSource {
    fun getTopNewsIds(): List<Int>
    fun saveTopNewsIds(list: List<Int>)
    fun noLocalNews(): Boolean
    fun getRandomNewsId(): Int
}