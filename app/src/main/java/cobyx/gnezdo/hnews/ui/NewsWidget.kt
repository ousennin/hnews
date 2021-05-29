package cobyx.gnezdo.hnews.ui

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import cobyx.gnezdo.hnews.R
import cobyx.gnezdo.hnews.network.NewsService


class NewsWidget : AppWidgetProvider() {
    companion object {
        private const val UPDATE_ACTION = "android.appwidget.action.APPWIDGET_UPDATE"
    }

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        val updateIntent = Intent(UPDATE_ACTION)
        val updatePendingIntent =
            PendingIntent.getBroadcast(context, 0, updateIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        appWidgetIds?.forEach { id ->
            val views = RemoteViews(context?.packageName, R.layout.news_widget_layout)
            views.setOnClickPendingIntent(R.id.refresh_btn, updatePendingIntent)
            appWidgetManager?.updateAppWidget(id, views)
        }


        val intent = Intent(context, NewsService::class.java)
        context?.let { NewsService.enqueueWork(it, intent) }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (intent?.action == UPDATE_ACTION) {
            val updateIntent = Intent(context, NewsService::class.java)
            context?.let { NewsService.enqueueWork(it, updateIntent) }
        }
    }
}