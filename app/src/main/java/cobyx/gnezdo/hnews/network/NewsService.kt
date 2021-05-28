package cobyx.gnezdo.hnews.network

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.app.JobIntentService
import cobyx.gnezdo.hnews.R
import cobyx.gnezdo.hnews.domain.news.NewsRepository
import cobyx.gnezdo.hnews.ui.NewsWidget
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class NewsService : JobIntentService() {
    private val repository: NewsRepository by inject()

    companion object {
        private const val JOB_ID = 108

        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(context, NewsService::class.java, JOB_ID, intent)
        }
    }
    override fun onHandleWork(intent: Intent) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val item = repository.getRandomTopNewsItem()
                val views = RemoteViews(packageName, R.layout.news_widget_layout)
                views.setTextViewText(R.id.title, item.title)

                val statsWidget = ComponentName(this@NewsService, NewsWidget::class.java)
                with(AppWidgetManager.getInstance(this@NewsService)) {
                    updateAppWidget(statsWidget, views)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}