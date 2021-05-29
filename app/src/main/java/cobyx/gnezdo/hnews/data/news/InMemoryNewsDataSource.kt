package cobyx.gnezdo.hnews.data.news

import kotlin.random.Random

class InMemoryNewsDataSource: LocalNewsDataSource {
    private val ids = mutableListOf<Int>()
    override fun getTopNewsIds(): List<Int> = ids.toList()

    override fun saveTopNewsIds(list: List<Int>) {
        ids.clear()
        ids.addAll(list)
    }

    override fun noLocalNews(): Boolean = ids.isEmpty()

    override fun getRandomNewsId(): Int = ids.removeAt(Random.nextInt(ids.size))


}