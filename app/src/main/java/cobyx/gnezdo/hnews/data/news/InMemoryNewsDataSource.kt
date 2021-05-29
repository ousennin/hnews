package cobyx.gnezdo.hnews.data.news

import kotlin.random.Random

class InMemoryNewsDataSource: LocalNewsDataSource {
    private val ids = mutableListOf<Int>()
    override fun getTopNewsIds(): List<Int> = ids.toList()

    override fun saveTopNewsIds(list: List<Int>) {
        ids.clear()
        ids.addAll(list)
    }

    override fun isEmpty(): Boolean = ids.isEmpty()

    override fun isNotEmpty(): Boolean = ids.isNotEmpty()

    override fun getRandomNewsId(): Int {
      if (ids.isNotEmpty()) {
          return ids.removeAt(Random.nextInt(ids.size))
      } else {
          throw NoSuchElementException()
      }
    }


}