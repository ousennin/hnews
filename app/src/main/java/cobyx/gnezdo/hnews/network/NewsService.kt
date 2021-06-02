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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

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
        repository.getRandomTopNewsItem()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .retryWhen { e ->
                e.delay(15, TimeUnit.SECONDS)
            }
            .subscribe(object : DisposableSingleObserver<NewsItem>() {
                override fun onSuccess(item: NewsItem) {
                    val views = RemoteViews(packageName, R.layout.news_widget_layout)
                    views.setTextViewText(R.id.title, item.title)

                    setBrowserActionOnClick(views, item.url)

                    val statsWidget = ComponentName(this@NewsService, NewsWidget::class.java)
                    with(AppWidgetManager.getInstance(this@NewsService)) {
                        updateAppWidget(statsWidget, views)
                    }
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }

            })

    }

    private fun setBrowserActionOnClick(v: RemoteViews, url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        val pIntent = PendingIntent.getActivity(
            applicationContext, BROWSER_REQUEST_CODE,
            browserIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        v.setOnClickPendingIntent(R.id.title, pIntent)
    }


}