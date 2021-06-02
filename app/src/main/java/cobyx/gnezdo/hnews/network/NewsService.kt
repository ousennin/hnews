package cobyx.gnezdo.hnews.network

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import androidx.core.app.JobIntentService
import cobyx.gnezdo.hnews.R
import cobyx.gnezdo.hnews.domain.news.NewsRepository
import cobyx.gnezdo.hnews.domain.news.model.NewsItem
import cobyx.gnezdo.hnews.ui.NewsWidget
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class NewsService : JobIntentService() {
    private val repository: NewsRepository by inject()

    companion object {
        private const val JOB_ID = 108
        private const val BROWSER_REQUEST_CODE = 109

        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(context, NewsService::class.java, JOB_ID, intent)
        }
    }

    override fun onHandleWork(intent: Intent) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.getRandomTopNewsItem()
                .retry {
                    delay(15000)
                    true
                }
                .collect {  updateWidget(it) }
        }
    }

    private fun updateWidget(item: NewsItem) {
        val views = RemoteViews(packageName, R.layout.news_widget_layout)
        views.setTextViewText(R.id.title, item.title)

        setBrowserActionOnClick(views, item.url)

        val statsWidget = ComponentName(this@NewsService, NewsWidget::class.java)
        with(AppWidgetManager.getInstance(this@NewsService)) {
            updateAppWidget(statsWidget, views)
        }
    }


    private fun setBrowserActionOnClick(v: RemoteViews, url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        val pIntent = PendingIntent.getActivity(applicationContext, BROWSER_REQUEST_CODE,
            browserIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        v.setOnClickPendingIntent(R.id.title, pIntent)
    }


}